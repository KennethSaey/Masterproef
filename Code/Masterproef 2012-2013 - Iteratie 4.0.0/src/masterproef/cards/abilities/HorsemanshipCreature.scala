package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature

class HorsemanshipCreature(val parent: Creature) extends AbilityCreature(parent) {

	override def canBeBlockedBy(creature: Creature): Boolean = creature.hasAbility(classOf[HorsemanshipCreature])
}