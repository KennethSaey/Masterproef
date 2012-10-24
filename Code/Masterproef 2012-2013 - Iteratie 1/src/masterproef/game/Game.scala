package masterproef.game

import scala.collection.mutable.ArrayBuffer
import scala.swing.Reactor
import masterproef.event.{PhaseFinished, PhaseStarted}
import masterproef.Player
import masterproef.game.phase.Phase

object Game extends Reactor {

	var phases: ArrayBuffer[Phase] = new ArrayBuffer();
	var currentPhase: Int = 0;
	var players: ArrayBuffer[Player] = new ArrayBuffer();
	var currentPlayerIndex: Int = 0;
	
	def init(phases: ArrayBuffer[Phase], players: ArrayBuffer[Player]): Unit = {
		this.phases = phases
		this.players = players
		for(phase <- phases){
			listenTo(phase);
			reactions += {
				case PhaseStarted() => println("Phase Started");
				case PhaseFinished() => println("Phase Finished");
			}
		}
	}
	
	def currentPlayer(): Player = {
		players(currentPlayerIndex)
	}
	
	def newGame(): Unit = {
		currentPhase = 0;
		currentPlayerIndex = 0;
		for(player <- players){
			player.reset();
		}
		
		startGame();
	}
	
	def startGame(): Unit = {
		
	}
	
	def playRound(): Unit = {
		for(phase <- phases){
			phase.start
			if(phase.actionsAvailable){
				val actions = phase.getAvailableActions
				if(actions.size == 1){
					actions(0).execute
				}
			}
		}
	}
}