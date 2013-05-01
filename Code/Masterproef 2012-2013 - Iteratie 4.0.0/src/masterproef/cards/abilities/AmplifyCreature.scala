package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import masterproef.counters.DamageHealthCounter

class AmplifyCreature(val parent: Creature) extends AbilityCreature(parent) {

	var currentCount = 0
	counters += (new DamageHealthCounter(1, 1), currentCount)
	
	override def startTurn {
		counters -= (new DamageHealthCounter(1, 1), currentCount)
		currentCount = owner.hand.count(_.isInstanceOf[Creature])
		counters += (new DamageHealthCounter(1, 1), currentCount)
		parent.startTurn
	}
}