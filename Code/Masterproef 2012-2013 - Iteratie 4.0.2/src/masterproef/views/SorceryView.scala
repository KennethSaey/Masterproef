package masterproef.views

import org.newdawn.slick.gui.GUIContext
import masterproef.cards.Card
import org.newdawn.slick.Graphics
import org.newdawn.slick.Color
import masterproef.cards.Sorcery

object SorceryView extends Function6[Card, GUIContext, Int, Int, Int, Int, SorceryView] {
	override def apply(card: Card, container: GUIContext, x: Int, y: Int, width: Int, height: Int): SorceryView = new SorceryView(card, container, x, y, width, height)
}

class SorceryView(card: Card, container: GUIContext, x: Int, y: Int, width: Int, height: Int) extends CardView(card, container, x, y, width, height) {

	override def renderSpecifics(container: GUIContext, g: Graphics) {
		var subtitle = "Sorcery"
		renderTitle(container, g, card.name)
		renderRequirements(container, g)
		renderSubtitle(container, g, subtitle)
		card.artwork.draw(area.getX + 25, area.getY + 33, 110, 81)
		renderText(container, g)
	}
	
	def renderText(container: GUIContext, g: Graphics) {
		g.setFont(CardView.mediumFont)
		g.setColor(Color.black)
		drawWrappedString(g, card.asInstanceOf[Sorcery].getText, area.getX + 26, area.getY + 128, 100)
	}
}