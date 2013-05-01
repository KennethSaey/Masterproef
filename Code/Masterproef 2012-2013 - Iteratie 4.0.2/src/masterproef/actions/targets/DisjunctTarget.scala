package masterproef.actions.targets

object DisjunctTargetImplicits {
	
	implicit def Target2DisjunctTarget(target: Target): DisjunctTarget = new DisjunctTarget(target)
}

class DisjunctTarget extends CompoundTarget {
	
	def this(target: Target) {
		this()
		targets += target
	}
	
	def targetable(target: Any): Boolean = {
		targets.exists(_.targetable(target))
	}
	
	def or(target: Target): DisjunctTarget = {
		targets += target
		this
	}
	
	override def toString: String = {
		targets.mkString(" or ")
	}

}