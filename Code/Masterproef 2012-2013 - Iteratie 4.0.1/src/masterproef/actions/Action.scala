package masterproef.actions

abstract class Action {

	def execute
	def executable: Boolean
	
	override def toString: String = {
		this.getClass().getSimpleName().replace("Action", "")
	}
}