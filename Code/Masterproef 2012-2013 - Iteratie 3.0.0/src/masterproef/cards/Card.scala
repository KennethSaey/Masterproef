package masterproef.cards

import masterproef.players.Player
import org.newdawn.slick.gui.GUIContext
import masterproef.views.CardView
import org.newdawn.slick.gui.ComponentListener
import org.newdawn.slick.Graphics
import org.newdawn.slick.Image
import masterproef.views.CardView
import masterproef.Masterproef

class Card(renderer: Function6[Card, GUIContext, Int, Int, Int, Int, CardView] = CardView) {

	private var _rendererClass: Function6[Card, GUIContext, Int, Int, Int, Int, CardView] = renderer
	private var _renderer: CardView = null

	protected var _name: String = ""
	protected var _owner: Player = null
	protected var _frontImage: Image = new Image(Masterproef.getClass().getResourceAsStream("/masterproef/assets/cards/CardFront_small.png"), "cardFront", false)
	protected var _backImage: Image = new Image(Masterproef.getClass().getResourceAsStream("/masterproef/assets/cards/CardBack_small.png"), "cardBack", false)
	protected var _artwork: Image = null
	var playable: Boolean = true
	var selectable: Boolean = true
	var selected: Boolean = false

	def copy(): Card = {
		new Card(this._rendererClass) called _name is_owned_by _owner
	}

	// Getters
	def name: String = _name
	def owner: Player = _owner
	def frontImage: Image = _frontImage
	def backImage: Image = _backImage
	def artwork: Image = _artwork
	def title: String = _name
	def subtitle: String = ""
	def text: String = ""
	def manatext: String = ""

	// Setters
	def called(name: String): this.type = {
		_name = name
		this
	}

	def is_owned_by(player: Player): this.type = {
		_owner = player
		this
	}

	def addListener(container: GUIContext, listener: ComponentListener) {
		if (_renderer == null) {
			_renderer = _rendererClass(this, container, 0, 0, 0, 0)
		}
		_renderer.addListener(listener)
	}

	def removeListener(listener: ComponentListener) {
		if (_renderer != null) {
			_renderer.removeListener(listener)
		}
	}

	def renderAt(container: GUIContext, g: Graphics, x: Int, y: Int, width: Int, height: Int, faceUp: Boolean = true) {
		if (_renderer == null) {
			_renderer = _rendererClass(this, container, x, y, width, height)
		} else {
			_renderer.setArea(x, y, width, height)
		}
		_renderer.render(container, g, faceUp)
	}
}