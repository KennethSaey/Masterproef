package masterproef.gamestates

import org.newdawn.slick.gui.ComponentListener
import org.newdawn.slick.gui.AbstractComponent
import masterproef.Masterproef
import org.newdawn.slick.gui.MouseOverArea
import masterproef.cards.Creature
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import masterproef.cards.Card
import masterproef.views.CreatureView

class DeclareBlockState extends BasicPlayState with ComponentListener {

	var blockButton: MouseOverArea = null
	var skipButton: MouseOverArea = null

	var currentAttacker: Creature = null
	var currentAttackerIndex: Int = 0
	val selectedBlockers: ArrayBuffer[Creature] = new ArrayBuffer[Creature]
	var blockableAttackers: ArrayBuffer[Creature] = new ArrayBuffer[Creature]
	var availableBlockers: ArrayBuffer[Creature] = new ArrayBuffer[Creature]
	var currentBlockerPool: ArrayBuffer[Creature] = new ArrayBuffer[Creature]

//	var container: GameContainer = null

	def getID(): Int = Masterproef.DECLARE_BLOCK_ID

	def componentActivated(source: AbstractComponent): Unit = {
		if (source == skipButton) {
			nextAttacker
		} else if (source == blockButton) {
			// Block is clicked
			if (!selectedBlockers.isEmpty) {
				// If blockers were selected, add them
				model.blockers(currentAttacker) = selectedBlockers.map(_.asInstanceOf[Card])
				// Remove them from the available blockers for the next attacker
				availableBlockers --= selectedBlockers
				// And reset cards
				selectedBlockers.foreach(card => {
					card.removeListener(this)
					card.selectable = false
				})
			}
			nextAttacker
		} else {
			val creature = source.asInstanceOf[CreatureView].card.asInstanceOf[Creature]
			if (selectedBlockers.contains(creature)) {
				selectedBlockers -= creature
			} else {
				selectedBlockers += creature
			}
		}
	}

	def nextAttacker() {
		selectedBlockers.clear
		// Go to the next attacker
		if (currentAttackerIndex + 1 < blockableAttackers.size) {
			currentAttackerIndex += 1
			currentAttacker.playable = false
			currentAttacker = blockableAttackers(currentAttackerIndex)
			currentAttacker.playable = true
			setCurrentBlockerPool
			// Set up the listeners
			currentBlockerPool.foreach(card => {
				card.selectable = true
				card.addListener(container, this)
			})
		} else {
			model.nextPhase
		}
	}

	override def init(container: GameContainer, game: StateBasedGame) {
		super.init(container, game)
		this.container = container
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

	override def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		super.render(container, game, g)
		renderButton(container, g, blockButton, "Block")
		renderButton(container, g, skipButton, "skip")
	}

	override def enter(container: GameContainer, game: StateBasedGame) {
		super.enter(container, game)
		model.blockers.clear
		if (model.opponent.battlefield.filter(card => (card.isInstanceOf[Creature] && card.asInstanceOf[Creature].canBlock)).size == 0) {
			model.nextPhase
		} else {
			if (model.attackers.filter(_.asInstanceOf[Creature].canBeBlocked).size == 0) {
				// If none of the attackers is blockable => go to next phase
				model.nextPhase
			} else {
				// Get all attackers which are blockable by the opponent
				blockableAttackers = model.attackers.filter(
					attacker => (
						model.opponent.battlefield.filter(
							card => (
								card.isInstanceOf[Creature] &&
								attacker.asInstanceOf[Creature].canBeBlocked &&
								card.asInstanceOf[Creature].canBlock &&
								attacker.asInstanceOf[Creature].canBeBlockedBy(card.asInstanceOf[Creature]) &&
								card.asInstanceOf[Creature].canBlockCreature(attacker.asInstanceOf[Creature])
							)
						).size > 0
					)
				).map(_.asInstanceOf[Creature])
				if (blockableAttackers.size == 0) {
					model.nextPhase
				} else {
					// Set the first attacker
					currentAttackerIndex = 0
					currentAttacker = blockableAttackers(currentAttackerIndex)
					currentAttacker.playable = true
					// Make the creatures playable
					model.opponent.battlefield.filter(_.isInstanceOf[Creature]).foreach(_.playable = true)
					// Set all available blockers
					availableBlockers = model.opponent.battlefield.filter(card => (card.isInstanceOf[Creature] && card.asInstanceOf[Creature].canBlock)).map(_.asInstanceOf[Creature])
					// Make the possible blockers selectable
					setCurrentBlockerPool
					currentBlockerPool.foreach(
						blocker => {
							blocker.selectable = true
							blocker.addListener(container, this)
						}
					)
				}
			}

		}
	}

	def setCurrentBlockerPool() {
		currentBlockerPool = availableBlockers.filter(
			card => (
				card.asInstanceOf[Creature].canBlock &&
				currentAttacker.canBeBlockedBy(card.asInstanceOf[Creature]) &&
				card.asInstanceOf[Creature].canBlockCreature(currentAttacker)
			)
		)
	}

}