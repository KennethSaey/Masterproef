package masterproef.views

import scala.swing.Panel
import masterproef.cards.CreatureCard
import masterproef.cards.Deck
import java.awt.Graphics2D
import java.awt.Dimension

class DeckView(deck: Deck) extends Panel {

	preferredSize = new Dimension(600, 380)
	opaque = false
	for(card <- deck){
		_contents += new CreatureCardView(card.asInstanceOf[CreatureCard])
	}
//	for (i <- 0 to deck.size - 1) {
//		val card = deck(i)
//		val cardView = new CreatureCardView(card.asInstanceOf[CreatureCard])
//		cardView.bounds.x = 10 + i * 60
//		cardView.bounds.y = 10
//		_contents += cardView
//	}

//	override def paintComponent(g: Graphics2D) = {
//		super.paintComponent(g)
//		println("DeckView.paintComponent")
//		var i = 0;
//		for (card <- deck.take(5)) {
//			val cardView = new CreatureCardView(card.asInstanceOf[CreatureCard])
//			val gr = g.create(10 + i * 60, 10, 250, 350)
//			cardView.paintComponent(gr.asInstanceOf[Graphics2D])
//			i += 1
//		}
//	}

	override def paintChildren(g: Graphics2D) = {
		super.paintComponent(g)
		println("DeckView.paintChildren")
		var i = 0;
		for(cardView <- _contents.take(5)){
			val gr = g.create(10 + i * 60, 10, 250, 350)
			cardView.asInstanceOf[CreatureCardView].paintComponent(gr.asInstanceOf[Graphics2D])
			i += 1
		}
//		for (card <- deck.take(5)) {
//			val cardView = new CreatureCardView(card.asInstanceOf[CreatureCard])
//			val gr = g.create(10 + i * 60, 10, 250, 350)
//			cardView.paintComponent(gr.asInstanceOf[Graphics2D])
//			i += 1
//		}
	}
}