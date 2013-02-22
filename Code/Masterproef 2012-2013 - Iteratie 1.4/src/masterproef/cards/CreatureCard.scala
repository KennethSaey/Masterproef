package masterproef.cards

import scala.collection.mutable.ArrayBuffer
import scala.swing.Reactor
import masterproef.events.DrawCardPhaseStartedEvent
import masterproef.players.Player
import masterproef.Main
import masterproef.Game


/**
 * In this class I try to split a creature into atomic operations, which can be overridden by abilities
 */
class CreatureCard(name: String, val defaultDamage: Int, val defaultHealth: Int, var abilities: ArrayBuffer[String]) extends Card(name) with Reactor {

	var modifiedDamage: Int = defaultDamage
	var modifiedHealth: Int = defaultHealth
	var currentDamage: Int = defaultDamage
	var currentHealth: Int = defaultHealth
//	val me = this//Could this be used for readability?
	reactions += {
		case DrawCardPhaseStartedEvent(currenPlayer) => {
//			println("DrawCardPhaseStartedEvent: currentPlayer = " + currenPlayer + " AND owner = " + owner)
			if (currenPlayer == owner) {
				println("Resetting damage and health for " + name)
				currentDamage = modifiedDamage
				currentHealth = modifiedHealth
			}
		}
	}
	
	def this(name: String, defaultDamage: Int, defaultHealth: Int, abilities: ArrayBuffer[String], state: CardState.Value, owner: Player) = {
		this(name, defaultDamage, defaultHealth, abilities)
		this.state = state
		this.owner = owner
		
	}
	
	override def copy(): CreatureCard = {
		new CreatureCard(this.name, this.defaultDamage, this.defaultHealth, this.abilities, this.state, this.owner)
	}
	
	def canAttack(): Boolean = {
		state == CardState.ACTIVE
	}
	
	def canBlock(): Boolean = {
		state == CardState.ACTIVE
	}
	
	def canBlockCreature(creature: CreatureCard): Boolean = {
		canBlock
	}
	
	def canBeBlocked(): Boolean = {
		true
	}
	
	def canBeBlockedBy(creature: CreatureCard): Boolean = {
		true
	}
	
	/**
	 * Taken damage is the minimum of currentHealth and creature.currentDamage
	 */
	def takeDamageFrom(creature: CreatureCard): Int = {
		val actualDamage = scala.math.min(currentHealth, creature.currentDamage)
		currentHealth -= actualDamage
		Main.addLine(this + "s health is reduced to " + currentHealth)
		actualDamage
	}
	
	def doDamageTo(creature: CreatureCard): Int = {
		Main.addLine(this + " attacks " + creature)
		creature takeDamageFrom this
	}
	
	def doDamageTo(player: Player, damage: Int): Int = {
		player takeDamageFrom(this, damage)
		damage
	}
	
	override def toString(): String = {
		name + "[" + currentDamage + "/" + currentHealth + "]"
	}
}