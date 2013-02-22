package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature

object Test extends Ability

class TestCreature(val parent: Creature) extends AbilityCreature(parent) {

}