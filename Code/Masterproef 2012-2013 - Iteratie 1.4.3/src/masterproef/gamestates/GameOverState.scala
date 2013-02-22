package masterproef.gamestates

import org.newdawn.slick.state.BasicGameState
import masterproef.Masterproef
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.Color

class GameOverState extends BasicGameState {

	var winner: String = ""

	def getID(): Int = Masterproef.GAME_OVER_ID

	def init(container: GameContainer, game: StateBasedGame) {

	}

	def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		g.setFont(BasicPlayState.largestFont)
		g.setColor(Color.red)
		g.drawString("GAME OVER", (container.getWidth() - g.getFont().getWidth("GAME OVER")) / 2, (container.getHeight() - g.getFont().getHeight("GAME OVER")) / 2 - g.getFont().getHeight("GAME OVER") - 5)
		g.setColor(Color.white)
		g.drawString(winner, (container.getWidth() - g.getFont().getWidth(winner)) / 2, (container.getHeight() - g.getFont().getHeight(winner)) / 2)
	}

	def update(container: GameContainer, game: StateBasedGame, delta: Int) {
	}

	override def enter(container: GameContainer, game: StateBasedGame) {
		super.enter(container, game)
		winner = game.asInstanceOf[Masterproef].model.winner + " wins!"
	}
}