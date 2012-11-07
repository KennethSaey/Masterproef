sealed trait Expression {
	def eval(env: Map[String, Literal]): Literal
}

sealed trait Literal extends Expression {
	def eval(env: Map[String, Literal]) = this
	def doubleValue: Double
	def boolValue: Boolean
	def stringValue: String
}

case class NumberLiteral(literal: Double) extends Literal {
	def doubleValue = literal
	def boolValue = literal != 0.0
	def stringValue = literal.toString
	override def toString = literal.toString
}

case class BooleanLiteral(literal: Boolean) extends Literal {
	def doubleValue = if (literal) 1.0 else 0.0
	def boolValue = literal
	def stringValue = literal.toString
	override def toString = literal.toString
}

case class StringLiteral(s: String) extends Literal {
	val literal = s.substring(1, s.length - 1)
	def doubleValue = literal.toDouble
	def boolValue = if (literal.toLowerCase == "false") false else true
	def stringValue = literal
	override def toString = s
}

case class Variable(name: String) extends Expression {
	def eval(env: Map[String, Literal]) = env(name)
	override def toString = name
}