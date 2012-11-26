package masterproef.views

import java.awt.Graphics2D
import scala.swing.Component
import java.awt.Color
import scala.swing.Reactor
import scala.swing.event.MouseMoved
import java.awt.Point
import scala.swing.event.MouseWheelMoved
import scala.swing.event.MouseExited
import scala.swing.Panel
import java.awt.Dimension

class HandView(cardCount: Int = 10) extends Component with Reactor {

	var selectedIndex = cardCount
	var cardWidth: Int = 150
	var cardHeight: Int = 210
	var borderRadius: Int = 20
	listenTo(mouse.moves)
	listenTo(mouse.wheel)
	var zoom = false
	preferredSize = new Dimension(cardWidth / 2 * (cardCount + 2) + 1, cardHeight + 1)
	reactions += {
		case MouseMoved(source, point, modifiers) => {
			val hoverIndex = cardHoverIndex(point.x)
			//			println(point.x + " => " + hoverIndex + "(actual index = " + selectedIndex + ")")
			if (hoverIndex != selectedIndex) {
				selectedIndex = scala.math.max(-1, scala.math.min(hoverIndex, cardCount + 1))
				repaint
			}
		}
		case MouseWheelMoved(source, point, modifiers, rotation) => {
			if (rotation < 0) {
//				deafTo(mouse.moves)
				zoom = true
				preferredSize = new Dimension(preferredSize.width, cardHeight * 3 + 1)
			} else {
//				listenTo(mouse.moves)
				zoom = false
				preferredSize = new Dimension(preferredSize.width, cardHeight + 1)
				val hoverIndex = cardHoverIndex(point.x)
				if (hoverIndex != selectedIndex) {
					selectedIndex = scala.math.max(-1, scala.math.min(hoverIndex, cardCount + 1))
				}
			}
			repaint
			revalidate
		}
		case MouseExited(source, point, modifiers) => {
			selectedIndex = cardCount
			repaint
		}
	}

	override def paintComponent(g: Graphics2D) {
		super.paintComponent(g)
		for (i <- 0 until scala.math.min(selectedIndex, cardCount)) {
			paintCard(g, i)
		}
		for (i <- (selectedIndex + 1 until cardCount).reverse) {
			paintCard(g, i)
		}
		if (selectedIndex < cardCount && selectedIndex > -1) {
			if (zoom) {
				paintZoomedCard(g)
			} else {
				paintSelectedCard(g)
			}
		}
	}

	def paintCard(g: Graphics2D, index: Int) {
		if (index < selectedIndex) {
			val offsetX = (index + 2) * cardWidth / 2
			val offsetY = if (zoom) cardHeight * 2 else 0
			g.setColor(Color.WHITE)
			g.fillRoundRect(offsetX, offsetY + 1, cardWidth, cardHeight, borderRadius, borderRadius)
			g.setColor(Color.BLACK)
			g.fillRoundRect(offsetX + 1, offsetY, cardWidth, cardHeight, borderRadius, borderRadius)
			g.setColor(new Color(83, 12, 0))
			g.fillRoundRect(offsetX + 11, offsetY + 10, cardWidth - 20, cardHeight - 20, borderRadius, borderRadius)
			g.setColor(new Color(242, 230, 70))
			g.fillOval(offsetX + 31, offsetY + 30, cardWidth - 60, cardHeight - 60)
		} else {
			val offsetX = (index + 3) * cardWidth / 2
			val offsetY = if (zoom) cardHeight * 2 else 0
			g.setColor(Color.WHITE)
			g.fillRoundRect(offsetX + 1, offsetY + 1, cardWidth, cardHeight, borderRadius, borderRadius)
			g.setColor(Color.BLACK)
			g.fillRoundRect(offsetX, offsetY, cardWidth, cardHeight, borderRadius, borderRadius)
			g.setColor(new Color(83, 12, 0))
			g.fillRoundRect(offsetX + 10, offsetY + 10, cardWidth - 20, cardHeight - 20, borderRadius, borderRadius)
			g.setColor(new Color(242, 230, 70))
			g.fillOval(offsetX + 30, offsetY + 30, cardWidth - 60, cardHeight - 60)
		}
	}

	def paintZoomedCard(g: Graphics2D) {
		println("Painting zoomed card")
		val offsetX = (selectedIndex + 1) * cardWidth / 2 - cardWidth / 4
		g.setColor(Color.WHITE)
		g.fillRoundRect(offsetX, 1, cardWidth * 3 + 2, cardHeight * 3, borderRadius * 3, borderRadius * 3)
		g.setColor(Color.BLACK)
		g.fillRoundRect(offsetX + 1, 0, cardWidth * 3, cardHeight * 3, borderRadius * 3, borderRadius * 3)
		g.setColor(new Color(83, 12, 0))
		g.fillRoundRect(offsetX + 31, 30, (cardWidth - 20) * 3, (cardHeight - 20) * 3, borderRadius * 3, borderRadius * 3)
		g.setColor(new Color(242, 230, 70))
		g.fillOval(offsetX + 91, 90, (cardWidth - 60) * 3, (cardHeight - 60) * 3)
	}

	def paintSelectedCard(g: Graphics2D) {
		val offsetX = (selectedIndex + 1) * cardWidth / 2 + 3 * cardWidth / 4
		g.setColor(Color.WHITE)
		g.fillRoundRect(offsetX, 1, cardWidth + 2, cardHeight, borderRadius, borderRadius)
		g.setColor(Color.BLACK)
		g.fillRoundRect(offsetX + 1, 0, cardWidth, cardHeight, borderRadius, borderRadius)
		g.setColor(new Color(83, 12, 0))
		g.fillRoundRect(offsetX + 11, 10, cardWidth - 20, cardHeight - 20, borderRadius, borderRadius)
		g.setColor(new Color(242, 230, 70))
		g.fillOval(offsetX + 31, 30, cardWidth - 60, cardHeight - 60)
	}

	def cardHoverIndex(x: Int): Int = {
		val selectedStartX = if (selectedIndex < cardCount) {
			cardWidth / 4 + (selectedIndex + 2) * cardWidth / 2
		} else {
			(cardCount) * cardWidth / 2 + cardWidth
		}
		if (selectedIndex < 0) {
			if (x >= 6 * cardWidth / 4) 0 else -1
		} else if (selectedIndex >= cardCount) {
			if (x >= (cardCount + 3) * cardWidth / 2) cardCount else cardCount - 1
		} else if (x >= selectedStartX && x < selectedStartX + cardWidth + 1) {
			selectedIndex
		} else if (x < selectedStartX) {
			scala.math.min((x - cardWidth) / (cardWidth / 2), selectedIndex - 1)
		} else {
			(x - (selectedStartX + cardWidth + 1)) / (cardWidth / 2) + selectedIndex + 1
		}
	}
}