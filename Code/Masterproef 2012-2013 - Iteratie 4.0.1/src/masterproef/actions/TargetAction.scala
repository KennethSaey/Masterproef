package masterproef.actions

import masterproef.cards.Card

abstract class TargetAction extends Action {

	private var _target: Any = null
	
	def target: Any = _target
	def target_=(target: Any) = if(targetable(target)) _target = target
	
	def targetable(target: Any): Boolean
	
}