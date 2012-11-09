package masterproef.actions

import masterproef.Main
import masterproef.Game
import masterproef.cards.Deck

class DeclareAttackAction extends GameAction("Declare Attack Action") {

	override def execute(): Unit = {
		super.execute
		Main.addLine("Your battlefield contains:")
		Main.addLine(Game.currentPlayer.battlefield.toString)
		if(!Game.currentPlayer.battlefield.isEmpty){
			val cardString = Main.getUserInput(Game.currentPlayer, "Select one or more cards to attack, or \"skip\" to skip:", "skip")
			if(!cardString.equals("skip")){
				val cardNames = cardString.split(",")
				Game.attackers = new Deck
				for(cardName <- cardNames){
					Game.attackers += Game.currentPlayer.battlefield.getCardByName(cardName.trim())
				}
			}
		}
	}
}