package masterproef.cards

class ReachCreature(parent: Creature) extends Creature(parent()) {

	override def copy(): ReachCreature = {
		new ReachCreature(parent)
	}

	override def hasAbility(ability: String): Boolean = {
		(ability == "Reach") || parent.hasAbility(ability)
	}
	
	override def toString(): String = {
		parent.toString + " has Reach"
	}
}