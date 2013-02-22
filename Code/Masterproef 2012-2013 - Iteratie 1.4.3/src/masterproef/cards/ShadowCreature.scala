package masterproef.cards

object ShadowCreature extends (Creature => ShadowCreature) {
	def apply(creature: Creature): ShadowCreature = new ShadowCreature(creature)
}

class ShadowCreature(val parent: Creature) extends AbilityCreature(parent) {

	override def canBlockCreature(creature: Creature): Boolean = {
		if (creature.hasAbility(classOf[ShadowCreature])) {
			this.parent.canBlockCreature(creature)
		} else {
			false
		}
	}
}