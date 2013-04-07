package masterproef.gamestates

import masterproef.Masterproef
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.gui.MouseOverArea
import scala.collection.mutable.ArrayBuffer
import org.newdawn.slick.gui.ComponentListener
import masterproef.cards.Creature
import org.newdawn.slick.gui.AbstractComponent
import org.newdawn.slick.Graphics
import org.newdawn.slick.Color
import scala.collection.mutable.HashMap
import masterproef.cards.Deck

class DeclareBlockState extends BasicPlayState with ComponentListener {

	val attackerAreas: ArrayBuffer[MouseOverArea] = new ArrayBuffer[MouseOverArea]
	val blockerAreas: ArrayBuffer[MouseOverArea] = new ArrayBuffer[MouseOverArea]
	var blockButton: MouseOverArea = null
	var skipButton: MouseOverArea = null

	val availableBlockers: ArrayBuffer[Creature] = new ArrayBuffer[Creature]()

	val attackerAreaMap: HashMap[Creature, MouseOverArea] = new HashMap[Creature, MouseOverArea]()
	var currentAttacker: Creature = null
	var currentAttackerIndex: Int = 0
	val selectedBlockers: ArrayBuffer[Creature] = new ArrayBuffer[Creature]
	val areaBlockerMap: HashMap[MouseOverArea, Creature] = new HashMap[MouseOverArea, Creature]()
	val blockerAreaMap: HashMap[Creature, MouseOverArea] = new HashMap[Creature, MouseOverArea]()
	val availableBlockersForAttacker: ArrayBuffer[Creature] = new ArrayBuffer[Creature]()

	def getID(): Int = Masterproef.DECLARE_BLOCK_ID

	def prepare() {
		/* Find the current attacker */
		currentAttacker = model.attackers(currentAttackerIndex).asInstanceOf[Creature]
		/* Set the correct attacker area images */
		attackerAreaMap.values.foreach(_.setNormalImage(textures("front unselectable")))
		attackerAreaMap.values.foreach(_.setMouseOverImage(textures("front unselectable")))
		attackerAreaMap.values.foreach(_.setMouseDownImage(textures("front unselectable")))
		attackerAreaMap(currentAttacker).setNormalImage(textures("front hover"))
		attackerAreaMap(currentAttacker).setMouseOverImage(textures("front hover"))
		attackerAreaMap(currentAttacker).setMouseDownImage(textures("front hover"))
		model.message = model.opponent.name + ", select your blockers for " + currentAttacker
		/* Find the available blockers */
		availableBlockersForAttacker.clear
		availableBlockers.filter(creature => (creature.canBlockCreature(currentAttacker) && currentAttacker.canBeBlocked() && currentAttacker.canBeBlockedBy(creature))).foreach(availableBlockersForAttacker += _)
		/* Reset all blocker area images */
		blockerAreas.foreach(_.setNormalImage(textures("front")))
		blockerAreas.foreach(_.setMouseOverImage(textures("front")))
		blockerAreas.foreach(_.setMouseDownImage(textures("front")))
		/* Set the correct blocker area images */
		blockerAreaMap.filter(pair => availableBlockersForAttacker.contains(pair._1)).foreach(pair => pair._2.setNormalImage(textures("front selectable")))
		blockerAreaMap.filter(pair => availableBlockersForAttacker.contains(pair._1)).foreach(_._2.setMouseOverImage(textures("front selected")))
		blockerAreaMap.filter(pair => availableBlockersForAttacker.contains(pair._1)).foreach(_._2.setMouseDownImage(textures("front selected")))
	}

	override def init(container: GameContainer, game: StateBasedGame) {
		super.init(container, game)
		val xOffset = gameAreas("playerDeck").getX() + (gameAreas("playerDeck").getWidth() - 110) / 2
		val yOffsetBlock = gameAreas("playerDeck").getY() - 120
		blockButton = new MouseOverArea(container, buttons("button"), xOffset, yOffsetBlock, 110, 50, this)
		blockButton.setMouseOverImage(buttons("hover"))
		blockButton.setMouseDownImage(buttons("mousedown"))
		val yOffsetSkip = gameAreas("playerDeck").getY() - 60
		skipButton = new MouseOverArea(container, buttons("button"), xOffset, yOffsetSkip, 110, 50, this)
		skipButton.setMouseOverImage(buttons("hover"))
		skipButton.setMouseDownImage(buttons("mousedown"))
	}

	def componentActivated(source: AbstractComponent) {
		if (source == skipButton) {
			selectedBlockers.clear
			if (currentAttackerIndex + 1 < model.attackers.size) {
				currentAttackerIndex += 1
				prepare
			} else {
				model.nextPhase
			}
		} else if (source == blockButton) {
			if (!selectedBlockers.isEmpty) {
				val blockers = new Deck
				blockers ++= selectedBlockers
				model.blockers(currentAttacker) = blockers
				availableBlockers --= selectedBlockers
				selectedBlockers.clear
			}
			if (currentAttackerIndex + 1 < model.attackers.size) {
				currentAttackerIndex += 1
				prepare
			} else {
				model.nextPhase
			}
		} else {
			if (blockerAreas.contains(source)) {
				val selectedArea = source.asInstanceOf[MouseOverArea]
				/* source is a blocking creature */
				val selectedBlocker = areaBlockerMap(selectedArea)
				/* Check if this is a valid blocker for the current attacker */
				if (availableBlockersForAttacker.contains(selectedBlocker)) {
					if (selectedBlockers.contains(selectedBlocker)) {
						/* Deselect */
						selectedArea.setNormalImage(textures("front selectable"))
						selectedBlockers -= selectedBlocker
					} else {
						/* Select */
						selectedArea.setNormalImage(textures("front selected"))
						selectedBlockers += selectedBlocker
					}
				}
			}
		}
	}

	override def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		super.render(container, game, g)
		renderButton(container, g, blockButton, "Block")
		renderButton(container, g, skipButton, "skip")
	}

	def renderCreature(creature: Creature, container: GameContainer, g: Graphics, area: MouseOverArea) {
		area.render(container, g)
		g.setFont(BasicPlayState.normalFont)
		g.setColor(Color.black)
		g.drawString(creature.name, area.getX() + 26, area.getY() + 23)
		g.drawImage(images(creature.name), area.getX() + 25, area.getY() + 33, area.getX() + 135, area.getY() + 114, 0, 0, 220, 161)
		g.drawString(creature.abilities.mkString(" | "), area.getX() + 26, area.getY() + 116)
		g.setFont(BasicPlayState.largeFont)
		g.drawString(creature.damage + " / " + creature.health, area.getX() + 35, area.getY() + 150)
	}

	override def renderPlayerBattlefield(container: GameContainer, game: StateBasedGame, g: Graphics) {
		val step = if (model.player.battlefield.size <= 1) 0 else scala.math.min(160, (gameAreas("playerBattlefield").getWidth() - 160) / (model.player.battlefield.size - 1))
		val battlefieldSize = model.player.battlefield.size
		if (attackerAreas.size >= battlefieldSize) {
			for (i <- 0 until battlefieldSize) {
				renderCreature(model.player.battlefield(i).asInstanceOf[Creature], container, g, attackerAreas(i))
			}
		}
	}

	override def renderOpponentBattlefield(container: GameContainer, game: StateBasedGame, g: Graphics) {
		val step = if (model.opponent.battlefield.size <= 1) 0 else scala.math.min(160, (gameAreas("opponentBattlefield").getWidth() - 160) / (model.opponent.battlefield.size - 1))
		val battlefieldSize = model.opponent.battlefield.size
		if (blockerAreas.size >= battlefieldSize) {
			for (i <- 0 until battlefieldSize) {
				renderCreature(model.opponent.battlefield(i).asInstanceOf[Creature], container, g, blockerAreas(i))
			}
		}
	}

	override def enter(container: GameContainer, game: StateBasedGame) {
		super.enter(container, game)
		println("Entering DeclareBlockState for " + model.opponent)
		attackerAreaMap.clear
		currentAttackerIndex = 0
		if (model.opponent.battlefield.filter(_.asInstanceOf[Creature].canBlock).size == 0) {
			model.nextPhase
		} else {
			/* Get all available blockers */
			availableBlockers.clear
			model.opponent.battlefield.filter(_.asInstanceOf[Creature].canBlock).foreach(availableBlockers += _.asInstanceOf[Creature])

			/* Create the attacker areas */
			attackerAreas.clear
			val attackersStep = if (model.player.battlefield.size <= 1) 0 else scala.math.min(160, (gameAreas("playerBattlefield").getWidth() - 160) / (model.player.battlefield.size - 1))
			for (i <- 0 until model.player.battlefield.size) {
				if (model.attackers.contains(model.player.battlefield(i))) {
					attackerAreas += new MouseOverArea(container, textures("front unselectable"), gameAreas("playerBattlefield").getX() + i * attackersStep, gameAreas("playerBattlefield").getY())
					attackerAreaMap(model.player.battlefield(i).asInstanceOf[Creature]) = attackerAreas.last
				} else {
					attackerAreas += new MouseOverArea(container, textures("front"), gameAreas("playerBattlefield").getX() + i * attackersStep, gameAreas("playerBattlefield").getY())
				}
			}

			/* Create the blocker areas */
			blockerAreas.clear
			val blockersStep = if (model.opponent.battlefield.size <= 1) 0 else scala.math.min(160, (gameAreas("opponentBattlefield").getWidth() - 160) / (model.opponent.battlefield.size - 1))
			for (i <- 0 until model.opponent.battlefield.size) {
				if (model.opponent.battlefield(i).asInstanceOf[Creature].canBlock) {
					blockerAreas += new MouseOverArea(container, textures("front"), gameAreas("opponentBattlefield").getX() + i * blockersStep, gameAreas("opponentBattlefield").getY(), this)
					areaBlockerMap(blockerAreas.last) = model.opponent.battlefield(i).asInstanceOf[Creature]
					blockerAreaMap(model.opponent.battlefield(i).asInstanceOf[Creature]) = blockerAreas.last
				} else {
					blockerAreas += new MouseOverArea(container, textures("front"), gameAreas("opponentBattlefield").getX() + i * blockersStep, gameAreas("opponentBattlefield").getY())
				}
			}

			/**/
			prepare
		}
	}
}