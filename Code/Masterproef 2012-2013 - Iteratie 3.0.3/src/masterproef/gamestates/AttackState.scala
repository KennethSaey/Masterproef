package masterproef.gamestates

import org.newdawn.slick.gui.ComponentListener
import org.newdawn.slick.gui.AbstractComponent
import masterproef.Masterproef
import org.newdawn.slick.gui.MouseOverArea
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import masterproef.cards.Creature

class AttackState extends BasicPlayState with ComponentListener {

	var attackButton: MouseOverArea = null
	var buttonText: String = null
	var currentAttackerIndex: Int = -1

	def getID(): Int = Masterproef.ATTACK_ID

	def componentActivated(source: AbstractComponent): Unit = {
		if (source == attackButton) {
			if (buttonText == "Attack") {
				// Attack
				val attacker = model.attackers(currentAttackerIndex).asInstanceOf[Creature]
				if (model.blockers.contains(attacker) && model.blockers(attacker).size > 0) {
					// Attack the blockers
					for (blocker <- model.blockers(attacker).map(_.asInstanceOf[Creature])) {
						attacker.doDamageTo(blocker)
						blocker.doDamageTo(attacker)
						if (attacker.health == 0) {
							model.player.kill(attacker)
						}
						if (blocker.health == 0) {
							model.opponent.kill(blocker)
						}
					}
				} else {
					println("Attacking opponent")
					// Attack the opponent
					attacker.asInstanceOf[Creature].doDamageTo(model.opponent, attacker.asInstanceOf[Creature].damage)
//					model.player.attack(model.opponent, attacker.asInstanceOf[Creature])
				}
				buttonText = "Continue"
			} else {
				// Continue
				if (currentAttackerIndex + 1 < model.attackers.size) {
					resetPlayables
					currentAttackerIndex += 1
					setPlayables
					buttonText = "Attack"
				} else {
					model.startNextTurn
				}
			}
		}
	}

	override def init(container: GameContainer, game: StateBasedGame) {
		super.init(container, game)
		val xOffset = gameAreas("playerDeck").getX() + (gameAreas("playerDeck").getWidth() - 110) / 2
		val yOffsetContinue = gameAreas("playerDeck").getY() - 60
		attackButton = new MouseOverArea(container, buttons("button"), xOffset, yOffsetContinue, 110, 50, this)
		attackButton.setMouseOverImage(buttons("hover"))
		attackButton.setMouseDownImage(buttons("mousedown"))
	}

	override def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		super.render(container, game, g)
		renderButton(container, g, attackButton, buttonText)
	}

	override def enter(container: GameContainer, game: StateBasedGame) {
		super.enter(container, game)
		currentAttackerIndex = 0
		buttonText = "Attack"
		setPlayables
	}

	def resetPlayables() {
		val attacker = model.attackers(currentAttackerIndex)
		attacker.playable = false
		if(model.blockers.contains(attacker)) {
			model.blockers(attacker).foreach(_.playable = false)
		}
	}

	def setPlayables() {
		val attacker = model.attackers(currentAttackerIndex)
		attacker.playable = true
		if(model.blockers.contains(attacker)) {
			model.blockers(attacker).foreach(_.playable = true)
		}
	}

}