package masterproef.actions

import masterproef.Main

class GameAction(val name: String) {

	def execute(): Unit = {
		Main.addLine("<font color=\"blue\"><i>" + name + "</i></font>")
	}
}