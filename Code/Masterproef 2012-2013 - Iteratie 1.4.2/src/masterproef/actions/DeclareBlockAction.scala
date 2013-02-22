package masterproef.actions

import masterproef.Game
import masterproef.Main
import scala.collection.mutable.ArrayBuffer
import masterproef.cards.Deck
import masterproef.cards.CreatureCard
import masterproef.cards.CreatureCard
import scala.collection.mutable.HashMap
import masterproef.cards.Card
import masterproef.cards.Creature

class DeclareDefenceAction extends GameAction("Declare Defence Action") {

	override def execute(): Unit = {
		super.execute
		val availableBlockers = beforeDeclareBlockAction
		val selectedBlockers = declareBlockAction(availableBlockers)
		Game.blockers = afterDeclareBlockAction(selectedBlockers)
	}

	/**
	 * Hook where stuff can be done before the defender gets to select
	 * his blocking creatures.
	 * @return Deck CreatureCards which are able to block
	 */
	def beforeDeclareBlockAction(): Deck = {
		Game.currentOpponent.battlefield.filter(creature => creature.canBlock).asInstanceOf[Deck]
	}

	/**
	 * This might be replaced by listeners for card selection
	 */
	def declareBlockAction(availableBlockers: Deck): HashMap[Creature, Deck] = {
		val selectedBlockers = new HashMap[Creature, Deck]

		if (Game.attackers.isEmpty) {
			Main.addLine(Game.currentPlayer + " doesn't attack, so there is no reason to block.")
		} else {
			Main.addLine(Game.currentPlayer + " attacks with:")
			Main.addLine(Game.attackers.toString)
			if (availableBlockers.isEmpty) {
				Main.addLine(Game.currentOpponent + ", you have no creatures available to block.")
			} else {
				var selectedCreatureBlockers = new Deck
				for (attacker <- Game.attackers.filter(attacker => attacker.canBeBlocked)) {
					availableBlockers --= selectedCreatureBlockers
					val availableCreatureBlockers = beforeDeclareBlockCreature(attacker, availableBlockers)
					if (availableCreatureBlockers.isEmpty) {
						Main.addLine(Game.currentOpponent + ", you have no creatures which can block " + attacker)
					} else {
						selectedCreatureBlockers = declareBlockCreature(attacker, availableCreatureBlockers)
						selectedCreatureBlockers = afterDeclareBlockCreature(attacker, selectedCreatureBlockers)
						if (!selectedCreatureBlockers.isEmpty) {
							selectedBlockers(attacker) = selectedCreatureBlockers
						}
					}
				}
			}
		}

		selectedBlockers
	}

	/**
	 * Hook where stuff can be done before the defender gets to select
	 * his blocking creatures for one specific attacker.
	 * @return Deck CreatureCards which are able to block the attacker
	 */
	def beforeDeclareBlockCreature(attacker: Creature, availableBlockers: Deck): Deck = {
		availableBlockers.filter(
			blocker =>
				blocker.canBlockCreature(attacker) &&
					attacker.canBeBlockedBy(blocker)
		).asInstanceOf[Deck]
	}

	/**
	 * This might be replaced by listeners for card selection
	 */
	def declareBlockCreature(attacker: Creature, availableBlockers: Deck): Deck = {
		val selectedBlockers = new Deck
		val cardString = Main.getUserInput(Game.currentOpponent, "Please select the creatures to block " + attacker + " or 'skip' to skip this attacker\n" + "You can choose from: " + availableBlockers, "skip");
		if (!cardString.equals("skip")) {
			val cardNames = cardString.split(",")
			for (cardName <- cardNames) {
				val card = availableBlockers.getCardByName(cardName.trim())
				selectedBlockers += card
			}
		}
		selectedBlockers
	}

	/**
	 * Hook where stuff can be done after the defender has selected
	 * his blocking creatures for one specific attacker.
	 * @return Deck The blocking CreatureCards for the specific attacker
	 */
	def afterDeclareBlockCreature(attacker: Creature, selectedBlockers: Deck): Deck = {
		selectedBlockers
	}

	/**
	 * Hook where stuff can be done after the defender has selected
	 * all blocking creatures.
	 * @return HashMap[Card, Deck] All blocking CreatureCards for each attacker
	 */
	def afterDeclareBlockAction(selectedBlockers: HashMap[Creature, Deck]): HashMap[Creature, Deck] = {
		selectedBlockers
	}
}