package masterproef.events

import masterproef.players.Player

case class AttackPhaseStartedEvent(currentPlayer: Player) extends PhaseStartedEvent(currentPlayer)