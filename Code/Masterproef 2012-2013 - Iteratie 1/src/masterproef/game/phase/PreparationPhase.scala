package masterproef.game.phase

import scala.collection.mutable.ArrayBuffer
import masterproef.game.action.GameAction

class PreparationPhase() extends Phase("Preparation Phase") {
	
	subPhases += new DrawCardPhase();
	subPhases += new PlayCardPhase();

	def actionsAvailable(): Boolean = { false }

	def getAvailableActions(): ArrayBuffer[GameAction] = { null }

}