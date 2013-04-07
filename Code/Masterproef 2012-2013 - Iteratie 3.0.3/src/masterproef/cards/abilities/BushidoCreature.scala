package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import masterproef.cards.CounterImplicits._
import masterproef.cards.CounterSetImplicits._
import masterproef.cards.Counter
import masterproef.cards.CounterSet

class BushidoCreature(val parent: Creature, x: Int) extends AbilityCreature(parent) {
	
	var bushidoUsed: Boolean = false
	
	override def ability: String = this.getClass().getSimpleName().replace("Creature", "") + "(" + x + ")"
	override def copy(): this.type = new BushidoCreature(parent.copy, x).asInstanceOf[this.type]
	
	override def takeDamageFrom(creature: Creature): Int = {
		if(!bushidoUsed) {
			counters += 1 * (x\x)
			bushidoUsed = true
		}
		parent.takeDamageFrom(creature)
	}

	override def doDamageTo(creature: Creature): Int = {
		if(!bushidoUsed) {
			counters += 1 * (x\x)
			bushidoUsed = true
		}
		parent.doDamageTo(creature)
	}
	
	override def endTurn = {
		println("endTurn of Bushido")
		println("bushidoUsed = " + bushidoUsed)
		if(bushidoUsed) {
			counters -= 1 * (x\x)
			bushidoUsed = false
		}
		parent.endTurn
	}

}