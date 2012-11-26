package masterproef.views

import scala.swing.Component
import java.awt.Graphics2D
import java.awt.Color
import java.awt.Dimension
import java.awt.Shape
import java.awt.Font

class DeckView(cardCount: Int = 10) extends Component {

	var cardWidth: Int = 150
	var cardHeight: Int = 210
	var borderRadius: Int = 20
	opaque = true

	override def paintComponent(g: Graphics2D) {
//		println("DeckView.paintComponent")
		preferredSize = new Dimension(cardWidth + 20, cardHeight + 20)
		for (level <- (0 to scala.math.min(9, cardCount - 1)).reverse) {
			paintCard(g, level)
		}
	}

	def paintCard(g: Graphics2D, level: Int): Unit = {
		val offset = 19 - 2 * (cardCount - level)
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