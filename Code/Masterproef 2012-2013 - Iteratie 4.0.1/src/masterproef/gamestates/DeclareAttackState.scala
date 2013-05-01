package masterproef.gamestates

import org.newdawn.slick.gui.ComponentListener
import org.newdawn.slick.gui.AbstractComponent
import masterproef.Masterproef
import org.newdawn.slick.gui.MouseOverArea
import scala.collection.mutable.ArrayBuffer
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.GameContainer
import masterproef.cards.Card
import masterproef.views.CardView
import org.newdawn.slick.Graphics
import masterproef.cards.Creature

class DeclareAttackState extends BasicPlayState with ComponentListener {
	
	var attackButton: MouseOverArea = null
	var skipButton: MouseOverArea = null
	var selectedCards: ArrayBuffer[Card] = new ArrayBuffer[Card]
	
//	var container: GameContainer = null
	
	def getID(): Int = Masterproef.DECLARE_ATTACK_ID

	def componentActivated(source: AbstractComponent): Unit = {
		if(source == skipButton) {
			model.startNextTurn
		} else if (source == attackButton) {
			if(selectedCards.size == 0) {
				model.startNextTurn
			} else {
				for(card <- selectedCards) {
					model.attackers += card
				}
				model.nextPhase
			}
		} else {
			var card = source.asInstanceOf[CardView].card
			if(selectedCards.contains(card)) {
				selectedCards -= card
			} else {
				selectedCards += card
			}
		}
	}

	override def init(container: GameContainer, game: StateBasedGame) {
		super.init(container, game)
		this.container = container
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

	override def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		super.render(container, game, g)
		renderButton(container, g, attackButton, "Attack")
		renderButton(container, g, skipButton, "Skip")
	}
	
	override def enter(container: GameContainer, game: StateBasedGame) {
		super.enter(container, game)
		selectedCards.clear
		model.attackers.clear
		model.blockers.clear
		if(model.player.battlefield.size ==  0) {
			model.startNextTurn
		} else {
			for(card <- model.player.battlefield) {
				card.selected = false
				if(card.isInstanceOf[Creature]){
					card.playable = true
					if(card.asInstanceOf[Creature].canAttack) {
						card.selectable = true
						card.addListener(container, this)
					} else {
						card.selectable = false
					}
				} else {
					card.playable = false
				}
			}
		}
	}

}