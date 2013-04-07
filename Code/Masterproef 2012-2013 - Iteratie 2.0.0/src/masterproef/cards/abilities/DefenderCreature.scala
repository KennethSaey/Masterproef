package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature

object Defender extends Ability


class DefenderCreature(val parent: Creature) extends AbilityCreature(parent) {

	override def canAttack = false
}