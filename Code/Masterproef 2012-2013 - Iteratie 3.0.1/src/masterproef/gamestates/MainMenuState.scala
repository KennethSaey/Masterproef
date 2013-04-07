package masterproef.gamestates

import masterproef.Masterproef
import org.newdawn.slick.GameContainer
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.Sound
import org.newdawn.slick.geom.Rectangle
import org.newdawn.slick.Graphics
import org.newdawn.slick.Color
import org.newdawn.slick.state.BasicGameState

class MainMenuState extends BasicGameState {

	var game: StateBasedGame = null

	val titleText: String = "Masterproef 2012-2013"
	var hover: Int = 0
	var width: Int = 0
	var xOffset: Int = 0
	val yOffset: Int = 300
	var lineHeight: Int = 0

	var playText: String = "  Play  "
	var playWidth: Int = 0
	var playColor: Color = Color.darkGray
	var playBox: Rectangle = null
	var settingsText: String = "  Settings  "
	var settingsWidth: Int = 0
	var settingsColor: Color = Color.darkGray
	var settingsBox: Rectangle = null
	var exitText: String = "  Exit  "
	var exitWidth: Int = 0
	var exitColor: Color = Color.darkGray
	var exitBox: Rectangle = null
	
	var blub: Sound = null

	def getID(): Int = Masterproef.MENU_ID

	def init(container: GameContainer, game: StateBasedGame) {
		this.game = game
		blub = new Sound("masterproef/assets/sounds/blub.wav")
		width = container.getDefaultFont().getWidth(titleText)
		xOffset = (container.getWidth() - width) / 2
		lineHeight = container.getDefaultFont().getHeight(titleText) + 10

		playWidth = container.getDefaultFont().getWidth("> Play <")
		playBox = new Rectangle(xOffset, yOffset + lineHeight, playWidth, lineHeight)
		settingsWidth = container.getDefaultFont().getWidth("> Settings <")
		settingsBox = new Rectangle(xOffset, yOffset + 2 * lineHeight, settingsWidth, lineHeight)
		exitWidth = container.getDefaultFont().getWidth("> Exit <")
		exitBox = new Rectangle(xOffset, yOffset + 4 * lineHeight, exitWidth, lineHeight)
	}

	def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		g.setColor(Color.gray)
		g.drawString(titleText, xOffset, yOffset)
		g.setColor(playColor)
		g.drawString(playText, xOffset, yOffset + lineHeight + 5)
		g.setColor(settingsColor)
		g.drawString(settingsText, xOffset, yOffset + 2 * lineHeight + 5)
		g.setColor(exitColor)
		g.drawString(exitText, xOffset, yOffset + 4 * lineHeight + 5)
	}

	def update(container: GameContainer, game: StateBasedGame, delta: Int) {
	}

	override def mouseMoved(oldx: Int, oldy: Int, newx: Int, newy: Int) {
		if (playBox.contains(newx, newy)) {
			if (!playBox.contains(oldx, oldy)) {
				playText = "> Play <"
				playColor = Color.gray
				if(game.asInstanceOf[Masterproef].soundsEnabled){
					blub.play()
				}
			}
		} else {
			playText = "  Play  "
			playColor = Color.darkGray
		}
		if (settingsBox.contains(newx, newy)) {
			if (!settingsBox.contains(oldx, oldy)) {
				settingsText = "> Settings <"
				settingsColor = Color.gray
				if(game.asInstanceOf[Masterproef].soundsEnabled){
					blub.play()
				}
			}
		} else {
			settingsText = "  Settings  "
			settingsColor = Color.darkGray
		}
		if (exitBox.contains(newx, newy)) {
			if (!exitBox.contains(oldx, oldy)) {
				exitText = "> Exit <"
				exitColor = Color.red
				if(game.asInstanceOf[Masterproef].soundsEnabled){
					blub.play()
				}
			}
		} else {
			exitText = "  Exit  "
			exitColor = Color.darkGray
		}
	}

	override def mouseClicked(button: Int, x: Int, y: Int, clickCount: Int) {
		if (button == 0) {
			if (playBox.contains(x, y)) {
				game.enterState(Masterproef.DRAW_CARD_ID)
			}
			if (settingsBox.contains(x, y)) {
				game.enterState(Masterproef.SETTINGS_ID)
			}
			if (exitBox.contains(x, y)) {
				game.getContainer.exit()
			}
		}
	}

	override def leave(container: GameContainer, game: StateBasedGame) {
		playText = "  Play  "
		playColor = Color.darkGray
		settingsText = "  Settings  "
		settingsColor = Color.darkGray
		exitText = "  Exit  "
		exitColor = Color.darkGray
	}
}