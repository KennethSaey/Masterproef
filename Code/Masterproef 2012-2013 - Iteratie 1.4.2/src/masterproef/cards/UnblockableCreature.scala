package masterproef.cards

import scala.collection.mutable.ArrayBuffer

class UnblockableCreature(parent: Creature) extends Creature(parent()) {

	override def copy(): UnblockableCreature = {
		new UnblockableCreature(parent)
	}

	override def hasAbility(ability: String): Boolean = {
		(ability == "Unblockable") || parent.hasAbility(ability)
	}
	
	override def abilities(): ArrayBuffer[String] = {
			val a = new ArrayBuffer[String]
			a += "Unblockable"
			super.abilities ++ a
	}

	override def canBeBlocked(): Boolean = {
		false
	}

	override def canBeBlockedBy(creature: Creature): Boolean = {
		false
	}
	
	override def toString(): String = {
		parent.toString + " is Unblockable"
	}
}