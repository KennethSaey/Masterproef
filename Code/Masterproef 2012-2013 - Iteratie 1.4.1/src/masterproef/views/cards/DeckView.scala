package masterproef.views.cards

import java.awt.Graphics2D
import java.awt.RenderingHints

import scala.swing.Component
import scala.swing.Reactor

import masterproef.cards.Deck
import masterproef.cards.DeckOrderChanged
import masterproef.cards.DeckSizeDecreased
import masterproef.cards.DeckSizeIncreased

object DeckView {
	object Vertical {
		type Direction = Int
		val Up: Direction = -1
		val Down: Direction = 1
	}
	object Horizontal {
		type Direction = Int
		val Left: Direction = -1
		val Right: Direction = 1
	}
}

class DeckView(val deck: Deck, x: Int, y: Int) extends Component with Reactor {

	private var _xOffset = x
	private var _yOffset = y
	private var _vertical = DeckView.Vertical.Up
	private var _horizontal = DeckView.Horizontal.Left
	private var _maximalDeckHeight = 10

	listenTo(deck)
	reactions += {
		case e: DeckOrderChanged => {}
		case e: DeckSizeIncreased => if(deck.size <= _maximalDeckHeight) repaint
		case e: DeckSizeDecreased => if(deck.size < _maximalDeckHeight) repaint
		case e: CreatureCardViewChanged => {
			println("DeckView: CreatureCardViewChanged")
			repaint
		}
	}
	
	def xOffset = _xOffset
	def yOffset = _yOffset
	def vertical = _vertical
	def horizontal = _horizontal
	def maximalDeckHeight = _maximalDeckHeight
	def xOffset_=(newOffset: Int) {
		if (newOffset != _xOffset) {
			_xOffset = newOffset
			repaint
		}
	}
	def yOffset_=(newOffset: Int) {
		if (newOffset != _yOffset) {
			_yOffset = newOffset
			repaint
		}
	}
	def vertical_=(newDirection: DeckView.Vertical.Direction) {
		if (newDirection != _vertical) {
			_vertical = newDirection
			repaint
		}
	}
	def horizontal_=(newDirection: DeckView.Horizontal.Direction) {
		if (newDirection != _horizontal) {
			_horizontal = newDirection
			repaint
		}
	}
	def maximalDeckHeight_=(newDeckHeight: Int) {
		if (newDeckHeight != _maximalDeckHeight && newDeckHeight > 0) {
			_maximalDeckHeight = newDeckHeight
			repaint
		}
	}
	def position_=(x: Int, y: Int) {
		if (x != _xOffset || y != _yOffset) {
			_xOffset = x
			_yOffset = y
			repaint
		}
	}

	override def paintComponent(g: Graphics2D) {
		val gCopy = g.create().asInstanceOf[Graphics2D]
		gCopy.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
		val xOffset = _xOffset + (if (_horizontal == DeckView.Horizontal.Right) 0 else 2 * (_maximalDeckHeight - 1))
		val yOffset = _yOffset + (if (_vertical == DeckView.Vertical.Down) 0 else 2 * (_maximalDeckHeight - 1))
		val numberOfCardsToDraw = scala.math.min(deck.size, _maximalDeckHeight)
		for (i <- 0 until numberOfCardsToDraw) {
			val creatureCardView = new CreatureCardView(deck(deck.size - numberOfCardsToDraw + i), xOffset + 2 * i * _horizontal, yOffset + 2 * i * _vertical, gCopy)
			creatureCardView.paintComponent(gCopy)
		}
		gCopy.dispose()
	}
}