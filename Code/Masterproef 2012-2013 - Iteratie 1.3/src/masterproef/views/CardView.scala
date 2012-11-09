package masterproef.views

import java.awt.Color
import java.awt.Dimension
import scala.swing.Alignment
import scala.swing.GridBagPanel
import scala.swing.Label
import scala.swing.Swing
import javax.swing.ImageIcon
import masterproef.cards.Card
import java.awt.Insets
import java.awt.Font
import java.awt.Image
import java.awt.image.BufferedImage
import java.awt.Graphics2D
import sun.java2d.pipe.DrawImage
import masterproef.Main
import scala.swing.event.MouseClicked

class CardView(card: Card) extends GridBagPanel {

	var selected: Boolean = false
	var face: CardFace.Value = CardFace.UP
	val title: Label = new Label() {
		text = card.name
		horizontalAlignment = Alignment.Left
		background = new Color(105, 165, 225)
		opaque = true
		font = new Font("Calibri", Font.PLAIN, 20)
		border = Swing.EmptyBorder(3)
		tooltip = text
	}
	val image: Label = new Label {
		icon = scale(new ImageIcon("src/masterproef/data/images/No image.jpg").getImage(), 230, 200)
	}
	val cardType: Label = new Label {
		text = card.getClass().getName()
		text = text.substring(text.lastIndexOf('.') + 1)
		horizontalAlignment = Alignment.Left
		background = new Color(105, 165, 225)
		opaque = true
		font = new Font("Calibri", Font.PLAIN, 20)
		border = Swing.EmptyBorder(3)
	}
	val text: Label = new Label{
		text = ""
		horizontalAlignment = Alignment.Left
		font = new Font("Calibri", Font.PLAIN, 20)
		border = Swing.LineBorder(Color.DARK_GRAY)
	}

	preferredSize = new Dimension(250, 350)
	maximumSize = new Dimension(250, 350)
	background = Color.WHITE
	border = Swing.LineBorder(Color.DARK_GRAY, 10)
	listenTo(mouse.clicks)
	reactions += {
		case e: MouseClicked => click
	}

	val c = new Constraints

	c.gridwidth = 4
	c.weightx = 0.0
	c.weighty = 0.0
	c.gridx = 0
	c.gridy = 0
	c.fill = GridBagPanel.Fill.Horizontal
	c.insets = new Insets(1, 1, 1, 1)
	add(title, c)
	c.fill = GridBagPanel.Fill.None
	c.gridy = 1
	add(image, c)
	c.gridy = 2
	c.fill = GridBagPanel.Fill.Horizontal
	add(cardType, c)
	c.gridy = 3
	c.fill = GridBagPanel.Fill.Both
	c.weighty = 1.0
	add(text, c)

	def scale(image: Image, maxWidth: Double, maxHeight: Double): ImageIcon = {
		println("Original: " + image.getWidth(null) + "/" + image.getHeight(null))
		val wScale = maxWidth / image.getWidth(null)
		val hScale = maxHeight / image.getHeight(null)
		println("Scales: " + wScale + "/" + hScale)
		val scale = scala.math.min(wScale, hScale)
		val width = (image.getWidth(null) * scale).toInt
		val height = (image.getHeight(null) * scale).toInt
		println("New: " + width + "/" + height)
		val dst = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
		val g2 = dst.createGraphics()
		g2.drawImage(image, 0, 0, width, height, null)
		g2.dispose()
		new ImageIcon(dst)
	}
	
	def click(): Unit = {
		selected = !selected
		if(selected) {
			border = Swing.LineBorder(Color.BLUE, 10)
		}else{
			border = Swing.LineBorder(Color.DARK_GRAY, 10)
		}
	}
	
	
}