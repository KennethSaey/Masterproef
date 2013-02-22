package masterproef.cards

import scala.swing.Publisher
import scala.swing.event.Event
import masterproef.players.Player

case class CreatureCardStateChanged( /*creatureCard: CreatureCard, */ previousState: CardState.Value) extends Event
case class CreatureCardOwnerChanged( /*creatureCard: CreatureCard, */ previousOwner: Player) extends Event
case class CreatureCardTookDamage( /*creatureCard: CreatureCard, */ damageTaken: Int) extends Event
case class CreatureCardHealed( /*creatureCard: CreatureCard, */ healthGained: Int) extends Event
case class CreatureCardFlipped extends Event

trait CreatureLike {
	def canAttack: Boolean = true
	def canBlock: Boolean = true
	def canBlock(creatureCard: CreatureCard): Boolean = true
	def canBeBlocked: Boolean = true
	def canBeBlockedBy(creatureCard: CreatureCard): Boolean = true
}

object CreatureCard {
	object CardFace extends Enumeration {
		val FRONT, BACK = Value;
	}
}

class CreatureCard(val name: String, val defaultDamage: Int, val defaultHealth: Int) extends Publisher with CreatureLike {
	/* Fields */
	private var _state: CardState.Value = CardState.UNPLAYED
	private var _damage: Int = defaultDamage
	private var _health: Int = defaultHealth
	private var _owner: Player = null
	private var _face: CreatureCard.CardFace.Value = CreatureCard.CardFace.BACK

	def printListeners = {
		for(l <- this.listeners){
			println(l.toString)
		}
	}
	
	/* Getters & Setters */
	def state = _state
	def state_=(newState: CardState.Value) = {
		if (newState != _state) {
			val previousState = _state
			_state = newState
			publish(new CreatureCardStateChanged( /*this, */ previousState))
		}
	}
	def damage = _damage
	def health = _health
	def owner = _owner
	def face = _face
	def owner_=(newOwner: Player) = {
		if (newOwner != _owner) {
			val previousOwner = _owner
			_owner = newOwner
			publish(new CreatureCardOwnerChanged( /*this, */ previousOwner))
		}
	}
	def face_=(newFace: CreatureCard.CardFace.Value) = {
		println("Something")
		if (newFace != _face) {
			_face = newFace
			publish(new CreatureCardFlipped)
		}
	}
	def flip = {
		_face = _face match {
			case CreatureCard.CardFace.BACK => CreatureCard.CardFace.FRONT
			case CreatureCard.CardFace.FRONT => CreatureCard.CardFace.BACK
		}
		publish(new CreatureCardFlipped)
	}

	/* Methods */
	def takeDamage(damage: Int): Int = {
		val actualDamage = scala.math.min(_health, damage)
		if (actualDamage > 0) {
			_health -= actualDamage
			publish(new CreatureCardTookDamage( /*this, */ actualDamage))
		}
		actualDamage
	}
	def takeDamageFrom(creatureCard: CreatureCard): Int = takeDamage(creatureCard.damage)
	def doDamageTo(creatureCard: CreatureCard): Int = creatureCard takeDamageFrom this
	def doDamageTo(player: Player): Int = player takeDamageFrom this
	def doDamageTo(player: Player, damage: Int) = player takeDamageFrom (this, damage)
	def heal(): Int = {
		val healthGained = scala.math.min(0, defaultHealth - _health)
		if (healthGained > 0) {
			_health = defaultHealth
			publish(new CreatureCardHealed( /*this, */ healthGained))
		}
		healthGained
	}

	/* CreauteLike overrides */
	override def canAttack: Boolean = _state == CardState.ACTIVE
	override def canBlock: Boolean = _state == CardState.ACTIVE
	override def canBlock(creatureCard: CreatureCard): Boolean = _state == CardState.ACTIVE

	/* Convenience methods*/
	def copy(): CreatureCard = {
		val copy = new CreatureCard(name, defaultDamage, defaultHealth)
		copy._state = this._state
		copy._damage = this._damage
		copy._health = this._health
		copy._owner = this._owner
		copy
	}

	override def toString(): String = {
		name + "[" + _damage + "/" + _health + "]"
	}
}