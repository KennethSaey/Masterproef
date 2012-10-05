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
	var currentPlayer: Int = 0;
	
	def newGame(): Unit = {
		currentPhase = 0;
		currentPlayer = 0;
		for(player <- players){
			player.reset();
		}
		for(phase <- phases){
			listenTo(phase);
			reactions += {
				case PhaseStarted() => println("Phase Started");
				case PhaseFinished() => println("Phase Finished");
			}
		}
		startGame();
	}
	
	def startGame(): Unit = {
		
	}
}