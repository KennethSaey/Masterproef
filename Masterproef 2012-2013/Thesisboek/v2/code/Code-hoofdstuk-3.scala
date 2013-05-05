// Code Listing 1: Card klasse
class Card {
	var _name: String = ""
}
// Code Listing 2: Called-methode
class Card {
	var _name: String = ""
	def called(name: String): this.type = {
		_name = name
		this
	}
}
// Code Listing 3: DSL kaart creatie
new Card called "Black Lotus"
// Code Listing 4: Kaart creatie zonder syntactic sugar
(new Card).called("Black Lotus")
// Code Listing 5: Kaart creatie zonder method chaining
val card = new Card
card called "Black Lotus"
// Code Listing 6: Impliciete String naar Card conversie
object CardImplicits {
	implicit def String2Card(name: String): Card = {
		new Card called name
	}
}
class Card {
	var _name: String = ""
	def called(name: String): this.type = {
		_name = name
		this
	}
}
// Code Listing 7: Kaart creatie met implicits
"Black Lotus"
// Code Listing 8: Creature klasse
class Creature extends Card {
	var _damage: Int = 0
	var _health: Int = 0
}
// Code Listing 9: with_damage- en with_health-methodes
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
// Code Listing 10: DSL creature creatie
new Creature called "Stoneforge Mystic" with_damage 1 with_health 2
// Code Listing 11: Impliciete String naar Creature conversie
object CreatureImplicits {
	implicit def String2Creature(name: String): Creature = {
		new Creature called name
	}
}
// Code Listing 12: Creature creatie met implicits
"Stoneforge Mystic" with_damage 1 with_health 2
// Code Listing 13: AbilityCreature klasse
class AbilityCreature(var creature: Creature) extends Creature {
	def called(name: String): this.type = creature.called(name)
	def with_damage(damage: Int): this.type = {
		creature.with_damage(damage)
	}
	def with_health(health: Int): this.type = {
		creature.with_health(health)
	}
}
// Code Listing 14: FlyingCreature klasse
class FlyingCreature(val parent: Creature)
	extends AbilityCreature(parent) {
}
// Code Listing 15: Flying vaardigheidsfunctie
val Flying: Function1[Creature, FlyingCreature] = {
	c => new FlyingCreature(c)
}
// Code Listing 16: has_ability-methode
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
// Code Listing 17: Vaardgiheid toevoegen in de DSL
new Creature called "Devouring Swarm" has_ability Flying
// Code Listing 18: AbsorbCreature klasse
class AbsorbCreature(val parent: Creature, x: Int)
	extends AbilityCreature(parent) {
}
// Code Listing 19: Absorb vaardigheidsfunctie
val Absorb: Function2[Int, Creature, AbsorbCreature] = {
	i => c => new AbsorbCreature(c, i)
}
// Code Listing 20: Vaardigheid met parameter toevoegen in DSL
new Creature "Lymph Sliver" has_ability Absorb(1)