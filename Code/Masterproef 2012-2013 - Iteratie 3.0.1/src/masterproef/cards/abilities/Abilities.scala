package masterproef.cards.abilities

import masterproef.cards.AbilityCreature
import masterproef.cards.Creature

object Abilities {

	val Deathtouch: Creature => DeathtouchCreature = c => new DeathtouchCreature(c)
	val Defender: Creature => DefenderCreature = c => new DefenderCreature(c)
	val Flying: Creature => FlyingCreature = c => new FlyingCreature(c)
	val Horsemanship: Creature => HorsemanshipCreature = c => new HorsemanshipCreature(c)
	val Indestructible: Creature => IndestructibleCreature = c => new IndestructibleCreature(c)
	val Lifelink: Creature => LifelinkCreature = c => new LifelinkCreature(c)
	val Reach: Creature => ReachCreature = c => new ReachCreature(c)
	val Shadow: Creature => ShadowCreature = c => new ShadowCreature(c)
	val Trample: Creature => TrampleCreature = c => new TrampleCreature(c)
	val Unblockable: Creature => UnblockableCreature = c => new UnblockableCreature(c)
}