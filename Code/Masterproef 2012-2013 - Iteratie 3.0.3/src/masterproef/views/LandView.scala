package masterproef.views

import org.newdawn.slick.gui.GUIContext
import masterproef.cards.Card
import org.newdawn.slick.Graphics
import org.newdawn.slick.Color

object LandView extends Function6[Card, GUIContext, Int, Int, Int, Int, LandView] {
	override def apply(card: Card, container: GUIContext, x: Int, y: Int, width: Int, height: Int): LandView = new LandView(card, container, x, y, width, height)
}

class LandView(card: Card, container: GUIContext, x: Int, y: Int, width: Int, height: Int) extends CardView(card, container, x, y, width, height) {

	override def renderSpecifics(container: GUIContext, g: Graphics) {
		renderTitle(container, g, card.name)
		renderSubtitle(container, g, "Land")
		card.artwork.draw(area.getX + 25, area.getY + 33, 110, 81)
	}
}