package masterproef.cards

import masterproef.players.Player

trait CreatureInterface {

	def name
	def state
	def owner
	def defaultDamage
	def defaultHealth
	def modifiedDamage
	def modifiedHealth
	def currentDamage
	def currentHealth
	
	// Setters
	def state_= (state: CardState.Value): Unit
	def owner_= (owner: Player): Unit
	def modifiedDamage_= (modifiedDamage: Int): Unit
	def modifiedHealth_= (modifiedHealth: Int): Unit
	def currentDamage_= (currentDamage: Int): Unit
	def currentHealth_= (currentHealth: Int): Unit
	
	// Provides us with "this()" to access the actual CreatureCard
	def apply(): CreatureCard
	def copy(): Creature
	def canAttack(): Boolean
	def canBlock(): Boolean
	def canBlockCreature(creature: Creature): Boolean
	def takeDamageFrom(creature: Creature): Int
	def doDamageTo(creature: Creature): Int
	def doDamageTo(player: Player): Int
	override def toString(): String
}