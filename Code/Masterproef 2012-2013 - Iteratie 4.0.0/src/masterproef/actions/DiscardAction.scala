package masterproef.actions

import masterproef.cards.Creature
import masterproef.cards.Card
import masterproef.players.Player
import scala.util.Random

class DiscardAction(count: Int) extends TargetAction {

	def execute {
		if(executable) {
			val player = target.asInstanceOf[Player]
			val filteredHand = player.hand.filter(!_.selected)
			val handCount = filteredHand.size
			if(handCount < count) {
				filteredHand.foreach(player.destroy(_))
			} else {
				val r = new Random
				for(i <- 0 until count) {
					val card = filteredHand(r.nextInt(filteredHand.size))
					player.destroy(card)
				}
			}
		}
	}
	
	def executable: Boolean = {
		target != null
	}
	
	def targetable(target: Any): Boolean = {
		target.isInstanceOf[Player]
	}
	
	override def toString: String = {
		super.toString + " " + count + " cards "
	}
}