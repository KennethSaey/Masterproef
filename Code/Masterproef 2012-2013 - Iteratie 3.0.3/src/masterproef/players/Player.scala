package masterproef.players

import masterproef.cards.Card
import masterproef.cards.Deck
import masterproef.cards.Creature
import org.newdawn.slick.Game
import scala.xml.include.sax.Main
import masterproef.counters.Counters
import masterproef.Masterproef

class Player(val name: String, var health: Int = 20) {

	var deck: Deck = new Deck
	var hand: Deck = new Deck
	var battlefield: Deck = new Deck
	var graveyard: Deck = new Deck
	var exile: Deck = new Deck
	
	val counters: Counters = new Counters

	def addToDeck(card: Card): Unit = {
		card is_owned_by this
		deck += card
	}

	def addToDeck(cards: Deck): Unit = {
//		println("Adding to " + name + "s deck...")
		cards.foreach(_ is_owned_by this)
		deck ++= cards
	}

	def draw(number: Int = 1): Deck = {
		val cards = deck.draw(number)
		hand ++= cards
		cards
	}

	private def move(card: Card, from: Deck, to: Deck/*, newState: CardState.Value*/) = {
//		println("Moving " + card)
		if (from.contains(card)) {
			from -= card
//			card.state = newState
			to += card
		}
	}

	def play(card: Card): Unit = {
//		println(hand.size + "-Playing " + card)
		move(card, hand, battlefield/*, CardState.ACTIVE*/)
//		println(hand.size + "-Played " + card)
	}

	def kill(card: Creature): Unit = {
		println("Killing " + card)
//		Main.addLine(card + " died.")
		move(card, battlefield, graveyard/*, CardState.DEAD*/)
//		card().deafTo(Game.getPhase("DrawCardPhase"))
//		if(card.isInstanceOf[CreatureCard]){
//			card.asInstanceOf[CreatureCard].deafTo(Game.getPhase("DrawCardPhase"))
//		}
	}

	def exile(card: Creature): Unit = {
		println("Exiling " + card)
//		Main.addLine(card + " was exiled.")
		move(card, battlefield, exile/*, CardState.EXILED*/)
	}

	def returnToHand(card: Creature): Unit = {
//		Main.addLine(card + " was returned to your hand.")
		move(card, battlefield, hand/*, CardState.UNPLAYED*/)
	}

	def attack(opponent: Player, card: Creature): Unit = {
		opponent.health -= card.damage
//		Main.addLine(name + " deals " + card.damage + " damage to " + opponent)
//		Main.addLine(opponent + "s health is reduced to " + opponent.health)
		if (opponent.health <= 0) {
//			Main.addLine(opponent + " died.")
//			Game.end()
		}
	}
	
	def takeDamage(damage: Int): Unit = {
		health -= damage
//		Main.addLine(name + "s health is reduced to " + health)
		if (health <= 0) {
//			Main.addLine(name + " died.")
//			Game.end()
			Masterproef.model.gameOver(Masterproef.model.player)
		}
	}
	
	def takeDamageFrom(creature: Creature, damage: Int): Unit = {
		takeDamage(damage)
	}

	override def toString(): String = {
		name
	}
	
	def endTurn = {
		for(card <- battlefield) {
			card.endTurn
		}
	}
	
	def startTurn = {
		for(card <- battlefield) {
			card.startTurn
		}
	}
}