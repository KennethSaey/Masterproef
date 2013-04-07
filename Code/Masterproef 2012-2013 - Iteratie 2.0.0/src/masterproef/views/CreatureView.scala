package masterproef.views

import org.newdawn.slick.gui.GUIContext
import masterproef.cards.Card
import org.newdawn.slick.Graphics

object CreatureView extends Function6[Card, GUIContext, Int, Int, Int, Int, CreatureView] {
	override def apply(card: Card, container: GUIContext, x: Int, y: Int, width: Int, height: Int): CreatureView = new CreatureView(card, container, x, y, width, height)
}

class CreatureView(card: Card, container: GUIContext, x: Int, y: Int, width: Int, height: Int) extends CardView(card, container, x, y, width, height) {

	override def renderSpecifics(container: GUIContext, g: Graphics) {
		renderTitle(container, g, card.name)
		renderSubtitle(container, g, "Creature")
		card.artwork.draw(area.getX + 25, area.getY + 33, 110, 81)
	}
}