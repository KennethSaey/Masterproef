package masterproef.card

class Card(name: String = "Unnamed Card") {
	var state: CardState.Value = CardState.TABBED;
	
	def activate(): Unit = {
		state = CardState.ACTIVE;
	}
	
	def tab(): Unit = {
		state = CardState.TABBED;
	}
	
	override def toString() = {
		"[Card] " + name;
	}
}