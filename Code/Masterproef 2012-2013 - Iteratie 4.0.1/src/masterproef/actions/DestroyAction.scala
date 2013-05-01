package masterproef.actions

import masterproef.cards.Creature

class DestroyAction extends TargetAction {

	def execute {
		if(executable) {
			val creature = target.asInstanceOf[Creature]
			creature.owner.kill(creature)
		}
	}
	
	def executable: Boolean = {
		target != null
	}
	
	def targetable(target: Any): Boolean = {
		target.isInstanceOf[Creature]
	}
}