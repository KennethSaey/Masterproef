package masterproef.cards

import scala.collection.mutable.ArrayBuffer
import RequirementImplicits._
import RequirementsImplicits._
import masterproef.cards.LandTypes._

object RequirementImplicits {
	implicit def Int2Requirement(int: Int) = new Requirement(int)
}

class Requirement(val times: Int) {
	var card: Card = null

	def this(times: Int, card: Card) {
		this(times)
		this.card = card
	}
	
	def apply(card: Card): Requirement = {
		this.card = card
		this
	}
	
	def *(card: Card): Requirement = {
		this.card = card
		this
	}
	
	override def toString(): String = {
		times + card.getClass().getSimpleName().substring(0, 1)
	}
}