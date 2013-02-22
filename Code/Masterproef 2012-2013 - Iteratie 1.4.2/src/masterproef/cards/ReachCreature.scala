package masterproef.cards

import scala.collection.mutable.ArrayBuffer

class ReachCreature(parent: Creature) extends Creature(parent()) {

	override def copy(): ReachCreature = {
		new ReachCreature(parent)
	}

	override def hasAbility(ability: String): Boolean = {
		(ability == "Reach") || parent.hasAbility(ability)
	}
	
	override def abilities(): ArrayBuffer[String] = {
			val a = new ArrayBuffer[String]
			a += "Reach"
			super.abilities ++ a
	}
	
	override def toString(): String = {
		parent.toString + " has Reach"
	}
}