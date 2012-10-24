package masterproef.game.phase

import scala.collection.mutable.ArrayBuffer
import masterproef.game.action.DrawCardAction
import masterproef.game.action.GameAction
import masterproef.event.PhaseStarted
import masterproef.event.PhaseFinished
import masterproef.game.Game

class DrawCardPhase(var number: Int = 1) extends Phase("Draw Card Phase") {

	def actionsAvailable(): Boolean = {
		number > 0 && Game.currentPlayer.hasCardsIn(Game.currentPlayer.deck);
	}

	def getAvailableActions(): ArrayBuffer[GameAction] = {
		val actions = new ArrayBuffer[GameAction]();
		actions += new DrawCardAction();
		actions;
	}

}