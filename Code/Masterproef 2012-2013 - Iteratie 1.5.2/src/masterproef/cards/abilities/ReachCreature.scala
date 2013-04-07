package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature

//object Reach extends (Creature => Reach) {
//	def apply(creature: Creature): Reach = new Reach(creature)
//}

object Reach extends Ability

class ReachCreature(val parent: Creature) extends AbilityCreature(parent)