package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import masterproef.players.Player
import masterproef.Masterproef
import masterproef.counters.DamageHealthCounter
import masterproef.EndTurnEvent

class BattlecryCreature(val parent: Creature) extends AbilityCreature(parent) {

	reactions += {
		case EndTurnEvent(player) => {
			if (player == owner && battlecryUsed) {
				Masterproef.model.attackers.foreach(card => {
					if (card != this) {
						card.asInstanceOf[Creature].counters -= new DamageHealthCounter(1, 0)
					}
				})
				battlecryUsed = false
			}
		}
	}

	var battlecryUsed: Boolean = false

	override def doDamageTo(other: Creature): Int = {
		Masterproef.model.attackers.foreach(card => {
			if (card != this) {
				card.asInstanceOf[Creature].counters += new DamageHealthCounter(1, 0)
			}
		})
		battlecryUsed = true
		parent.doDamageTo(other)
	}

	override def doDamageTo(player: Player, damage: Int): Int = {
		Masterproef.model.attackers.foreach(card => {
			if (card != this) {
				card.asInstanceOf[Creature].counters += new DamageHealthCounter(1, 0)
			}
		})
		battlecryUsed = true
		parent.doDamageTo(player, damage)
	}

//	override def endTurn {
//		if (battlecryUsed) {
//			Masterproef.model.attackers.foreach(card => {
//				if (card != this) {
//					card.asInstanceOf[Creature].counters -= new DamageHealthCounter(1, 0)
//				}
//			})
//			battlecryUsed = false
//		}
//		parent.endTurn
//	}
}