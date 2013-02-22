package masterproef.parsers

import scala.util.parsing.combinator.JavaTokenParsers
import masterproef.cards.Card
import masterproef.cards.CreatureCard
import scala.collection.mutable.ArrayBuffer
import masterproef.cards.Creature
import masterproef.cards.FlyingCreature
import masterproef.cards.ReachCreature
import masterproef.cards.ShadowCreature
import masterproef.cards.TrampleCreature
import masterproef.cards.UnblockableCreature

object CardParser extends JavaTokenParsers {

	def creature: Parser[Creature] = """[a-zA-Z'\s-]*""".r ~ "," ~ wholeNumber ~ "," ~ wholeNumber ~ opt(",") ~ repsep(ident,",") ^^ {
		case name ~ spacer1 ~ damage ~ spacer2 ~ health ~ spacer3 ~ abilities => {
			val abilityBuffer = new ArrayBuffer[String]()
			abilityBuffer ++= abilities
			var creature = new Creature(new CreatureCard(name, damage.toInt, health.toInt, abilityBuffer))
			for(ability <- abilities){
				ability match {
					case "Flying" => creature = new FlyingCreature(creature)
					case "Reach" => creature = new ReachCreature(creature)
					case "Shadow" => creature = new ShadowCreature(creature)
					case "Unblockable" => creature = new UnblockableCreature(creature)
					case "Trample" => creature = new TrampleCreature(creature)
				}
			}
			println("Parsed a creature: " + creature)
			creature
		}
	}
	
	def parse(text: String) = {
		parseAll(creature, text)
	}
}