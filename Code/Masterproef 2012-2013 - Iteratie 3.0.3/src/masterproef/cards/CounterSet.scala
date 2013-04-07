package masterproef.cards

object CounterSetImplicits {
	implicit def Int2CounterSet(int: Int): CounterSet = new CounterSet(int)
}

class CounterSet(var times: Int) {

	var counter: Counter = null
	
	def *(counter: Counter): CounterSet = {
		this.counter = counter
		this
	}
	
	override def equals(obj: Any): Boolean = {
		if(obj.isInstanceOf[CounterSet]) {
			val counterSet: CounterSet = obj.asInstanceOf[CounterSet]
			return (counter equals counterSet.counter) && times == counterSet.times
		}
		false
	}
	
	def damageString: String = {
		val damage = times * counter.damage
		if(damage == 0) {
			"+0"
		} else if(damage < 0) {
			"" + damage
		} else {
			"+" + damage
		}
	}
	
	def healthString: String = {
		val health = times * counter.health
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