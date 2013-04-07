package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature

object Indestructible extends Ability


class IndestructibleCreature(val parent: Creature) extends AbilityCreature(parent) {

	override def takeDamageFrom(creature: Creature): Int =  0
}