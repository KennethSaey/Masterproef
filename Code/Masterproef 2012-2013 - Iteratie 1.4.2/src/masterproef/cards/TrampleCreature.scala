package masterproef.cards

import masterproef.players.Player
import scala.collection.mutable.ArrayBuffer

class TrampleCreature(parent: Creature) extends Creature(parent()) {

	override def copy(): TrampleCreature = {
		new TrampleCreature(parent)
	}

	override def hasAbility(ability: String): Boolean = {
		(ability == "Trample") || parent.hasAbility(ability)
	}
	
	override def abilities(): ArrayBuffer[String] = {
			val a = new ArrayBuffer[String]
			a += "Trample"
			super.abilities ++ a
	}
	
	override def doDamageTo(creature: Creature): Int = {
		val attackDamage = parent.doDamageTo(creature)
		val trampleDamage = creature.currentDamage - attackDamage
		parent.doDamageTo(creature.owner, trampleDamage)
	}
	
	override def toString(): String = {
		parent.toString + " has Trample"
	}
}