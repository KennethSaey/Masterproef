package masterproef.actions.targets

import masterproef.players.Player

class PlayerTarget extends Target {

	def targetable(target: Any): Boolean = {
		target != null && target.isInstanceOf[Player]
	}
}