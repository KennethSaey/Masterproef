package masterproef

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Deck {
	var cards: ArrayBuffer[Card] = new ArrayBuffer();
	
	/**
	 * Shuffle the deck.
	 */
	def shuffle(): Unit = {
		cards = ArrayBuffer(Random.shuffle(cards.toSeq): _*);
	}
	
	/**
	 * Draw cards and remove them from the deck.
	 */
	def draw(number: Int = 1): ArrayBuffer[Card] = {
		val draw = peek(number);
		cards.trimEnd(number);
		draw;
	}

	/**
	 * Draw cards without removing them from the deck.
	 */
	def peek(number: Int = 1): ArrayBuffer[Card] = {
		cards.takeRight(number);
	}
	
	def +=(card: Card): Unit = {
		cards += card;
	}
	
	def ++=(cardBuffer: ArrayBuffer[Card]): Unit = {
		cards ++= cardBuffer;
	}
	
	override def toString() = {
		cards.mkString("Deck:\n", "\n", "\n-----");
	}
	
	def unary_~(): Unit = {
		shuffle();
	}
	
	def empty(): ArrayBuffer[Card] = {
		val copy = cards;
		cards.clear();
		copy;
	}
	
	def contains(card: Card): Boolean = {
		cards.contains(card);
	}
	
	def pick(card: Card): Card = {
		cards.remove(cards.indexOf(card));
	}
}