package masterproef.views

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D

import scala.swing.Component
import scala.swing.Reactor
import scala.swing.event.Event
import scala.swing.event.MouseReleased

class DeckView(gameboard: Gameboard, var cardCount: Int = 10) extends Component with Reactor {

	var cardWidth: Int = 150
	var cardHeight: Int = 210
	var borderRadius: Int = 20

	listenTo(mouse.clicks);
	val released: PartialFunction[Event, Unit] = {
		case e: MouseReleased => {
			if (cardCount > 0) {
				cardCount -= 1
				repaint
				gameboard.hand.cardCount += 1
				gameboard.hand.selectedIndex = gameboard.hand.cardCount
				gameboard.hand.repaint
			}
		}
	}
	reactions += released

	override def paintComponent(g: Graphics2D) {
		preferredSize = new Dimension(cardWidth + 20, cardHeight + 20)
		for (level <- (0 to scala.math.min(9, cardCount - 1)).reverse) {
			paintCard(g, level)
		}
	}

	def paintCard(g: Graphics2D, level: Int): Unit = {
		val offset = 19 - 2 * (scala.math.min(10, cardCount) - level)
		g.setColor(Color.WHITE)
		g.fillRoundRect(offset + 1, offset + 1, cardWidth, cardHeight, borderRadius, borderRadius)
		g.setColor(Color.BLACK)
		g.fillRoundRect(offset, offset, cardWidth, cardHeight, borderRadius, borderRadius)
		if (level == 0) {
			g.setColor(new Color(83, 12, 0))
			g.fillRoundRect(offset + 10, offset + 10, cardWidth - 20, cardHeight - 20, borderRadius, borderRadius)
			g.setColor(new Color(242, 230, 70))
			g.fillOval(offset + 30, offset + 30, cardWidth - 60, cardHeight - 60)
		}
	}
}