package masterproef.events

import masterproef.players.Player

case class PlayCardPhaseStartedEvent(currentPlayer: Player) extends PhaseStartedEvent(currentPlayer)