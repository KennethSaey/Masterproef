package masterproef

import scala.io.Source
import scala.swing.MainFrame
import scala.swing.SimpleSwingApplication
import masterproef.cards.Deck
import masterproef.parsers.CardParser
import java.awt.Dimension
import java.awt.Point
import java.awt.Toolkit
import javax.swing.JLabel
import scala.swing.BoxPanel
import javax.swing.JPanel
import scala.swing.Orientation
import scala.swing.Label
import scala.swing.BorderPanel
import masterproef.players.Player
import scala.swing.Dialog
import Dialog.Message
import scala.swing.Swing
import scala.collection.script.Message
import masterproef.players.Player
import scala.swing.MenuItem
import scala.swing.Menu
import scala.swing.MenuBar
import scala.swing.Action
import scala.swing.event.Key
import masterproef.phases.PlayCardPhase

/**
 * How to create multiple identical cards?
 * - Read count from cards.txt and parse?
 * - Create a Card.getCopy() method?
 * 
 */
object Main extends SimpleSwingApplication {

	var gameOutput: Label = new Label("");
	var gameText: String = "";

	def top = new MainFrame {
		title = "Masterproef (c) 2012-2013 Kenneth Saey"
		size = new Dimension(600, 400)
		preferredSize = new Dimension(600, 400)
		location_=(new Point((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 300).toInt, 0))
		contents = new BorderPanel() {
			layout(gameOutput) = BorderPanel.Position.South
		}
		menuBar = new MenuBar;

		val gameMenu = new Menu("Game");
		gameMenu.mnemonic = Key.G;

		val newGameItem = new MenuItem(Action("New Game") {
			game()
//			println(Game.getPhases("PlayCardPhase"))
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
		var allCards = cards
		println("Read all cards...")
		allCards.shuffle
		val cardCount = allCards.size
		val me = new Player("Kenneth", 5)
		me.addToDeck(allCards.draw(cardCount/2))
		println("Created player " + me)
		val opponent = new Player("Lord of the Sith", 5)
		opponent.addToDeck(allCards)
		println("Created player " + opponent)
		Game.addPlayer(me)
		println("Added " + me + " to the game")
		Game.addPlayer(opponent)
		println("Added " + opponent + " to the game")
		Game.start()
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