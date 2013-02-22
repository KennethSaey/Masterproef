package masterproef.cards

import scala.collection.mutable.ArrayBuffer
import scala.swing.Publisher
import scala.swing.event.Event
import java.util.Arrays
import scala.util.Random

case class DeckOrderChanged extends Event
case class DeckSizeIncreased(number: Int) extends Event
case class DeckSizeDecreased(number: Int) extends Event

class Deck extends ArrayBuffer[CreatureCard] with Publisher{

	def shuffle = {
		val shuffled = Random.shuffle(this)
		this.clear
		this ++= shuffled
		publish(new DeckOrderChanged)
	}
	def getCardByName(name: String): CreatureCard = {
		find(creatureCard => creatureCard.name == name) match {
			case None => null
			case Some(c) => c
		}
	}
	def draw(number: Int = 1): Deck = {
		val cards = takeRight(number)
		trimEnd(number)
		publish(new DeckSizeDecreased(number))
		val deck = new Deck
		deck ++= cards
	}
	def peek(number: Int = 1): Deck = {
		val cards = takeRight(number)
		val deck = new Deck
		deck ++= cards
	}
	override def +=(creatureCard: CreatureCard): Deck.this.type = {
		super.+=(creatureCard)
		publish(new DeckSizeIncreased(1))
		this
	}
	
	override def filter(p: CreatureCard => Boolean): Deck = {
		val deck = new Deck
		for(creature <- this){
			if(p(creature)){
				deck += creature
			}
		}
		deck
	}
	override def toString(): String = {
		if (isEmpty) {
			"Empty Deck"
		} else {
			mkString("\n")
		}
	}
}