package masterproef.cards

object FlyingCreature extends (Creature => FlyingCreature) {
	def apply(creature: Creature): FlyingCreature = new FlyingCreature(creature)
}

class FlyingCreature(val parent: Creature) extends AbilityCreature(parent) {

	override def canBeBlockedBy(creature: Creature): Boolean = {
		if(creature.hasAbility(classOf[FlyingCreature]) || creature.hasAbility(classOf[ReachCreature])){
			this.parent.canBeBlockedBy(creature)
		}else{
			false
		}
	}
}