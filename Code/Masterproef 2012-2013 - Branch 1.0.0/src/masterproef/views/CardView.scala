package masterproef.views

import org.newdawn.slick.gui.AbstractComponent
import org.newdawn.slick.gui.GUIContext
import org.newdawn.slick.Image
import masterproef.cards.Card
import org.newdawn.slick.gui.ComponentListener
import org.newdawn.slick.geom.Shape
import org.newdawn.slick.geom.Rectangle
import org.newdawn.slick.Input
import org.newdawn.slick.Graphics
import org.newdawn.slick.Color
import org.newdawn.slick.TrueTypeFont
import java.awt.Font

object CardView extends Function6[Card, GUIContext, Int, Int, Int, Int, CardView]{
	private final var NORMAL: Int = 1
	private final var MOUSE_DOWN: Int = 2
	private final var MOUSE_OVER: Int = 3
	val normalFont: TrueTypeFont = new TrueTypeFont(new Font("Calibri", Font.PLAIN, 8), true)
	val largeFont: TrueTypeFont = new TrueTypeFont(new Font("Calibri", Font.PLAIN, 18), true)
	val largerFont: TrueTypeFont = new TrueTypeFont(new Font("Calibri", Font.PLAIN, 32), true)
	val largestFont: TrueTypeFont = new TrueTypeFont(new Font("Calibri", Font.PLAIN, 48), true)
	
	override def apply(card: Card, container: GUIContext, x: Int, y: Int, width: Int, height: Int): CardView = new CardView(card, container, x, y, width, height)
}

class CardView(card: Card, container: GUIContext, x: Int, y: Int, width: Int, height: Int) extends AbstractComponent(container) {

	protected var area: Shape = new Rectangle(x, y, width, height)

	private var _input: Input = container.getInput()
	private var over: Boolean = area.contains(input.getMouseX(), input.getMouseY())
	private var mouseDown: Boolean = input.isMouseButtonDown(0)
	private var state: Int = CardView.NORMAL
	var rounding: Int = 20

	def this(card: Card, container: GUIContext, x: Int, y: Int, width: Int, height: Int, listener: ComponentListener) {
		this(card, container, x, y, width, height)
		addListener(listener)
	}

	def this(card: Card, container: GUIContext) {
		this(card, container, 0, 0, 0, 0)
	}


	def getX: Int = area.getX().toInt
	def getY: Int = area.getY().toInt
	def setX(x: Int) = area.setX(x)
	def setY(y: Int) = area.setY(y)
	def setLocation(x: Int, y: Int)  = area = new Rectangle(x, y, if(area != null) area.getWidth else 0, if(area != null) area.getHeight else 0)
	
	def getHeight: Int = area.getHeight().toInt
	def getWidth: Int = area.getWidth().toInt
	def setWidth(width: Int) = area = new Rectangle(area.getX, area.getY, width, area.getHeight)
	def setHeight(height: Int) = area = new Rectangle(area.getX, area.getY, area.getWidth, height)
	def setSize(width: Int, height: Int) = area = new Rectangle(area.getX, area.getY, width, height)
	
	def setArea(x: Int, y: Int, width: Int, height: Int) = area = new Rectangle(x, y, width, height)

	def render(container: GUIContext, g: Graphics) = render(container, g, true)

	def render(container: GUIContext, g: Graphics, faceUp: Boolean) = if (faceUp) renderFaceUp(container, g) else renderFaceDown(container, g)

	def renderFaceUp(container: GUIContext, g: Graphics) {
		renderGlow(container, g)
		val imageX = (area.getX() + (area.getWidth - card.frontImage.getWidth()) / 2)
		val imageY = (area.getY() + (area.getHeight - card.frontImage.getHeight()) / 2)
		card.frontImage.draw(imageX, imageY)
		renderSpecifics(container, g)
	}
	
	/* Method to overwrite in subclasses */
	def renderSpecifics(container: GUIContext, g: Graphics) {
	}
	
	def renderTitle(container: GUIContext, g: Graphics, title: String) {
		g.setFont(CardView.normalFont)
		g.setColor(Color.black)
		g.drawString(title, area.getX + 26, area.getY + 23)
	}
	
	def renderSubtitle(container: GUIContext, g: Graphics, subtitle: String) {
		g.setFont(CardView.normalFont)
		g.setColor(Color.black)
		g.drawString(subtitle, area.getX + 26, area.getY + 116)
	}

	def renderGlow(container: GUIContext, g: Graphics) {
		if (card.playable) {
			val iterations = scala.math.max((area.getWidth - card.frontImage.getWidth()) / 2, (area.getHeight - card.frontImage.getHeight()) / 2).toInt
			val transparencyStep = if (over) 0.9f / iterations else 0.5f / iterations
			val baseGlowColor = getBaseGlowColor
			for (i <- 0 until iterations) {
				g.setColor(new Color(baseGlowColor.r, baseGlowColor.g, baseGlowColor.b, i.toFloat * transparencyStep))
				g.fillRoundRect(getX + i, getY + i, area.getWidth - 2 * i, area.getHeight - 2 * i, rounding)
			}
		}
	}

	def getBaseGlowColor: Color = {
		if (!card.selectable) {
			Color.red
		} else if (over && mouseDown) {
			Color.cyan
		} else if (card.selected) {
			Color.blue
		} else {
			Color.green
		}
		//		if(!card.selectable){
		//			Color.red
		//		} else if(!card.selected && !over) {
		//			Color.green
		//		} else if(card.selected && !over) {
		//			Color.blue
		//		} else if(!mouseDown) {
		//			Color.yellow
		//		} else {
		//			Color.cyan
		//		}
	}

	def renderFaceDown(container: GUIContext, g: Graphics) {
		val imageX = (area.getX() + (area.getWidth - card.backImage.getWidth()) / 2)
		val imageY = (area.getY() + (area.getHeight - card.backImage.getHeight()) / 2)
		card.backImage.draw(imageX, imageY)
	}

	override def mouseMoved(oldx: Int, oldy: Int, newx: Int, newy: Int) = over = area.contains(newx, newy)
	override def mouseDragged(oldx: Int, oldy: Int, newx: Int, newy: Int) = mouseMoved(oldx, oldy, newx, newy)
	override def mousePressed(button: Int, x: Int, y: Int) {
		over = area.contains(x, y)
		if (button == 0) mouseDown = true
	}
	override def mouseReleased(button: Int, x: Int, y: Int) {
		over = area.contains(x, y)
		if (button == 0) {
			mouseDown = false
			if (card.selectable && over) card.selected = !card.selected
		}
	}

	def isMouseOver = over

}