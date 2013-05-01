package masterproef.counters

class Counter1(val value: Int) extends Counter {

	override def equals(other: Any): Boolean = if (getClass != other.getClass) false else value == other.asInstanceOf[this.type].value
	
	override def hashCode(): Int = value

	override def toString: String = "" + value
}