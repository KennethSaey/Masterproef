package masterproef.cards

import masterproef.players.Player
import scala.collection.mutable.ArrayBuffer
import scala.reflect.Type
import masterproef.views.CreatureView
import org.newdawn.slick.Image
import masterproef.Masterproef
import org.newdawn.slick.gui.GUIContext
import masterproef.counters.Counters

object CreatureImplicits {
	implicit def String2Creature(name: String): Creature = new Creature called name
//	implicit def Card2Creature(card: Card): Creature = new Creature called card.name
}

class Creature extends Card(CreatureView) {

	private var _defaultHealth: Int = 0
	private var _defaultDamage: Int = 0
	private var _health: Int = 0
	private var _damage: Int = 0
	private var _counters: Counters = new Counters()
	
	override def copy(): Creature = {
		new Creature called name is_owned_by owner requires requirements with_default_damage _defaultDamage with_default_health _defaultHealth
	}
	
	override def artwork: Image = {
		if (_artwork == null) _artwork = new Image(Masterproef.getClass().getResourceAsStream("/masterproef/assets/cards/creatures/" + name + ".png"), "creature-" + name, false)
		_artwork
	}
	
	// Getters
	def damage: Int = _damage + _counters.damage
	def health: Int = _health + _counters.health
	def counters: Counters = _counters
	// Setters
	def damage_=(damage: Int) = _damage = damage
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
	
//	def has_ability2[T <: AbilityCreature](f: Creature => T): T = {
//		f(this)
//	}
//	
//	def has_param_ability(f: (Creature, Int) => AbilityCreature, x: Int): AbilityCreature = {
//		f(this, x)
//	}
	
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
		val actualDamage = scala.math.min(health, creature.damage)
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
		_name + "[" + damage + "/" + health + "]"
	}
	
	def resetDamage {
		damage = _defaultDamage
	}
	
	def resetHealth {
		health = _defaultHealth
	}
	
	override def endTurn = {
		
	}
	
	override def startTurn = {
		resetDamage
		resetHealth
	}
}