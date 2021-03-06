package masterproef.gamestates

import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.GameContainer
import masterproef.Masterproef
import org.newdawn.slick.Graphics
import org.newdawn.slick.gui.MouseOverArea
import org.newdawn.slick.gui.ComponentListener
import org.newdawn.slick.gui.AbstractComponent
import org.newdawn.slick.Color

class DrawCardState extends BasicPlayState with ComponentListener {

	var drawCardButton: MouseOverArea = null

	def getID(): Int = Masterproef.DRAW_CARD_ID

	override def init(container: GameContainer, game: StateBasedGame) {
		super.init(container, game)
		val xOffset = gameAreas("playerDeck").getX() + (gameAreas("playerDeck").getWidth() - 110) / 2
		val yOffset = gameAreas("playerDeck").getY() + (gameAreas("playerDeck").getHeight() - 50) / 2
		drawCardButton = new MouseOverArea(container, buttons("button"), xOffset, yOffset, 110, 50, this)
		drawCardButton.setMouseOverImage(buttons("hover"))
		drawCardButton.setMouseDownImage(buttons("mousedown"))
	}

	def componentActivated(source: AbstractComponent) {
		model.player.draw(1)
		model.nextPhase
	}

	override def renderPlayerDeck(container: GameContainer, game: StateBasedGame, g: Graphics) {
		super.renderPlayerDeck(container, game, g)
		renderButton(container, g, drawCardButton, "Draw Card")
//		drawCardButton.render(container, g)
//		g.setColor(Color.black)
//		g.setFont(BasicPlayState.largeFont)
//		val xOffset = (110 - g.getFont().getWidth("Draw Card")) / 2
//		g.drawString("Draw Card", drawCardButton.getX() + xOffset, drawCardButton.getY() + 16)
	}

	override def enter(container: GameContainer, game: StateBasedGame) {
		super.enter(container, game)
		if (model.player.deck.size == 0) {
			model.nextPhase
		}
		model.message = "Draw a card"
	}
}