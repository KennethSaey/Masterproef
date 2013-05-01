package masterproef.gamestates

import org.newdawn.slick.gui.AbstractComponent
import org.newdawn.slick.gui.ComponentListener
import masterproef.Masterproef
import org.newdawn.slick.GameContainer
import org.newdawn.slick.state.StateBasedGame
import masterproef.cards.Card
import org.newdawn.slick.Graphics
import org.newdawn.slick.MouseListener

class ZoomState extends BasicPlayState with ComponentListener with MouseListener {

	var zoom: Card = null
	val zoomFactor: Int = 4
	
	def getID(): Int = Masterproef.ZOOM_ID

	def componentActivated(source: AbstractComponent): Unit = {
	}
	
	override def init(container: GameContainer, game: StateBasedGame) {
		super.init(container, game)
	}
	
	override def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		super.render(container, game, g)
		g.scale(zoomFactor, zoomFactor)
//		g.setAntiAlias(true)
		zoom.renderAt(container, g, container.getWidth() / 2 / zoomFactor - 80, container.getHeight() / 2 / zoomFactor - 110, 160, 220)
	}
	
	override def enter(container: GameContainer, game: StateBasedGame) {
		super.enter(container, game)
		zoom = model.zoom
	}
}