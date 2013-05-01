package masterproef.cards

import org.newdawn.slick.Image
import masterproef.Masterproef
import org.newdawn.slick.gui.GUIContext
import org.newdawn.slick.Graphics

class Plains extends Land {

	override def copy(): Plains = {
		new Plains() called name is_owned_by owner requires requirements
	}

	_frontImage = new Image(Masterproef.getClass().getResourceAsStream("/masterproef/assets/cards/lands/Plains_small.png"), "plainsFront", false)

}