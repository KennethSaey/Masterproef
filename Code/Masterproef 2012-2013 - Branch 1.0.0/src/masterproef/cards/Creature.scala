package masterproef.cards

import org.newdawn.slick.Image
import masterproef.Masterproef
import masterproef.views.CreatureView

class Creature extends Card(CreatureView) {
	
	override def artwork: Image = {
		if (_artwork == null) _artwork = new Image(Masterproef.getClass().getResourceAsStream("/masterproef/assets/cards/creatures/" + name + ".png"), "creature-" + name, false)
		_artwork
	}
}