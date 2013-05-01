package masterproef.cards.abilities

import masterproef.cards.Creature

object Abilities {

	// Regular abilities
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
	
	// Abilities with parameters
	val Absorb: Int => Creature => AbsorbCreature = i => c => new AbsorbCreature(c, i)
	
	// Abilities with counters
	val Bushido: Int => Creature => BushidoCreature = x => c => new BushidoCreature(c, x)
	val Frenzy: Int => Creature => FrenzyCreature = x => c => new FrenzyCreature(c, x)
	val Rampage: Int => Creature => RampageCreature = x => c => new RampageCreature(c, x)
	val Battlecry: Creature => BattlecryCreature = c => new BattlecryCreature(c)
	val Flanking: Creature => FlankingCreature = c => new FlankingCreature(c)
	val Persist: Creature => PersistCreature = c => new PersistCreature(c)
	val Poisonous: Int => Creature => PoisonousCreature = x => c => new PoisonousCreature(c, x)
	val Vanishing: Int => Creature => VanishingCreature = x => c => new VanishingCreature(c, x)
	val Wither: Creature => WitherCreature = c => new WitherCreature(c)
	val Amplify: Creature => AmplifyCreature = c => new AmplifyCreature(c)
	val Exalted: Creature => ExaltedCreature = c => new ExaltedCreature(c)
	val Graft: Int => Creature => GraftCreature = x => c => new GraftCreature(c, x)
	val Modular: Int => Creature => ModularCreature =  x => c => new ModularCreature(c, x)
}