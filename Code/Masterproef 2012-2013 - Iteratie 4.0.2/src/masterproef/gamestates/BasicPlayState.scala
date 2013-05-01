package masterproef.gamestates

import org.newdawn.slick.TrueTypeFont
import java.awt.Font
import org.newdawn.slick.state.BasicGameState
import masterproef.Masterproef
import masterproef.GameModel
import org.newdawn.slick.GameContainer
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.Graphics
import org.newdawn.slick.Color
import scala.collection.mutable.HashMap
import org.newdawn.slick.gui.MouseOverArea
import org.newdawn.slick.Image
import masterproef.players.Player
import masterproef.cards.CardPool
import org.newdawn.slick.MouseListener

object BasicPlayState {
	val normalFont: TrueTypeFont = new TrueTypeFont(new Font("Calibri", Font.PLAIN, 8), true)
	val largeFont: TrueTypeFont = new TrueTypeFont(new Font("Calibri", Font.PLAIN, 18), true)
	val largerFont: TrueTypeFont = new TrueTypeFont(new Font("Calibri", Font.PLAIN, 32), true)
	val largestFont: TrueTypeFont = new TrueTypeFont(new Font("Calibri", Font.PLAIN, 48), true)
}

abstract class BasicPlayState extends BasicGameState with MouseListener {

	var game: Masterproef = null
	var model: GameModel = null
	var container: GameContainer = null
	var messageDisplayStartTime: Int = -1
	val maxDeckHeight = 10

	val gameAreas: HashMap[String, MouseOverArea] = new HashMap[String, MouseOverArea]
	val buttons: HashMap[String, Image] = new HashMap[String, Image]

	
	
	def init(container: GameContainer, game: StateBasedGame) {
		this.game = game.asInstanceOf[Masterproef]
		model = this.game.model
		this.container = container
		/* Loading game areas*/
		gameAreas("playerDeck") = new MouseOverArea(container, null, 5, container.getHeight() - 245, 180, 240)
		gameAreas("playerGraveyard") = new MouseOverArea(container, null, container.getWidth() - 185, container.getHeight() - 245, 180, 240)
		gameAreas("playerHand") = new MouseOverArea(container, null, 190, container.getHeight() - 245, container.getWidth() - 380, 220)
		gameAreas("playerBattlefield") = new MouseOverArea(container, null, 190, container.getHeight() - 465, container.getWidth() - 195, 220)

		gameAreas("opponentDeck") = new MouseOverArea(container, null, 5, 5, 180, 240)
		gameAreas("opponentGraveyard") = new MouseOverArea(container, null, container.getWidth() - 185, 5, 180, 240)
		gameAreas("opponentHand") = new MouseOverArea(container, null, 190, 5, container.getWidth() - 380, 220)
		gameAreas("opponentBattlefield") = new MouseOverArea(container, null, 5, 240, container.getWidth() - 10, 220)
		/* Loading button textures*/
		val buttonImages = new Image(Masterproef.getClass().getResourceAsStream("/masterproef/assets/layout/Buttons.png"), "buttons", false)
		buttons.put("button", buttonImages.getSubImage(0, 0, 110, 50))
		buttons.put("hover", buttonImages.getSubImage(0, 50, 110, 50))
		buttons.put("mousedown", buttonImages.getSubImage(0, 100, 110, 50))
		
		initModel()
	}

	def update(container: GameContainer, game: StateBasedGame, delta: Int): Unit = {
		if (messageDisplayStartTime >= 0) {
			messageDisplayStartTime += delta
		}
	}
	
	override def enter(container: GameContainer, game: StateBasedGame) {
		// Reset all card settings
		for(card <- model.player.hand ++ model.player.battlefield ++ model.opponent.battlefield) {
			card.playable = false
			card.selectable = false
			card.selected = false
			card.clearListeners
		}
	}

	def initModel() = {
		if (!game.modelInitialised) {
			game.modelInitialised = true
			val player = new Player(game.playerName, 20)
			player.addToDeck(CardPool.createDeck(30))
//			player.deck.foreach(c => println(c.getClass()))
			val opponent = new Player("Opponent", 20)
			opponent.addToDeck(CardPool.createDeck(30))
			model.addPlayer(player)
			model.addPlayer(opponent)
			player.draw(3)
			opponent.draw(3)
		}
	}

	/* Renders the game */
	def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		/* Render the player game areas*/
		renderPlayerDeck(container, game, g)
		renderPlayerGraveyard(container, game, g)
		renderPlayerHand(container, game, g)
		renderPlayerBattlefield(container, game, g)
		/* Render the opponent game areas */
		renderOpponentDeck(container, game, g)
		renderOpponentGraveyard(container, game, g)
		renderOpponentHand(container, game, g)
		renderOpponentBattlefield(container, game, g)
		/* Render general stuff */
		renderPlayerNames(container, game, g)
		renderMessage(container, game, g)
	}

	/**
	 *  The game area renderers
	 */
	def renderPlayerDeck(container: GameContainer, game: StateBasedGame, g: Graphics) {
		if (model.player.deck.size == 0) {
			renderEmptyDeck(g, gameAreas("playerDeck").getX() + (maxDeckHeight - 1) * 2, gameAreas("playerDeck").getY() + (maxDeckHeight - 1) * 2)
		} else {
			for (i <- 0 until scala.math.min(maxDeckHeight, model.player.deck.size)) {
//				println(model.player.deck(i).getClass())
				model.player.deck(i).renderAt(
					container,
					g,
					gameAreas("playerDeck").getX() + (maxDeckHeight - 1) * 2 - i * 2,
					gameAreas("playerDeck").getY() + (maxDeckHeight - 1) * 2 - i * 2,
					160,
					220,
					false
				)
			}
		}
	}

	def renderPlayerGraveyard(container: GameContainer, game: StateBasedGame, g: Graphics) {
		if (model.player.graveyard.size == 0) {
			renderEmptyDeck(g, gameAreas("playerGraveyard").getX(), gameAreas("playerGraveyard").getY() + (maxDeckHeight - 1) * 2)
		} else {
			for (i <- 0 until scala.math.min(maxDeckHeight, model.player.graveyard.size)) {
				model.player.graveyard(i).renderAt(
					container,
					g,
					gameAreas("playerGraveyard").getX() + i * 2,
					gameAreas("playerGraveyard").getY() + (maxDeckHeight - 1) * 2 - i * 2,
					160,
					220,
					false
				)
			}
		}
	}

	def renderPlayerHand(container: GameContainer, game: StateBasedGame, g: Graphics) {
		val step = if (model.player.hand.size <= 1) 0 else scala.math.min(160, (gameAreas("playerHand").getWidth() - 160) / (model.player.hand.size - 1))
		for (i <- 0 until model.player.hand.size) {
			model.player.hand(i).renderAt(
				container,
				g,
				gameAreas("playerHand").getX() + i * step,
				gameAreas("playerHand").getY(),
				160,
				220
			)
		}
	}

	def renderPlayerBattlefield(container: GameContainer, game: StateBasedGame, g: Graphics) {
		val step = if (model.player.battlefield.size <= 1) 0 else scala.math.min(160, (gameAreas("playerBattlefield").getWidth() - 160) / (model.player.battlefield.size - 1))
		for (i <- 0 until model.player.battlefield.size) {
			model.player.battlefield(i).renderAt(
				container,
				g,
				gameAreas("playerBattlefield").getX() + i * step,
				gameAreas("playerBattlefield").getY(),
				160,
				220
			)
		}
	}

	def renderOpponentDeck(container: GameContainer, game: StateBasedGame, g: Graphics) {
		if (model.opponent.deck.size == 0) {
			renderEmptyDeck(g, gameAreas("opponentDeck").getX() + (maxDeckHeight - 1) * 2, gameAreas("opponentDeck").getY() + (maxDeckHeight - 1) * 2)
		} else {
			for (i <- 0 until scala.math.min(maxDeckHeight, model.opponent.deck.size)) {
				model.opponent.deck(i).renderAt(
					container,
					g,
					gameAreas("opponentDeck").getX() + (maxDeckHeight - 1) * 2 - i * 2,
					gameAreas("opponentDeck").getY() + (maxDeckHeight - 1) * 2 - i * 2,
					160,
					220,
					false
				)
			}
		}
	}

	def renderOpponentGraveyard(container: GameContainer, game: StateBasedGame, g: Graphics) {
		if (model.opponent.graveyard.size == 0) {
			renderEmptyDeck(g, gameAreas("opponentGraveyard").getX(), gameAreas("opponentGraveyard").getY() + (maxDeckHeight - 1) * 2)
		} else {
			for (i <- 0 until scala.math.min(maxDeckHeight, model.opponent.graveyard.size)) {
				model.opponent.graveyard(i).renderAt(
					container,
					g,
					gameAreas("opponentGraveyard").getX() + i * 2,
					gameAreas("opponentGraveyard").getY() + (maxDeckHeight - 1) * 2 - i * 2,
					160,
					220,
					false
				)
			}
		}
	}

	def renderOpponentHand(container: GameContainer, game: StateBasedGame, g: Graphics) {
		val step = if (model.opponent.hand.size <= 1) 0 else scala.math.min(160, (gameAreas("opponentHand").getWidth() - 160) / (model.opponent.hand.size - 1))
		for (i <- 0 until model.opponent.hand.size) {
			model.opponent.hand(i).renderAt(
				container,
				g,
				gameAreas("opponentHand").getX() + i * step,
				gameAreas("opponentHand").getY(),
				160,
				220,
				false
			)
		}
	}

	def renderOpponentBattlefield(container: GameContainer, game: StateBasedGame, g: Graphics) {
		val step = if (model.opponent.battlefield.size <= 1) 0 else scala.math.min(160, (gameAreas("opponentBattlefield").getWidth() - 160) / (model.opponent.battlefield.size - 1))
		for (i <- 0 until model.opponent.battlefield.size) {
			model.opponent.battlefield(i).renderAt(
				container,
				g,
				gameAreas("opponentBattlefield").getX() + i * step,
				gameAreas("opponentBattlefield").getY(),
				160,
				220
			)
		}
	}

	def renderPlayerNames(container: GameContainer, game: StateBasedGame, g: Graphics) {
		g.setColor(Color.lightGray)
		g.setFont(BasicPlayState.largerFont)
		val playerString = model.player.name + " (" + model.player.health + ")" + (if(model.player.counters.poison > 0) "[P" + model.player.counters.poison + "]" else "")
		val opponentString = model.opponent.name + " (" + model.opponent.health + ")" + (if(model.opponent.counters.poison > 0) "[P" + model.opponent.counters.poison + "]" else "")
		g.drawString(opponentString, 5, container.getHeight() / 2 - g.getFont().getHeight(opponentString) - 5)
		g.drawString(playerString, 5, container.getHeight() / 2 + 5)
		g.drawLine(0, container.getHeight() / 2, container.getWidth(), container.getHeight() / 2)
	}

	def renderMessage(container: GameContainer, game: StateBasedGame, g: Graphics) {
		if (model.newMessage) {
			messageDisplayStartTime = 0
		}
		if (messageDisplayStartTime >= 0 && messageDisplayStartTime < 5000) {
			if (messageDisplayStartTime <= 3000) {
				g.setColor(Color.lightGray)
			} else {
				g.setColor(new Color(0.7f, 0.7f, 0.7f, (5000.0f - messageDisplayStartTime) / 2000.0f))
			}
			g.setFont(BasicPlayState.largestFont)
			val message = model.message
			g.drawString(message, (container.getWidth() - g.getFont().getWidth(message)) / 2, (container.getHeight() - g.getFont().getHeight(message)) / 2)
		} else {
			messageDisplayStartTime = -1
		}
	}

	/**
	 * Additional renderers
	 */

	def renderEmptyDeck(g: Graphics, x: Int, y: Int) {
		val color = g.getColor()
		g.setColor(Color.darkGray)
		g.fillRoundRect(x, y, 150, 210, 20, 20)
		g.setColor(Color.lightGray)
		g.drawRoundRect(x, y, 150, 210, 20, 20)
		g.fillRoundRect(x + 10, y + 10, 130, 190, 20, 20)
		g.setColor(color)
	}

	def renderButton(container: GameContainer, g: Graphics, button: MouseOverArea, text: String) {
		button.render(container, g)
		g.setColor(Color.black)
		g.setFont(BasicPlayState.largeFont)
		val xOffset = (110 - g.getFont().getWidth(text)) / 2
		g.drawString(text, button.getX() + xOffset, button.getY() + 16)
	}
}