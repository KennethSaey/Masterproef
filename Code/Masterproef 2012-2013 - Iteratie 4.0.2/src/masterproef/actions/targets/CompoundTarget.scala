package masterproef.actions.targets

import scala.collection.mutable.ArrayBuffer

abstract class CompoundTarget extends Target {

	val targets: ArrayBuffer[Target] = new ArrayBuffer[Target]()
	
	def this(target: Target) {
		this()
		targets += target
	}
}