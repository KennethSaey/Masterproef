package masterproef

import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.state.BasicGameState
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.lwjgl.input.Mouse
import org.newdawn.slick.geom.Rectangle
import org.newdawn.slick.Color
import org.newdawn.slick.Input
import org.newdawn.slick.gui.TextField
import org.newdawn.slick.Sound

class SettingsState extends BasicGameState {

	var game: StateBasedGame = null

	val titleText: String = "Settings"
	var width: Int = 0
	var xOffset: Int = 0
	val yOffset: Int = 300
	var lineHeight: Int = 0

	var labelBox: Rectangle = null
	var valueBox: Rectangle = null

	var nameText: String = "  Name:"
	var nameTextBox: Rectangle = null
	var nameValueBox: Rectangle = null
	var textField: TextField = null

	var soundText: String = "  Sound:"
	var soundTextBox: Rectangle = null
	var soundValueBox: Rectangle = null
	var soundValueColor: Color = Color.darkGray

	var saveText: String = "  Save  "
	//	var saveWidth: Int = 0
	var saveColor: Color = Color.darkGray
	var saveBox: Rectangle = null

	var cancelText: String = "  Cancel  "
	//	var cancelWidth: Int = 0
	var cancelColor: Color = Color.darkGray
	var cancelBox: Rectangle = null

	var blub: Sound = null

	def getID(): Int = Masterproef.SETTINGS_ID

	def init(container: GameContainer, game: StateBasedGame) {
		this.game = game
		blub = new Sound("masterproef/data/sounds/blub.wav")
		width = container.getDefaultFont().getWidth("Masterproef 2012-2013")
		xOffset = (container.getWidth() - width) / 2
		lineHeight = container.getDefaultFont().getHeight(titleText) + 10

		val maxWidth = scala.math.max(container.getDefaultFont().getWidth(nameText), container.getDefaultFont().getWidth(soundText))
		labelBox = new Rectangle(xOffset, yOffset + lineHeight, maxWidth, lineHeight)
		valueBox = new Rectangle(xOffset + maxWidth + 10, yOffset + lineHeight, 0, lineHeight)

		nameTextBox = labelBox
		nameValueBox = new Rectangle(valueBox.getX(), valueBox.getY(), 200, valueBox.getHeight())

		soundTextBox = new Rectangle(labelBox.getX(), labelBox.getY() + lineHeight, labelBox.getWidth(), labelBox.getHeight())
		soundValueBox = new Rectangle(valueBox.getX(), valueBox.getY() + lineHeight, container.getDefaultFont().getWidth("yes"), valueBox.getHeight())

		textField = new TextField(container, container.getDefaultFont(), nameValueBox.getX().toInt, nameValueBox.getY().toInt + 5, 200, nameValueBox.getHeight().toInt - 10)
		textField.setBackgroundColor(Color.black)
		textField.setTextColor(Color.white)
		textField.setBorderColor(Color.white)

		saveBox = new Rectangle(labelBox.getX(), labelBox.getY + 3 * lineHeight, container.getDefaultFont().getWidth("> Save <"), lineHeight)
		cancelBox = new Rectangle(labelBox.getX() + container.getDefaultFont().getWidth("> Save <") + 10, labelBox.getY + 3 * lineHeight, container.getDefaultFont().getWidth("> Cancel <"), lineHeight)
	}

	def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		g.setColor(Color.gray)
		g.drawString(titleText, xOffset, yOffset)

		g.setColor(Color.darkGray)
		g.drawString(nameText, nameTextBox.getX, nameTextBox.getY + 5)
		textField.setFocus(true)
		textField.render(container, g)

		g.drawString(soundText, soundTextBox.getX, soundTextBox.getY + 5)
		g.setColor(soundValueColor)
		g.drawString(if (game.asInstanceOf[Masterproef].soundsEnabled) "yes" else "no", soundValueBox.getX, soundValueBox.getY + 5)

		g.setColor(saveColor)
		g.drawString(saveText, saveBox.getX(), saveBox.getY() + 5)
		g.setColor(cancelColor)
		g.drawString(cancelText, cancelBox.getX(), cancelBox.getY() + 5)
	}

	def update(container: GameContainer, game: StateBasedGame, delta: Int) {
	}

	override def enter(container: GameContainer, game: StateBasedGame) {
		textField.setText(game.asInstanceOf[Masterproef].playerName)
	}

	override def leave(container: GameContainer, game: StateBasedGame) {
		saveText = "  Save  "
		saveColor = Color.darkGray
		cancelText = "  Cancel  "
		cancelColor = Color.darkGray
	}

	override def mouseMoved(oldx: Int, oldy: Int, newx: Int, newy: Int) {
		if (saveBox.contains(newx, newy)) {
			if (!saveBox.contains(oldx, oldy)) {
				saveText = "> Save <"
				saveColor = Color.gray
				if (game.asInstanceOf[Masterproef].soundsEnabled) {
					blub.play()
				}
			}
		} else {
			saveText = "  Save  "
			saveColor = Color.darkGray
		}
		if (cancelBox.contains(newx, newy)) {
			if (!cancelBox.contains(oldx, oldy)) {
				cancelText = "> Cancel <"
				cancelColor = Color.gray
				if (game.asInstanceOf[Masterproef].soundsEnabled) {
					blub.play()
				}
			}
		} else {
			cancelText = "  Cancel  "
			cancelColor = Color.darkGray
		}
		if (soundValueBox.contains(newx, newy)) {
			if (!soundValueBox.contains(oldx, oldy) && game.asInstanceOf[Masterproef].soundsEnabled) {
				soundValueColor = Color.gray
				blub.play()
			}
		} else {
			soundValueColor = Color.darkGray
		}
	}

	override def mouseClicked(button: Int, x: Int, y: Int, clickCount: Int) {
		if (button == 0) {
			if (saveBox.contains(x, y)) {
				game.asInstanceOf[Masterproef].playerName = textField.getText()
				game.enterState(Masterproef.MENU_ID)
			} else {
				saveText = "  Save  "
			}
			if (cancelBox.contains(x, y)) {
				game.enterState(Masterproef.MENU_ID)
			} else {
				cancelText = "  Cancel  "
			}
			if (soundValueBox.contains(x, y)) {
				game.asInstanceOf[Masterproef].soundsEnabled = !game.asInstanceOf[Masterproef].soundsEnabled
			}
		}
	}
}