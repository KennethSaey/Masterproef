package masterproef.game.action

import masterproef.game.Game

class DrawCardAction extends GameAction {

	def execute(): Unit = {
		Game.currentPlayer.draw()
	}
}