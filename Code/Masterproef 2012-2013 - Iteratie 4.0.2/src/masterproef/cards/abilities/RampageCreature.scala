package masterproef.cards.abilities
import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import masterproef.counters.DamageHealthCounter
import masterproef.EndTurnEvent

class RampageCreature(val parent: Creature, x: Int) extends AbilityCreature(parent) {

	reactions += {
		case EndTurnEvent(player) => {
			if (player == owner) {
				for (i <- 1 until blockerCount) {
					counters -= new DamageHealthCounter(x, x)
				}
				blockerCount = 0
			}
		}
	}

	var blockerCount: Int = 0

	override def ability: String = this.getClass().getSimpleName().replace("Creature", "") + "(" + x + ")"
	override def copy(): this.type = new RampageCreature(parent.copy, x).asInstanceOf[this.type]

	override def takeDamageFrom(other: Creature): Int = {
		if (blockerCount > 0) {
			counters += new DamageHealthCounter(x, x)
		}
		blockerCount += 1
		parent.takeDamageFrom(other)
	}

//	override def endTurn {
//		for (i <- 1 until blockerCount) {
//			counters -= new DamageHealthCounter(x, x)
//		}
//		blockerCount = 0
//		parent.endTurn
//	}

}