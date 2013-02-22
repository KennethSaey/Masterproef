package masterproef.cards

import scala.collection.mutable.ArrayBuffer

class ShadowCreature(parent: Creature) extends Creature(parent()) {

	override def copy(): ShadowCreature = {
		new ShadowCreature(parent)
	}

	override def hasAbility(ability: String): Boolean = {
		(ability == "Shadow") || parent.hasAbility(ability)
	}
	
	override def abilities(): ArrayBuffer[String] = {
			val a = new ArrayBuffer[String]
			a += "Shadow"
			super.abilities ++ a
	}
	
	override def canBlockCreature(creature: Creature): Boolean = {
		if(creature.hasAbility("Shadow")){
			parent.canBlockCreature(creature)
		}else{
			false
		}
	}
	
	override def canBeBlockedBy(creature: Creature): Boolean = {
		if(creature.hasAbility("Shadow")){
			parent.canBeBlockedBy(creature)
		}else{
			false
		}
	}
	
	override def toString(): String = {
		parent.toString + " is Shadow"
	}
}