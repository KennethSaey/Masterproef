package masterproef.views

import masterproef.cards.CreatureCard
import java.awt.Graphics2D
import java.awt.Image
import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import java.awt.Font
import java.awt.Color

class CreatureCardView2(creature: CreatureCard) extends CardView2(creature) {

	override def paintComponent(g: Graphics2D) = {
		super.paintComponent(g)
//		println("CreatureCardView2.paintComponent")
		// The image
		g.drawImage(new ImageIcon("src/masterproef/data/images/" + creature.name + ".jpg").getImage(), 25, 60, 210, 150, null)
		// The damage/health
		g.setColor(new Color(185, 180, 175))
		g.fillRoundRect(175, 310, 65, 30, 10, 10)
		g.setColor(Color.BLACK)
		g.setFont(new Font("Calibri", Font.PLAIN, 20))
		val damage = creature.currentDamage.toString
		val health = creature.currentHealth.toString
		g.drawString(damage, 180 + (if(damage.length==1) 5 else 0), 332)
		g.drawString("/", 205, 332)
		g.drawString(health, 215 + (if(health.length==1) 5 else 0), 332)
	}
	
	def scale(image: Image, maxWidth: Double, maxHeight: Double): Image = {
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
		dst
	}
}