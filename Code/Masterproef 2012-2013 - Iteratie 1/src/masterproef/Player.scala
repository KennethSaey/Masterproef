/**
 * @author Kenneth
 *
 */
package masterproef

import masterproef.card.Deck

import masterproef.card.Card

class Player(name: String, defaultHealth: Int = 20) {
	var deck: Deck = new Deck();
	var graveyard: Deck = new Deck();
	var hand: Deck = new Deck();
	var battlefield: Deck = new Deck();
	var currentHealth: Int = defaultHealth;
	
	def takeDamage(damage: Int): Unit = {
		currentHealth = math.min(0, currentHealth - damage);
	}
	
	def regenerate(health: Int): Unit = {
		currentHealth += health;
	}
	
	def resetHealth(): Unit = {
		currentHealth = defaultHealth;
	}
	
	def reset(): Unit = {
		resetHealth();
		deck ++= graveyard.empty();
		deck ++= hand.empty();
		deck ++= battlefield.empty();
	}
	
	def draw(number: Int = 1): Unit = {
		hand ++= deck.draw(number);
	}
	
	def play(card: Card): Unit = {
		battlefield += hand.pick(card);
	}
	
	def toGraveyard(card: Card): Unit = {
		graveyard += battlefield.pick(card);
	}
	
	def hasCardsIn(deck: Deck): Boolean = {
		!deck.cards.isEmpty;
	}
}