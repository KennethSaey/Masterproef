package masterproef.actions

object Actions {

	val Destroy: DestroyAction = new DestroyAction()
	val Discard: Int => DiscardAction = x => new DiscardAction(x)
}