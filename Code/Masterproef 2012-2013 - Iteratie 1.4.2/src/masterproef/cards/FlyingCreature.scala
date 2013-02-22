package masterproef.cards

import scala.collection.mutable.ArrayBuffer

class FlyingCreature(parent: Creature) extends Creature(parent()) {

	override def copy(): FlyingCreature = {
		new FlyingCreature(parent)
	}
	
	override def hasAbility(ability: String): Boolean = {
		(ability == "Flying") || parent.hasAbility(ability)
	}
	
	override def abilities(): ArrayBuffer[String] = {
			val a = new ArrayBuffer[String]
			a += "Flying"
			super.abilities ++ a
	}
	
	override def canBeBlockedBy(creature: Creature): Boolean = {
		if(creature.hasAbility("Flying") || creature.hasAbility("Reach")){
			parent.canBeBlockedBy(creature)
		}else{
			false
		}
	}
	
	override def toString(): String = {
		parent.toString + " has Flying"
	}
}