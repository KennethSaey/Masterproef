package masterproef.views

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Rectangle
import scala.swing.Component
import scala.swing.MainFrame
import scala.swing.Reactor
import scala.swing.event.MouseReleased
import scala.swing.event.UIElementResized
import scala.swing.event.MouseMoved
import scala.swing.event.MouseWheelMoved
import java.awt.RenderingHints

class Gameboard(window: MainFrame, var width: Int, var height: Int) extends Component with Reactor {
	val Top: Int = 1
	val Bottom: Int = -1
	val Left: Int = 1
	val Right: Int = -1

	var zoom = false

	val player1: String = "Kenneth"
	val player2: String = "Opponent"
	val maxDeckCards: Int = 10
	var deck1: Int = 30
	var hand1: Int = 0
	var battlefield1: Int = 0
	var graveyard1: Int = 2
	var hover1: Int = -1
	var deck2: Int = 20
	var hand2: Int = 5
	var battlefield2: Int = 4
	var graveyard2: Int = 5
	val defaultCardWidth: Int = 150
	val defaultCardHeight: Int = 210
	val defaultCardRadius: Int = 20
	preferredSize = new Dimension(width, height)
	listenTo(window)
	listenTo(mouse.clicks)
	listenTo(mouse.moves)
	listenTo(mouse.wheel)

	reactions += {
		case we: UIElementResized => {
			width = scala.math.max(1200, window.size.width - 18)
			height = scala.math.max(800, window.size.height - 70)
			repaint
		}
		case me: MouseReleased => {
			val area = clickedArea(me.point.x, me.point.y)
			area match {
				case "playerDeck" => {
					if (deck1 > 0) {
						deck1 -= 1
						hand1 += 1
					}
				}
				case "playerHand" => {
					hand1 -= 1
					battlefield1 += 1
				}
				case "none" =>
			}
			repaint
		}
		case me: MouseMoved => {
			val newIndex = hoverIndex(me.point.x, me.point.y)
			if (newIndex != hover1) {
				hover1 = newIndex
				repaint
			}
		}
		case me: MouseWheelMoved => {
			if (me.rotation < 0) {
				zoom = true
				repaint
				deafTo(mouse.moves)
			} else {
				zoom = false
				repaint
				listenTo(mouse.moves)
			}
		}
	}

	def clickedArea(x: Int, y: Int): String = {
		val hover = hoverIndex(x, y)
		if (hover >= 0 && hover < hand1) {
			"playerHand"
		} else if (inPlayerDeckArea(x, y)) {
			"playerDeck"
		} else {
			"none"
		}
	}

	def inPlayerDeckArea(x: Int, y: Int): Boolean = {
		val area = new Rectangle(5, height - defaultCardHeight - 5 - 2 * maxDeckCards, defaultCardWidth + 2 * maxDeckCards - 1, defaultCardHeight + 2 * maxDeckCards - 1)
		area.contains(x, y)
	}

	override def paintComponent(g: Graphics2D) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
		paintPlayerDeck(g, 5, height - defaultCardHeight - 5 - 2 * maxDeckCards)
		paintOpponentDeck(g, width - defaultCardWidth - 5 - 2 * maxDeckCards, 5)
		paintPlayerGraveyard(g, width - defaultCardWidth - 5 - 2 * maxDeckCards, height - defaultCardHeight - 5 - 2 * maxDeckCards)
		paintOpponentGraveyard(g, 5, 5)
		paintOpponentHand(g, 10 + defaultCardWidth + 2 * maxDeckCards - 1, 0)
		val center = defaultCardHeight / 2 + ((height - 11 - defaultCardHeight) - defaultCardHeight / 2) / 2
		paintPlayerBattlefield(g, 10 + defaultCardWidth + 2 * maxDeckCards - 1, center + 15)
		paintOpponentBattlefield(g, 10 + defaultCardWidth + 2 * maxDeckCards - 1, center - 15 - defaultCardHeight)

		paintPlayerHand(g, 10 + defaultCardWidth + 2 * maxDeckCards - 1, height - 11 - defaultCardHeight)
	}

	def paintCardBack(g: Graphics2D, x: Int, y: Int, horizontal: Int = Left, vertical: Int = Top, cardWidth: Int = defaultCardWidth, cardHeight: Int = defaultCardHeight, cardRadius: Int = defaultCardRadius) {
		g.setColor(Color.WHITE)
		g.fillRoundRect(x + horizontal, y + vertical, cardWidth, cardHeight, cardRadius, cardRadius)
		g.setColor(Color.BLACK)
		g.fillRoundRect(x, y, cardWidth, cardHeight, cardRadius, cardRadius)
		g.setColor(new Color(83, 12, 0))
		g.fillRoundRect(x + 10, y + 10, cardWidth - 20, cardHeight - 20, cardRadius, cardRadius)
		g.setColor(new Color(242, 230, 70))
		g.fillOval(x + 30, y + 30, cardWidth - 60, cardHeight - 60)
	}

	def paintTabbedCardBack(g: Graphics2D, x: Int, y: Int, horizontal: Int = Left, vertical: Int = Top, cardWidth: Int = defaultCardWidth, cardHeight: Int = defaultCardHeight, cardRadius: Int = defaultCardRadius) {
		paintCardBack(g, x, y, horizontal, vertical, cardHeight, cardWidth, cardRadius)
	}

	def paintCardFace(g: Graphics2D, x: Int, y: Int, horizontal: Int = Left, vertical: Int = Top, cardWidth: Int = defaultCardWidth, cardHeight: Int = defaultCardHeight, cardRadius: Int = defaultCardRadius) {
		g.setColor(Color.WHITE)
		g.fillRoundRect(x + horizontal, y + vertical, cardWidth, cardHeight, cardRadius, cardRadius)
		g.setColor(Color.BLACK)
		g.fillRoundRect(x, y, cardWidth, cardHeight, cardRadius, cardRadius)
		g.setColor(new Color(83, 12, 0))
		g.fillRoundRect(x + 10, y + 10, cardWidth - 20, cardHeight - 20, cardRadius, cardRadius)
		g.setColor(new Color(242, 230, 70))
		g.fillOval(x + 15, y + 15, cardWidth - 30, 70)
	}

	def paintZoomedCardBack(g: Graphics2D, x: Int, y: Int, horizontal: Int = Left, vertical: Int = Top, cardWidth: Int = defaultCardWidth, cardHeight: Int = defaultCardHeight, cardRadius: Int = defaultCardRadius) {
		val offsetX = 10 + cardWidth + 2 * maxDeckCards - 1;
		val offsetY = y - 2 * cardHeight
		val handWidth = width - 2 * (5 + defaultCardWidth + 2 * maxDeckCards - 1) - 10
		val actualX = scala.math.min(scala.math.max(offsetX, x - cardWidth), offsetX + handWidth - 3 * cardWidth)
		val zoomedCardWidth = 3 * cardWidth
		val zoomedCardHeight = 3 * cardHeight
		val zoomedCardRadius = 3 * cardRadius
		g.setColor(Color.WHITE)
		g.fillRoundRect(actualX + horizontal, offsetY + vertical, zoomedCardWidth, zoomedCardHeight, zoomedCardRadius, zoomedCardRadius)
		g.setColor(Color.BLACK)
		g.fillRoundRect(actualX, offsetY, zoomedCardWidth, zoomedCardHeight, zoomedCardRadius, zoomedCardRadius)
		g.setColor(new Color(83, 12, 0))
		g.fillRoundRect(actualX + 3 * 10, offsetY + 3 * 10, zoomedCardWidth - 3 * 20, zoomedCardHeight - 3 * 20, zoomedCardRadius, zoomedCardRadius)
		g.setColor(new Color(242, 230, 70))
		g.fillOval(actualX + 3 * 30, offsetY + 3 * 30, zoomedCardWidth - 3 * 60, zoomedCardHeight - 3 * 60)
	}

	def paintPlayerDeck(g: Graphics2D, offsetX: Int, offsetY: Int) {
		for (i <- 0 until scala.math.min(maxDeckCards, deck1)) {
			paintCardBack(g, offsetX + (2 * (maxDeckCards - 1)) - 2 * i, offsetY + (2 * (maxDeckCards - 1)) - 2 * i)
		}
	}

	def paintOpponentDeck(g: Graphics2D, offsetX: Int, offsetY: Int) {
		for (i <- 0 until scala.math.min(maxDeckCards, deck2)) {
			paintCardBack(g, offsetX + 2 * i, offsetY + (2 * (maxDeckCards - 1)) - 2 * i, Right)
		}
	}

	def paintPlayerHand(g: Graphics2D, offsetX: Int, offsetY: Int) {
		val (preferredWidth, step) = calcSpaceNeeded
		val handWidth = width - 2 * (5 + defaultCardWidth + 2 * maxDeckCards - 1) - 10
		val newOffsetX = offsetX + (handWidth - preferredWidth) / 2
		val endX = newOffsetX + preferredWidth
		if (hover1 < 0) {
			for (i <- (0 until hand1).reverse) {
				paintCardBack(g, newOffsetX + i * step, offsetY)
			}
		} else if (hover1 >= hand1) {
			for (i <- 0 until hand1) {
				paintCardBack(g, newOffsetX + i * step, offsetY, Right)
			}
		} else if (hover1 == 0) {
			for (i <- 0 until hand1 - 1) {
				paintCardBack(g, endX - defaultCardWidth - i * step, offsetY)
			}
			if (zoom) {
				paintZoomedCardBack(g, newOffsetX, offsetY)
			} else {
				paintCardBack(g, newOffsetX, offsetY)
			}
		} else if (hover1 == hand1 - 1) {
			for (i <- 0 until hand1 - 1) {
				paintCardBack(g, newOffsetX + i * step, offsetY, Right)
			}
			if (zoom) {
				paintZoomedCardBack(g, endX - defaultCardWidth, offsetY, Right)
			} else {
				paintCardBack(g, endX - defaultCardWidth, offsetY, Right)
			}
		} else {
			for (i <- 0 until hover1) {
				paintCardBack(g, newOffsetX + i * step, offsetY, Right)
			}
			for (i <- 0 until (hand1 - hover1 - 1)) {
				paintCardBack(g, endX - defaultCardWidth - i * step, offsetY)
			}
			val center = ((endX - step * (hand1 - hover1 - 1)) - (newOffsetX + step * hover1)) / 2 + newOffsetX + step * hover1
			if (zoom) {
				paintZoomedCardBack(g, center - defaultCardWidth / 2, offsetY)
			} else {
				paintCardBack(g, center - defaultCardWidth / 2, offsetY)
			}
		}
	}

	def paintOpponentHand(g: Graphics2D, offsetX: Int, offsetY: Int) {
		val (preferredWidth, step) = calcOpponentHandSpaceNeeded
		val handWidth = width - 2 * (5 + defaultCardWidth + 2 * maxDeckCards - 1) - 10
		val newOffsetX = offsetX + (handWidth - preferredWidth) / 2
		for (i <- (0 until hand2).reverse) {
			paintCardBack(g, newOffsetX + i * step, -defaultCardHeight / 2)
		}
	}

	def paintPlayerBattlefield(g: Graphics2D, offsetX: Int, offsetY: Int) {
		paintPlayerTabbed(g, offsetX, offsetY)
		paintPlayerUntabbed(g, offsetX, offsetY)
	}

	def paintOpponentBattlefield(g: Graphics2D, offsetX: Int, offsetY: Int) {
		paintOpponentTabbed(g, offsetX, offsetY)
		paintOpponentUntabbed(g, offsetX, offsetY)

	}

	def paintPlayerUntabbed(g: Graphics2D, offsetX: Int, offsetY: Int) {
		val cardCount = battlefield1 - battlefield1 / 2
		if (cardCount > 0) {
			val handWidth = width - 2 * (5 + defaultCardWidth + 2 * maxDeckCards - 1) - 10
			val untabbedWidth = handWidth - defaultCardHeight - 10
			val untabbedOffsetX = offsetX + defaultCardHeight + 5
			val wide = cardCount * defaultCardWidth <= untabbedWidth
			val step = if (wide) untabbedWidth / cardCount else (untabbedWidth - defaultCardWidth) / (cardCount - 1)
			for (i <- 0 until (battlefield1 - battlefield1 / 2)) {
				paintCardBack(g, untabbedOffsetX + i * step + (if (wide) (step - defaultCardWidth) / 2 else 5), offsetY)
			}
		}
	}

	def paintOpponentUntabbed(g: Graphics2D, offsetX: Int, offsetY: Int) {
		val cardCount = battlefield2 - battlefield2 / 2
		if (cardCount > 0) {
			val handWidth = width - 2 * (5 + defaultCardWidth + 2 * maxDeckCards - 1) - 10
			val untabbedWidth = handWidth - defaultCardHeight - 10
			val untabbedOffsetX = offsetX + defaultCardHeight + 5
			val wide = cardCount * defaultCardWidth <= untabbedWidth
			val step = if (wide) untabbedWidth / cardCount else (untabbedWidth - defaultCardWidth) / (cardCount - 1)
			for (i <- 0 until (battlefield2 - battlefield2 / 2)) {
				paintCardBack(g, untabbedOffsetX + i * step + (if (wide) (step - defaultCardWidth) / 2 else 5), offsetY)
			}
		}
	}

	def paintPlayerTabbed(g: Graphics2D, offsetX: Int, offsetY: Int) {
		if (battlefield1 > 1) {
			val step = if (battlefield1 / 2 > 1) scala.math.min(defaultCardWidth / 2, (defaultCardHeight - defaultCardWidth) / (battlefield1 / 2 - 1)) else 0
			for (i <- 0 until battlefield1 / 2) {
				paintTabbedCardBack(g, offsetX, offsetY + i * step, Left, Bottom)
			}
		}
	}

	def paintOpponentTabbed(g: Graphics2D, offsetX: Int, offsetY: Int) {
		if (battlefield2 > 1) {
			val step = if (battlefield2 / 2 > 1) scala.math.min(defaultCardWidth / 2, (defaultCardHeight - defaultCardWidth) / (battlefield2 / 2 - 1)) else 0
			for (i <- 0 until battlefield2 / 2) {
				paintTabbedCardBack(g, offsetX, offsetY + defaultCardHeight - defaultCardWidth - i * step)
			}
		}
	}

	def paintPlayerGraveyard(g: Graphics2D, offsetX: Int, offsetY: Int) {
		for (i <- 0 until scala.math.min(maxDeckCards, graveyard1)) {
			paintCardBack(g, offsetX + 2 * i, offsetY + (2 * (maxDeckCards - 1)) - 2 * i, Right, Top)
		}
	}

	def paintOpponentGraveyard(g: Graphics2D, offsetX: Int, offsetY: Int) {
		for (i <- 0 until scala.math.min(maxDeckCards, graveyard2)) {
			paintCardBack(g, offsetX + (2 * (maxDeckCards - 1)) - 2 * i, offsetY + (2 * (maxDeckCards - 1)) - 2 * i, Left, Top)
		}
	}

	def hoverIndex(x: Int, y: Int): Int = {
		val offsetX = 10 + defaultCardWidth + 2 * maxDeckCards - 1
		val offsetY = height - 11 - defaultCardHeight
		val (preferredWidth, step) = calcSpaceNeeded
		val handWidth = width - 2 * (5 + defaultCardWidth + 2 * maxDeckCards - 1) - 10
		val newOffsetX = offsetX + (handWidth - preferredWidth) / 2
		val rect = new Rectangle(newOffsetX - (if (hover1 > 0) 1 else 0), offsetY, preferredWidth + (if (hover1 < hand1 - 1) 1 else 0), defaultCardHeight + 1)
		if (!rect.contains(x, y)) {
			if (hover1 < hand1 / 2) {
				-1
			} else {
				hand1
			}
		} else {
			val translatedX = x - newOffsetX
			if (hover1 < 0) {
				scala.math.max(0, (translatedX - step - 1) / (step))
			} else if (hover1 >= hand1) {
				scala.math.min(hand1 - 1, (translatedX + 1) / (step))
			} else if (hover1 == 0) {
				if (translatedX <= defaultCardWidth) {
					0
				} else {
					scala.math.max(1, hand1 - 1 - (preferredWidth + 1 - translatedX) / step)
				}
			} else if (hover1 == hand1 - 1) {
				if (translatedX >= (preferredWidth - defaultCardWidth - 1)) {
					hand1 - 1
				} else {
					scala.math.min(hand1 - 2, (translatedX + 1) / step)
				}
			} else {
				val center = ((preferredWidth - step * (hand1 - hover1 - 1)) - (step * hover1)) / 2 + step * hover1
				if (translatedX >= center - defaultCardWidth / 2 && translatedX <= center - defaultCardWidth / 2 + defaultCardWidth) {
					hover1
				} else if (translatedX < center - defaultCardWidth / 2) {
					scala.math.min(hover1 - 1, (translatedX + 1) / (step))
				} else {
					scala.math.max(hover1 + 1, hand1 - 1 - (preferredWidth + 1 - translatedX) / step)
				}
			}
		}
	}

	def calcOpponentHandSpaceNeeded(): (Int, Int) = {
		var preferredWidth = defaultCardWidth / 2 * (hand2 + 1)
		val handWidth = width - 2 * (5 + defaultCardWidth + 2 * maxDeckCards - 1) - 10
		if (preferredWidth > handWidth) {
			preferredWidth = if (hand2 > 1) ((handWidth - defaultCardWidth) / (hand2 - 1)) * (hand2 - 1) + defaultCardWidth else defaultCardWidth
		}
		val step = if (hand2 > 1) (preferredWidth - defaultCardWidth) / (hand2 - 1) else 1
		(preferredWidth, step)
	}

	def calcSpaceNeeded(): (Int, Int) = {
		var preferredWidth = defaultCardWidth / 2 * (hand1 + 1)
		val handWidth = width - 2 * (5 + defaultCardWidth + 2 * maxDeckCards - 1) - 10
		if (preferredWidth > handWidth) {
			preferredWidth = ((handWidth - defaultCardWidth) / (hand1 - 1)) * (hand1 - 1) + defaultCardWidth
		}
		if (hover1 < 0 || hover1 >= hand1) {
			val step = if (hand1 > 1) (preferredWidth - defaultCardWidth) / (hand1 - 1) else 1
			(preferredWidth, step)
		} else if (hover1 == 0 || hover1 == hand1 - 1) {
			val step = if (hand1 > 2) (preferredWidth - 13 * defaultCardWidth / 8) / (hand1 - 2) else 1
			(preferredWidth, step)
		} else {
			val step = if (hand1 > 3) (preferredWidth - 9 * defaultCardWidth / 4) / (hand1 - 3) else 1
			(preferredWidth, step)
		}
	}
}