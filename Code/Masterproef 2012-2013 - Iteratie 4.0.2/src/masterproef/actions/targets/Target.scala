package masterproef.actions.targets

abstract class Target {
	
	var _target: Any = null
	
	def target: Any = _target
	def target_=(target: Any) = if(targetable(target)) _target = target

	def targetable(target: Any): Boolean
	
	override def toString: String = {
		this.getClass().getSimpleName().replace("Target", "")
	}
}