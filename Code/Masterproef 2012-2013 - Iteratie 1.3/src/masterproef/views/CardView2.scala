package masterproef.views

import scala.swing.Component
import masterproef.cards.Card
import java.awt.Graphics2D
import java.awt.Color
import java.awt.Dimension
import java.awt.event.ActionListener
import scala.swing.event.MouseClicked
import java.awt.Font
import scala.swing.event.MouseMoved

class CardView2(card: Card) extends Component {
	var face: CardFace.Value = CardFace.UP
	var selectionState: CardSelectionState.Value = CardSelectionState.UNSELECTED
	preferredSize = new Dimension(260, 360)
	listenTo(mouse.clicks)
	reactions += {
		case e: MouseClicked => {
//			println("Clicked")
			selectionState match {
				case CardSelectionState.UNSELECTED => selectionState = CardSelectionState.SELECTED
				case CardSelectionState.SELECTED => selectionState = CardSelectionState.SELECTABLE
				case CardSelectionState.SELECTABLE => selectionState = CardSelectionState.UNSELECTABLE
				case CardSelectionState.UNSELECTABLE => selectionState = CardSelectionState.UNSELECTED
			}
			repaint
		}
	}
	
	override def paintComponent(g: Graphics2D) = {
//		println("CardView2.paintComponent")
		paintGlow(g)
		g.setColor(Color.BLACK)
		g.fillRoundRect(5, 5, 250, 350, 40, 40)
		face match {
			case CardFace.DOWN => paintBack(g)
			case CardFace.UP => paintFront(g)
		}
	}
	
	def paintFront(g: Graphics2D) = {
		g.setColor(new Color(79, 77, 52))
		g.fillRoundRect(15, 15, 230, 330, 30, 30)
		// The name
		g.setColor(new Color(185, 180, 175))
		g.fillRoundRect(25, 25, 210, 30, 10, 10)
		g.setColor(Color.BLACK)
		g.setFont(new Font("Calibri", Font.PLAIN, 20))
		g.drawString(card.name, 35, 47)
	}
	
	def paintBack(g: Graphics2D) = {
		g.setColor(new Color(216, 191, 216))
		g.setFont(new Font("Comic sans MS", Font.PLAIN, 305))
		g.drawString("?", 67, 283)
		g.setColor(new Color(147, 112, 219))
		g.setFont(new Font("Comic sans MS", Font.PLAIN, 300))
		g.drawString("?", 70, 280)
	}
	
	def paintGlow(g: Graphics2D) = {
//		println("CardView2.paintGlow")
		selectionState match {
			case CardSelectionState.UNSELECTED => {}
			case CardSelectionState.SELECTED => {
				g.setColor(new Color(146, 163, 226))
				g.fillRoundRect(0, 0, 260, 360, 25, 25)
				g.setColor(new Color(124, 144, 220))
				g.fillRoundRect(1, 1, 258, 358, 25, 25)
				g.setColor(new Color(100, 124, 214))
				g.fillRoundRect(2, 2, 256, 356, 25, 25)
				g.setColor(new Color(74, 102, 207))
				g.fillRoundRect(3, 3, 254, 354, 25, 25)
				g.setColor(new Color(46, 78, 199))
				g.fillRoundRect(4, 4, 252, 352, 25, 25)
			}
			case CardSelectionState.UNSELECTABLE => {
				g.setColor(new Color(226, 146, 146))
				g.fillRoundRect(0, 0, 260, 360, 25, 25)
				g.setColor(new Color(220, 124, 124))
				g.fillRoundRect(1, 1, 258, 358, 25, 25)
				g.setColor(new Color(214, 100, 100))
				g.fillRoundRect(2, 2, 256, 356, 25, 25)
				g.setColor(new Color(207, 74, 74))
				g.fillRoundRect(3, 3, 254, 354, 25, 25)
				g.setColor(new Color(199, 46, 46))
				g.fillRoundRect(4, 4, 252, 352, 25, 25)
			}
			case CardSelectionState.SELECTABLE => {
				g.setColor(new Color(146, 226, 149))
				g.fillRoundRect(0, 0, 260, 360, 25, 25)
				g.setColor(new Color(124, 220, 128))
				g.fillRoundRect(1, 1, 258, 358, 25, 25)
				g.setColor(new Color(100, 214, 105))
				g.fillRoundRect(2, 2, 256, 356, 25, 25)
				g.setColor(new Color(74, 207, 81))
				g.fillRoundRect(3, 3, 254, 354, 25, 25)
				g.setColor(new Color(46, 199, 53))
				g.fillRoundRect(4, 4, 252, 352, 25, 25)
			}
		}
	}
}