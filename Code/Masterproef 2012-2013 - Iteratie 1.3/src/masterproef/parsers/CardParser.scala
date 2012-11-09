package masterproef.parsers

import scala.util.parsing.combinator.JavaTokenParsers
import masterproef.cards.Card
import masterproef.cards.CreatureCard
import scala.collection.mutable.ArrayBuffer

object CardParser extends JavaTokenParsers {

//	def creatureCard: Parser[CreatureCard] = """[a-zA-Z'\s-]*""".r ~ "," ~ wholeNumber ~ "," ~ wholeNumber ~ rep("," ~ ident) ^^ {
//		case name ~ spacer1 ~ damage ~ spacer2 ~ health ~ modifiers => {
//			println("Parsing " + name)
//			println(modifiers)
//			if(modifiers.size > 0){
//				println(modifiers(0)._2)
//				println(modifiers(0)._2.getClass().getName())
//			}
//			new CreatureCard(name, damage.toInt, health.toInt)
//		}
//	}
	def creatureCard: Parser[CreatureCard] = """[a-zA-Z'\s-]*""".r ~ "," ~ wholeNumber ~ "," ~ wholeNumber ~ opt(",") ~ repsep(ident,",") ^^ {
		case name ~ spacer1 ~ damage ~ spacer2 ~ health ~ spacer3 ~ abilities => {
			println("Parsing " + name)
			println(abilities)
			val abilityBuffer = new ArrayBuffer[String]()
			abilityBuffer ++= abilities
			new CreatureCard(name, damage.toInt, health.toInt, abilityBuffer)
		}
	}
	
	def parse(text: String) = {
		parseAll(creatureCard, text)
	}
}