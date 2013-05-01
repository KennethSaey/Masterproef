package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature

class TrampleCreature(val parent: Creature) extends AbilityCreature(parent) {

	override def doDamageTo(creature: Creature): Int = {
		val attackDamage = TrampleCreature.this.parent.doDamageTo(creature)
		val trampleDamage = damage - attackDamage
//		println("damage " + damage + " - attack " + attackDamage + " = trample " + trampleDamage)
		TrampleCreature.this.parent.doDamageTo(creature.owner, trampleDamage)
	}
}