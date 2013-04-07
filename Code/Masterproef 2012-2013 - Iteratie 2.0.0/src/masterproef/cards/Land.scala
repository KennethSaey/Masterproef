package masterproef.cards

import org.newdawn.slick.Image
import masterproef.Masterproef
import masterproef.views.LandView

class Land extends Card(LandView) {
	
	override def artwork: Image = {
		if(_artwork == null) _artwork = new Image(Masterproef.getClass().getResourceAsStream("/masterproef/assets/cards/lands/" + name + ".png"), "land-" + name, false)
		_artwork
	}
	
}