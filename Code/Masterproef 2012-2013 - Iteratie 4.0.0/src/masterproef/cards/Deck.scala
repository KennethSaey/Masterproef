package masterproef.cards

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Deck extends ArrayBuffer[Card] {

	/* DSL stuff*/
	def contains(card: Card): this.type = {
		this += card
		this
	}

	def and(card: Card): this.type = {
		this += card
		this
	}

	/* other stuff*/
	def shuffle() = {
		val shuffled = Random.shuffle(this)
		this.clear
		this ++= shuffled
	}

	def draw(number: Int = 1): Deck = {
		val cards = takeRight(number)
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

//	def getCardByName(name: String): Card = {
//		find(card => card._name == name) match {
//			case None => null
//			case Some(c) => c
//		}
//	}

	/**
	 * Vraag: Waarom moet ik filter hier overschrijven. (Anders krijg ik een class cast exception van ArrayBuffer naar Deck)
	 */
	override def filter(p: Card => Boolean): Deck = {
		val deck = new Deck
		for (creature <- this) {
			if (p(creature)) {
				deck += creature
			}
		}
		deck
	}
}