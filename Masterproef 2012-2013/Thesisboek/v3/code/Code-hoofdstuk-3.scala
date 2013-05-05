// Code Listing 1: Card klasse
class Card {
	var _name: String = ""
}
// Code Listing 2: Called-mthode
class Card {
	var _name: String = ""
	def called(name: String): Unit = {
		_name = name
	}
}
// Code Listing 3: Kaart creatie
(new Card()).called("Black Lotus")
// Code Listing 4: Kaart creatie in DSL
new Card called "Black Lotus"
// Code Listing 5: Called-methode met method-chaining
class Card {
	var _name: String = ""
	def called(name: String): this.type = {
		_name = name
		this
	}
}
// Code Listing 6: Impliciete String naar Card conversie
object CardImplicits {
	implicit def String2Card(name: String): Card = {
		new Card called name
	}
}
// Code Listing 7: Statisch importeren van impliciete methodes
import CardImplicits._
// Code listing 8: Kaart creatie met gebruik van implicits
val card: Card = "Black Lotus"
// Code Listing 9: Creature klasse
class Creature extends Card {
	var _damage: Int = 0
	var _health: Int = 0
}
// Code Listing 10: with_damage- en with_health-methodes
class Creature extends Card {
	var _damage: Int = 0
	var _healt: Int = 0
	def with_damage(damage: Int): this.type = {
		_damage = damage
		this
	}
	def with_health(health: Int): this.type = {
		_health = health
		this
	}
}
// Code Listing 11: DSL creature creatie
new Creature called "Stoneforge Mystic" with_damage 1 with_health 2
// Code Listing 12: Impliciete String naar Creature conversie
object CreatureImplicits {
	implicit def String2Creature(name: String): Creature = {
		new Creature called name
	}
}
// Code Listing 13: Creature creatie met implicits
import CreatureImplicits._
val creature: Creature = 
	"Stoneforge Mystic" with_damage 1 with_health 2
// Code Listing 14: Creature met de canBeBlockedBy-methode
class Creature extends Card {
	// ...voorgaande implementatie
	def canBeBlockedBy(other: Creature): Boolean = {
		true
	}
}
// Code Listing 15: FlyingCreature als subklasse van Creature
class FlyingCreature extends Creature {
	override def canBeBlockedBy(other: Creature): Boolean = {
		other.isInstanceOf[FlyingCreature]
	}
}
// Code Listing 16: Creature met Flying trait
trait Flying {
	def canBeBlockedBy(other: Creature): Boolean = {
		other.isInstanceOf[Flying]
	}
}
class FlyingCreature extends Creature with Flying {
}
// Code Listing 17: Het decorator ontwerppatroon
class BasicClass {
	def a() = println("BasicClass.a")
	def b() = println("BasicClass.b")
}
class Decorator(basicClass: BasicClass) extends BasicClass {
	override def a() = basicClass.a()
	override def b() = println("Decorator.b")
}
var base: BasicClass = new BasicClass()
base.a() // "BasicClass.a"
base.b() // "BasicClass.b"
base = new Decorator(base)
base.a() // "BasicClass.a"
base.b() // "Decorator.b"
// Code Listing 18: AbilityCreature klasse
class AbilityCreature(var creature: Creature) extends Creature {
	def called(name: String): this.type = creature.called(name)
	def with_damage(damage: Int): this.type = {
		creature.with_damage(damage)
	}
	def with_health(health: Int): this.type = {
		creature.with_health(health)
	}
	def canBeBlockedBy(other: Creature): Boolean = {
		creature.canBeBlockedBy(other)
	}
}
// Code Listing 19: FlyingCreature klasse
class FlyingCreature(val parent: Creature)
	extends AbilityCreature(parent) {
	override def canBeBlockedBy(other: Creature): Boolean = {
		other.isInstanceOf[FlyingCreature] && 
			parent.canbeBlockedBy(other)
	}
}
// Code Listing 20: Gebruik van vaardigheden
var c: Creature = new Creature
					called "Stoneforge Mystic"
					with_damage 1
					with_health 2
c = new FlyingCreature(c) // Flying toevoegen
c = new UnblockableCreature(c) // Unblockable toevoegen
c = c.parent // Unblockable wegnemen
c = c.parent // Flying wegnemen
// Code Listing 21: Flying vaardigheidsfunctie
val Flying: Function1[Creature, FlyingCreature] = {
	creature => new FlyingCreature(creature)
}
// Code Listing 22: has_ability-methode
class Creature extends Card {
	var _damage: Int = 0
	var _healt: Int = 0
	def with_damage(damage: Int): this.type = {
		_damage = damage
		this
	}
	def with_health(health: Int): this.type = {
		_health = health
		this
	}
	def has_ability(function: Function1[Creature, AbilityCreature]): 
		AbilityCreature = {
			function(this)
	}
}
// Code Listing 23: Vaardgiheid toevoegen in de DSL
new Creature called "Devouring Swarm" has_ability Flying
// Code Listing 24: AbsorbCreature klasse
class AbsorbCreature(val parent: Creature, x: Int)
	extends AbilityCreature(parent) {
}
// Code Listing 25: Absorb vaardigheidsfunctie
val Absorb: Function2[Int, Creature, AbsorbCreature] = {
	i => creature => new AbsorbCreature(creature, i)
}
// Code Listing 26: Vaardigheid met parameter toevoegen in DSL
new Creature "Lymph Sliver" has_ability Absorb(1)
// Code Listing 27: Landkaarten
class Land extends Card {}

class Forest extends Land {}
class Island extends Land {}
class Mountain extends Land {}
class Plains extends Land {}
class Swamp extends Land {}
// Code Listing 28: Landcreatie
val land: Land = new Forest
// Code Listing 29: Requirement-klasse
class Requirement {
	var times: Int = 0
	var land: Land = null
}
// Code Listing 30: Requirement-klasse met *-operator
class Requirement(val times: Int) {
	var land: Land = null
	def *(land: Land): Requirement = {
		this.land = land
		this
	}
}
// Code Listing 31: Set namen voor land-instanties
object LandTypes {
	val Forest: Forest = new Forest
	val Forests: Forest = new Forest
	val Island: Island = new Island
	val Islands: Island = new Island
	val Mountain: Mountain = new Mountain
	val Mountains: Mountain = new Mountain
	val Plain: Plains = new Plains
	val Plains: Plains = new Plains
	val Swamp: Swamp = new Swamp
	val Swamps: Swamp = new Swamp

	val Land: Land = new Land
	val Lands: Land = new Land
}
// Code Listing 32: Impliciete Integer naar Requirement conversie
object RequirementImplicits {
	implicit def Int2Requirement(int: Int) = {
		new Requirement(int)
	}
}
// Code Listing 33: Requirements-klasse
object RequirementsImplicits {
	implicit def Requirement2Requirements(requirement: Requirement): 
		Requirements = {
		new Requirements(requirement)
	}
}
class Requirement extends ArrayBuffer[Requirement] {
	def this(requirement: Requirement) {
		this()
		this += requirement
	}
	def and(other: Requirement): Requirements = {
		this += other
		this
	}
}
// Code Listing 34: Requirements in de DSL
val requirements: Requirements = 3 * Lands and 2 * Swamps
// Code Listing 35: Card-klasse met Requirements
class Card {
	// ...Voorgaande implementatie
	var _requirements: Requirements = new Requirements()
	def requires(requirements: Requirements): this.type = {
		_requirements = requirements
		this
	}
}
// Code Listing 36: Gebruik van Requirements in de DSL
val card: Card = new Creature
					called "Bloodlord of Vaasgoth"
					requires 3 * Lands and 2 * Swamps
// Code Listing 37: Target-klasse
abstract class Target {
	var _target: Any = null
	def targetable(target: Any): Boolean
}
// Code Listing 38: CreatureTarget-klasse
class CreatureTarget extends Target {
	def targetable(target: Any): Boolean = {
		target != null && target.isInstanceOf[Creature]
	}
}
// Code Listing 39: PlayerTarget-klasse
class PlayerTarget extends Target {
	def targetable(target: Any): Boolean = {
		target != null && target.isInstanceOf[Player]
	}
}
// Code Listing 40: Targets-object
object Targets {
	val Creature: CreatureTarget = new CreatureTarget
	val Player: PlayerTarget = new PlayerTarget
}
// Code Listing 41: CompoundTarget-klasse
abstract class CompoundTarget extends Target {
	val targets: ArrayBuffer[Target] = 
		new ArrayBuffer[Target]()
}
// Code Listing 42: ConjunctTarget-klasse
class ConjunctTarget extends CompoundTarget {
	def targetable(target: Any): Boolean = {
		targets.forall(_.targetable(target))
	}
}
// Code Listing 43: DisjunctTarget-klasse
class DisjunctTarget extends CompoundTarget {
	def targetable(target: Any): Boolean = {
		targets.exists(_.targetable(target))
	}
}
// Code Listing 44: ConjunctTarget-klasse met DSL voorzieningen
object ConjuntTargetImplicits {
	implicit def Target2ConjunctTarget(target: Target): 
		ConjunctTarget = new ConjunctTarget(target: Target)
}
class ConjunctTarget extends CompoundTarget {
	def this(target: Target) {
		this()
		targets += target
	}
	def targetable(target: Any): Boolean = {
		targets.forall(_.targetable(target))
	}
	def and(target: Target): ConjunctTarget = {
		targets += target
		this
	}
}
// Code Listing 45: DisjunctTarget-klasse met DSL voorzieningen
object DisjunctTargetImplicits {
	implicit def Target2DisjunctTarget(target: Target):
		DisjunctTarget = new DisjunctTarget(target: Target)
}
class DisjunctTarget extends CompoundTarget {
	def this(target: Target) {
		this()
		targets += target
	}
	def targetable(target: Any): Boolean = {
		targets.exists(_.targetable(target))
	}
	def and(target: Target): DisjunctTarget = {
		targets += target
		this
	}
}
// Code Listing 46: Creatie van samengestelde doelwitten in de DSL
val target: Target = Green or White and Creature
// Code Listing 47: Action-klasse
abstract class Action {
	def execute: Unit
}
// Code Listing 48: TargetAction-klasse
abstract class TargetAction extends Action {
	var _target: Any = null
}
// Code Listing 49: DestroyAction-klasse
class DestroyAction extends TargetAction {
	def execute: Unit = {
		target.asInstanceOf[Creature].destroy
	}
}
// Code Listing 50: Action-object
object Action {
	val Destroy: DestroyAction = new DestroyAction
	val Discard: Int => DiscardAction = {
		x => new DiscardAction(x)
	}
}
// Code Listing 51: Sorcery-klasse
class Sorcery extends Card {
	var _action: Action = null
	var _target: Target = null
	def defined_as(action: Action): this.type = {
		_action = action
		this
	}
	def targets(target: Target): Sorcery = {
		_target = target
		this
	}
}
// Code Listing 52: Creatie van een Sorcery in de DSL
val sorcery: Sorcery = new Sorcery called "Doom Blade"
				defined_as Destroy target NonBlack and Creature










