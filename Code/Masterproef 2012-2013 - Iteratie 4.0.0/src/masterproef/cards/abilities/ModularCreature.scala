package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import masterproef.counters.DamageHealthCounter
import scala.util.Random

class ModularCreature(val parent: Creature, x: Int) extends AbilityCreature(parent) {

	counters += (new DamageHealthCounter(1, 1), x)

	override def ability: String = this.getClass().getSimpleName().replace("Creature", "") + "(" + x + ")"
	override def copy(): this.type = new ModularCreature(parent.copy, x).asInstanceOf[this.type]

	override def onDeath() {
		if (this.owner.battlefield.filter(_.isInstanceOf[Creature]).size > 0) {
			val i = (new Random).nextInt(this.owner.battlefield.filter(_.isInstanceOf[Creature]).size)
			val counterCount = counters(new DamageHealthCounter(1, 1))
			counters -= (new DamageHealthCounter(1, 1), counterCount)
			this.owner.battlefield.filter(_.isInstanceOf[Creature])(i).asInstanceOf[Creature].counters += (new DamageHealthCounter(1, 1), counterCount)
		}
	}
}