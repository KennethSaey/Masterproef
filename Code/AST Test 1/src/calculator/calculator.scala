package calculator {
	
	private[calculator] abstract class Expression
	private[calculator] case class Variable(name: String) extends Expression
	private[calculator] case class Number(value: Double) extends Expression
	private[calculator] case class UnaryOperator(operator: String, arg: Expression) extends Expression
	private[calculator] case class BinaryOperator(operator: String, left: Expression, right: Expression) extends Expression

	object Calculator {
		def main(args: Array[String]): Unit = {
				System.out.println(Calculator.evaluate("((1+2)*3)-(4/(2+0))"))
		}
		
		def evaluate(text: String): Double = evaluate(parse(text))
		
		def evaluate(e: Expression): Double = {
			simplify(e) match {
				case Number(x) => x
				case UnaryOperator("-", x) => -(evaluate(x))
				case BinaryOperator("+", x, y) => (evaluate(x) + evaluate(y))
				case BinaryOperator("-", x, y) => (evaluate(x) - evaluate(y))
				case BinaryOperator("*", x, y) => (evaluate(x) * evaluate(y))
				case BinaryOperator("/", x, y) => (evaluate(x) / evaluate(y))
			}
		}
		
		def simplify(e: Expression): Expression = {
			val simplifySubexpressions = e match {
				case BinaryOperator(op, x, y) => BinaryOperator(op, simplify(x), simplify(y))
				case UnaryOperator(op, x) => UnaryOperator(op, simplify(x))
				case _ => e
			}

			def simplifyTop(x: Expression) = x match {
				case UnaryOperator("-", UnaryOperator("-", x)) => x
				case UnaryOperator("+", x) => x
				case BinaryOperator("+", Number(0), x) => x
				case BinaryOperator("+", x, Number(0)) => x
				case BinaryOperator("-", Number(0), x) => UnaryOperator("-", x)
				case BinaryOperator("-", x, Number(0)) => x
				case BinaryOperator("*", Number(0), x) => Number(0)
				case BinaryOperator("*", x, Number(0)) => Number(0)
				case BinaryOperator("*", Number(1), x) => x
				case BinaryOperator("*", x, Number(1)) => x
				case BinaryOperator("/", x, Number(1)) => x
				case e => e
			}

			simplifyTop(simplifySubexpressions)
		}
		
		import scala.util.parsing.combinator._
		
		object ArithParser extends JavaTokenParsers {
			def expr: Parser[Any] = (term~"+"~term) | (term~"-"~term) | term
			def term: Parser[Any] = (factor~"*"~factor) | (factor~"/"~factor) | factor
			def factor: Parser[Any] = floatingPointNumber | "(" ~> expr <~ ")"
			
			def parse(text: String) = {
				parseAll(expr, text)
			}
		}
		
		object ExpressionParser extends JavaTokenParsers {
			def expr: Parser[Expression] = 
				(term ~ "+" ~ term) ^^ {case lhs~plus~rhs => BinaryOperator("+", lhs, rhs)} | 
				(term ~ "-" ~ term) ^^ {case lhs~minus~rhs => BinaryOperator("-", lhs, rhs)} | 
				term
			def term: Parser[Expression] = 
				(factor ~ "*" ~ factor) ^^ {case lhs~times~rhs => BinaryOperator("*", lhs, rhs)} |
				(factor ~ "/" ~ factor) ^^ {case lhs~div~rhs => BinaryOperator("/", lhs, rhs)} |
				factor
			def factor: Parser[Expression] = 
				"(" ~> expr <~ ")" |
				floatingPointNumber ^^ {x => Number(x.toFloat)}
			def parse(text: String) = parseAll(expr, text)
		}
		
		def parse(text: String) = {
			ExpressionParser.parse(text).get
		}
	}
}