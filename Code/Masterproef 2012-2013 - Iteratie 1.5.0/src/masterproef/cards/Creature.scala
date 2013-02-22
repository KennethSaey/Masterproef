package masterproef.cards

import masterproef.players.Player
import scala.collection.mutable.ArrayBuffer
import scala.reflect.Type


class Creature extends Card {

	private var _defaultHealth: Int = 0
	private var _defaultDamage: Int = 0
	private var _health: Int = 0
	private var _damage: Int = 0
	
	private var _function: Function1[Any, Any] = null
	
	override def copy: Creature = {
		val creature: Creature = new Creature
		creature._name = _name
		creature.state = state
		creature.owner = owner
		creature._defaultHealth = _defaultHealth
		creature._defaultDamage = _defaultDamage
		creature._health = _health
		creature._damage = _damage
		creature
	}

	def damage: Int = _damage
	def health: Int = _health
	def name: String = _name
	
	def health_=(health: Int) = _health = health

	def with_default_health(defaultHealth: Int): Creature = {
		_defaultHealth = defaultHealth
		_health = defaultHealth
		this
	}

	def with_default_damage(defaultDamage: Int): Creature = {
		_defaultDamage = defaultDamage
		_damage = defaultDamage
		this
	}
	
	def has_ability(f: Creature => AbilityCreature): AbilityCreature = {
		f(this)
	}
	
	def has_ability2[T <: AbilityCreature](f: Creature => T): T = {
		f(this)
	}
	
	def has_param_ability(f: (Creature, Int) => AbilityCreature, x: Int): AbilityCreature = {
		f(this, x)
	}
	
	def hasAbility(c: Class[_ <: AbilityCreature]): Boolean = {
		false
	}
	
	def abilities(): ArrayBuffer[String] = {
		new ArrayBuffer[String]()
	}

	/**/
	def canAttack(): Boolean = {
//		state == CardState.ACTIVE
		true
	}

	def canBlock(): Boolean = {
//		state == CardState.ACTIVE
		true
	}

	def canBlockCreature(creature: Creature): Boolean = {
		canBlock
	}

	def canBeBlocked(): Boolean = {
		true
	}

	def canBeBlockedBy(creature: Creature): Boolean = {
		true
	}
	
	def takeDamageFrom(creature: Creature): Int = {
		val actualDamage = scala.math.min(_health, creature.damage)
		_health -= actualDamage
		actualDamage
	}

	def doDamageTo(creature: Creature): Int = {
		creature takeDamageFrom this
	}

	def doDamageTo(player: Player, damage: Int): Int = {
		player takeDamageFrom (this, damage)
		damage
	}
	
	override def toString(): String = {
		_name + "[" + _damage + "/" + _health + "]"
	}
}