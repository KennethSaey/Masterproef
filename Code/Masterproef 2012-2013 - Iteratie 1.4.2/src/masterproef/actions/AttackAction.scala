package masterproef.actions

import masterproef.Game
import masterproef.cards.CardState
import masterproef.cards.CreatureCard
import scala.collection.mutable.HashMap
import masterproef.cards.Deck
import masterproef.cards.Card
import masterproef.Main
import masterproef.cards.Creature

class AttackAction extends GameAction("Attack Action") {

	override def execute(): Unit = {
		super.execute
		val (attackers, blockers) = beforeAttackAction
		attackAction(attackers, blockers)
		afterAttackAction(attackers, blockers)
	}

	def beforeAttackAction(): (Deck, HashMap[Creature, Deck]) = {
		(Game.attackers, Game.blockers)
	}

	def attackAction(attackers: Deck, blockers: HashMap[Creature, Deck]): Unit = {
		if (attackers.isEmpty) {
			Main.addLine("Time passes peacefully...")
		} else {
			for (attacker <- attackers) {
				blockers.get(attacker) match {
					case Some(currentBlockers) => {
						for (blocker <- currentBlockers) {
							beforeAttack(attacker, blocker)
							val attackDamage = attack(attacker, blocker)
							afterAttack(attacker, blocker)
							beforeBlock(attacker, blocker)
							val blockDamage = block(attacker, blocker)
							afterBlock(attacker, blocker)
							if (attacker.currentHealth <= 0) {
								Game.currentPlayer.kill(attacker)
							}
							if (blocker.currentHealth <= 0) {
								Game.currentOpponent.kill(blocker)
							}
						}
					}
					case None => {
						beforeAttack(attacker)
						val attackDamage = attack(attacker)
						afterAttack(attacker)
					}
				}
			}
		}
	}

	def beforeAttack(attacker: Creature, blocker: Creature): Unit = {}
	
	def attack(attacker: Creature, blocker: Creature): Int = {
		attacker.doDamageTo(blocker)
	}
	
	def afterAttack(attacker: Creature, blocker: Creature): Unit = {}
	
	def beforeBlock(attacker: Creature, blocker: Creature): Unit = {}
	
	def block(attacker: Creature, blocker: Creature): Int = {
		blocker doDamageTo attacker
	}
	
	def afterBlock(attacker: Creature, blocker: Creature): Unit = {}

	def beforeAttack(attacker: Creature): Unit = {}
	
	def attack(attacker: Creature): Int = {
		attacker.doDamageTo(Game.currentOpponent, attacker.currentDamage)
	}
	
	def afterAttack(attacker: Creature): Unit = {}

	def afterAttackAction(attackers: Deck, blockers: HashMap[Creature, Deck]): Unit = {
		Game.attackers.clear
		Game.blockers.clear
	}
}