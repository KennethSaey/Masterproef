package masterproef.cards

import masterproef.views.SorceryView
import masterproef.actions.Action
import masterproef.actions.targets.CompoundTarget
import masterproef.actions.targets.Target
import org.newdawn.slick.Image
import masterproef.Masterproef
import masterproef.actions.TargetAction

class Sorcery extends Card(SorceryView) {

	private var _action: Action = null
	private var _targets: Target = null

	override def copy(): Sorcery = {
		new Sorcery called name is_owned_by owner requires requirements defined_as action target targets
	}

	override def artwork: Image = {
		if (_artwork == null) _artwork = new Image(Masterproef.getClass().getResourceAsStream("/masterproef/assets/cards/sorceries/" + name + ".png"), "sorcery-" + name, false)
		_artwork
	}
	_frontImage = new Image(Masterproef.getClass().getResourceAsStream("/masterproef/assets/cards/lands/Swamp_small.png"), "swampFront", false)

	def action: Action = _action
	def action_=(action: Action) = _action = action
	def targets: Target = _targets
	def targets_=(target: Target) = _targets = targets

	def defined_as(action: Action): this.type = {
		_action = action
		this
	}

	def target(target: Target): Sorcery = {
		_targets = target
		this
	}

	def getText: String = {
		action + " target " + targets
	}
	
	def execute(target: Any = null) {
		if(action.isInstanceOf[TargetAction]){
			action.asInstanceOf[TargetAction].target = target
		}
		action.execute
	}
}