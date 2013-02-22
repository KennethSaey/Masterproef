package masterproef.views

import java.awt.Color
import java.awt.Graphics2D
import scala.swing.event.MouseReleased

class GraveyardView(gameboard: Gameboard, _cardCount: Int = 10) extends DeckView(gameboard, _cardCount) {

	reactions -= released
	reactions += {
		case e: MouseReleased => {
			if (cardCount > 0) {
				val cc = cardCount
				cardCount = 0
				repaint
				gameboard.deck.cardCount += cc
				gameboard.deck.repaint
			}
		}
	}

	override def paintCard(g: Graphics2D, level: Int): Unit = {
		val offsetX = 2 * (scala.math.min(10, cardCount) - level - 1)
		val offsetY = 19 - offsetX
		g.setColor(Color.WHITE)
		g.fillRoundRect(offsetX, offsetY + 1, cardWidth, cardHeight, borderRadius, borderRadius)
		g.setColor(Color.BLACK)
		g.fillRoundRect(offsetX + 1, offsetY, cardWidth, cardHeight, borderRadius, borderRadius)
		if (level == 0) {
			g.setColor(new Color(83, 12, 0))
			g.fillRoundRect(offsetX + 10, offsetY + 10, cardWidth - 20, cardHeight - 20, borderRadius, borderRadius)
			g.setColor(new Color(242, 230, 70))
			g.fillOval(offsetX + 30, offsetY + 30, cardWidth - 60, cardHeight - 60)
		}
	}
}