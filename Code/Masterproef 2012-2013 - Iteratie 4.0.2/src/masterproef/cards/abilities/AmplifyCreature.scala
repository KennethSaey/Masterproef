package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import masterproef.counters.DamageHealthCounter
import masterproef.StartTurnEvent

class AmplifyCreature(val parent: Creature) extends AbilityCreature(parent) {

	reactions += {
		case StartTurnEvent(player) => {
			if (player == owner) {
				counters -= (new DamageHealthCounter(1, 1), currentCount)
				currentCount = owner.hand.count(_.isInstanceOf[Creature])
				counters += (new DamageHealthCounter(1, 1), currentCount)
			}
		}
	}

	var currentCount = 0
	counters += (new DamageHealthCounter(1, 1), currentCount)

	//	override def startTurn {
	//		counters -= (new DamageHealthCounter(1, 1), currentCount)
	//		currentCount = owner.hand.count(_.isInstanceOf[Creature])
	//		counters += (new DamageHealthCounter(1, 1), currentCount)
	//		parent.startTurn
	//	}
}