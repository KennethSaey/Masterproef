package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import masterproef.counters.DamageHealthCounter

class WitherCreature(val parent: Creature) extends AbilityCreature(parent) {
	
	override def doDamageTo(other: Creature): Int = {
		other.counters += (new DamageHealthCounter(-1, -1), damage)
		0
	}

}