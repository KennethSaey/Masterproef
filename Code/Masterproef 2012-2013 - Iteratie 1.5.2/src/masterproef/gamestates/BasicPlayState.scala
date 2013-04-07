package masterproef.gamestates

import scala.collection.mutable.HashMap
import org.newdawn.slick.Image
import org.newdawn.slick.TrueTypeFont
import java.awt.Font
import org.newdawn.slick.GameContainer
import org.newdawn.slick.state.StateBasedGame
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import org.newdawn.slick.state.BasicGameState
import org.newdawn.slick.Graphics
import org.newdawn.slick.gui.MouseOverArea
import masterproef.Masterproef
import masterproef.GameModel
import masterproef.players.Player
import masterproef.cards.Creature
import masterproef.cards.CardPool
import org.newdawn.slick.Color

object BasicPlayState {
	val normalFont: TrueTypeFont = new TrueTypeFont(new Font("Calibri", Font.PLAIN, 8), true)
	val largeFont: TrueTypeFont = new TrueTypeFont(new Font("Calibri", Font.PLAIN, 18), true)
	val largerFont: TrueTypeFont = new TrueTypeFont(new Font("Calibri", Font.PLAIN, 32), true)
	val largestFont: TrueTypeFont = new TrueTypeFont(new Font("Calibri", Font.PLAIN, 48), true)
}

trait BasicPlayState extends BasicGameState {

	var game: Masterproef = null
	var model: GameModel = null

	val textureStrings: Array[String] = Array(
		"back", "back selected", "back selectable", "back unselectable", "back hover",
		"front", "front selected", "front selectable", "front unselectable", "front hover"
	)
//	val textureAreas: Array[(Int, Int, Int, Int)] = Array(
//		(0, 0, 150, 210), (150, 0, 160, 220), (310, 0, 160, 220), (470, 0, 160, 220), (630, 0, 160, 220),
//		(0, 220, 150, 210), (150, 220, 160, 220), (310, 220, 160, 220), (470, 220, 160, 220), (630, 220, 160, 220)
//	)
	val textureAreas: Array[(Int, Int, Int, Int)] = Array(
		(0, 0, 160, 220), (160, 0, 160, 220), (320, 0, 160, 220), (480, 0, 160, 220), (640, 0, 160, 220),
		(0, 220, 160, 220), (160, 220, 160, 220), (320, 220, 160, 220), (480, 220, 160, 220), (640, 220, 160, 220)
	)
	val textures: HashMap[String, Image] = new HashMap[String, Image]
	val images: HashMap[String, Image] = new HashMap[String, Image]
	val buttons: HashMap[String, Image] = new HashMap[String, Image]

	val gameAreas: HashMap[String, MouseOverArea] = new HashMap[String, MouseOverArea]

	val maxDeckHeight = 10
	var messageDisplayStartTime: Int = -1

	def init(container: GameContainer, game: StateBasedGame) {
		this.game = game.asInstanceOf[Masterproef]
		model = game.asInstanceOf[Masterproef].model
		/* Loading card textures */
//		val textureImages = new Image("masterproef/data/images/textures/textures.png")
		val textureImages = new Image(Masterproef.getClass().getResourceAsStream("/masterproef/data/images/textures/textures.png"), "textures", false)
		for (i <- 0 until textureStrings.size) {
			val name = textureStrings(i)
			val area = textureAreas(i)
			textures.put(name, textureImages.getSubImage(area._1, area._2, area._3, area._4))
		}
		/* Loading creature textures */
//		val creatureImages = new Image("masterproef/data/images/creatures/creatures.png")
		val creatureImages = new Image(Masterproef.getClass().getResourceAsStream("/masterproef/data/images/creatures/creatures.png"), "creatures", false)
		var iX = 0
		var iY = 0
		for (cardName <- getCardnames) {
			images.put(cardName, creatureImages.getSubImage(iX * 220, iY * 161, 220, 161))
			iX = (iX + 1) % 5
			if (iX == 0) {
				iY += 1
			}
		}
		/* Loading button textures*/
//		val buttonImages = new Image("masterproef/data/images/textures/buttons.png")
		val buttonImages = new Image(Masterproef.getClass().getResourceAsStream("/masterproef/data/images/textures/Buttons.png"), "buttons", false)
		buttons.put("button", buttonImages.getSubImage(0, 0, 110, 50))
		buttons.put("hover", buttonImages.getSubImage(0, 50, 110, 50))
		buttons.put("mousedown", buttonImages.getSubImage(0, 100, 110, 50))

		/* Loading game areas*/
		gameAreas("playerDeck") = new MouseOverArea(container, null, 5, container.getHeight() - 245, 180, 240)
		gameAreas("playerGraveyard") = new MouseOverArea(container, null, container.getWidth() - 185, container.getHeight() - 245, 180, 240)
		gameAreas("playerHand") = new MouseOverArea(container, null, 190, container.getHeight() - 245, container.getWidth() - 380, 220)
		gameAreas("playerBattlefield") = new MouseOverArea(container, null, 190, container.getHeight() - 465, container.getWidth() - 195, 220)

		gameAreas("opponentDeck") = new MouseOverArea(container, null, 5, 5, 180, 240)
		gameAreas("opponentGraveyard") = new MouseOverArea(container, null, container.getWidth() - 185, 5, 180, 240)
		gameAreas("opponentHand") = new MouseOverArea(container, null, 190, 5, container.getWidth() - 380, 220)
		gameAreas("opponentBattlefield") = new MouseOverArea(container, null, 5, 240, container.getWidth() - 10, 220)

		initModel()
	}

	def initModel() = {
		if (!game.modelInitialised) {
			game.modelInitialised = true
			val player = new Player(game.playerName, 20)
			player.addToDeck(CardPool.createDeck(30))
			val opponent = new Player("Opponent", 20)
			opponent.addToDeck(CardPool.createDeck(30))
			model.addPlayer(player)
			model.addPlayer(opponent)
			player.draw(3)
			opponent.draw(3)
		}
	}

	def update(container: GameContainer, game: StateBasedGame, delta: Int): Unit = {
		if (messageDisplayStartTime >= 0) {
			messageDisplayStartTime += delta
		}
	}

	def getCardnames(): ArrayBuffer[String] = {
		val names = new ArrayBuffer[String]
//				for (line <- Source.fromFile("src/masterproef/data/cards.txt").getLines) {
		for (line <- Source.fromInputStream(Masterproef.getClass().getResourceAsStream("/masterproef/data/cards.txt")).getLines) {
			names += line
		}
		names
	}

	def renderButton(container: GameContainer, g: Graphics, button: MouseOverArea, text: String) {
		button.render(container, g)
		g.setColor(Color.black)
		g.setFont(BasicPlayState.largeFont)
		val xOffset = (110 - g.getFont().getWidth(text)) / 2
		g.drawString(text, button.getX() + xOffset, button.getY() + 16)
	}

	def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		renderPlayerDeck(container, game, g)
		renderPlayerGraveyard(container, game, g)
		renderPlayerHand(container, game, g)
		renderPlayerBattlefield(container, game, g)
		renderOpponentDeck(container, game, g)
		renderOpponentGraveyard(container, game, g)
		renderOpponentHand(container, game, g)
		renderOpponentBattlefield(container, game, g)
		renderPlayerNames(container, g)
		renderMessage(container, g)
	}

	def renderPlayerNames(container: GameContainer, g: Graphics) {
		g.setColor(Color.lightGray)
		g.setFont(BasicPlayState.largerFont)
		val playerString = model.player.name + " (" + model.player.health + ")"
		val opponentString = model.opponent.name + " (" + model.opponent.health + ")"
		g.drawString(opponentString, 5, container.getHeight() / 2 - g.getFont().getHeight(opponentString) - 5)
		g.drawString(playerString, 5, container.getHeight() / 2 + 5)
		g.drawLine(0, container.getHeight() / 2, container.getWidth(), container.getHeight() / 2)
	}

	def renderMessage(container: GameContainer, g: Graphics) {
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

	def renderEmptyDeck(g: Graphics, x: Int, y: Int) {
		val color = g.getColor()
		g.setColor(Color.darkGray)
		g.fillRoundRect(x, y, 150, 210, 20, 20)
		g.setColor(Color.lightGray)
		g.drawRoundRect(x, y, 150, 210, 20, 20)
		g.fillRoundRect(x + 10, y + 10, 130, 190, 20, 20)
		g.setColor(color)
	}

	def renderCreature(creature: Creature, g: Graphics, x: Int, y: Int) {
//		g.setFont(BasicPlayState.normalFont)
//		g.setColor(Color.black)
//		g.drawImage(textures("front"), x, y)
//		//		println(creature)
//		g.drawString(creature.name, x + 21, y + 17)
//		g.drawImage(images(creature.name), x + 20, y + 28, x + 130, y + 109, 0, 0, 220, 161)
//		g.drawString(creature.abilities.mkString(" | "), x + 21, y + 111)
//		g.setFont(BasicPlayState.largeFont)
//		g.drawString(creature.damage + " / " + creature.health, x + 30, y + 145)
		g.setFont(BasicPlayState.normalFont)
		g.setColor(Color.black)
		g.drawImage(textures("front"), x, y)
		g.drawString(creature.name, x + 26, y + 23)
		g.drawImage(images(creature.name), x + 25, y + 33, x + 135, y + 114, 0, 0, 220, 161)
		g.drawString(creature.abilities.mkString(" | "), x + 26, y + 116)
		g.setFont(BasicPlayState.largeFont)
		g.drawString(creature.damage + " / " + creature.health, x + 35, y + 150)
	}

	def renderPlayerDeck(container: GameContainer, game: StateBasedGame, g: Graphics) {
		if (model.player.deck.size == 0) {
			renderEmptyDeck(g, gameAreas("playerDeck").getX() + (maxDeckHeight - 1) * 2, gameAreas("playerDeck").getY() + (maxDeckHeight - 1) * 2)
		} else {
			for (i <- 0 until scala.math.min(maxDeckHeight, model.player.deck.size)) {
				g.drawImage(textures("back"), gameAreas("playerDeck").getX() + (maxDeckHeight - 1) * 2 - i * 2, gameAreas("playerDeck").getY() + (maxDeckHeight - 1) * 2 - i * 2)
			}
		}
	}

	def renderPlayerGraveyard(container: GameContainer, game: StateBasedGame, g: Graphics) {
		if (model.player.graveyard.size == 0) {
			renderEmptyDeck(g, gameAreas("playerGraveyard").getX(), gameAreas("playerGraveyard").getY() + (maxDeckHeight - 1) * 2)
		} else {
			for (i <- 0 until scala.math.min(maxDeckHeight, model.player.graveyard.size)) {
				g.drawImage(textures("back"), gameAreas("playerGraveyard").getX() + i * 2, gameAreas("playerGraveyard").getY() + (maxDeckHeight - 1) * 2 - i * 2)
			}
		}
	}

	def renderPlayerHand(container: GameContainer, game: StateBasedGame, g: Graphics) {
		val step = if (model.player.hand.size <= 1) 0 else scala.math.min(160, (gameAreas("playerHand").getWidth() - 160) / (model.player.hand.size - 1))
		for (i <- 0 until model.player.hand.size) {
			renderCreature(model.player.hand(i).asInstanceOf[Creature], g, gameAreas("playerHand").getX() + i * step, gameAreas("playerHand").getY())
		}
	}

	def renderPlayerBattlefield(container: GameContainer, game: StateBasedGame, g: Graphics) {
		val step = if (model.player.battlefield.size <= 1) 0 else scala.math.min(160, (gameAreas("playerBattlefield").getWidth() - 160) / (model.player.battlefield.size - 1))
		for (i <- 0 until model.player.battlefield.size) {
			renderCreature(model.player.battlefield(i).asInstanceOf[Creature], g, gameAreas("playerBattlefield").getX() + i * step, gameAreas("playerBattlefield").getY())
		}
	}

	def renderOpponentDeck(container: GameContainer, game: StateBasedGame, g: Graphics) {
		if (model.opponent.deck.size == 0) {
			renderEmptyDeck(g, gameAreas("opponentDeck").getX() + (maxDeckHeight - 1) * 2, gameAreas("opponentDeck").getY() + (maxDeckHeight - 1) * 2)
		} else {
			for (i <- 0 until scala.math.min(maxDeckHeight, model.opponent.deck.size)) {
				g.drawImage(textures("back"), gameAreas("opponentDeck").getX() + (maxDeckHeight - 1) * 2 - i * 2, gameAreas("opponentDeck").getY() + (maxDeckHeight - 1) * 2 - i * 2)
			}
		}
	}

	def renderOpponentGraveyard(container: GameContainer, game: StateBasedGame, g: Graphics) {
		if (model.opponent.graveyard.size == 0) {
			renderEmptyDeck(g, gameAreas("opponentGraveyard").getX(), gameAreas("opponentGraveyard").getY() + (maxDeckHeight - 1) * 2)
		} else {
			for (i <- 0 until scala.math.min(maxDeckHeight, model.opponent.graveyard.size)) {
				g.drawImage(textures("back"), gameAreas("opponentGraveyard").getX() + i * 2, gameAreas("opponentGraveyard").getY() + (maxDeckHeight - 1) * 2 - i * 2)
			}
		}
	}

	def renderOpponentHand(container: GameContainer, game: StateBasedGame, g: Graphics) {
		val step = if (model.opponent.hand.size <= 1) 0 else scala.math.min(160, (gameAreas("opponentHand").getWidth() - 160) / (model.opponent.hand.size - 1))
		for (i <- 0 until model.opponent.hand.size) {
			g.drawImage(textures("back"), gameAreas("opponentHand").getX() + i * step, gameAreas("opponentHand").getY())
		}
	}

	def renderOpponentBattlefield(container: GameContainer, game: StateBasedGame, g: Graphics) {
		val step = if (model.opponent.battlefield.size <= 1) 0 else scala.math.min(160, (gameAreas("opponentBattlefield").getWidth() - 160) / (model.opponent.battlefield.size - 1))
		for (i <- 0 until model.opponent.battlefield.size) {
			renderCreature(model.opponent.battlefield(i).asInstanceOf[Creature], g, gameAreas("opponentBattlefield").getX() + i * step, gameAreas("opponentBattlefield").getY())
		}
	}
}