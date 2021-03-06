package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature

object Deathtouch extends Ability


class DeathtouchCreature(val parent: Creature) extends AbilityCreature(parent) {

	override def doDamageTo(creature: Creature): Int = {
		creature.owner.kill(creature)
		0
	}
}