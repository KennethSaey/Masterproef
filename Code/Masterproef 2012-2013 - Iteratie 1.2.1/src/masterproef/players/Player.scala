package masterproef.players

import masterproef.Game
import masterproef.Main
import masterproef.cards.Card
import masterproef.cards.CardState
import masterproef.cards.CreatureCard
import masterproef.cards.Deck
import masterproef.cards.Creature

class Player(val name: String, var health: Int = 20) {

	var deck: Deck = new Deck
	var hand: Deck = new Deck
	var battlefield: Deck = new Deck
	var graveyard: Deck = new Deck
	var exile: Deck = new Deck

	def addToDeck(card: Creature): Unit = {
		card.owner = this
		deck += card
	}

	def addToDeck(cards: Deck): Unit = {
		println("Adding to " + name + "s deck...")
		cards.foreach(_.owner = this)
		deck ++= cards
	}

	def draw(number: Int = 1): Deck = {
		val cards = deck.draw(number)
		hand ++= cards
		cards
	}

	private def move(card: Creature, from: Deck, to: Deck, newState: CardState.Value) = {
//		println("Moving " + card)
		if (from.contains(card)) {
			from -= card
			card.state = newState
			to += card
		}
	}

	def play(card: Creature): Unit = {
		println("Playing " + card)
		move(card, hand, battlefield, CardState.ACTIVE)
		card().listenTo(Game.getPhase("DrawCardPhase"))
//		if (card.isInstanceOf[CreatureCard]) {
//			card.asInstanceOf[CreatureCard].listenTo(Game.getPhase("DrawCardPhase"))
//		}
	}

	def kill(card: Creature): Unit = {
		println("Killing " + card)
		Main.addLine(card + " died.")
		move(card, battlefield, graveyard, CardState.DEAD)
		card().deafTo(Game.getPhase("DrawCardPhase"))
//		if(card.isInstanceOf[CreatureCard]){
//			card.asInstanceOf[CreatureCard].deafTo(Game.getPhase("DrawCardPhase"))
//		}
	}

	def exile(card: Creature): Unit = {
		println("Exiling " + card)
		Main.addLine(card + " was exiled.")
		move(card, battlefield, exile, CardState.EXILED)
	}

	def returnToHand(card: Creature): Unit = {
		Main.addLine(card + " was returned to your hand.")
		move(card, battlefield, hand, CardState.UNPLAYED)
	}

	def attack(opponent: Player, card: Creature): Unit = {
		opponent.health -= card.currentDamage
		Main.addLine(name + " deals " + card.currentDamage + " damage to " + opponent)
		Main.addLine(opponent + "s health is reduced to " + opponent.health)
		if (opponent.health <= 0) {
			Main.addLine(opponent + " died.")
			Game.end()
		}
	}
	
	def takeDamage(damage: Int): Unit = {
		health -= damage
		Main.addLine(name + "s health is reduced to " + health)
		if (health <= 0) {
			Main.addLine(name + " died.")
			Game.end()
		}
	}
	
	def takeDamageFrom(creature: CreatureCard, damage: Int): Unit = {
		Main.addLine(creature + " attacks " + name)
		takeDamage(damage)
	}

	override def toString(): String = {
		name
	}
}