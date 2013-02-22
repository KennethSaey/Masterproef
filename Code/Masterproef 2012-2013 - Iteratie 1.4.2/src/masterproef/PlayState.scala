package masterproef

import org.newdawn.slick.state.GameState
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.state.BasicGameState
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.Input
import org.newdawn.slick.Image
import org.lwjgl.input.Mouse
import org.newdawn.slick.Sound
import masterproef.cards.Deck
import scala.io.Source
import masterproef.parsers.CardParser
import masterproef.players.Player
import masterproef.cards.CreatureCard
import org.newdawn.slick.Color
import org.newdawn.slick.TrueTypeFont
import java.awt.Font
import scala.collection.mutable.Map
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer
import org.newdawn.slick.geom.Rectangle

class PlayState extends BasicGameState {

	var game: StateBasedGame = null
	var container: GameContainer = null

	var textures: Array[Image] = null
	var images: HashMap[String, Image] = null
	var imageIndex: Int = 0
	var x: Int = 0
	var y: Int = 0
	var snap: Sound = null
	var clap: Sound = null

	var playerBattlefieldX: Int = 0
	var playerBattlefieldY: Int = 0
	var opponentBattlefieldX: Int = 0
	var opponentBattlefieldY: Int = 0

	val normalFont = new Font("Calibri", Font.PLAIN, 8)
	val normal = new TrueTypeFont(normalFont, true)
	val largeFont = new Font("Calibri", Font.PLAIN, 18)
	val large = new TrueTypeFont(largeFont, true)

	var continueArea: Rectangle = null
	var continueHover = false

	var gameInitialized = false

	def getID(): Int = Masterproef.PLAY_ID

	def init(container: GameContainer, game: StateBasedGame) {
		this.game = game
		this.container = container
		continueArea = new Rectangle(10, container.getHeight() / 2 - 15, 100, 30)
		textures = Array(
			new Image("masterproef/data/images/textures/CardBack_small.png"),
			new Image("masterproef/data/images/textures/CardBack_hover_small.png"),
			new Image("masterproef/data/images/textures/CardBack_selectable_small.png"),
			new Image("masterproef/data/images/textures/CardBack_selected_small.png"),
			new Image("masterproef/data/images/textures/CardBack_unselectable_small.png"),
			new Image("masterproef/data/images/textures/CardFront_small.png")
		);
		images = new HashMap
		for (cardName <- getCardnames) {
			images.put(cardName, new Image("masterproef/data/images/creatures/" + cardName + ".jpg"))
		}
		snap = new Sound("masterproef/data/sounds/snap.wav")
		clap = new Sound("masterproef/data/sounds/clap.wav")
		//		println("Initialized PlayState")
		playerBattlefieldX = textures(0).getWidth() + 15
		playerBattlefieldY = container.getHeight() / 2 + (container.getHeight() / 2 - textures(0).getHeight() * 2 - 5) / 2
		opponentBattlefieldX = textures(0).getWidth() + 15
		opponentBattlefieldY = textures(0).getHeight() + 5 + (container.getHeight() / 2 - textures(0).getHeight() * 2 - 5) / 2
	}

	def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		renderContinueButton(container, game, g)
		renderPlayerDeck(container, game, g)
		renderPlayerHand(container, game, g)
		renderPlayerBattlefield(container, game, g)
		renderPlayerGraveyard(container, game, g)
		renderOpponentDeck(container, game, g)
		renderOpponentHand(container, game, g)
		renderOpponentBattlefield(container, game, g)
		renderOpponentGraveyard(container, game, g)
	}

	def renderContinueButton(container: GameContainer, game: StateBasedGame, g: Graphics) {
		if (continueHover) {
			g.setColor(Color.cyan)
		} else {
			g.setColor(Color.green)
		}
		g.setFont(large)
		val xOffset = 10
		val yOffset = container.getHeight() / 2 - 15
		g.fillRoundRect(xOffset, yOffset, 100, 30, 5)
		val string = "Continue"
		g.setColor(Color.black)
		g.drawString(string, xOffset + (100 - g.getFont().getWidth(string)) / 2, yOffset + (30 - g.getFont().getHeight(string)) / 2)
	}

	def renderPlayerDeck(container: GameContainer, game: StateBasedGame, g: Graphics) {
		if (Game.currentPlayer.deck.size > 0) {
			g.setFont(large)
			g.setColor(Color.black)
			val x = 5
			val y = container.getHeight() - textures(0).getHeight() - 5
			val cardCount = "" + Game.currentPlayer.deck.size
			g.drawImage(textures(0), x, y)
			g.drawString(cardCount, x + (textures(0).getWidth() - g.getFont().getWidth(cardCount)) / 2, y + (textures(0).getHeight() - g.getFont().getHeight(cardCount)) / 2)
			g.setColor(Color.white)
			g.drawString(Game.currentPlayer.name, x, y - 30)
		}
	}

	def renderOpponentDeck(container: GameContainer, game: StateBasedGame, g: Graphics) {
		if (Game.currentOpponent.deck.size > 0) {
			g.setFont(large)
			g.setColor(Color.black)
			val x = 5
			val y = 5
			val cardCount = "" + Game.currentOpponent.deck.size
			g.drawImage(textures(0), x, y)
			g.drawString(cardCount, x + (textures(0).getWidth() - g.getFont().getWidth(cardCount)) / 2, y + (textures(0).getHeight() - g.getFont().getHeight(cardCount)) / 2)
			g.setColor(Color.white)
			g.drawString(Game.currentOpponent.name, x, y + textures(0).getHeight() + 10)
		}
	}

	def renderPlayerHand(container: GameContainer, game: StateBasedGame, g: Graphics) {
		g.setColor(Color.black)
		val xOffset = 15 + textures(0).getWidth()
		val yOffset = container.getHeight() - textures(0).getHeight() - 5
		val step = 5 + textures(5).getWidth()
		val hand = Game.currentPlayer.hand
		for (i <- 0 until hand.size) {
			g.drawImage(textures(5), xOffset + i * step, yOffset)
			g.setFont(normal)
			g.drawString(hand(i).name, xOffset + i * step + 22, yOffset + 17)
			g.drawImage(images(hand(i).name), xOffset + i * step + 20, yOffset + 28, xOffset + i * step + 130, yOffset + 109, 0, 0, 220, 161)
			g.setFont(large)
			var s = hand(i).currentDamage + " / " + hand(i).currentHealth
			val sWidth = g.getFont().getWidth(s)
			val sHeight = g.getFont().getWidth(s)
			g.drawString(s, xOffset + i * step + (150 - sWidth) / 2, yOffset + 124 /*3 + (63 - sHeight)/2*/ )
			var j = 1
			for (ability <- hand(i).abilities) {
				g.drawString(ability, xOffset + i * step + (150 - g.getFont().getWidth(ability)) / 2, yOffset + 124 + j * 20 /*(sHeight)*/ )
				j += 1
			}
		}
	}

	def renderOpponentHand(container: GameContainer, game: StateBasedGame, g: Graphics) {
		val xOffset = 15 + textures(0).getWidth()
		val yOffset = 5
		val step = 5 + textures(0).getWidth()
		val hand = Game.currentOpponent.hand
		for (i <- 0 until hand.size) {
			g.drawImage(textures(0), xOffset + i * step, yOffset)
		}
	}

	def renderPlayerBattlefield(container: GameContainer, game: StateBasedGame, g: Graphics) {
		g.setColor(Color.black)
		val step = 5 + textures(5).getWidth()
		val battlefield = Game.currentPlayer.battlefield
		for (i <- 0 until battlefield.size) {
			g.drawImage(textures(5), playerBattlefieldX + i * step, playerBattlefieldY)
			g.setFont(normal)
			g.drawString(battlefield(i).name, playerBattlefieldX + i * step + 22, playerBattlefieldY + 17)
			g.drawImage(images(battlefield(i).name), playerBattlefieldX + i * step + 20, playerBattlefieldY + 28, playerBattlefieldX + i * step + 130, playerBattlefieldY + 109, 0, 0, 220, 161)
			g.setFont(large)
			var s = battlefield(i).currentDamage + " / " + battlefield(i).currentHealth
			val sWidth = g.getFont().getWidth(s)
			val sHeight = g.getFont().getWidth(s)
			g.drawString(s, playerBattlefieldX + i * step + (150 - sWidth) / 2, playerBattlefieldY + 124 /*3 + (63 - sHeight)/2*/ )
			var j = 1
			for (ability <- battlefield(i).abilities) {
				g.drawString(ability, playerBattlefieldX + i * step + (150 - g.getFont().getWidth(ability)) / 2, playerBattlefieldY + 124 + j * 20 /*(sHeight)*/ )
				j += 1
			}
		}
	}

	def renderOpponentBattlefield(container: GameContainer, game: StateBasedGame, g: Graphics) {
		g.setColor(Color.black)
		val step = 5 + textures(5).getWidth()
		val battlefield = Game.currentOpponent.battlefield
		for (i <- 0 until battlefield.size) {
			g.drawImage(textures(5), opponentBattlefieldX + i * step, opponentBattlefieldY)
			g.setFont(normal)
			g.drawString(battlefield(i).name, opponentBattlefieldX + i * step + 22, opponentBattlefieldY + 17)
			g.drawImage(images(battlefield(i).name), opponentBattlefieldX + i * step + 20, opponentBattlefieldY + 28, opponentBattlefieldX + i * step + 130, opponentBattlefieldY + 109, 0, 0, 220, 161)
			g.setFont(large)
			var s = battlefield(i).currentDamage + " / " + battlefield(i).currentHealth
			val sWidth = g.getFont().getWidth(s)
			val sHeight = g.getFont().getWidth(s)
			g.drawString(s, opponentBattlefieldX + i * step + (150 - sWidth) / 2, opponentBattlefieldY + 124 /*3 + (63 - sHeight)/2*/ )
			var j = 1
			for (ability <- battlefield(i).abilities) {
				g.drawString(ability, opponentBattlefieldX + i * step + (150 - g.getFont().getWidth(ability)) / 2, opponentBattlefieldY + 124 + j * 20 /*(sHeight)*/ )
				j += 1
			}
		}
	}

	def renderPlayerGraveyard(container: GameContainer, game: StateBasedGame, g: Graphics) {
		if (Game.currentPlayer.graveyard.size > 0) {
			g.setFont(large)
			g.setColor(Color.black)
			val x = container.getWidth() - textures(0).getWidth() - 5
			val y = container.getHeight() - textures(0).getHeight() - 5
			val cardCount = "" + Game.currentPlayer.graveyard.size
			g.drawImage(textures(0), x, y)
			g.drawString(cardCount, x + (textures(0).getWidth() - g.getFont().getWidth(cardCount)) / 2, y + (textures(0).getHeight() - g.getFont().getHeight(cardCount)) / 2)
		}
	}

	def renderOpponentGraveyard(container: GameContainer, game: StateBasedGame, g: Graphics) {
		if (Game.currentOpponent.graveyard.size > 0) {
			g.setFont(large)
			g.setColor(Color.black)
			val x = container.getWidth() - textures(0).getWidth() - 5
			val y = 5
			val cardCount = "" + Game.currentOpponent.graveyard.size
			g.drawImage(textures(0), x, y)
			g.drawString(cardCount, x + (textures(0).getWidth() - g.getFont().getWidth(cardCount)) / 2, y + (textures(0).getHeight() - g.getFont().getHeight(cardCount)) / 2)
		}
	}

	def update(container: GameContainer, game: StateBasedGame, delta: Int) {
	}

	override def keyPressed(key: Int, c: Char) {
		if (key == Input.KEY_BACK || key == Input.KEY_ESCAPE) {
			game.enterState(Masterproef.MENU_ID)
		}
	}

	override def mouseReleased(button: Int, x: Int, y: Int) {
		if (button == 3) {
			game.enterState(Masterproef.MENU_ID)
		} else if (continueArea.contains(x, y) && button == 0) {
			Game.executeNextPhase
		}
	}

	override def mouseMoved(oldx: Int, oldy: Int, newx: Int, newy: Int) {
		if (continueArea.contains(newx, newy) && !continueArea.contains(oldx, oldy)) {
			continueHover = true
		} else if (!continueArea.contains(newx, newy) && continueArea.contains(oldx, oldy)) {
			continueHover = false
		}
	}

	override def enter(container: GameContainer, game: StateBasedGame) {
		//		val input = container.getInput()
		//		x = Mouse.getX() - images(imageIndex).getWidth() / 2
		//		y = container.getHeight() - Mouse.getY() - images(imageIndex).getHeight() / 2
		if (!gameInitialized) {
			Game.reset
			var allCards = getAllCards()
			allCards.shuffle
			val cardCount = allCards.size
			val me = new Player(game.asInstanceOf[Masterproef].playerName, 10)
			me.addToDeck(allCards.draw(cardCount / 2))
			val opponent = new Player("Opponent", 10)
			opponent.addToDeck(allCards)
			Game.addPlayer(me)
			Game.addPlayer(opponent)
			Game.currentPlayerIndex = 0
			Game.ended = false
			Game.currentPlayer.draw(3)
			Game.currentOpponent.draw(3)
		}
	}

	//	override def mouseDragged(oldx: Int, oldy: Int, newx: Int, newy: Int) {
	//		x = newx - images(imageIndex).getWidth() / 2
	//		y = newy - images(imageIndex).getHeight() / 2
	//	}

	def getAllCards(): Deck = {
		val cards = new Deck
		for (line <- Source.fromFile("src/masterproef/data/cards.txt").getLines) {
			val card = CardParser.parse(line).get
			cards += card.copy
			cards += card.copy
			cards += card.copy
			cards += card.copy
		}
		cards
	}

	def getCardnames(): ArrayBuffer[String] = {
		val names = new ArrayBuffer[String]
		for (line <- Source.fromFile("src/masterproef/data/cards.txt").getLines) {
			val card = CardParser.parse(line).get
			names += card.name
		}
		names
	}
}