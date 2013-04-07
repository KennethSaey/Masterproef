package masterproef.cards

import scala.collection.mutable.ArrayBuffer
import RequirementsImplicits._

object RequirementsImplicits {
	implicit def Requirement2Requirements(requirement: Requirement) = new Requirements(requirement)
}

class Requirements extends ArrayBuffer[Requirement] {

	//	val _requirements: ArrayBuffer[Requirement] = new ArrayBuffer[Requirement]()// + requirement

	def this(requirement: Requirement) {
		this()
		this += requirement
		//		_requirements += requirement
	}

	def and(other: Requirement): Requirements = {
		//		_requirements += other
		this += other
		this
	}

	def and(others: Requirements): Requirements = {
		//		_requirements ++= others._requirements
		this ++= others
		this
	}

	def AND(other: Requirement): Requirements = {
		and(other)
	}

	def AND(others: Requirements): Requirements = {
		and(others)
	}

	def &(other: Requirement): Requirements = {
		and(other)
	}

	def &(others: Requirements): Requirements = {
		and(others)
	}

	override def toString(): String = {
		//			_requirements.mkString(" ")
		mkString(" ")
	}
	
	def count: Int = (0 /: this)(_ + _.times)
}