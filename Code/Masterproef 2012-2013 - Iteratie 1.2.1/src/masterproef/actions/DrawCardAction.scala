package masterproef.actions

import masterproef.Game
import masterproef.Main

class DrawCardAction() extends GameAction("Draw Card Action") {

	override def execute(): Unit = {
		super.execute
		val cards = Game.currentPlayer.draw()
		Main.addLine("\tDrawn cards: " + cards)
	}
}