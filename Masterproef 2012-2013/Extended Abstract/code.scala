// Code Listing 1: Ability Creature
class AbilityCreature(var creature: Creature) extends Creature {
	override def damage: Int = creature.damage
	override def health: Int = creature.health
}
// Code Listing 2: Flying Ability
class Flying(val parent: Creature) extends AbilityCreature(parent) {
	override def canBeBlockedBy(creature: Creature): Boolean = {
		if (creature.hasAbility(classOf[Flying])) {
			parent.canBeBlockedBy(creature)
		} else {
			false
		}
	}
}
// Code Listing 3: Creature class
class Creature {
	var _name: String = ""
	var _health: Int = 0
	var _damage: Int = 0

	def called(name: String): this.type = {
		this._name = name
		this
	}
	def with_health(health: Int): this.type = {
		this._health = health
		this
	}
	def with_damage(damage: Int): this.type = {
		this._damage = damage
		this
	}
	def has_ability(function: Creature => AbilityCreature): AbilityCreature = {
		function(this)
	}
}
// Code Listing 4: Flying object
object Flying extends (Creature => Flying) {
	def apply(creature: Creature): Flying = new Flying(creature)
}
// Code Listing 5: Creature construction
new Creature called "name" with_damage 4 with_health 5 has_ability Flying
// Code Listing 6: Creature construction without sugar
(new Creature).called("name").with_damage(4).with_health(5).has_ability(Flying())
// Code Listing 7: Creature construction with apply
(new Creature).called("name").with_damage(4).with_health(5).has_ability(Flying.apply())