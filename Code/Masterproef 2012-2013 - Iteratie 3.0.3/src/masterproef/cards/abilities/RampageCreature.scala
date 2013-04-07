package masterproef.cards.abilities
import masterproef.cards.CounterImplicits._
import masterproef.cards.CounterSetImplicits._
import masterproef.cards.AbilityCreature
import masterproef.cards.Creature

class RampageCreature(val parent: Creature, x: Int) extends AbilityCreature(parent) {

	var blockerCount: Int = 0

	override def ability: String = this.getClass().getSimpleName().replace("Creature", "") + "(" + x + ")"
	override def copy(): this.type = new RampageCreature(parent.copy, x).asInstanceOf[this.type]

	override def takeDamageFrom(other: Creature): Int = {
		println("takeDamgeFrom in Rampage")
		if (blockerCount > 0) {
			println("blockerCount = " + blockerCount)
			counters += 1 * (x \ x)
			println("counters = " + counters)
		}
		blockerCount += 1
		parent.takeDamageFrom(other)
	}

	override def endTurn {
		println("endTurn in Rampage")
		for (i <- 1 until blockerCount) {
			println("i = " + i)
			counters -= 1 * (x \ x)
		}
		blockerCount = 0
		parent.endTurn
	}

}