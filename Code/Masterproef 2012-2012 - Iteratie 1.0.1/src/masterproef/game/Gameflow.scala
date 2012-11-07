package masterproef.game

/*
 * Een Game heeft geen mooie boomstructuur.
 * Een Game bestaat uit een of meer GamePhases waarvan de volgorde vast ligt
 * Elke GamePhase bestaat uit een of meer GameActions waarvan de volgorde vast ligt
 * 
 * Game
 *   |
 * Phase -----------------------------> Phase ----------------> Phase
 *   |                                    |                       |
 * Action --> Action --> Action         Action --> Action       Action --> Action --> Action --> Action
 * 
 * Game(firstPhase)
 * Phase(firstAction, nextPhase)
 * Action(nextAction)
 */

private[game] abstract class GameflowElement
private[game] case class Game(firstPhase: GamePhase) extends GameflowElement {
	override def toString(): String = {
		"Game(" + firstPhase.toString(1) + "\n)"
	}
}
private[game] case class GamePhase(name: String, required: Boolean, firstAction: GameAction, nextPhase: GamePhase) extends GameflowElement {
	def toString(tabCount: Int): String = {
		val tabs = "   " * tabCount
		"\n" + tabs + (if (required) "required " else "optional ") + name + firstAction.toString(tabCount + 1) + (if (nextPhase != null) nextPhase.toString(tabCount) else "")
	}
}
private[game] case class GameAction(name: String, required: Boolean, multiple: Boolean, nextAction: GameAction) extends GameflowElement {
	def toString(tabCount: Int): String = {
		val tabs = "   " * tabCount
		"\n" + tabs + (if (multiple) "one or more " else "one ") + (if (required) "required " else "optional ") + name + (if (nextAction != null) nextAction.toString(tabCount) else "")
	}
}

object Gameflow {
	import scala.util.parsing.combinator._

	object GameflowParser extends JavaTokenParsers {
		def game: Parser[Game] =
			(phase) ^^ {
				case phase => {
					Game(phase)
				}
			}
		def phase: Parser[GamePhase] =
			(("required" | "optional") ~ ident ~ actions ~ phase) ^^ {
				case modifier ~ phase ~ actions ~ nextPhase => {
					GamePhase(phase, if (modifier == "required") true else false, actions, nextPhase)
				}
			} |
				(("required" | "optional") ~ ident ~ actions) ^^ {
					case modifier ~ phase ~ actions => {
						GamePhase(phase, if (modifier == "required") true else false, actions, null)
					}
				}
		def actions: Parser[GameAction] =
			("ended by one " ~ ident) ^^ {
				case end ~ actionName => {
					GameAction(actionName, true, false, null)
				}
			} |
				("consisting of" ~ ("one or more" | "one") ~ ("required" | "optional") ~ ident ~ actions) ^^ {
					case consistingOf ~ count ~ modifier ~ actionName ~ actions => {
						GameAction(actionName, if (modifier == "required") true else false, if (count == "one or more") true else false, actions)
					}
				} |
				("followed by" ~ ("one or more" | "one") ~ ("required" | "optional") ~ ident ~ actions) ^^ {
					case consistingOf ~ count ~ modifier ~ actionName ~ actions => {
						GameAction(actionName, if (modifier == "required") true else false, if (count == "one or more") true else false, actions)
					}
				}

		def parse(text: String) = parseAll(game, text)
	}

	def parse(text: String) = {
		GameflowParser.parse(text).get
	}
}