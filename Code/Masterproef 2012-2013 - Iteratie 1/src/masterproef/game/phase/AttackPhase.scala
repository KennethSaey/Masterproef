package masterproef.game.phase

import scala.collection.mutable.ArrayBuffer
import masterproef.game.Game
import masterproef.game.action.GameAction
import masterproef.game.action.DeclareAttackAction
import masterproef.game.action.EndPhaseAction

class AttackPhase() extends Phase("Attack Phase") {
	
	def actionsAvailable(): Boolean = {
		!Game.currentPlayer.battlefield.activeCards.isEmpty;
	}

	def getAvailableActions(): ArrayBuffer[GameAction] = {
		val actions = new ArrayBuffer[GameAction]();
		if(!Game.currentPlayer.battlefield.activeCards.isEmpty){
			actions += new DeclareAttackAction();
		}
		actions += new EndPhaseAction();
		actions;
	}

}