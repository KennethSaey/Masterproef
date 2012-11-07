package masterproef.players

import masterproef.Game
import masterproef.Main
import masterproef.cards.Card
import masterproef.cards.CardState
import masterproef.cards.CreatureCard
import masterproef.cards.Deck

class Player(val name: String, var health: Int = 20) {

	var deck: Deck = new Deck
	var hand: Deck = new Deck
	var battlefield: Deck = new Deck
	var graveyard: Deck = new Deck
	var exile: Deck = new Deck

	def addToDeck(card: Card): Unit = {
		card.setOwner(this)
		deck += card
	}

	def addToDeck(cards: Deck): Unit = {
		println("Adding to " + name + "s deck...")
		cards.foreach(_.setOwner(this))
		deck ++= cards
	}

	def draw(number: Int = 1): Deck = {
		val cards = deck.draw(number)
		hand ++= cards
		cards
	}

	private def move(card: Card, from: Deck, to: Deck, newState: CardState.Value) = {
		if (from.contains(card)) {
			from -= card
			card.state = newState
			to += card
		}
	}

	def play(card: Card): Unit = {
		move(card, hand, battlefield, CardState.ACTIVE)
		if (card.isInstanceOf[CreatureCard]) {
			card.asInstanceOf[CreatureCard].listenTo(Game.getPhase("DrawCardPhase"))
		}
	}

	def kill(card: Card): Unit = {
		move(card, battlefield, graveyard, CardState.DEAD)
	}

	def exile(card: Card): Unit = {
		move(card, battlefield, exile, CardState.EXILED)
	}

	def returnToHand(card: Card): Unit = {
		move(card, battlefield, hand, CardState.UNPLAYED)
	}

	def attack(opponent: Player, card: CreatureCard): Unit = {
		opponent.health -= card.currentDamage
		Main.addLine(name + " deals " + card.currentDamage + " damage to " + opponent)
		Main.addLine(opponent + "s health is reduced to " + opponent.health)
		if (opponent.health <= 0) {
			Main.addLine(opponent + " died.")
			Game.end()
		}
	}

	override def toString(): String = {
		name
	}
}