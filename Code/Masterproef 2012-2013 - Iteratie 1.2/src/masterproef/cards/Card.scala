package masterproef.cards

import masterproef.players.Player

class Card(val name: String) {
	var state: CardState.Value = CardState.UNPLAYED
	var owner: Player = null
	
	def this(name: String, state: CardState.Value, owner: Player) = {
		this(name)
		this.state = state
		this.owner = owner
	}
	
	override def toString(): String = {
		name;
	}

	override def equals(obj: Any): Boolean = {
		obj match {
			case that: Card => that.name == name
		}
	}
	
	def setOwner(player: Player): Unit = {
		owner = player
	}
	
	def copy(): Card = {
		new Card(this.name, this.state, this.owner)
	}
	
}