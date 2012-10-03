import scala.swing.{Action, MainFrame, Menu, MenuBar, MenuItem, SimpleSwingApplication}
import scala.swing.event.Key
import scala.swing.Label
import scala.swing.BoxPanel
import scala.swing.Orientation

object Main extends SimpleSwingApplication {

  def top = new MainFrame {
    // Initializations
    title = "Masterproef 2012-2013 - Kenneth Saey";
    maximize();
    
    // Menu
    menuBar = new MenuBar;
    
    val gameMenu = new Menu("Game");
    gameMenu.mnemonic = Key.G;
    
    val newGameItem = new MenuItem(Action("New Game"){
      println("Starting a new game...");
    });
    
    val exitItem = new MenuItem(Action("Exit"){
      dispose();
      System.exit(0);
    });
    exitItem.mnemonic = Key.X;
    
    gameMenu.contents += newGameItem;
    gameMenu.contents += exitItem;
    menuBar.contents += gameMenu;
    
    // Content
    val text1 = new Label{
      text = "Opponent";
    }
    val text2 = new Label {
      text = "Me";
    }
    
    contents = new BoxPanel(Orientation.Vertical){
      contents += text1;
      contents += text2;
    }
  }

}