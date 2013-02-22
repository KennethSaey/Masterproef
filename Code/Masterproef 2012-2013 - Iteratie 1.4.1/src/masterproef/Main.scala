package masterproef

import java.awt.Dimension

import scala.swing.Action
import scala.swing.MainFrame
import scala.swing.Menu
import scala.swing.MenuBar
import scala.swing.MenuItem
import scala.swing.SimpleSwingApplication
import scala.swing.event.Key

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel

import javax.swing.KeyStroke
import javax.swing.UIManager
import masterproef.cards.CreatureCard
import masterproef.cards.Deck
import masterproef.views.cards.DeckView

object Main extends SimpleSwingApplication {

	def top = new MainFrame {
		UIManager.setLookAndFeel(new WindowsLookAndFeel)
		title = "Masterproef (c) 2012-2013 Kenneth Saey"
		maximize
		centerOnScreen
		menuBar = new MenuBar
		minimumSize = new Dimension(1218, 870)

		var view: DeckView = null

		val card1 = new CreatureCard("Vengeful Pharaoh", 3, 5)
		val card2 = new CreatureCard("Tormented Soul", 5, 3)
		val card3 = new CreatureCard("Onyx Mage", 2, 2)
		val deck = new Deck

		val gameMenu = new Menu("Game")
		gameMenu.mnemonic = Key.G

		val deckMenu = new Menu("Deck")
		deckMenu.mnemonic = Key.D

		val cardMenu = new Menu("Card")
		cardMenu.mnemonic = Key.C

		val newGameItem = new MenuItem(Action("New Game") {
			deck += card1
//			deck += card2
//			deck += card3
			deck.listenTo(card1)
			view = new DeckView(deck, 20, 20)
			contents = view
			maximize
		})
		newGameItem.action.accelerator = Some(KeyStroke.getKeyStroke("F2"))

		val flipItem = new MenuItem(Action("Flip card") {
			deck(deck.size - 1).flip
		})
		flipItem.action.accelerator = Some(KeyStroke.getKeyStroke("ctrl F"))

		val takeDamageItem = new MenuItem(Action("Take damage") {
			deck(deck.size - 1).takeDamage(2)
		})
		takeDamageItem.action.accelerator = Some(KeyStroke.getKeyStroke("ctrl D"))

		val moveItem = new MenuItem(Action("Move") {
			view.position_=(view.xOffset + 10, view.yOffset + 10)
		})
		moveItem.action.accelerator = Some(KeyStroke.getKeyStroke("ctrl M"))

		val toggleHorizontalItem = new MenuItem(Action("Toggle Horizontal") {
			view.horizontal = view.horizontal match {
				case DeckView.Horizontal.Left => DeckView.Horizontal.Right
				case DeckView.Horizontal.Right => DeckView.Horizontal.Left
			}
		})
		toggleHorizontalItem.action.accelerator = Some(KeyStroke.getKeyStroke("ctrl H"))

		val toggleVerticalItem = new MenuItem(Action("Toggle Vertical") {
			view.vertical = view.vertical match {
				case DeckView.Vertical.Up => DeckView.Vertical.Down
				case DeckView.Vertical.Down => DeckView.Vertical.Up
			}
		})
		toggleVerticalItem.action.accelerator = Some(KeyStroke.getKeyStroke("ctrl V"))

		val addCardItem = new MenuItem(Action("Add Card") {
			view.deck += card1
		})
		addCardItem.action.accelerator = Some(KeyStroke.getKeyStroke("ctrl A"))

		val removeCardItem = new MenuItem(Action("Remove Card") {
			view.deck.draw()
		})
		removeCardItem.action.accelerator = Some(KeyStroke.getKeyStroke("ctrl R"))

		val exitItem = new MenuItem(Action("Exit") {
			dispose()
			System.exit(0)
		});
		exitItem.mnemonic = Key.X
		exitItem.action.accelerator = Some(KeyStroke.getKeyStroke("alt F4"))

		gameMenu.contents += newGameItem
		gameMenu.contents += moveItem
		gameMenu.contents += exitItem
		deckMenu.contents += toggleHorizontalItem
		deckMenu.contents += toggleVerticalItem
		deckMenu.contents += addCardItem
		deckMenu.contents += removeCardItem
		cardMenu.contents += flipItem
		cardMenu.contents += takeDamageItem
		menuBar.contents += gameMenu
		menuBar.contents += deckMenu
		menuBar.contents += cardMenu
	}
}