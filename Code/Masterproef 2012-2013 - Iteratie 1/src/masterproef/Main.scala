package masterproef

import scala.swing.{Action, MainFrame, Menu, MenuBar, MenuItem, SimpleSwingApplication}
import scala.swing.event.Key
import scala.swing.Label
import scala.swing.BoxPanel
import scala.swing.Orientation
import scala.swing.event.ButtonClicked
import java.awt.Dimension
import masterproef.game.Game
import masterproef.game.phase.Phase
import scala.collection.mutable.ArrayBuffer
import masterproef.game.phase.PlayCardPhase
import masterproef.game.phase.DrawCardPhase
import masterproef.game.phase.PreparationPhase
import masterproef.game.phase.AttackPhase

object Main extends SimpleSwingApplication {

  def top = new MainFrame {
    // Initializations
    title = "Masterproef 2012-2013 - Kenneth Saey";
    size = new Dimension(800, 600);
    preferredSize = new Dimension(800, 600);
    minimumSize = new Dimension(800, 600);
    centerOnScreen();
    
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
    
    listenTo(newGameItem);
    reactions += {
      case ButtonClicked(newGameItem) =>
        println("Listener");
    }
    
    val phases: ArrayBuffer[Phase] = new ArrayBuffer();
    phases += new DrawCardPhase
    phases += new PlayCardPhase
    phases += new PreparationPhase
    phases += new AttackPhase
    
    val players: ArrayBuffer[Player] = new ArrayBuffer();
    players += new Player("Me")
    players += new Player("Opponent") 
    
    Game.init(phases, players)
    
    
  }
}