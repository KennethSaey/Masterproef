package masterproef.cards

import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import masterproef.cards.abilities.Abilities._

object CardPool {

	val creatures: Array[Creature] = Array(
		new Creature called "Blood Seeker"
			with_default_damage 1
			with_default_health 1
			has_ability Absorb(4),
		new Creature called "Bloodlord of Vaasgoth"
			with_default_damage 3
			with_default_health 3
			has_ability Flying,
		new Creature called "Bloodrage Vampire"
			with_default_damage 3
			with_default_health 1
			has_ability Lifelink,
		new Creature called "Cemetery Reaper"
			with_default_damage 2
			with_default_health 2
			has_ability Deathtouch,
		new Creature called "Child of Night"
			with_default_damage 2
			with_default_health 1
			has_ability Defender,
		new Creature called "Devouring Swarm"
			with_default_damage 2
			with_default_health 1
			has_ability Flying,
		new Creature called "Drifting Shade"
			with_default_damage 1
			with_default_health 1
			has_ability Flying
			has_ability Shadow,
		new Creature called "Duskhunter Bat"
			with_default_damage 4
			with_default_health 2
			has_ability Flying
			has_ability Trample,
		new Creature called "Grave Titan"
			with_default_damage 6
			with_default_health 6
			has_ability Reach,
		new Creature called "Gravedigger"
			with_default_damage 2
			with_default_health 2
			has_ability Horsemanship,
		new Creature called "Onyx Mage"
			with_default_damage 2
			with_default_health 1
			has_ability Reach,
		new Creature called "Reassembling Skeleton"
			with_default_damage 1
			with_default_health 1
			has_ability Indestructible,
		new Creature called "Royal Assassin"
			with_default_damage 1
			with_default_health 1
			has_ability Shadow,
		new Creature called "Rune-Scarred Demon"
			with_default_damage 6
			with_default_health 6,
		new Creature called "Sengir Vampire"
			with_default_damage 4
			with_default_health 4
			has_ability Lifelink,
		new Creature called "Sutured Ghoul"
			with_default_damage 3
			with_default_health 3
			has_ability Trample,
		new Creature called "Tormented Soul"
			with_default_damage 1
			with_default_health 1
			has_ability Unblockable,
		new Creature called "Vampire Outcasts"
			with_default_damage 2
			with_default_health 2
			has_ability Lifelink,
		new Creature called "Vengeful Pharaoh"
			with_default_damage 5
			with_default_health 4,
		new Creature called "Warpath Ghoul"
			with_default_damage 3
			with_default_health 2,
		new Creature called "Zombie Goliath"
			with_default_damage 4
			with_default_health 4
			has_ability Reach
	)
	val lands: Array[Land] = Array(
		new Forest called "Forest 01",
		new Forest called "Forest 02",
		new Forest called "Forest 03",
		new Forest called "Forest 04",
		new Island called "Island 01",
		new Island called "Island 02",
		new Island called "Island 03",
		new Island called "Island 04",
		new Mountain called "Mountain 01",
		new Mountain called "Mountain 02",
		new Mountain called "Mountain 03",
		new Mountain called "Mountain 04",
		new Plains called "Plains 01",
		new Plains called "Plains 02",
		new Plains called "Plains 03",
		new Plains called "Plains 04",
		new Swamp called "Swamp 01",
		new Swamp called "Swamp 02",
		new Swamp called "Swamp 03",
		new Swamp called "Swamp 04"
	)

	def createDeck(cardCount: Int = 20): Deck = {
		//		println(creatures(0).getClass())
		//		println(creatures(1).getClass())
		val availableCards: ArrayBuffer[Card] = new ArrayBuffer[Card]()
		availableCards ++= creatures
				availableCards ++= lands
		//		availableCards ++= lands
		//		availableCards ++= lands
		val deck = new Deck
		val r = new Random()
		for (i <- 0 until cardCount) {
			val nextInt = r.nextInt(availableCards.size)
			deck += availableCards(nextInt).copy
		}
		deck
	}
}