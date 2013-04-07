package masterproef.cards

import scala.util.Random
import masterproef.cards.abilities.Flying
import masterproef.cards.abilities.Shadow
import masterproef.cards.abilities.Trample
import masterproef.cards.abilities.Unblockable
import masterproef.cards.abilities.Reach
import masterproef.cards.abilities.Lifelink
import masterproef.cards.abilities.Absorb
import masterproef.cards.abilities.Deathtouch
import masterproef.cards.abilities.Defender
import masterproef.cards.abilities.Horsemanship
import masterproef.cards.abilities.Indestructible
import masterproef.cards.abilities.Reach
import masterproef.cards.abilities.Absorb2

/* Objecten abilities ipv classes */
/* Misschien kan een kaart toevoegen aan de CardPool zoveel betekenen als een functie toevoegen die zo een kaart genereert
 * en bij het bouwen van het deck wordt die functie opgeroepen, waardoor eigenlijk een nieuwe kaart wordt gecreëert */
/* currying => werkt niet aangezien haakjes niet weggelaten kunnen worden bij currying*/
object CardPool {

	val cards: Array[Card] = Array(
		new Creature called "Blood Seeker"
			with_default_damage 1
			with_default_health 1
			has_ability Absorb(4),
		new Creature called "Bloodlord of Vaasgoth"
			with_default_damage 1
			with_default_health 1
			has_ability Absorb2(4),
//		new Creature called "Bloodlord of Vaasgoth"
//			with_default_damage 3
//			with_default_health 3
//			has_ability Flying,
//		new Creature called "Bloodrage Vampire"
//			with_default_damage 3
//			with_default_health 1
//			has_ability Lifelink,
//		new Creature called "Cemetery Reaper"
//			with_default_damage 2
//			with_default_health 2
//			has_ability Deathtouch,
//		new Creature called "Child of Night"
//			with_default_damage 2
//			with_default_health 1
//			has_ability Defender,
//		new Creature called "Devouring Swarm"
//			with_default_damage 2
//			with_default_health 1
//			has_ability Flying,
//		new Creature called "Drifting Shade"
//			with_default_damage 1
//			with_default_health 1
//			has_ability Flying
//			has_ability Shadow,
//		new Creature called "Duskhunter Bat"
//			with_default_damage 4
//			with_default_health 2
//			has_ability Flying
//			has_ability Trample,
//		new Creature called "Grave Titan"
//			with_default_damage 6
//			with_default_health 6
//			has_ability Reach,
//		new Creature called "Gravedigger"
//			with_default_damage 2
//			with_default_health 2
//			has_ability Horsemanship,
//		new Creature called "Onyx Mage"
//			with_default_damage 2
//			with_default_health 1
//			has_ability Reach,
//		new Creature called "Reassembling Skeleton"
//			with_default_damage 1
//			with_default_health 1
//			has_ability Indestructible,
//		new Creature called "Royal Assassin"
//			with_default_damage 1
//			with_default_health 1
//			has_ability Shadow,
//		new Creature called "Rune-Scarred Demon"
//			with_default_damage 6
//			with_default_health 6,
//		new Creature called "Sengir Vampire"
//			with_default_damage 4
//			with_default_health 4
//			has_ability Lifelink,
//		new Creature called "Sutured Ghoul"
//			with_default_damage 3
//			with_default_health 3
//			has_ability Trample,
//		new Creature called "Tormented Soul"
//			with_default_damage 1
//			with_default_health 1
//			has_ability Unblockable,
//		new Creature called "Vampire Outcasts"
//			with_default_damage 2
//			with_default_health 2
//			has_ability Lifelink,
//		new Creature called "Vengeful Pharaoh"
//			with_default_damage 5
//			with_default_health 4,
//		new Creature called "Warpath Ghoul"
//			with_default_damage 3
//			with_default_health 2,
		new Creature called "Zombie Goliath"
			with_default_damage 4
			with_default_health 4
			has_ability Reach
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