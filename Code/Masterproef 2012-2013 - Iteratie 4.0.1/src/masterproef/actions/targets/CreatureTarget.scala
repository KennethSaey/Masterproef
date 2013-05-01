package masterproef.actions.targets

import masterproef.cards.Creature

class CreatureTarget extends Target {

	def targetable(target: Any): Boolean = {
		target != null && target.isInstanceOf[Creature]
	}
}