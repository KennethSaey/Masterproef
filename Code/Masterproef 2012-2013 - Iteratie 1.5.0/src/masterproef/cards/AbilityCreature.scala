package masterproef.cards

import scala.collection.mutable.ArrayBuffer

import masterproef.players.Player

class AbilityCreature(var creature: Creature) extends Creature {

	def ability: String = this.getClass().getSimpleName().replace("Creature", "")
	
	def apply = this.getClass()
	
	override def abilities(): ArrayBuffer[String] = {
		val abilities = creature.abilities
		abilities += ability//this.getClass().getSimpleName().replace("Creature", "")
		abilities
	}

	override def hasAbility(c: Class[_ <: AbilityCreature]): Boolean = {
		creature.hasAbility(c) || this.getClass() == c
	}

	def removeAbility(c: Class[_ <: AbilityCreature]): Creature = {
		if (hasAbility(c)) {
			if (this.getClass() == c) {
				creature
			} else {
				if (creature.isInstanceOf[AbilityCreature]) {
					creature.asInstanceOf[AbilityCreature].removeAbility(c, this)
				} else {
					creature
				}
			}
		} else {
			creature
		}
	}

	private def removeAbility(c: Class[_ <: AbilityCreature], parent: AbilityCreature): Creature = {
		if (this.getClass() == c) {
			parent.creature = creature
			parent
		} else {
			if (creature.isInstanceOf[AbilityCreature]) {
				creature.asInstanceOf[AbilityCreature].removeAbility(c, this)
			} else {
				creature
			}
		}
	}

	/* Fall through methods for the decorator pattern */

	override def name: String = creature.name
	override def damage: Int = creature.damage
	override def health: Int = creature.health
	override def owner: Player = creature.owner
	
	override def is_owned_by(player: Player): this.type = {
		creature is_owned_by player
		this
	}

	override def copy: this.type = {
		this.getClass().getConstructors()(0).newInstance(creature.copy).asInstanceOf[this.type]
	}

	override def toString(): String = {
		creature.toString + ", " + ability//this.getClass().getSimpleName()//.replace("Creature", "")
	}

	override def canAttack(): Boolean = {
		creature.canAttack
	}

	override def canBlock(): Boolean = {
		creature.canBlock
	}

	override def canBlockCreature(creature: Creature): Boolean = {
		this.creature.canBlockCreature(creature)
	}

	override def canBeBlocked(): Boolean = {
		creature.canBeBlocked
	}

	override def canBeBlockedBy(creature: Creature): Boolean = {
		this.creature.canBeBlockedBy(creature)
	}

	override def takeDamageFrom(creature: Creature): Int = {
		this.creature.takeDamageFrom(creature)
	}

	override def doDamageTo(creature: Creature): Int = {
		this.creature.doDamageTo(creature)
	}

	override def doDamageTo(player: Player, damage: Int): Int = {
		creature.doDamageTo(player, damage)
	}
}