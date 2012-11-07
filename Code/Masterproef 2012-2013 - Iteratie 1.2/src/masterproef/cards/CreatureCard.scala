package masterproef.cards

import masterproef.Main
import masterproef.Game
import scala.collection.mutable.Subscriber
import masterproef.phases.AttackPhase
import scala.swing.Reactor
import masterproef.events.DrawCardPhaseStartedEvent
import masterproef.players.Player

class CreatureCard(name: String, defaultDamage: Int, defaultHealth: Int) extends Card(name) with Reactor {

	var modifiedDamage: Int = defaultDamage
	var modifiedHealth: Int = defaultHealth
	var currentDamage: Int = defaultDamage
	var currentHealth: Int = defaultHealth
	reactions += {
		case DrawCardPhaseStartedEvent(currenPlayer) => {
			if (currenPlayer == Game.currentPlayer) {
				println("Resetting damage and health for " + name)
				currentDamage = modifiedDamage
				currentHealth = modifiedHealth
			}
		}
	}
	
	def this(name: String, state: CardState.Value, owner: Player, defaultDamage: Int, defaultHealth: Int) = {
		this(name, defaultDamage, defaultHealth)
		this.state = state
		this.owner = owner
	}

	override def toString(): String = {
		owner.name + "s " + name + " [D:" + currentDamage + "][H:" + currentHealth + "]"
	}

	def attack(creature: CreatureCard): Boolean = {
		Main.addLine(Game.currentPlayer + "s " + name + " attacks " + Game.currentOpponent + "s " + creature)
		if (state == CardState.ACTIVE) {
			Main.addLine(Game.currentPlayer + "s " + name + " does " + currentDamage + " damage to " + Game.currentOpponent + "s " + creature + ". " + Game.currentOpponent + "s " + creature + "s health is reduced to " + creature.currentHealth)
			creature.takeDamage(currentDamage)
			if (creature.state == CardState.ACTIVE) {
				takeDamage(creature.currentDamage)
			}
			true
		}
		false
	}

	def takeDamage(damage: Int) = {
		currentHealth = Math.max(currentHealth - damage, 0)
		if (currentHealth == 0) {
			state = CardState.DEAD;
			Main.addLine(name + " died.")
		}
	}
	
	override def copy(): CreatureCard = {
		new CreatureCard(name, state, owner, defaultDamage, defaultHealth)
	}
}