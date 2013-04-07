package masterproef

import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.AppGameContainer
import org.newdawn.slick.GameContainer

object Masterproef {
	
	val PLAY_ID: Int = 1

	def main(args: Array[String]) {
		val masterproef = new Masterproef
		val app = new AppGameContainer(masterproef)
		app.setDisplayMode(1600, 1000, false)
		app.setTargetFrameRate(24)
		app.setForceExit(false)
		app.start()
		System.exit(0)
	}
}

class Masterproef extends StateBasedGame("Masterproef 2012-2013 (c) Kenneth Saey") {

	def initStatesList(container: GameContainer) {
		addState(new PlayState)
	}
}