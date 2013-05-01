package masterproef.cards

import masterproef.players.Player
import org.newdawn.slick.gui.GUIContext
import masterproef.views.CardView
import org.newdawn.slick.gui.ComponentListener
import org.newdawn.slick.Graphics
import org.newdawn.slick.Image
import masterproef.views.CardView
import masterproef.Masterproef
import scala.collection.mutable.ArrayBuffer

object CardImplicits {
	implicit def String2Card(name: String): Card = new Card called name
}

class Card(r: Function6[Card, GUIContext, Int, Int, Int, Int, CardView] = CardView) {

	var _rendererClass: Function6[Card, GUIContext, Int, Int, Int, Int, CardView] = r
	var _renderer: CardView = null

	var _name: String = ""
	var _owner: Player = null
	var _frontImage: Image = new Image(Masterproef.getClass().getResourceAsStream("/masterproef/assets/cards/CardFront_small.png"), "cardFront", false)
	var _backImage: Image = new Image(Masterproef.getClass().getResourceAsStream("/masterproef/assets/cards/CardBack_small.png"), "cardBack", false)
	var _artwork: Image = null
	var _playable: Boolean = true
	var _selectable: Boolean = true
	var _selected: Boolean = false
	
	var _requirements: Requirements = new Requirements()

	def copy(): Card = {
		new Card(this._rendererClass) called _name is_owned_by _owner requires _requirements
	}

	// Getters
	def renderer: CardView = _renderer
	def name: String = _name
	def owner: Player = _owner
	def requirements: Requirements = _requirements
	def frontImage: Image = _frontImage
	def backImage: Image = _backImage
	def artwork: Image = _artwork
	def title: String = _name
	def subtitle: String = ""
	def text: String = ""
	def manatext: String = ""
	def playable: Boolean = _playable
	def selectable: Boolean = _selectable
	def selected: Boolean = _selected

	// Setters
	def renderer_=(renderer: CardView) = _renderer = renderer
	def called(name: String): this.type = {
		_name = name
		this
	}

	def is_owned_by(player: Player): this.type = {
		_owner = player
		this
	}
	
	def requires(requirements: Requirements): this.type = {
		_requirements = requirements
		this
	}

	def playable_=(playable: Boolean) = _playable = playable
	def selectable_=(selectable: Boolean) = _selectable = selectable
	def selected_=(selected: Boolean) = _selected = selected

	def addListener(container: GUIContext, listener: ComponentListener) {
		initRenderer(container)
		renderer.addListener(listener)
	}

	def removeListener(listener: ComponentListener) {
		if (renderer != null) {
			renderer.removeListener(listener)
		}
	}

	def renderAt(container: GUIContext, g: Graphics, x: Int, y: Int, width: Int, height: Int, faceUp: Boolean = true) {
		initRenderer(container)
		renderer.setArea(x, y, width, height)
		renderer.render(container, g, faceUp)
	}

	def initRenderer(container: GUIContext) {
		if (renderer == null) {
			renderer = _rendererClass(this, container, 0, 0, 0, 0)
		}
	}
	
	// Other
	def ==(other: Card): Boolean = {
		_name == other.name
	}
	
	def meetsRequirements(cards: ArrayBuffer[Card]): Boolean = {
		for(requirement <- requirements) {
			if(cards.count(card => card == requirement.card) < requirement.times) {
				false
			}
		}
		cards.count(_.isInstanceOf[Land]) >= requirements.count
	}
	
	def endTurn {
		
	}
	
	def startTurn {
		
	}
	
	// Hooks
	
	def onPlayerAttackInBattlefieldStart {}
	def onPlayerAttackInBattlefieldEnd {}
	def onPlayerPlayInBattlefield(card: Card) {}
	def onDeath {}
	
}