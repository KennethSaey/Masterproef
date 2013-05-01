package masterproef.actions.targets

object ConjunctTargetImplicits {
	
	implicit def Target2ConjunctTarget(target: Target): ConjunctTarget = new ConjunctTarget(target)
}

class ConjunctTarget extends CompoundTarget {
	
	def this(target: Target) {
		this()
		targets += target
	}
	
	def targetable(target: Any): Boolean = {
		targets.forall(_.targetable(target))
	}
	
	def and(target: Target): ConjunctTarget = {
		targets += target
		this
	}
	
	override def toString: String = {
		targets.mkString(" and ")
	}

}