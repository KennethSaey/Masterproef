package masterproef.actions

import masterproef.Main
import masterproef.Game

class PlayCardAction extends GameAction("Play Card Action"){

	override def execute(): Unit = {
		super.execute
		Main.addLine("Your hand contains:")
		Main.addLine(Game.currentPlayer.hand.toString)
		if(!Game.currentPlayer.hand.isEmpty){
			var cardName = Main.getUserInput(Game.currentPlayer, "Please choose a card to put on the battlefield or \"stop\" to stop playing cards:", "stop")
			while(!cardName.equals("stop")){
				val card = Game.currentPlayer.hand.getCardByName(cardName)
				if(card != null){
					Game.currentPlayer.play(card)
				}
				if(!Game.currentPlayer.hand.isEmpty){
					cardName = Main.getUserInput(Game.currentPlayer, "Please choose another card to put on the battlefield or \"stop\" to stop playing cards:", "stop")
				}else{
					cardName = "stop"
				}
			}
		}
	}
}