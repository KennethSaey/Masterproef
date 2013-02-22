package masterproef.phases

import scala.collection.mutable.ArrayBuffer
import masterproef.actions.GameAction
import masterproef.Main
import scala.swing.Publisher

class GamePhase(val name: String) extends ArrayBuffer[GameAction] with Publisher {

//	var actions: ArrayBuffer[GameAction] = new ArrayBuffer[GameAction]()
	
	def execute(): Unit = {
		Main.addLine("<font color=\"red\"" + name + "</font>")
		foreach(action => action.execute)
	}
}