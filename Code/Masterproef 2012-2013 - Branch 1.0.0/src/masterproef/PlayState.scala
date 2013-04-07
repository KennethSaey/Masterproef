package masterproef

import org.newdawn.slick.state.BasicGameState
import org.newdawn.slick.GameContainer
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.Graphics
import masterproef.cards.Card
import masterproef.views.CardView
import masterproef.cards.Forest
import masterproef.cards.Mountain
import masterproef.cards.Island
import masterproef.cards.Swamp
import masterproef.cards.Plains
import masterproef.cards.Creature
import scala.collection.mutable.ArrayBuffer

class PlayState extends BasicGameState {

	var card = new Card
	var forest = new Forest called "forest-1"
	var island = new Island called "island-1"
	var mountain = new Mountain called "mountain-1"
	var plains = new Plains called "plains-1"
	var swamp = new Swamp called "swamp-1"
	var bloodSeeker = new Creature called "Blood Seeker"
	
	def getID(): Int = Masterproef.PLAY_ID
	
	def init(container: GameContainer, game: StateBasedGame) {
	}
	
	def update(container: GameContainer, game: StateBasedGame, delta: Int) {
		
	}
	
	def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		val r: ArrayBuffer[Card] = new ArrayBuffer[Card]()
		r += card
		r += forest
		r += island
		for(i <- 0 until r.size){
			println(r(i).getClass())
			r(i).renderAt(container, g, 50 + 170*i, 50, 160, 220)
		}
//		card.renderAt(container, g, 50, 50, 160, 220)
//		forest.renderAt(container, g, 220, 50, 160, 220)
//		island.renderAt(container, g, 390, 50, 160, 220)
//		mountain.renderAt(container, g, 560, 50, 160, 220)
//		plains.renderAt(container, g, 730, 50, 160, 220)
//		swamp.renderAt(container, g, 900, 50, 160, 220)
//		bloodSeeker.renderAt(container, g, 50, 280, 160, 220)
	}
}