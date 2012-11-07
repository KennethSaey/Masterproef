import scala.util.parsing.input.CharSequenceReader

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class ExpressionParsersTest extends ExpressionParsers with FlatSpec with ShouldMatchers {
	private def parsing[T](s: String)(implicit p: Parser[T]): T = {
		val phraseParser = phrase(p)
		val input = new CharSequenceReader(s)
		phraseParser(input) match {
			case Success(t, _) => t
			case NoSuccess(msg, _) => throw new IllegalArgumentException("Could not parse '" + s + "': " + msg)
		}
	}

	private def assertFail[T](input: String)(implicit p: Parser[T]) {
		evaluating(parsing(input)(p)) should produce[IllegalArgumentException]
	}

	"The ExpressionParsers" should "parse boolean literals" in {
		implicit val parserToTest = boolean
		parsing("true") should equal(BooleanLiteral(true))
		parsing("false") should equal(BooleanLiteral(false))
		assertFail("True")
		assertFail("False")
		assertFail("TRUE")
		assertFail("FALSE")
		assertFail("truefoo")
	}

//		val they = it

	they should "parse floating point numbers" in {
		implicit val parserToTest = double
		parsing("0.0") should equal(NumberLiteral(0.0))
		parsing("1.0") should equal(NumberLiteral(1.0))
		parsing("-1.0") should equal(NumberLiteral(-1.0))
		parsing("0.2") should equal(NumberLiteral(0.2))
		parsing("-0.2") should equal(NumberLiteral(-0.2))
		parsing(".2") should equal(NumberLiteral(.2))
		parsing("-.2") should equal(NumberLiteral(-.2))
		parsing("2.0e3") should equal(NumberLiteral(2000.0))
		parsing("2.0E3") should equal(NumberLiteral(2000.0))
		parsing("-2.0e3") should equal(NumberLiteral(-2000.0))
		parsing("-2.0E3") should equal(NumberLiteral(-2000.0))
		parsing("2.0e-3") should equal(NumberLiteral(0.002))
		parsing("2.0E-3") should equal(NumberLiteral(0.002))
		parsing("-2.0e-3") should equal(NumberLiteral(-0.002))
		parsing("-2.0E-3") should equal(NumberLiteral(-0.002))
	}

	they should "parse integral numbers" in {
		implicit val parserToTest = int
		parsing("0") should equal(NumberLiteral(0))
		parsing("1") should equal(NumberLiteral(1))
		parsing("-1") should equal(NumberLiteral(-1))
		parsing("20") should equal(NumberLiteral(20))
		parsing("-20") should equal(NumberLiteral(-20))
	}

	they should "parse string literals" in {
		implicit val parserToTest = string
		parsing("\"test\"") should equal(StringLiteral("\"test\""))
		parsing("\"\"") should equal(StringLiteral("\"\"")) //empty string

		assertFail("\"test") //string literal not closed
		assertFail("test") //no quotation marks
		assertFail("\"te\"st\"") //unescaped quotation mark
		//TODO: add interesting cases once we have proper escape handling
	}

	they should "parse variable names" in {
		implicit val parserToTest = variable
		parsing("foo") should equal(Variable("foo"))
		parsing("_foo") should equal(Variable("_foo"))
		parsing("foo_bar") should equal(Variable("foo_bar"))

		assertFail("foo-bar")
		assertFail("+foo")
		assertFail("foo+")
		assertFail("")
	}
}