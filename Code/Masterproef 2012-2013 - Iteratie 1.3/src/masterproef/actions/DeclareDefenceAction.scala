package masterproef.actions

import masterproef.Game
import masterproef.Main
import scala.collection.mutable.ArrayBuffer
import masterproef.cards.Deck
import masterproef.cards.CreatureCard

class DeclareDefenceAction extends GameAction("Declare Defence Action") {

	override def execute(): Unit = {
		super.execute
		if (!Game.attackers.isEmpty) {
			Main.addLine(Game.currentPlayer + " attacks with:")
			Main.addLine(Game.attackers.toString)
			if (Game.currentOpponent.battlefield.isEmpty) {
				Main.addLine(Game.currentOpponent + ", you have no cards in play, so you cannot defend.")
			} else {
				var opponentCards = Game.currentOpponent.battlefield
				for (attacker <- Game.attackers if !opponentCards.filter(defender => attacker.asInstanceOf[CreatureCard].canBeDefendedBy(defender.asInstanceOf[CreatureCard])).isEmpty) {
					val cardString = Main.getUserInput(Game.currentOpponent, "Please select the cards with which you want to defend " + attacker + " or \"skip\" to skip this attacker or \"skip all\" to skip all attackers\n" + "You can choose from: " + opponentCards.filter(defender => attacker.asInstanceOf[CreatureCard].canBeDefendedBy(defender.asInstanceOf[CreatureCard])), "skip");
					if (cardString.equals("skip")) {
						// Continue
					} else if (cardString.equals("skip all")) {
						opponentCards.clear
					} else {
						val cardNames = cardString.split(",")
						val defenders = new Deck
						for (cardName <- cardNames) {
							val card = Game.currentOpponent.battlefield.getCardByName(cardName.trim())
							if (attacker.asInstanceOf[CreatureCard].canBeDefendedBy(card.asInstanceOf[CreatureCard])) {
								defenders += card
							}
						}
						Game.defenders.put(attacker, defenders)
					}
				}
			}
		}
	}
}