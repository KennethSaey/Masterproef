package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import scala.collection.mutable.ArrayBuffer
import masterproef.counters.DamageHealthCounter

class FlankingCreature(val parent: Creature) extends AbilityCreature(parent) {

	val blockers: ArrayBuffer[Creature] = new ArrayBuffer[Creature]()
	
	override def doDamageTo(other: Creature): Int = {
		if(!other.hasAbility(this.getClass())) {
			blockers += other
			other.counters += new DamageHealthCounter(-1, -1)
		}
		parent.doDamageTo(other)
	}
	
	override def endTurn {
		for(creature <- blockers) {
			creature.counters -= new DamageHealthCounter(-1, -1)
		}
		parent.endTurn
	}
}