package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature

class ShadowCreature(val parent: Creature) extends AbilityCreature(parent) {

	override def canBlockCreature(creature: Creature): Boolean = {
//		println(this + " canBlockCreature " + creature + " = " + creature.hasAbility(classOf[ShadowCreature]))
		if (creature.hasAbility(classOf[ShadowCreature])) {
			ShadowCreature.this.parent.canBlockCreature(creature)
		} else {
			false
		}
	}
	
	override def canBeBlockedBy(creature: Creature): Boolean = {
//		println(this + " canBeBlockBy " + creature + " = " + creature.hasAbility(classOf[ShadowCreature]))
		if(creature.hasAbility(classOf[ShadowCreature])){
			parent.canBeBlockedBy(creature)
		} else {
			false
		}
	}
}