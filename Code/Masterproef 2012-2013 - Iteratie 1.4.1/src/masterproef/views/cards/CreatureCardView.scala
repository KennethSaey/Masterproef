package masterproef.views.cards

import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.RenderingHints
import scala.swing.Component
import scala.swing.Reactor
import masterproef.cards.CreatureCard
import masterproef.cards.CreatureCardHealed
import masterproef.cards.CreatureCardOwnerChanged
import masterproef.cards.CreatureCardStateChanged
import masterproef.cards.CreatureCardTookDamage
import masterproef.cards.CreatureCardFlipped
import scala.swing.Publisher
import scala.swing.event.Event
import java.awt.Dimension
import javax.swing.border.LineBorder
import javax.swing.BorderFactory
import scala.swing.Swing

object CreatureCardView {
	val RED: Color = new Color(83, 12, 0)
	val YELLOW: Color = new Color(242, 230, 70)
}

case class CreatureCardViewChanged extends Event

class CreatureCardView(val creatureCard: CreatureCard, x: Int, y: Int, val gEnv: Graphics2D = null) extends Component with Reactor with Publisher {

	private var _xOffset: Int = x
	private var _yOffset: Int = y
	private var _cardWidth: Int = 150 * 3
	private var _cardHeight: Int = 210 * 3
	private var _cardRadius: Int = 20 * 3
	opaque = true
	border = Swing.EmptyBorder(50)
	listenTo(creatureCard)
	reactions += {
		case e: CreatureCardStateChanged => {
			println("CardView: CreatureCardStateChanged")
			repaint
		}
		case e: CreatureCardOwnerChanged => {
			println("CardView: CreatureCardOwnerChanged")
			repaint
		}
		case e: CreatureCardTookDamage => {
			println("CardView: CreatureCardTookDamage")
			repaint(new Rectangle(_xOffset + _cardWidth - 9 * _cardRadius / 4, _yOffset + _cardHeight - 5 * _cardRadius / 4, 3 * _cardRadius / 2, _cardRadius / 2))
		}
		case e: CreatureCardHealed => {
			println("CardView: CreatureCardHealed")
			repaint(new Rectangle(_xOffset + _cardWidth - 9 * _cardRadius / 4, _yOffset + _cardHeight - 5 * _cardRadius / 4, 3 * _cardRadius / 2, _cardRadius / 2))
		}
		case e: CreatureCardFlipped => {
			println("CardView: Flipping " + creatureCard.name)
			publish(new CreatureCardViewChanged)
//			paintComponent(gEnv)
			repaint
		}
	}

	def xOffset = _xOffset
	def yOffset = _yOffset
	def cardWidth = _cardWidth
	def cardHeight = _cardHeight
	def cardRadius = _cardRadius

	def xOffset_=(newOffset: Int) = {
		if (newOffset != xOffset) {
			_xOffset = newOffset
			repaint
		}
	}
	def yOffset_=(newOffset: Int) = {
		if (newOffset != yOffset) {
			_yOffset = newOffset
			repaint
		}
	}
	def position_=(x: Int, y: Int) {
		if (x != _xOffset || y != _yOffset) {
			_xOffset = x
			_yOffset = y
			repaint
		}
	}
	def cardWidth_=(newWidth: Int) = {
		if (newWidth != _cardWidth) {
			_cardWidth = newWidth
			repaint
		}
	}
	def cardHeight_=(newHeight: Int) = {
		if (newHeight != _cardHeight) {
			_cardHeight = newHeight
			repaint
		}
	}
	def cardRadius_=(newRadius: Int) = {
		if (newRadius != _cardRadius) {
			_cardRadius = newRadius
			repaint
		}
	}

	override def paintComponent(g: Graphics2D) {
		val gCopy = g.create().asInstanceOf[Graphics2D]
		gCopy.fillRect(10, 10, 50, 50)
//		gCopy.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
//		creatureCard.face match {
//			case CreatureCard.CardFace.FRONT => paintFront(gCopy)
//			case CreatureCard.CardFace.BACK => paintBack(gCopy)
//		}
		gCopy.dispose()
	}

	def paintFront(g: Graphics2D) {
		// Border
		g.setColor(Color.WHITE)
		g.fillRoundRect(_xOffset, _yOffset, _cardWidth, _cardHeight, _cardRadius, _cardRadius)
		g.setColor(Color.BLACK)
		g.fillRoundRect(_xOffset + 1, _yOffset + 1, _cardWidth - 2, _cardHeight - 2, _cardRadius, _cardRadius)
		// Background
		g.setColor(CreatureCardView.RED)
		g.fillRoundRect(_xOffset + _cardRadius / 2, _yOffset + _cardRadius / 2, _cardWidth - _cardRadius, _cardHeight - _cardRadius, _cardRadius, _cardRadius)

		g.setColor(CreatureCardView.YELLOW)
		g.setFont(new Font("Cambria", Font.PLAIN, _cardRadius / 2 - 4))
		// Titleframe
		g.fillRect(_xOffset + 3 * _cardRadius / 4, _yOffset + 3 * _cardRadius / 4, _cardWidth - 3 * _cardRadius / 2, _cardRadius / 2)
		// Imageframe
		g.setColor(Color.LIGHT_GRAY)
		g.fillRect(_xOffset + 7 * _cardRadius / 8, _yOffset + 11 * _cardRadius / 8, _cardWidth - 7 * _cardRadius / 4, 8 * (_cardWidth - 7 * _cardRadius / 4) / 11)
		// Typeframe
		g.fillRect(_xOffset + 3 * _cardRadius / 4, _yOffset + 8 * (_cardWidth - 7 * _cardRadius / 4) / 11 + 3 * _cardRadius / 2, _cardWidth - 3 * _cardRadius / 2, _cardRadius / 2)
		// Textframe
		g.fillRect(_xOffset + 3 * _cardRadius / 4, _yOffset + 8 * (_cardWidth - 7 * _cardRadius / 4) / 11 + 17 * _cardRadius / 8, _cardWidth - 3 * _cardRadius / 2, _cardHeight - 23 * _cardRadius / 8 - 8 * (_cardWidth - 7 * _cardRadius / 4) / 11)
		// Damage/Health-box
		g.setColor(CreatureCardView.YELLOW)
		g.fillRect(_xOffset + _cardWidth - 9 * _cardRadius / 4, _yOffset + _cardHeight - 5 * _cardRadius / 4, 3 * _cardRadius / 2, _cardRadius / 2)
		g.setColor(Color.BLACK)
		// The name
		g.drawString(creatureCard.name, _xOffset + 7 * _cardRadius / 8, _yOffset + 5 * _cardRadius / 4 - 6)
		// The type
		g.drawString("Creature", _xOffset + 7 * _cardRadius / 8, _yOffset + 8 * (_cardWidth - 7 * _cardRadius / 4) / 11 + 2 * _cardRadius - 6)
		// Damage & Health
		val slashWidth = g.getFontMetrics().stringWidth(" / ")
		val damageWidth = g.getFontMetrics().stringWidth("" + creatureCard.damage)
		g.drawString(" / ", _xOffset + _cardWidth - 3 * _cardRadius / 2 - slashWidth / 2, _yOffset + _cardHeight - 3 * _cardRadius / 4 - 6)
		g.drawString("" + creatureCard.damage, _xOffset + _cardWidth - 3 * _cardRadius / 2 - slashWidth / 2 - damageWidth, _yOffset + _cardHeight - 3 * _cardRadius / 4 - 6)
		g.drawString("" + creatureCard.health, _xOffset + _cardWidth - 3 * _cardRadius / 2 + slashWidth / 2, _yOffset + _cardHeight - 3 * _cardRadius / 4 - 6)

	}

	def paintBack(g: Graphics2D) {
		// Border
		g.setColor(Color.WHITE)
		g.fillRoundRect(_xOffset, _yOffset, _cardWidth, _cardHeight, _cardRadius, _cardRadius)
		g.setColor(Color.BLACK)
		g.fillRoundRect(_xOffset + 1, _yOffset + 1, _cardWidth - 2, _cardHeight - 2, _cardRadius, _cardRadius)
		// Background
		g.setColor(CreatureCardView.RED)
		g.fillRoundRect(_xOffset + _cardRadius / 2, _yOffset + _cardRadius / 2, _cardWidth - _cardRadius, _cardHeight - _cardRadius, _cardRadius, _cardRadius)
		// Logo
		g.setColor(CreatureCardView.YELLOW)
		g.fillOval(_xOffset + 2 * _cardRadius / 2, _yOffset + 2 * _cardRadius / 2, _cardWidth - 2 * _cardRadius, _cardHeight - 2 * _cardRadius)
	}
	
//	override def minimumSize: Dimension = {
//		new Dimension(_cardWidth, _cardHeight)
//	}
//	
//	override def preferredSize: Dimension = {
//		new Dimension(_cardWidth, _cardHeight)
//	}
//	
//	override def maximumSize: Dimension = {
//		new Dimension(_cardWidth, _cardHeight)
//	}
}	