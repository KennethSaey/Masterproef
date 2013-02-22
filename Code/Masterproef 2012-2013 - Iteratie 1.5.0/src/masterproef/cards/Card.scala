package masterproef.cards

import masterproef.players.Player

class Card {

	var _name: String = ""
	private var _state: CardState.Value = CardState.UNPLAYED
	private var _owner: Player = null
	
	def copy: Card = {
		val card = new Card
		card._name = _name
		card._state = _state
		card._owner = _owner
		card
	}

	def state: CardState.Value = _state
	def owner: Player = _owner

	def play = {
		_state = CardState.PLAYED
	}

	def activate = {
		_state = CardState.ACTIVE
	}

	def tab = {
		_state = CardState.TABBED
	}

	def kill = {
		_state = CardState.DEAD
	}

	def exile = {
		_state = CardState.EXILED
	}
	
	def unplay = {
		_state = CardState.UNPLAYED
	}
	
	def state_=(state: CardState.Value) = {
		_state = state
	}
	
	def owner_=(owner: Player) = {
		_owner = owner
	}

	def called(name: String): this.type = {
		this._name = name
		this
	}
	
	def apply(name: String): this.type = {
		this._name = name
		this
	}
	
	def is_owned_by(player: Player): this.type = {
//		println(this._name + " is now owned by " + player.name)
		this._owner = player
		this
	}
	
	override def toString(): String = {
		_name
	}
}