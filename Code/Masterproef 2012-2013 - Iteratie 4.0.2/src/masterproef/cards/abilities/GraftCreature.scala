package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import masterproef.cards.Card
import masterproef.counters.DamageHealthCounter
import masterproef.PlayEvent
import masterproef.Masterproef

/**
 * When played, creature has X +1/+1 counters on it. When playing another creature, one +1/+1 counter may be moved from every graft creature to newly played creature
 */
class GraftCreature(val parent: Creature, x: Int) extends AbilityCreature(parent) {

	reactions += {
		case PlayEvent(card) => {
			if (Masterproef.model.player == owner) {
				if (counters.count(_._1 == new DamageHealthCounter(1, 1)) > 0 && card.isInstanceOf[Creature]) {
					counters -= new DamageHealthCounter(1, 1)
					card.asInstanceOf[Creature].counters += new DamageHealthCounter(1, 1)
				}
			}
		}
	}

	counters += (new DamageHealthCounter(1, 1), x)

	override def ability: String = this.getClass().getSimpleName().replace("Creature", "") + "(" + x + ")"
	override def copy(): this.type = new GraftCreature(parent.copy, x).asInstanceOf[this.type]

//	override def onPlayerPlayInBattlefield(card: Card) {
//		if (counters.count(_._1 == new DamageHealthCounter(1, 1)) > 0 && card.isInstanceOf[Creature]) {
//			counters -= new DamageHealthCounter(1, 1)
//			card.asInstanceOf[Creature].counters += new DamageHealthCounter(1, 1)
//		}
//	}
}