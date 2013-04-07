package masterproef.views

import org.newdawn.slick.gui.GUIContext
import masterproef.cards.Card
import org.newdawn.slick.Graphics
import org.newdawn.slick.Color
import masterproef.cards.Creature

object CreatureView extends Function6[Card, GUIContext, Int, Int, Int, Int, CreatureView] {
	override def apply(card: Card, container: GUIContext, x: Int, y: Int, width: Int, height: Int): CreatureView = new CreatureView(card, container, x, y, width, height)
}

class CreatureView(card: Card, container: GUIContext, x: Int, y: Int, width: Int, height: Int) extends CardView(card, container, x, y, width, height) {

	override def renderSpecifics(container: GUIContext, g: Graphics) {
		renderTitle(container, g, card.name)
		renderSubtitle(container, g, "Creature")
		card.artwork.draw(area.getX + 25, area.getY + 33, 110, 81)
		renderAttributes(container, g)
	}
	
	def renderAttributes(container: GUIContext, g: Graphics) {
		g.setFont(CardView.largeFont)
		g.setColor(Color.black)
		g.drawString(card.asInstanceOf[Creature].damage + "/" + card.asInstanceOf[Creature].health, area.getX + 46, area.getY + 146)
	}
}