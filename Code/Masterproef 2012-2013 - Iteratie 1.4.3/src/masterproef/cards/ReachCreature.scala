package masterproef.cards

object ReachCreature extends (Creature => ReachCreature) {
	def apply(creature: Creature): ReachCreature = new ReachCreature(creature)
}

class ReachCreature(val parent: Creature) extends AbilityCreature(parent) {
}