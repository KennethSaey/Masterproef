package masterproef

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;

object TextFieldTest {

	def main(args: Array[String]) {
		new AppGameContainer(new TextFieldTest(), 800, 600, false).start();
	}

}

class TextFieldTest extends BasicGame("TextFieldTest") {

	var username: TextField = null

	def init(c: GameContainer) {
		c.getGraphics().setBackground(Color.gray);

		username = new TextField(c, c.getDefaultFont(), 500, 500, 200, 20);
		username.setBorderColor(Color.black);
		username.setBackgroundColor(Color.white);
		username.setTextColor(Color.black);
		// username.setAcceptingInput(true);
		// username.setCursorVisible(true);
		// username.setText("Tree");
	}

	def render(c: GameContainer, g: Graphics) {
		username.render(c, g);
	}

	def update(c: GameContainer, delta: Int) {

	}
}