package masterproef

import java.util.prefs.Preferences
import org.newdawn.slick.AppGameContainer
import org.newdawn.slick.GameContainer
import org.newdawn.slick.state.StateBasedGame
import masterproef.counters.PoisonCounter
import masterproef.gamestates.AttackState
import masterproef.gamestates.DeclareAttackState
import masterproef.gamestates.DeclareBlockState
import masterproef.gamestates.DrawCardState
import masterproef.gamestates.GameOverState
import masterproef.gamestates.MainMenuState
import masterproef.gamestates.PlayCardState
import masterproef.gamestates.SettingsState
import masterproef.counters.Counters

object Masterproef {
	val MENU_ID: Int = 0
	val SETTINGS_ID: Int = 1
	val PLAY_ID: Int = 2
	val DRAW_CARD_ID = 3
	val PLAY_CARD_ID = 4
	val DECLARE_ATTACK_ID = 5
	val DECLARE_BLOCK_ID = 6
	val ATTACK_ID = 7
	val GAME_OVER_ID = 8
	
	var masterproef: Masterproef = null

	def main(args: Array[String]) {
		masterproef = new Masterproef
		val app = new AppGameContainer(masterproef)
		app.setDisplayMode(1600, 1000, false)
		app.setTargetFrameRate(60)
		app.setForceExit(false)
		app.start()
		masterproef.doCleanup()
		System.exit(0)
	}
	
	def model = masterproef.model
}

class Masterproef extends StateBasedGame("Masterproef 2012-2013 (c) Kenneth Saey") {
	
	val prefs: Preferences = Preferences.userRoot().node(this.getClass().getName())
	
	var playerName: String = prefs.get("playerName", "")
	var soundsEnabled: Boolean = prefs.getBoolean("soundsEnabled", true)
	
	val model: GameModel = new GameModel(this)
	var modelInitialised: Boolean = false
	
	def initStatesList(container: GameContainer) {
		addState(new MainMenuState)
		addState(new SettingsState)
		addState(new DrawCardState)
//		addState(new PlayState)
		addState(new PlayCardState)
		addState(new DeclareAttackState)
		addState(new DeclareBlockState)
		addState(new AttackState)
		addState(new GameOverState)
	}

	def saveSettings() {
		prefs.put("playerName", playerName)
		prefs.putBoolean("soundsEnabled", soundsEnabled)
	}
	
	def doCleanup(){
		saveSettings
	}
}