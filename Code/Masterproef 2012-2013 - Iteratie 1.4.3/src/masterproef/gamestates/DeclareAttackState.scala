package masterproef.gamestates

import masterproef.Masterproef
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.gui.MouseOverArea
import scala.collection.mutable.ArrayBuffer
import org.newdawn.slick.gui.ComponentListener
import org.newdawn.slick.gui.AbstractComponent
import org.newdawn.slick.Graphics
import masterproef.cards.Creature
import org.newdawn.slick.Color

class DeclareAttackState extends BasicPlayState with ComponentListener {

	val battlefieldAreas: ArrayBuffer[MouseOverArea] = new ArrayBuffer[MouseOverArea]
	var attackButton: MouseOverArea = null
	var skipButton: MouseOverArea = null
	var selectedCards: ArrayBuffer[Int] = new ArrayBuffer[Int]

	def getID(): Int = Masterproef.DECLARE_ATTACK_ID

	override def init(container: GameContainer, game: StateBasedGame) {
		super.init(container, game)
		val xOffset = gameAreas("playerDeck").getX() + (gameAreas("playerDeck").getWidth() - 110) / 2
		val yOffsetAttack = gameAreas("playerDeck").getY() - 120
		attackButton = new MouseOverArea(container, buttons("button"), xOffset, yOffsetAttack, 110, 50, this)
		attackButton.setMouseOverImage(buttons("hover"))
		attackButton.setMouseDownImage(buttons("mousedown"))
		val yOffsetSkip = gameAreas("playerDeck").getY() - 60
		skipButton = new MouseOverArea(container, buttons("button"), xOffset, yOffsetSkip, 110, 50, this)
		skipButton.setMouseOverImage(buttons("hover"))
		skipButton.setMouseDownImage(buttons("mousedown"))
	}

	def componentActivated(source: AbstractComponent) {
		if (source == skipButton) {
			model.startNextTurn
		} else if (source == attackButton) {
			if (selectedCards.size == 0) {
				model.startNextTurn
			} else {
				for (i <- selectedCards) {
					model.attackers += model.player.battlefield(i)
				}
				model.nextPhase
			}
		} else {
			val index = battlefieldAreas.indexOf(source)
			if (selectedCards.contains(index)) {
				selectedCards -= index
				source.asInstanceOf[MouseOverArea].setNormalImage(textures("front"))
				source.asInstanceOf[MouseOverArea].setMouseOverImage(textures("front selectable"))
				source.asInstanceOf[MouseOverArea].setMouseDownImage(textures("front selected"))
			} else {
				if (model.player.battlefield(index).asInstanceOf[Creature].canAttack) {
					selectedCards += index
					source.asInstanceOf[MouseOverArea].setNormalImage(textures("front selected"))
					source.asInstanceOf[MouseOverArea].setMouseOverImage(textures("front selected"))
					source.asInstanceOf[MouseOverArea].setMouseDownImage(textures("front selectable"))
				}
			}
		}
	}

	override def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		super.render(container, game, g)
		renderButton(container, g, attackButton, "Attack")
		renderButton(container, g, skipButton, "Skip")
	}

	def renderBattlefieldCreature(creature: Creature, container: GameContainer, g: Graphics, area: MouseOverArea) {
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
		for (i <- 0 until model.player.battlefield.size) {
			renderBattlefieldCreature(model.player.battlefield(i).asInstanceOf[Creature], container, g, battlefieldAreas(i))
		}
	}

	override def enter(container: GameContainer, game: StateBasedGame) {
		super.enter(container, game)
		println("Entering DeclareAttackState for " + model.player)
		selectedCards.clear
		if (model.player.battlefield.size == 0) {
			model.startNextTurn
		} else {
			battlefieldAreas.clear
			val step = if (model.player.battlefield.size <= 1) 0 else scala.math.min(160, (gameAreas("playerBattlefield").getWidth() - 160) / (model.player.battlefield.size - 1))
			for (i <- 0 until model.player.battlefield.size) {
				battlefieldAreas += new MouseOverArea(container, textures("front"), gameAreas("playerBattlefield").getX() + i * step, gameAreas("playerBattlefield").getY(), this)
				if (model.player.battlefield(i).asInstanceOf[Creature].canAttack) {
					battlefieldAreas(i).setMouseOverImage(textures("front selectable"))
				} else {
					battlefieldAreas(i).setMouseOverImage(textures("front unselectable"))
				}
				battlefieldAreas(i).setMouseDownImage(textures("front selected"))
			}
		}
		model.message = model.player.name + ", select your attackers"
	}
}