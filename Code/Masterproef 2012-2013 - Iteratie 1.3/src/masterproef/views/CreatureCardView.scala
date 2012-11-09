package masterproef.views

import masterproef.cards.CreatureCard
import scala.swing.Label
import java.awt.Font
import java.awt.Insets
import scala.swing.GridBagPanel
import scala.swing.Swing
import java.awt.Color

class CreatureCardView(creature: CreatureCard) extends CardView(creature) {

	val damage = new Label {
		text = creature.currentDamage.toString
		opaque = true
		background = Color.WHITE
		font = new Font("Calibri", Font.PLAIN, 20)
	}

	val spacer = new Label {
		text = "/"
		opaque = true
		background = Color.WHITE
		font = new Font("Calibri", Font.PLAIN, 20)
	}

	val health = new Label {
		text = creature.currentHealth.toString
		opaque = true
		background = Color.WHITE
		font = new Font("Calibri", Font.PLAIN, 20)
	}

	c.gridwidth = 1
	c.weightx = 1.0
	c.weighty = 0.0
	c.gridx = 0
	c.gridy = 4
	c.fill = GridBagPanel.Fill.Horizontal
	c.insets = new Insets(1, 1, 1, 1)
	add(Swing.HGlue, c)
	c.weightx = 0.0
	c.gridx = 1
	c.fill = GridBagPanel.Fill.None
	add(damage, c)
	c.gridx = 2
	add(spacer, c)
	c.gridx = 3
	add(health, c)
}