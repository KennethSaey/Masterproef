package masterproef.cards

class AbilityCreature1[T](creature: Creature, x: T = null.asInstanceOf[T]) extends AbilityCreature(creature) {

	protected var _x: T = x
	
	override def ability: String = this.getClass().getSimpleName().replace("Creature", "") + " " + _x
	
	override def copy: this.type = {
		this.getClass().getConstructors()(0).newInstance(creature.copy, _x.asInstanceOf[Object]).asInstanceOf[this.type]
	}
}