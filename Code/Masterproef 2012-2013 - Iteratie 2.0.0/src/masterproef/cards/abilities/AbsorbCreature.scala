package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import masterproef.cards.AbilityCreature1

object Absorb extends Ability1[Int]

class AbsorbCreature(val parent: Creature, val x: Int = 0) extends AbilityCreature1[Int](parent, x) {
	override def takeDamageFrom(creature: Creature): Int =  {
		val actualDamage = scala.math.min(parent.health, scala.math.max(creature.damage - _x.asInstanceOf[Int], 0))
		parent.health = parent.health - actualDamage
		actualDamage
	}
}