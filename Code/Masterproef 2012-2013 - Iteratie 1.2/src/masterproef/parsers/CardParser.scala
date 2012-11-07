package masterproef.parsers

import scala.util.parsing.combinator.JavaTokenParsers

import masterproef.cards.Card
import masterproef.cards.CreatureCard

object CardParser extends JavaTokenParsers {

	def creatureCard: Parser[CreatureCard] = """[a-zA-Z'\s-]*""".r ~ "," ~ wholeNumber ~ "," ~ wholeNumber ^^ {
		case name ~ spacer1 ~ damage ~ spacer2 ~ health => {
			new CreatureCard(name, damage.toInt, health.toInt)
		}
	}
	
	def parse(text: String) = {
		parseAll(creatureCard, text)
	}
}