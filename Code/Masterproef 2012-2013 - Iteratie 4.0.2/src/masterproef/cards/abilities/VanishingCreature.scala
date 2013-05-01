package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import masterproef.counters.TimeCounter
import masterproef.Masterproef
import masterproef.StartTurnEvent

class VanishingCreature(val parent: Creature, x: Int) extends AbilityCreature(parent) {

	reactions += {
		case StartTurnEvent(player) => {
			if (player == owner && player.battlefield.contains(this)) {
				if (counters.time > 1) {
					counters -= new TimeCounter(1)
				} else {
					Masterproef.model.player.kill(this)
				}
			}
		}
	}

	counters += (new TimeCounter(1), x)

	override def ability: String = this.getClass().getSimpleName().replace("Creature", "") + "(" + x + ")"
	override def copy(): this.type = new VanishingCreature(parent.copy, x).asInstanceOf[this.type]

	//	override def startTurn {
	//		if (counters.time > 1) {
	//			counters -= new TimeCounter(1)
	//		} else {
	//			Masterproef.model.player.kill(this)
	//		}
	//		parent.startTurn
	//	}
}