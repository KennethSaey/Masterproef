package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature

object Lifelink extends Ability


class LifelinkCreature(val parent: Creature) extends AbilityCreature(parent) {

	override def doDamageTo(creature: Creature): Int =  {
		val actualDamage = LifelinkCreature.this.creature.doDamageTo(creature)
		parent.owner.health += actualDamage
		actualDamage
	}
}