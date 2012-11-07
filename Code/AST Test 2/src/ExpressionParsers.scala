import scala.util.parsing.combinator.JavaTokenParsers

trait ExpressionParsers extends JavaTokenParsers {
	def boolean: Parser[Expression] = ("true" | "false") ^^ { s => new BooleanLiteral(s.toBoolean) }

	def string: Parser[Expression] = super.stringLiteral ^^ { s => new StringLiteral(s) }

	def double: Parser[Expression] = floatingPointNumber ^^ { s => new NumberLiteral(s.toDouble) }

	def int: Parser[Expression] = wholeNumber ^^ { (s => new NumberLiteral(s.toInt)) }

	def literal: Parser[Expression] = boolean | string | double

	def variable: Parser[Expression] = ident ^^ { s => new Variable(s) }

	def expression: Parser[Expression] = literal | variable
}