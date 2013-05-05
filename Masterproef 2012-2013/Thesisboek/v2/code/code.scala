// Code Listing 1: Card class
class Card {
	var _name: String = ""
}
// Code Listing 2: Card called
class Card {
	var _name: String = ""
	def called(name: String): this.type = {
		_name = name
		this
	}
}
// Code Listing 3: Called DSL
new Card called "Some card name"
// Code Listing 4: Creature class
class Creature extends Card {
	var _damage: Int = 0
	var _health: Int = 0
}
// Code Listing 5: Creature with property
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
// Code Listing 6: Creature DSL
new Creature called "Some creature name" with_damage 3 with_health 4
// Code Listing 7: AbilityCreature class
class AbilityCreature(var creature: Creature) extends Creature {
	def called(name: String): this.type = creature.called(name)
	def with_damage(damage: Int): this.type = creature.with_damage(damage)
	def with_health(health: Int): this.type = creature.with_health(health)
}
// Code Listing 8: FlyingCreature class
class FlyingCreature(val parent: Creature) extends AbilityCreature(parent) {
}
// Code Listing 9: Creature has ability
class Creature extends Card {
	def has_ability(function: Creature => AbilityCreature): AbilityCreature = {
		function(this)
	}
}
// Code Listing 10: Has ability DSL
new Creature called "Some creature name" has_ability wrapper_function
// Code Listing 11: Ability companion object
object Flying extends (Creature => AbilityCreature) {
	def apply(creature: Creature): AbilityCreature = new FlyingCreature(creature)
}
// Code Listing 12: Flying DSL
new Creature called "Some creature name" with_damage 3 with_health 4 has_ability Flying
// Code Listing 13: Ability trait
trait Ability extends (Creature => AbilityCreature) {
	def apply(creature: Creature): AbilityCreature = {
		var objectName = this.getClass.getSimpleName()
		objectName = objectName.substring(0, objectName.size - 1)
		val className = objectName + "Creature"
		val c = Class.forName("masterproef.cards.abilities." + className)
		c.getConstructors()(0).newInstance(creature).asInstanceOf[AbilityCreature]
	}
}
// Code Listing 14: Specific ability companion object
object Flying extends Ability
// Code Listing 15: Ability with argument
trait Ability1Arg[T] extends Ability {
	def apply(x: T)(creature: Creature): AbilityCreature = {
		var objectName = this.getClass.getSimpleName()
		objectName = objectName.substring(0, objectName.size - 1)
		val className = objectName + "Creature"
		val c = Class.forName("masterproef.cards.abilities." + className)
		c.getConstructors()(0).newInstance(creature, x.asInstanceOf[Object])
			.asInstanceOf[AbilityCreature]
	}
}
// Code Listing 16: Ability with argument DSL
object Absorb extends Ability1Arg[Int]
new Creature called "Some creature name" with_ability Absorb(4)
// Code Listing 17: Ability with argument without currying
trait Ability1[T] extends Ability {
	var _x: T = null.asInstanceOf[T]
	override def apply(creature: Creature): AbilityCreature = {
		var objectName = this.getClass.getSimpleName()
		objectName = objectName.substring(0, objectName.size - 1)
		val className = objectName + "Creature"
		val c = Class.forName("masterproef.cards.abilities." + className)
		c.getConstructors()(0).newInstance(creature, _x.asInstanceOf[Object])
			.asInstanceOf[AbilityCreature]
	}
	def apply(x: T): this.type = {
		_x = x
		this
	}
}