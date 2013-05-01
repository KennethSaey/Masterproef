package masterproef.counters

class DamageHealthCounter(damage: Int, health: Int) extends Counter2(damage, health) {

	def damage: Int = value1
	def health: Int = value2
	def damage_=(damage: Int) = value1 = damage
	def health_=(health: Int) = value2 = health

	private def damageString: String = if (value1 < 0) "" + value1 else "+" + value1
	private def healthString: String = if (value2 < 0) "" + value2 else "+" + value2

	override def toString: String = damageString + "/" + healthString

}