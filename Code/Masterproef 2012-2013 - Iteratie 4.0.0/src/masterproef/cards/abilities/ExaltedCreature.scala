package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import masterproef.Masterproef
import masterproef.counters.DamageHealthCounter

/**
 * If you have an exalted creature in play, every creature that attacks alone gets +1/+1 until end of turn for every exalted creature you have in play
 */
class ExaltedCreature(val parent: Creature) extends AbilityCreature(parent) {
	
	override def onPlayerAttackInBattlefieldStart = {
		if(Masterproef.model.attackers.size == 1) {
			Masterproef.model.attackers(0).asInstanceOf[Creature].counters += new DamageHealthCounter(1, 1)
		}
	}
	
	override def onPlayerAttackInBattlefieldEnd = {
		if(Masterproef.model.attackers.size == 1) {
			Masterproef.model.attackers(0).asInstanceOf[Creature].counters -= new DamageHealthCounter(1, 1)
		}
	}

}