package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature

object Flying extends Ability

class FlyingCreature(val parent: Creature) extends AbilityCreature(parent) {

	override def canBeBlockedBy(creature: Creature): Boolean = {
		if(creature.hasAbility(classOf[FlyingCreature]) || creature.hasAbility(classOf[ReachCreature])){
			parent.canBeBlockedBy(creature)
		}else{
			false
		}
	}
}