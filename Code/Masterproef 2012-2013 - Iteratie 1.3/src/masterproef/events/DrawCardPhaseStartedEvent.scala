package masterproef.events

import masterproef.players.Player

case class DrawCardPhaseStartedEvent(currentPlayer: Player) extends PhaseStartedEvent(currentPlayer)