package masterproef.cards

import masterproef.players.Player
import scala.swing.Reactor
import scala.collection.mutable.ArrayBuffer

class Creature(val creatureCard: CreatureCard) extends Reactor {

	// Getters
	def name = this().name
	def state = this().state
	def owner = this().owner
	def defaultDamage = this().defaultDamage
	def defaultHealth = this().defaultHealth
	def modifiedDamage = this().modifiedDamage
	def modifiedHealth = this().modifiedHealth
	def currentDamage: Int = this().currentDamage
	def currentHealth = this().currentHealth
	
	// Setters
	def state_= (state: CardState.Value): Unit = this().state = state
	def owner_= (owner: Player): Unit = this().owner = owner
	def modifiedDamage_= (modifiedDamage: Int): Unit = this().modifiedDamage = modifiedDamage
	def modifiedHealth_= (modifiedHealth: Int): Unit = this().modifiedHealth = modifiedHealth
	def currentDamage_= (currentDamage: Int): Unit = this().currentDamage = currentDamage
	def currentHealth_= (currentHealth: Int): Unit = this().currentHealth = currentHealth
	
	// Provides us with "this()" to access the actual CreatureCard
	def apply(): CreatureCard = {
		creatureCard
	}
	
	def hasAbility(ability: String): Boolean = {
		false
	}
	
	def abilities(): ArrayBuffer[String] = {
		new ArrayBuffer()
	}
	
	def copy(): Creature = {
		new Creature(this().copy)
	}
	
	def canAttack(): Boolean = {
		this().canAttack
	}
	
	def canBlock(): Boolean = {
		this().canBlock
	}
	
	def canBlockCreature(creature: Creature): Boolean = {
		this().canBlockCreature(creature())
	}
	
	def canBeBlocked(): Boolean = {
		this().canBeBlocked
	}
	
	def canBeBlockedBy(creature: Creature): Boolean = {
		this().canBeBlockedBy(creature())
	}
	
	def takeDamageFrom(creature: Creature): Int = {
		this().takeDamageFrom(creature())
	}
	
	def doDamageTo(creature: Creature): Int = {
		this().doDamageTo(creature())
	}
	
	def doDamageTo(player: Player, damage: Int): Int = {
		this().doDamageTo(player, damage)
	}
	
	override def toString(): String = {
		this().toString
	}
}