package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import masterproef.cards.AbilityCreature1

/**
 * Looking for a way to write:
 * new Creature called "name" has_ability Absorb 4
 */
object Absorb extends Ability1[Int]

class AbsorbCreature(val parent: Creature, val x: Int = 0) extends AbilityCreature1[Int](parent, x) {
	override def takeDamageFrom(creature: Creature): Int =  {
		val actualDamage = scala.math.min(parent.health, scala.math.max(creature.damage - _x.asInstanceOf[Int], 0))
		parent.health = parent.health - actualDamage
		actualDamage
	}
}

//class AbsorbCreature(val parent: Creature, val x: Int = 2) extends AbilityCreature(parent) {
//
//	private var _x: Int = x
//	
//	override def takeDamageFrom(creature: Creature): Int =  {
//		val actualDamage = scala.math.min(parent.health, scala.math.max(creature.damage - _x, 0))
//		parent.health = parent.health - actualDamage
//		actualDamage
//	}
//	
//	override def ability: String = AbsorbCreature.this.getClass().getSimpleName().replace("Creature", "") + " " + _x
//	
//	override def copy: this.type = {
//		new AbsorbCreature(this.parent, this._x).asInstanceOf[this.type]
//	}
//}