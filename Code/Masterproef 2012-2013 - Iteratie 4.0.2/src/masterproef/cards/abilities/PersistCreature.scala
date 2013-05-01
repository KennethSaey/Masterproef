package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import masterproef.counters.DamageHealthCounter

class PersistCreature(val parent: Creature) extends AbilityCreature(parent) {

	override def takeDamageFrom(other: Creature): Int = {
		val actualDamage = parent.takeDamageFrom(other)
		if (health <= 0 && !counters.contains(new DamageHealthCounter(-1, -1))) {
			counters += new DamageHealthCounter(-1, -1)
//			println("inside health: " + health)
			resetDamage
			resetHealth
		}
//		println("outside health " + health)
		actualDamage
	}
}