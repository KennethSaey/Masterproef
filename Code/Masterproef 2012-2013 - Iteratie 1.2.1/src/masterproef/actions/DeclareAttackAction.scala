package masterproef.actions

import masterproef.Game
import masterproef.cards.CreatureCard
import masterproef.cards.Deck
import masterproef.Main
import masterproef.cards.Deck
import masterproef.cards.CreatureCard

class DeclareAttackAction extends GameAction("Declare Attack Action") {

	/**
	 * Working with hooks to prepare for game-action-modification.
	 * Hooks:
	 * - beforeDeclareAttackAction
	 * - declareAttackAction
	 * - afterDeclareAttackAction
	 */
	override def execute(): Unit = {
		super.execute
		val availableAttackers = beforeDeclareAttackAction
		val selectedAttackers = declareAttackAction(availableAttackers)
		Game.attackers = afterDeclareAttackAction(selectedAttackers)
	}

	/**
	 * Hook where stuff can be done before the attacker gets to select
	 * his attacking creatures.
	 * @return Deck CreatureCards which are able to attack
	 */
	def beforeDeclareAttackAction(): Deck = {
		Game.currentPlayer.battlefield.filter(creature => creature.canAttack).asInstanceOf[Deck]
	}

	/**
	 * This might be replace by listeners for card selection
	 */
	def declareAttackAction(availableAttackers: Deck): Deck = {
		val selectedAttackers = new Deck
		if (availableAttackers.isEmpty) {
			Main.addLine(Game.currentPlayer + ", you have no creatures available for an attack.")
		} else {
			Main.addLine("Choose your attackers:")
			Main.addLine(availableAttackers.toString)
			val cardString = Main.getUserInput(Game.currentPlayer, "Select one or more cards to attack (seperated by ','), or 'skip' to skip:", "skip")
			if (!cardString.equals("skip")) {
				val cardNames = cardString.split(",")
				for (cardName <- cardNames) {
					selectedAttackers += Game.currentPlayer.battlefield.getCardByName(cardName.trim())
				}
			}
		}
		selectedAttackers
	}

	/**
	 * Hook where stuff can be done after the attacker has selected attacking
	 * creatures.
	 * @return Deck The attacking CreatureCards
	 */
	def afterDeclareAttackAction(selectedAttackers: Deck): Deck = {
		selectedAttackers
	}
}