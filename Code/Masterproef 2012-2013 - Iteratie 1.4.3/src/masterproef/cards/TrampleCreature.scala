package masterproef.cards

object TrampleCreature extends (Creature => TrampleCreature) {
	def apply(creature: Creature): TrampleCreature = new TrampleCreature(creature)
}

class TrampleCreature(val parent: Creature) extends AbilityCreature(parent) {

	override def doDamageTo(creature: Creature): Int = {
		val attackDamage = this.parent.doDamageTo(creature)
		val trampleDamage = creature.damage - attackDamage
		this.parent.doDamageTo(creature.owner, trampleDamage)
	}
}