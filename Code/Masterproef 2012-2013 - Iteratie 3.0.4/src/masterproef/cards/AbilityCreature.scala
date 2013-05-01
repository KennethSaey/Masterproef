package masterproef.cards

import masterproef.players.Player
import org.newdawn.slick.Image
import org.newdawn.slick.gui.GUIContext
import org.newdawn.slick.gui.ComponentListener
import org.newdawn.slick.Graphics
import scala.collection.mutable.ArrayBuffer
import masterproef.views.CardView
import masterproef.counters.Counters

class AbilityCreature(var creature: Creature) extends Creature {

	def ability: String = this.getClass().getSimpleName().replace("Creature", "")
	override def abilities(): ArrayBuffer[String] = {
		val abilities = creature.abilities
		abilities += ability
		abilities
	}
	override def hasAbility(c: Class[_ <: AbilityCreature]): Boolean = creature.hasAbility(c) || this.getClass() == c

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

	/**
	 * Decorator methods for Card
	 */
	override def copy(): this.type = {
		// MUST BE LIKE THIS!!
		this.getClass().getConstructors()(0).newInstance(creature.copy).asInstanceOf[this.type]
	}
	// Getters
	override def renderer: CardView = creature.renderer
	override def playable: Boolean = creature.playable
	override def selectable: Boolean = creature.selectable
	override def selected: Boolean = creature.selected
	override def name: String = creature.name
	override def owner: Player = creature.owner
	override def requirements: Requirements = creature.requirements
	override def frontImage: Image = creature._frontImage
	override def backImage: Image = creature._backImage
	override def artwork: Image = creature.artwork
	override def title: String = ""
	override def text: String = ""
	override def manatext: String = ""

	// Setters
	override def renderer_=(renderer: CardView) = creature.renderer = renderer
	override def called(name: String): this.type = {
		creature = creature called name
		this
	}
	override def is_owned_by(player: Player): this.type = {
		creature = creature is_owned_by player
		this
	}
	override def requires(requirements: Requirements): this.type = {
		creature = creature requires requirements
		this
	}
	override def playable_=(playable: Boolean) = creature.playable = playable
	override def selectable_=(selectable: Boolean) = creature.selectable = selectable
	override def selected_=(selected: Boolean) = creature.selected = selected

	// Others
	override def addListener(container: GUIContext, listener: ComponentListener) = {
		creature.addListener(container, listener)
	}
	override def removeListener(listener: ComponentListener) = creature.removeListener(listener)
	override def renderAt(container: GUIContext, g: Graphics, x: Int, y: Int, width: Int, height: Int, faceUp: Boolean = true) = {
		initRenderer(container)
		creature.renderer.setArea(x, y, width, height)
		creature.renderer.render(container, g, faceUp)
	}
	override def initRenderer(container: GUIContext) {
		if (creature.renderer == null) {
			creature.renderer = _rendererClass(this, container, 0, 0, 0, 0)
		}
	}
	/**
	 * Decorator methods for Creature
	 */
	// Getters
	override def damage: Int = creature.damage
	override def health: Int = creature.health
	override def counters: Counters = creature.counters

	// Setters
	override def health_=(health: Int) = creature.health = health
	override def with_default_damage(defaultDamage: Int): Creature = creature.with_default_damage(defaultDamage)
	override def with_default_health(defaultHealth: Int): Creature = creature.with_default_health(defaultHealth)

	// Others
	override def canAttack: Boolean = creature.canAttack
	override def canBlock: Boolean = creature.canBlock
	override def canBlockCreature(other: Creature): Boolean = creature.canBlockCreature(other)
	override def canBeBlocked: Boolean = creature.canBeBlocked
	override def canBeBlockedBy(other: Creature): Boolean = creature.canBeBlockedBy(other)
	override def takeDamageFrom(other: Creature): Int = creature.takeDamageFrom(other)
	override def doDamageTo(other: Creature): Int = creature.doDamageTo(other)
	override def doDamageTo(player: Player, damage: Int): Int = creature.doDamageTo(player, damage)
	override def resetDamage = creature.resetDamage
	override def resetHealth = creature.resetHealth
	override def endTurn = creature.endTurn
	override def startTurn = creature.startTurn
	
	// Hooks
	override def onPlayerAttackInBattlefieldStart = creature.onPlayerAttackInBattlefieldStart
	override def onPlayerAttackInBattlefieldEnd = creature.onPlayerAttackInBattlefieldEnd
	override def onPlayerPlayInBattlefield(card: Card) = creature.onPlayerPlayInBattlefield(card)
	override def onDeath = creature.onDeath

}