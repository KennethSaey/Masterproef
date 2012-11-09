package masterproef.cards

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Deck extends ArrayBuffer[Card] {

	def shuffle() = {
		val shuffled = Random.shuffle(this)
		this.clear
		this ++= shuffled
	}

	def draw(number: Int = 1): Deck = {
		val cards = takeRight(number)
//		println(cards(0).getClass().get)
		trimEnd(number)
		val deck = new Deck
		deck ++= cards
	}

	def peek(number: Int = 1): Deck = {
		val cards = takeRight(number)
		val deck = new Deck
		deck ++= cards
	}

	override def toString(): String = {
		if (isEmpty) {
			"Empty Deck"
		} else {
			mkString("\n")
		}
	}

	def getCardByName(name: String): Card = {
		find(card => card.name == name) match {
			case None => null
			case Some(c) => c
		}
	}
}