package masterproef.cards

import org.newdawn.slick.Image
import masterproef.Masterproef
import org.newdawn.slick.gui.GUIContext
import org.newdawn.slick.Graphics

class Swamp extends Land {

	override def copy(): Swamp = {
		new Swamp() called name is_owned_by owner
	}

	_frontImage = new Image(Masterproef.getClass().getResourceAsStream("/masterproef/assets/cards/lands/Swamp_small.png"), "swampFront", false)

}