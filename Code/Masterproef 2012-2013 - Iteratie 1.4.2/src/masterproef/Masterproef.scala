package masterproef

import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties
import org.newdawn.slick.AppGameContainer
import org.newdawn.slick.GameContainer
import org.newdawn.slick.state.StateBasedGame
import java.io.OutputStreamWriter
import java.io.FileOutputStream
import org.newdawn.slick.loading.LoadingList
import org.newdawn.slick.Graphics
import org.newdawn.slick.Color

object Masterproef {
	val MENU_ID: Int = 0
	val SETTINGS_ID: Int = 1
	val PLAY_ID: Int = 2

	def main(args: Array[String]) {
		val masterproef = new Masterproef
		val app = new AppGameContainer(masterproef)
		app.setDisplayMode(1440, 900, false)
		app.setTargetFrameRate(120)
		app.setForceExit(false)
		app.start()
		masterproef.doCleanup()
		System.exit(0)
	}
}

class Masterproef extends StateBasedGame("Masterproef 2012-2013 (c) Kenneth Saey") {

	val settings = new Properties()
	val inputStream: InputStreamReader = new InputStreamReader(new FileInputStream("src/masterproef/data/settings.properties"))
	settings.load(inputStream)
	val outputStream: OutputStreamWriter = new OutputStreamWriter(new FileOutputStream("src/masterproef/data/settings.properties"))
	
	var playerName: String = ""
	var soundsEnabled: Boolean = true
	loadSettings
	
	def initStatesList(container: GameContainer) {
		addState(new MenuState)
		addState(new SettingsState)
		addState(new PlayState)
	}

	def loadSettings() {
		playerName = settings.getProperty("playerName", "")
		soundsEnabled = settings.getProperty("soundsEnabled", "true").equals("true")
	}

	def saveSettings() {
		settings.setProperty("playerName", playerName)
		settings.setProperty("soundsEnabled", "" + soundsEnabled)
		settings.store(outputStream, "")
	}
	
	def doCleanup(){
		saveSettings
	}
}