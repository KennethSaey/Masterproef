package masterproef.counters

class Counter2(var value1: Int, var value2: Int) extends Counter {

	override def equals(other: Any): Boolean = {
		if (getClass != other.getClass) {
			false
		} else {
			val inst = other.asInstanceOf[this.type]
			value1 == inst.value1 && value2 == inst.value2
		}
	}
	
	override def hashCode(): Int = 41 * (41 + value1) + value2

	override def toString: String = value1 + "/" + value2

}