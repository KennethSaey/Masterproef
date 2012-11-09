package masterproef.cards

import scala.collection.mutable.ArrayBuffer
import scala.swing.Reactor

import masterproef.Game
import masterproef.Main
import masterproef.events.DrawCardPhaseStartedEvent
import masterproef.players.Player

class CreatureCard(name: String, defaultDamage: Int, defaultHealth: Int, var abilities: ArrayBuffer[String]) extends Card(name) with Reactor {

	var modifiedDamage: Int = defaultDamage
	var modifiedHealth: Int = defaultHealth
	var currentDamage: Int = defaultDamage
	var currentHealth: Int = defaultHealth

	reactions += {
		case DrawCardPhaseStartedEvent(currenPlayer) => {
			if (currenPlayer == owner) {
				println("Resetting damage and health for " + name)
				currentDamage = modifiedDamage
				currentHealth = modifiedHealth
			}
		}
	}

	def this(name: String, state: CardState.Value, owner: Player, defaultDamage: Int, defaultHealth: Int, modifiers: ArrayBuffer[String]) = {
		this(name, defaultDamage, defaultHealth, modifiers)
		this.state = state
		this.owner = owner
	}

	def hasAbility(ability: String): Boolean = {
		abilities.contains(ability)
	}

	def canBeDefendedBy(creature: CreatureCard): Boolean = {
		if (cantBeDefended) {
			false
		} else if (hasAbility("Flying")) {
			creature.hasAbility("Flying") || creature.hasAbility("Reach")
		} else if (hasAbility("Shadow")) {
			creature.hasAbility("Shadow")
		} else {
			true
		}
	}

	def cantBeDefended(): Boolean = {
		!canBeDefended
	}

	def canBeDefended(): Boolean = {
		if (hasAbility("Unblockable")) {
			false
		} else {
			true
		}
	}

	override def toString(): String = {
		owner.name + "s " + name + " [D:" + currentDamage + "][H:" + currentHealth + "]" + abilities
	}

	def attack(creature: CreatureCard): Boolean = {
		Main.addLine(Game.currentPlayer + "s " + name + " attacks " + Game.currentOpponent + "s " + creature)
		if (state == CardState.ACTIVE) {
			creature.takeDamage(this, currentDamage, hasAbility("Trample"))
			if (creature.state == CardState.ACTIVE) {
				takeDamage(creature, creature.currentDamage, creature.hasAbility("Trample"))
			}
			if(creature.currentHealth == 0){
				creature.state = CardState.DEAD
				Main.addLine(creature + " died.")
			}
			if(currentHealth == 0){
				state = CardState.DEAD
				Main.addLine(this + " died.")
			}
			true
		} else {
			false
		}
	}

	def takeDamage(creature: CreatureCard, damage: Int, trample: Boolean) = {
		if (trample) {
			val trampleDamage = scala.math.max(damage - currentHealth, 0)
			Main.addLine(creature + " does " + damage + " damage to " + this + ".")
			currentHealth = scala.math.max(currentHealth - damage, 0)
			Main.addLine(this + "s health is reduced to " + currentHealth)
//			if (currentHealth == 0) {
//				state = CardState.DEAD;
//				Main.addLine(name + " died.")
//			}
			Main.addLine(creature + " does " + trampleDamage + " trample damage to " + owner)
			owner.takeDamage(trampleDamage)
		} else {
			Main.addLine(creature + " does " + damage + " damage to " + this + ".")
			currentHealth = scala.math.max(currentHealth - damage, 0)
			Main.addLine(this + "s health is reduced to " + currentHealth)
//			if (currentHealth == 0) {
//				state = CardState.DEAD;
//				Main.addLine(name + " died.")
//			}
		}
	}

	override def copy(): CreatureCard = {
		new CreatureCard(name, state, owner, defaultDamage, defaultHealth, abilities)
	}
}