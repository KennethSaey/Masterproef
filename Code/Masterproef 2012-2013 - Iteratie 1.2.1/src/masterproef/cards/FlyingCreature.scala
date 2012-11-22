package masterproef.cards

class FlyingCreature(parent: Creature) extends Creature(parent()) {

	override def copy(): FlyingCreature = {
		new FlyingCreature(parent)
	}
	
	override def hasAbility(ability: String): Boolean = {
		(ability == "Flying") || parent.hasAbility(ability)
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