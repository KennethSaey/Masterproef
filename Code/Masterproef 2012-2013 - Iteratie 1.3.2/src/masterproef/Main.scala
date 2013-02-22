package masterproef

import scala.swing.Action
import scala.swing.BorderPanel
import scala.swing.Button
import scala.swing.MainFrame
import scala.swing.Menu
import scala.swing.MenuBar
import scala.swing.MenuItem
import scala.swing.SimpleSwingApplication
import scala.swing.event.Key
import masterproef.views.Gameboard
import java.awt.Dimension

object Main extends SimpleSwingApplication {

	def top = new MainFrame {
		title = "Masterproef (c) 2012-2013 Kenneth Saey"
		maximize
		centerOnScreen
		menuBar = new MenuBar;
		minimumSize = new Dimension(1218, 870)

		val gameMenu = new Menu("Game");
		gameMenu.mnemonic = Key.G;

		val newGameItem = new MenuItem(Action("New Game") {
			contents = new Gameboard(this, size.width - 18, size.height - 70)
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
}