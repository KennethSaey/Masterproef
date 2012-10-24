package masterproef.game.phase

import scala.collection.mutable.ArrayBuffer
import masterproef.game.Game
import masterproef.game.action.GameAction
import masterproef.game.action.PlayCardAction
import masterproef.game.action.EndPhaseAction

class PlayCardPhase extends Phase {

	def actionsAvailable(): Boolean = {
		Game.currentPlayer.hasCardsIn(Game.currentPlayer.hand);
	}

	def getAvailableActions(): ArrayBuffer[GameAction] = {
		val actions = new ArrayBuffer[GameAction]();
		if(Game.currentPlayer.hasCardsIn(Game.currentPlayer.hand)){
			actions += new PlayCardAction();
		}
		actions += new EndPhaseAction();
		actions;
	}
}