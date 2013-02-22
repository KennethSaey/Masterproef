package masterproef.views

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import scala.swing.Component
import scala.swing.Reactor
import scala.swing.event.MouseReleased

class BattlefieldView(gameboard: Gameboard, var cardCount: Int = 10) extends Component with Reactor {
	var selectedIndex = cardCount
	var cardWidth: Int = 150
	var cardHeight: Int = 210
	var borderRadius: Int = 20
	var zoom = false
	val cardsPerRow = 5
	val offsetY1 = 0
	val offsetY2 = cardHeight + 10
	preferredSize = new Dimension(0, 2 * cardHeight + 20)
	listenTo(mouse.clicks)
	reactions += {
		case e: MouseReleased => {
			if (cardCount > 0) {
				cardCount -= 1
				repaint
				gameboard.graveyard.cardCount += 1
				gameboard.graveyard.repaint
			}
		}
	}

	override def paintComponent(g: Graphics2D) {
		if (cardCount <= cardsPerRow) {
			for (i <- 0 until cardCount) {
				val offsetX = size.width / 2 - (cardCount - 2 * i) * (cardHeight + 10) / 2
				paintCard(g, offsetX, offsetY1)
			}
		} else if (cardCount <= 2 * cardsPerRow) {
			for (i <- 0 until cardsPerRow) {
				val offsetX = size.width / 2 - (cardsPerRow - 2 * i) * (cardHeight + 10) / 2
				paintCard(g, offsetX, offsetY1)
			}
			for (i <- cardsPerRow until cardCount) {
				val offsetX = size.width / 2 - (cardCount - cardsPerRow - 2 * (i - cardsPerRow)) * (cardHeight + 10) / 2
				paintCard(g, offsetX, offsetY2)
			}
		} else if (cardCount > 2 * cardsPerRow) {
			for (i <- 0 until (cardCount + 1) / 2) {
				val offsetX = size.width / 2 - 5 * (cardHeight + 10) / 2 + i * ((4 * (cardHeight + 10)) / ((cardCount + 1) / 2 - 1))
				paintCard(g, offsetX, offsetY1)
			}
			val row2CardCount = cardCount - (cardCount + 1) / 2
			for (i <- (cardCount + 1) / 2 until cardCount) {
				val offsetX = size.width / 2 - 5 * (cardHeight + 10) / 2 + (i - (cardCount + 1) / 2) * ((4 * (cardHeight + 10)) / (row2CardCount - 1))
				paintCard(g, offsetX, offsetY2)
			}
		}
	}

	def paintCard(g: Graphics2D, offsetX: Int, offsetY: Int) {
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