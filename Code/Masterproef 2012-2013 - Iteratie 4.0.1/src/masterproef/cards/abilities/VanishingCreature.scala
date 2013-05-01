package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import masterproef.counters.TimeCounter
import masterproef.Masterproef

class VanishingCreature(val parent: Creature, x: Int) extends AbilityCreature(parent) {

	counters += (new TimeCounter(1), x)

	override def ability: String = this.getClass().getSimpleName().replace("Creature", "") + "(" + x + ")"
	override def copy(): this.type = new VanishingCreature(parent.copy, x).asInstanceOf[this.type]

	override def startTurn {
		if (counters.time > 1) {
			counters -= new TimeCounter(1)
		} else {
			Masterproef.model.player.kill(this)
		}
		parent.startTurn
	}
}