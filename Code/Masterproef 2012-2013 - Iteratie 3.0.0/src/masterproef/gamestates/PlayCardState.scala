package masterproef.gamestates

import org.newdawn.slick.gui.ComponentListener
import masterproef.Masterproef
import org.newdawn.slick.gui.AbstractComponent
import org.newdawn.slick.gui.MouseOverArea
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import masterproef.cards.Card
import masterproef.views.CardView

class PlayCardState extends BasicPlayState with ComponentListener {

	var continueButton: MouseOverArea = null

	def getID(): Int = Masterproef.PLAY_CARD_ID
	
	def componentActivated(source: AbstractComponent): Unit = {
		if(source == continueButton){
			model.nextPhase
		} else {
			val card = source.asInstanceOf[CardView].card
			model.player.play(card)
			card.playable = false
			card.removeListener(this)
			if(model.player.hand.isEmpty) model.nextPhase
		}
	}

	override def init(container: GameContainer, game: StateBasedGame) {
		super.init(container, game)
		val xOffset = gameAreas("playerDeck").getX() + (gameAreas("playerDeck").getWidth() - 110) / 2
		val yOffset = gameAreas("playerDeck").getY() - 60
		continueButton = new MouseOverArea(container, buttons("button"), xOffset, yOffset, 110, 50, this)
		continueButton.setMouseOverImage(buttons("hover"))
		continueButton.setMouseDownImage(buttons("mousedown"))
	}

	override def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		super.render(container, game, g)
		renderButton(container, g, continueButton, "Continue")
	}
	
	override def enter(container: GameContainer, game: StateBasedGame) {
		super.enter(container, game)
		if (model.player.hand.size == 0) {
			model.nextPhase
		} else {
			for(card <- model.player.hand) {
				card.playable = true
				card.selectable = true
				card.addListener(container, this)
			}
			model.message = model.player.name + ", play one or more cards"
		}
	}

}