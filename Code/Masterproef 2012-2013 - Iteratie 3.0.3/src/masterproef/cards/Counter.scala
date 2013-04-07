package masterproef.cards

object CounterImplicits {
	implicit def Int2Counter(int: Int) = new Counter(int, int)
}

class Counter(var damage: Int, var health: Int) {

	def \(health: Int): Counter = {
		this.health = health
		this
	}
	
	override def equals(obj: Any): Boolean = {
		if(obj.isInstanceOf[Counter]){
			return (damage equals obj.asInstanceOf[Counter].damage) && (health equals obj.asInstanceOf[Counter].health)
		}
		false
	}
	
	def damageString: String = {
		if(damage == 0) {
			"+0"
		} else if(damage < 0) {
			"" + damage
		} else {
			"+" + damage
		}
	}
	
	def healthString: String = {
		if(health == 0) {
			"+0"
		} else if(health < 0) {
			"" + health
		} else {
			"+" + health
		}
	}
	
	override def toString: String = {
		damageString + "/" + healthString
	}
	
}