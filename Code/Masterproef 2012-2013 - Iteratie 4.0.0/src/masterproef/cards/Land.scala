package masterproef.cards

import org.newdawn.slick.Image
import masterproef.Masterproef
import masterproef.views.LandView

class Land extends Card(LandView) {

	override def copy(): Land = {
		new Land() called name is_owned_by owner requires requirements
	}

	override def artwork: Image = {
		if(_artwork == null) _artwork = new Image(Masterproef.getClass().getResourceAsStream("/masterproef/assets/cards/lands/" + name + ".png"), "land-" + name, false)
		_artwork
	}
	
	override def ==(other: Card): Boolean = {
		getClass().getName() == other.getClass().getName()
	}
	
}