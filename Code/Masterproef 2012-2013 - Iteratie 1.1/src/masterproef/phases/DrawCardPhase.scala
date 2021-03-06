package masterproef.phases

import masterproef.actions.DrawCardAction
import masterproef.events.DrawCardPhaseStartedEvent
import scala.swing.Publisher
import masterproef.Game

class DrawCardPhase extends GamePhase("Draw Card Phase") with Publisher {

	this += new DrawCardAction
	
	override def execute(): Unit = {
		publish(new DrawCardPhaseStartedEvent(Game.currentPlayer))
		super.execute
	}
}