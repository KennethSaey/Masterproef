package masterproef

import scala.collection.mutable.ArrayBuffer
import masterproef.players.Player
import masterproef.phases.GamePhase
import masterproef.cards.Deck
import scala.collection.mutable.HashMap
import masterproef.cards.Card
import masterproef.phases.DrawCardPhase
import masterproef.phases.PlayCardPhase
import masterproef.phases.AttackPhase

object Game {

	var players: ArrayBuffer[Player] = new ArrayBuffer[Player]()
	var currentPlayerIndex: Int = 0
	var phases: ArrayBuffer[GamePhase] = new ArrayBuffer[GamePhase]()
	phases += new DrawCardPhase
	phases += new PlayCardPhase
	phases += new AttackPhase
	var attackers: Deck = new Deck
	var defenders: HashMap[Card, Deck] = new HashMap[Card, Deck]()
	var ended: Boolean = false
	
	def getPhases(phaseName: String): ArrayBuffer[GamePhase] = {
		val result = new ArrayBuffer[GamePhase]
		for(phase <- phases){
			if(phase.getClass().getName().endsWith(phaseName)){
				result += phase
			}
		}
		result
	}
	
	def getPhase(phaseName: String): GamePhase = {
		phases.find(phase => phase.getClass().getName().endsWith(phaseName)) match {
			case Some(value) => value
			case None => null
		}
	}

	def addPlayer(player: Player): Unit = {
		if (!players.contains(player)) {
			players += player
		}
	}

	def currentPlayer(): Player = {
		players(currentPlayerIndex)
	}

	def currentOpponent(): Player = {
		players((currentPlayerIndex + 1) % players.size)
	}

	def start(): Unit = {
		currentPlayerIndex = 0
		ended = false
		gameLoop()
	}

	def gameLoop(): Unit = {
		println("Starting game loop...")
		while (!ended) {
			Main.addLine("<h3><font color=\"green\">It's " + currentPlayer + "s turn to play.</font></h3>")
			for (i <- 0 to phases.size-1 if !ended) {
				phases(i).execute
			}
			if (!ended) {
				currentPlayerIndex += 1
				currentPlayerIndex %= players.size
			}
		}
        Main.addLine("<h3><font color=\"green\">The game has ended.</font></h3>");
        Main.addLine("<font color=\"green\">" + Game.currentPlayer + " wins the game!</font>");
	}

	def end(): Unit = {
		ended = true;
	}
}