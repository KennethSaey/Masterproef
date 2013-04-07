package masterproef.gamestates

import masterproef.Masterproef
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.GameContainer
import masterproef.cards.Creature
import org.newdawn.slick.Graphics
import org.newdawn.slick.Color
import org.newdawn.slick.gui.MouseOverArea
import scala.collection.mutable.ArrayBuffer
import org.newdawn.slick.gui.ComponentListener
import org.newdawn.slick.gui.AbstractComponent

class PlayCardState extends BasicPlayState with ComponentListener {

	val handAreas: ArrayBuffer[MouseOverArea] = new ArrayBuffer[MouseOverArea]
	var continueButton: MouseOverArea = null

	def getID(): Int = Masterproef.PLAY_CARD_ID

	override def init(container: GameContainer, game: StateBasedGame) {
		super.init(container, game)
		val xOffset = gameAreas("playerDeck").getX() + (gameAreas("playerDeck").getWidth() - 110) / 2
		val yOffset = gameAreas("playerDeck").getY() - 60
		continueButton = new MouseOverArea(container, buttons("button"), xOffset, yOffset, 110, 50, this)
		continueButton.setMouseOverImage(buttons("hover"))
		continueButton.setMouseDownImage(buttons("mousedown"))
	}

	def componentActivated(source: AbstractComponent) {
		if (source == continueButton) {
			model.nextPhase
		} else {
			val index = handAreas.indexOf(source)
			model.player.play(model.player.hand(index))
			if(model.player.hand.isEmpty){
				model.nextPhase
			}
		}
	}

	def renderPlayableCreature(creature: Creature, container: GameContainer, g: Graphics, area: MouseOverArea) {
		area.render(container, g)
		g.setFont(BasicPlayState.normalFont)
		g.setColor(Color.black)
		g.drawString(creature.name, area.getX() + 26, area.getY() + 23)
		g.drawImage(images(creature.name), area.getX() + 25, area.getY() + 33, area.getX() + 135, area.getY() + 114, 0, 0, 220, 161)
		g.drawString(creature.abilities.mkString(" | "), area.getX() + 26, area.getY() + 116)
		g.setFont(BasicPlayState.largeFont)
		g.drawString(creature.damage + " / " + creature.health, area.getX() + 35, area.getY() + 150)
	}

	override def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		super.render(container, game, g)
		renderButton(container, g, continueButton, "Continue")
	}

	override def renderPlayerHand(container: GameContainer, game: StateBasedGame, g: Graphics) {
		for (i <- 0 until model.player.hand.size) {
			if (i < model.player.hand.size) { // Extra check, because componentActivated runs in an other thread and could change model.player.hand.size
				renderPlayableCreature(model.player.hand(i).asInstanceOf[Creature], container, g, handAreas(i))
			}
		}
	}

	override def enter(container: GameContainer, game: StateBasedGame) {
		super.enter(container, game)
		if (model.player.hand.size == 0) {
			model.nextPhase
		} else {
			handAreas.clear
			val step = if (model.player.hand.size <= 1) 0 else scala.math.min(160, (gameAreas("playerHand").getWidth() - 160) / (model.player.hand.size - 1))
			for (i <- 0 until model.player.hand.size) {
				handAreas += new MouseOverArea(container, textures("front"), gameAreas("playerHand").getX() + i * step, gameAreas("playerHand").getY(), this)
				handAreas(i).setMouseOverImage(textures("front hover"))
				handAreas(i).setMouseDownImage(textures("front selected"))
			}
		}
		model.message = model.player.name + ", play one or more cards"
	}
}