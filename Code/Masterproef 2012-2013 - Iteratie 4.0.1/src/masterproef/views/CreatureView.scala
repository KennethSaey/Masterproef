package masterproef.views

import org.newdawn.slick.gui.GUIContext
import masterproef.cards.Card
import org.newdawn.slick.Graphics
import org.newdawn.slick.Color
import masterproef.cards.Creature
import masterproef.cards.AbilityCreature

object CreatureView extends Function6[Card, GUIContext, Int, Int, Int, Int, CreatureView] {
	override def apply(card: Card, container: GUIContext, x: Int, y: Int, width: Int, height: Int): CreatureView = new CreatureView(card, container, x, y, width, height)
}

class CreatureView(card: Card, container: GUIContext, x: Int, y: Int, width: Int, height: Int) extends CardView(card, container, x, y, width, height) {
	
	override def renderSpecifics(container: GUIContext, g: Graphics) {
		var subtitle = "Creature "
		if (card.isInstanceOf[AbilityCreature]) subtitle += card.asInstanceOf[AbilityCreature].abilities.mkString(", ")
		renderTitle(container, g, card.name)
		renderRequirements(container, g)
		renderSubtitle(container, g, subtitle)
		card.artwork.draw(area.getX + 25, area.getY + 33, 110, 81)
		renderAttributes(container, g)
	}

	def renderAttributes(container: GUIContext, g: Graphics) {
		g.setFont(CardView.largeFont)
		g.setColor(Color.black)
		val creature = card.asInstanceOf[Creature]
		val timeCounters = creature.counters.time
		val timeCounterString = if(timeCounters != 0) " [T" + timeCounters + "]" else ""
		val damage = creature.damage - creature.counters.damage
		val health = creature.health - creature.counters.health
		val damageCounters = creature.counters.damage
		val counterDamage = if(damageCounters != 0) "+" + damageCounters else ""
		val healthCounters = creature.counters.health
		val counterHealth = if(healthCounters != 0) "+" + healthCounters else ""
		val attributeString = damage + counterDamage + "/" + health + counterHealth + timeCounterString
		g.drawString(attributeString, area.getX + 46, area.getY + 146)
	}
}