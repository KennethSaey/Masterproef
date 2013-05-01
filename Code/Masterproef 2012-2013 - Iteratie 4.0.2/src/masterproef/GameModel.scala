package masterproef

import masterproef.players.Player
import scala.collection.mutable.ArrayBuffer
import masterproef.cards.Deck
import scala.collection.mutable.HashMap
import masterproef.cards.Card
import scala.collection.mutable.Stack
import masterproef.cards.Sorcery
import scala.swing.Publisher
import scala.swing.event.Event

case class GameEvent extends Event
case class StartTurnEvent(player: Player) extends GameEvent
case class EndTurnEvent(player: Player) extends GameEvent
case class StartAttackEvent extends GameEvent
case class EndAttackEvent extends GameEvent
case class PlayEvent(card: Card) extends GameEvent

class GameModel(val game: Masterproef) extends Publisher {

	val players: ArrayBuffer[Player] = new ArrayBuffer
	var playerIndex: Int = 0

	val gamePhases: Array[Int] = Array(Masterproef.DRAW_CARD_ID, Masterproef.PLAY_CARD_ID, Masterproef.DECLARE_ATTACK_ID, Masterproef.DECLARE_BLOCK_ID, Masterproef.ATTACK_ID)
	var currentPhase: Int = 0

	private var _message: String = ""
	var newMessage: Boolean = false

	var attackers: ArrayBuffer[Card] = new ArrayBuffer[Card]
	var blockers: HashMap[Card, ArrayBuffer[Card]] = new HashMap[Card, ArrayBuffer[Card]]
	var sorceries: Stack[Sorcery] = new Stack[Sorcery]
	var zoom: Card = null
	var beforeZoomState: Int = -1
	
	var winner: Player = null

	def player: Player = players(playerIndex)
	def opponent: Player = players((playerIndex + 1) % 2)

	def addPlayer(player: Player) = {
		if (players.size < 2) {
			players += player
		}
	}

	def startNextTurn = {
//		player.endTurn
		publish(new EndTurnEvent(player))
		playerIndex = (playerIndex + 1) % 2
//		player.startTurn
		publish(new StartTurnEvent(player))
		currentPhase = 0
		game.enterState(gamePhases(currentPhase))
	}

	def nextPhase = {
		if (currentPhase == gamePhases.size - 1) {
			startNextTurn
		} else {
			if(gamePhases(currentPhase) == Masterproef.ATTACK_ID) publish(new EndAttackEvent)
			currentPhase += 1
			if(gamePhases(currentPhase) == Masterproef.ATTACK_ID) publish(new StartAttackEvent)
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
	
	def gameOver(winner: Player) {
		this.winner = winner
		game.enterState(Masterproef.GAME_OVER_ID)
	}
}