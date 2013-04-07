package masterproef

import masterproef.players.Player
import scala.collection.mutable.ArrayBuffer
import masterproef.cards.Deck
import scala.collection.mutable.HashMap
import masterproef.cards.Card

class GameModel(val game: Masterproef) {

	val players: ArrayBuffer[Player] = new ArrayBuffer
	var playerIndex: Int = 0

	val gamePhases: Array[Int] = Array(Masterproef.DRAW_CARD_ID, Masterproef.PLAY_CARD_ID, Masterproef.DECLARE_ATTACK_ID, Masterproef.DECLARE_BLOCK_ID, Masterproef.ATTACK_ID)
	var currentPhase: Int = 0

	private var _message: String = ""
	var newMessage: Boolean = false

	var attackers: ArrayBuffer[Card] = new ArrayBuffer[Card]
	var blockers: HashMap[Card, ArrayBuffer[Card]] = new HashMap[Card, ArrayBuffer[Card]]
	
	var winner: Player = null

	def player: Player = players(playerIndex)
	def opponent: Player = players((playerIndex + 1) % 2)

	def addPlayer(player: Player) = {
		if (players.size < 2) {
			players += player
		}
	}

	def startNextTurn = {
		playerIndex = (playerIndex + 1) % 2
		currentPhase = 0
		game.enterState(gamePhases(currentPhase))
	}

	def nextPhase = {
		if (currentPhase == gamePhases.size - 1) {
			startNextTurn
		} else {
			currentPhase += 1
		}
		game.enterState(gamePhases(currentPhase))
	}

	def message_=(message: String) {
		_message = message
		newMessage = true
	}

	def message: String = {
		newMessage = false
		_message
	}
}