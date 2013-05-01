package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import masterproef.players.Player
import masterproef.counters.DamageHealthCounter

class FrenzyCreature(val parent: Creature, x: Int) extends AbilityCreature(parent) {
	
	var frenzyUsed: Boolean = false
	
	override def ability: String = this.getClass().getSimpleName().replace("Creature", "") + "(" + x + ")"
	override def copy(): this.type = new FrenzyCreature(parent.copy, x).asInstanceOf[this.type]
	
	override def doDamageTo(player: Player, damage: Int): Int = {
		println("doDamageTo in Frenzy")
		if(!frenzyUsed) {
			println("Adding counter")
			counters += new DamageHealthCounter(x, 0)
			frenzyUsed = true
		}
		parent.doDamageTo(player, this.damage)
	}
	
	override def endTurn {
		if(frenzyUsed) {
			counters -= new DamageHealthCounter(x, 0)
			frenzyUsed = false
		}
		parent.endTurn
	}

}