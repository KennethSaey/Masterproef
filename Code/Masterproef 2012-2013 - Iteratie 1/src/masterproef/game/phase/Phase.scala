package masterproef.game.phase

import scala.collection.mutable.ArrayBuffer
import scala.swing.Publisher
import masterproef.game.action.GameAction
import masterproef.event.PhaseStarted
import masterproef.event.PhaseFinished

/**
 * Model for a game phase. A game phase can be optional or required.
 * Sub-phases must be executed in correct order.
 * Game actions can be executed in any order.s
 */
abstract class Phase(name: String = "Unnamed Phase") extends Publisher {
	var subPhases: ArrayBuffer[Phase] = new ArrayBuffer();
	var currentPhase: Int = 0;
	
	def start(): Unit = {
		publish(new PhaseStarted());
	}

	def finish(): Unit = {
		publish(new PhaseFinished());
	}
	
	def actionsAvailable(): Boolean;
	def getAvailableActions(): ArrayBuffer[GameAction];
}