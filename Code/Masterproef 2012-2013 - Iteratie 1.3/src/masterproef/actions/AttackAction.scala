package masterproef.actions

import masterproef.Game
import masterproef.cards.CreatureCard
import masterproef.cards.CardState

class AttackAction extends GameAction("Attack Action") {

	override def execute(): Unit = {
		super.execute
		if (!Game.attackers.isEmpty) {
			for (attacker <- Game.attackers) {
				Game.defenders.get(attacker) match {
					case Some(defenders) => {
						for (defender <- defenders) {
							attacker.asInstanceOf[CreatureCard].attack(defender.asInstanceOf[CreatureCard])
							if (attacker.state == CardState.DEAD) {
								Game.currentPlayer.kill(attacker)
							}
							if (defender.state == CardState.DEAD) {
								Game.currentOpponent.kill(defender)
							}
						}
					}
					case None => {
						Game.currentPlayer.attack(Game.currentOpponent, attacker.asInstanceOf[CreatureCard])
					}
				}
			}
		}
		Game.attackers.clear
		Game.defenders.clear
	}
}