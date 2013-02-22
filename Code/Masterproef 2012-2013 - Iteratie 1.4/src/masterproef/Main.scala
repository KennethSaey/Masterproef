package masterproef

import java.awt.Dimension
import java.awt.Point
import java.awt.Toolkit

import scala.io.Source
import scala.swing.Action
import scala.swing.BorderPanel
import scala.swing.Dialog
import scala.swing.Dialog.Message
import scala.swing.Label
import scala.swing.MainFrame
import scala.swing.Menu
import scala.swing.MenuBar
import scala.swing.MenuItem
import scala.swing.SimpleSwingApplication
import scala.swing.Swing
import scala.swing.event.Key

import masterproef.cards.Deck
import masterproef.parsers.CardParser
import masterproef.players.Player
import masterproef.views.Gameboard

/**
 * 
 * 
 */
object Main extends SimpleSwingApplication {

	var gameOutput: Label = new Label("");
	var gameText: String = "";

	def top = new MainFrame {
		title = "Masterproef (c) 2012-2013 Kenneth Saey"
		maximize
		centerOnScreen
		menuBar = new MenuBar;
		minimumSize = new Dimension(1218, 870)

		val gameMenu = new Menu("Game");
		gameMenu.mnemonic = Key.G;

		val newGameItem = new MenuItem(Action("New Game") {
			game()
			val gameboard = new Gameboard(this, size.width - 18, size.height - 70)
			Game.gameboard = gameboard
			contents = gameboard
			Game.start
		});

		val exitItem = new MenuItem(Action("Exit") {
			dispose();
			System.exit(0);
		});
		exitItem.mnemonic = Key.X;

		gameMenu.contents += newGameItem;
		gameMenu.contents += exitItem;
		menuBar.contents += gameMenu;
	}

	def game(): Unit = {
		Game.reset
		var allCards = cards
		allCards.shuffle
		val cardCount = allCards.size
		val me = new Player("Kenneth", 5)
		me.addToDeck(allCards.draw(cardCount/2))
		val opponent = new Player("Lord of the Sith", 5)
		opponent.addToDeck(allCards)
		Game.addPlayer(me)
		Game.addPlayer(opponent)
//		Game.start()
	}

	def cards(): Deck = {
		val cards = new Deck
		for (line <- Source.fromFile("src/masterproef/data/cards.txt").getLines) {
			val card = CardParser.parse(line).get
			cards += card.copy
			cards += card.copy
			cards += card.copy
			cards += card.copy
		}
		cards
	}

	def addLine(text: String): Unit = {
//		println("Adding line: " + text);
		gameText += "<br>" + text
		gameOutput.text = "<html>" + gameText + "</html>"
	}

	def clear(): Unit = {
		gameText = ""
		gameOutput.text = ""
	}

	def getUserInput(player: Player, message: String, cancelText: String): String = {
		val input = Dialog.showInput(null, message, player.name + ", what should we do?", Message.Question, Swing.EmptyIcon, Nil, cancelText)
		clear
		input match {
			case Some("exit") => {
				System.exit(0)
				null
			}
			case Some(input) => input
			case None => cancelText
		}
	}
}