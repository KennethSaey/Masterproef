package masterproef.cards

object UnblockableCreature extends (Creature => UnblockableCreature) {
	def apply(creature: Creature): UnblockableCreature = new UnblockableCreature(creature)
}

class UnblockableCreature(val parent: Creature) extends AbilityCreature(parent) {

	override def canBeBlocked(): Boolean = {
		false
	}
	
	override def canBeBlockedBy(creature: Creature): Boolean = {
		false
	}
}