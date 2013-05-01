package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature

class AbsorbCreature(val parent: Creature, absorb: Int) extends AbilityCreature(parent) {
	
	override def ability: String = this.getClass().getSimpleName().replace("Creature", "") + "(" + absorb + ")"
	override def copy(): this.type = new AbsorbCreature(parent.copy, absorb).asInstanceOf[this.type]

	/**
	 * Maybe actual damage has to include absorb? (For Trample reasons)
	 */
	override def takeDamageFrom(other: Creature): Int = {
//		println(this + " is taking damage from " + other)
//		println("parent.health = " + parent.health)
//		println("other.damage = " + other.damage)
//		println("absorb = " + absorb)
		val actualDamage = scala.math.min(parent.health, scala.math.max(other.damage - absorb, 0))
//		println("actualDamage = " + actualDamage)
		parent.health = parent.health - actualDamage//This is a bug
//		println("parent.health = " + parent.health)
//		println("health = " + health)
		actualDamage
	}
}