package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature

class UnblockableCreature(val parent: Creature) extends AbilityCreature(parent) {

	override def canBeBlocked(): Boolean = false

	override def canBeBlockedBy(creature: Creature): Boolean = false
}