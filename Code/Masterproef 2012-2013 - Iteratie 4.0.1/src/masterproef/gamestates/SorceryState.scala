package masterproef.gamestates

import org.newdawn.slick.gui.ComponentListener
import org.newdawn.slick.gui.AbstractComponent
import masterproef.Masterproef
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.GameContainer
import masterproef.cards.Sorcery
import masterproef.views.CardView
import org.newdawn.slick.gui.MouseOverArea
import org.newdawn.slick.Graphics

class SorceryState extends BasicPlayState with ComponentListener {

	var sorcery: Sorcery = null
	var playerButton: MouseOverArea = null
	var opponentButton: MouseOverArea = null
	var renderFunction: Graphics => Unit = null

	def getID(): Int = Masterproef.SORCERY_ID

	def componentActivated(source: AbstractComponent): Unit = {
		if (source == playerButton) {
			sorcery.execute(model.player)
			cleanup
		} else if (source == opponentButton) {
			sorcery.execute(model.opponent)
			cleanup
		} else if (source.asInstanceOf[CardView].card == sorcery) {
			model.game.enterState(Masterproef.PLAY_CARD_ID)
		} else {
			val card = source.asInstanceOf[CardView].card
			sorcery.execute(card)
			cleanup
		}
	}

	override def init(container: GameContainer, game: StateBasedGame) {
		super.init(container, game)
		opponentButton = new MouseOverArea(container, buttons("button"), 5, container.getHeight() / 2 - 55, 110, 50, this)
		opponentButton.setMouseOverImage(buttons("hover"))
		opponentButton.setMouseDownImage(buttons("mousedown"))
		playerButton = new MouseOverArea(container, buttons("button"), 5, container.getHeight() / 2 + 5, 110, 50, this)
		playerButton.setMouseOverImage(buttons("hover"))
		playerButton.setMouseDownImage(buttons("mousedown"))
	}

	override def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		super.render(container, game, g)
		renderFunction(g)
	}

	override def enter(container: GameContainer, game: StateBasedGame) {
		super.enter(container, game)
		sorcery = model.sorceries.pop
		//		model.player.hand.foreach(_.playable = false)
		sorcery.playable = true
		sorcery.selectable = true
		sorcery.selected = true
		sorcery.addListener(container, this)
		if (sorcery.targets != null) {
			model.message = model.player.name + ", select a target"
			model.opponent.battlefield.foreach(card => card.playable = true)
			model.opponent.battlefield.filter(card => sorcery.targets.targetable(card)).foreach(card => {
				card.selectable = true
				card.addListener(container, this)
			})
			if (sorcery.targets.targetable(model.player) && sorcery.targets.targetable(model.opponent)) {
				renderFunction = g => {
					renderButton(container, g, opponentButton, "Opponent")
					renderButton(container, g, playerButton, "Player")
				}
			} else if (sorcery.targets.targetable(model.player)) {
				renderFunction = g => {
					renderButton(container, g, playerButton, "Player")
				}
			} else if (sorcery.targets.targetable(model.opponent)) {
				renderFunction = g => {
					renderButton(container, g, opponentButton, "Opponent")
				}
			} else {
				renderFunction = g => {}
			}
		} else {
			sorcery.execute()
			cleanup
		}
	}

	def cleanup {
		sorcery.owner.destroy(sorcery)
		model.game.enterState(Masterproef.PLAY_CARD_ID)
	}
}