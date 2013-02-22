package masterproef.cards

import scala.util.Random

/* Objecten voor abilities ipv classes */
object CardPool {

	val cards: Array[Card] = Array(
		new Creature called "Blood Seeker"
			with_default_damage 1
			with_default_health 1,
		new Creature called "Bloodlord of Vaasgoth"
			with_default_damage 3
			with_default_health 3
			has_ability FlyingCreature,
		new Creature called "Bloodrage Vampire"
			with_default_damage 3
			with_default_health 1,
		new Creature called "Cemetery Reaper"
			with_default_damage 2
			with_default_health 2,
		new Creature called "Child of Night"
			with_default_damage 2
			with_default_health 1,
		new Creature called "Devouring Swarm"
			with_default_damage 2
			with_default_health 1
			has_ability FlyingCreature,
		new Creature called "Drifting Shade"
			with_default_damage 1
			with_default_health 1
			has_ability FlyingCreature
			has_ability ShadowCreature,
		new Creature called "Duskhunter Bat"
			with_default_damage 4
			with_default_health 2
			has_ability FlyingCreature
			has_ability TrampleCreature,
		new Creature called "Grave Titan"
			with_default_damage 6
			with_default_health 6
			has_ability ReachCreature,
		new Creature called "Gravedigger"
			with_default_damage 2
			with_default_health 2,
		new Creature called "Onyx Mage"
			with_default_damage 2
			with_default_health 1
			has_ability ReachCreature,
		new Creature called "Reassembling Skeleton"
			with_default_damage 1
			with_default_health 1,
		new Creature called "Royal Assassin"
			with_default_damage 1
			with_default_health 1
			has_ability ShadowCreature,
		new Creature called "Rune-Scarred Demon"
			with_default_damage 6
			with_default_health 6,
		new Creature called "Sengir Vampire"
			with_default_damage 4
			with_default_health 4,
		new Creature called "Sutured Ghoul"
			with_default_damage 3
			with_default_health 3
			has_ability TrampleCreature,
		new Creature called "Tormented Soul"
			with_default_damage 1
			with_default_health 1
			has_ability UnblockableCreature,
		new Creature called "Vampire Outcasts"
			with_default_damage 2
			with_default_health 2,
		new Creature called "Vengeful Pharaoh"
			with_default_damage 5
			with_default_health 4,
		new Creature called "Warpath Ghoul"
			with_default_damage 3
			with_default_health 2,
		new Creature called "Zombie Goliath" with_default_damage 4
			with_default_health 4
			has_ability ReachCreature,
		new Creature called "Bloodlord of Vaasgoth"
			with_default_damage 3
			with_default_health 3
			has_ability FlyingCreature
	)

	def createDeck(cardCount: Int = 20): Deck = {
		val deck = new Deck
		val r = new Random()
		for (i <- 0 until cardCount) {
			val nextInt = r.nextInt(cards.size)
			deck += cards(nextInt).copy
		}
		deck
	}
}