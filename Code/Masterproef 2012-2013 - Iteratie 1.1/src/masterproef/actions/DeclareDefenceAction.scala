package masterproef.actions

import masterproef.Game
import masterproef.Main
import scala.collection.mutable.ArrayBuffer
import masterproef.cards.Deck

class DeclareDefenceAction extends GameAction("Declare Defence Action") {

	override def execute(): Unit = {
		super.execute
		if(!Game.attackers.isEmpty){
			Main.addLine(Game.currentPlayer + " attacks with:")
			Main.addLine(Game.attackers.toString)
			if(Game.currentOpponent.battlefield.isEmpty){
				Main.addLine(Game.currentOpponent + ", you have no cards in play, so you cannot defend.")
			}else{
				var opponentCards = Game.currentOpponent.battlefield
				for(attacker <- Game.attackers if !opponentCards.isEmpty){
					val cardString = Main.getUserInput(Game.currentOpponent, "Please select the cards with which you want to defend " + attacker + " or \"skip\" to skip this attacker or \"skip all\" to skip all attackers<br>" + "You can choose from: " + opponentCards, "skip");
					if(cardString.equals("skip")) {
						// Continue
					}else if(cardString.equals("skip all")){
						opponentCards.clear
					} else {
						val cardNames = cardString.split(",")
						val defenders = new Deck
						for(cardName <- cardNames) {
							defenders += Game.currentOpponent.battlefield.getCardByName(cardName.trim())
						}
						Game.defenders.put(attacker, defenders)
					}
				}
			}
		}
	}
}