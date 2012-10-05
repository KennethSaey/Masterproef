package masterproef.game.phase

import scala.collection.mutable.ArrayBuffer
import masterproef.game.Game
import masterproef.game.action.GameAction
import masterproef.game.action.PlayCardAction
import masterproef.game.action.EndPhaseAction

class PlayCardPhase extends Phase {

	def actionsAvailable(): Boolean = {
		Game.players(Game.currentPlayer).hasCardsIn(Game.players(Game.currentPlayer).hand);
	}

	def getAvailableActions(): ArrayBuffer[GameAction] = {
		val actions = new ArrayBuffer[GameAction]();
		if(Game.players(Game.currentPlayer).hasCardsIn(Game.players(Game.currentPlayer).hand)){
			actions += new PlayCardAction();
		}
		actions += new EndPhaseAction();
		actions;
	}
}