package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import masterproef.players.Player
import masterproef.counters.PoisonCounter
import masterproef.Masterproef

class PoisonousCreature(val parent: Creature, x: Int) extends AbilityCreature(parent) {

	override def ability: String = this.getClass().getSimpleName().replace("Creature", "") + "(" + x + ")"
	override def copy(): this.type = new PoisonousCreature(parent.copy, x).asInstanceOf[this.type]
	
	override def doDamageTo(player: Player, damage: Int): Int = {
		player.counters += new PoisonCounter(x)
		if(player.counters.poison >= 10) {
			Masterproef.model.gameOver(Masterproef.model.player)
		}
		parent.doDamageTo(player, damage)
	}

}