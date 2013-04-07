package masterproef.cards

import scala.collection.mutable.ArrayBuffer

class Counters extends ArrayBuffer[CounterSet] {

	def damage: Int = (0 /: this)((total, counterSet) => total + (counterSet.times * counterSet.counter.damage))
	def health: Int = (0 /: this)((total, counterSet) => total + (counterSet.times * counterSet.counter.health))
	
	override def -=(counterSet: CounterSet): this.type = {
		for(i <- 0 until size) {
			val cs = this(i)
			if(cs equals(counterSet)) {
				this.remove(i)
				return this
			}
		}
		this
	}
	
	def damageString: String = {
		val damage = this.damage
		if(damage == 0) {
			""
		} else if(damage < 0) {
			"" + damage
		} else {
			"+" + damage
		}
	}
	
	def healthString: String = {
		val health = this.health
		if(health == 0) {
			""
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