package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature
import scala.reflect.Manifest

trait Ability extends (Creature => AbilityCreature) {

	def apply(creature: Creature): AbilityCreature = {
		var objectName = this.getClass.getSimpleName()
		objectName = objectName.substring(0, objectName.size - 1)
		val className = objectName + "Creature"
		val c = Class.forName("masterproef.cards.abilities." + className)
		c.getConstructors()(0).newInstance(creature).asInstanceOf[AbilityCreature]
	}
}