package masterproef.cards

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Deck extends ArrayBuffer[Creature] {

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

	def getCardByName(name: String): Creature = {
		find(card => card.name == name) match {
			case None => null
			case Some(c) => c
		}
	}
	
	/**
	 * Vraag: Waarom moet ik filter hier overschrijven. (Anders krijg ik een class cast exception van ArrayBuffer naar Deck)
	 */
	override def filter(p: Creature => Boolean): Deck = {
		val deck = new Deck
		for(creature <- this){
			if(p(creature)){
				deck += creature
			}
		}
		deck
	}
}