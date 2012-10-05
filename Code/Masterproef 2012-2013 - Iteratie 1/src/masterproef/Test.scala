package masterproef

import scala.collection.mutable.ArrayBuffer
import masterproef.card.Deck
import masterproef.card.Creature

object Test {

	def main(args: Array[String]): Unit = {
		val creature1: Creature = new Creature("Creature 1", 1, 1);
		val creature2: Creature = new Creature("Creature 2", 2, 2);
		val creature3: Creature = new Creature("Creature 3", 3, 3);
		val creature4: Creature = new Creature("Creature 4", 4, 4);
		val deck1: Deck = new Deck();
		deck1 += creature1;
		deck1 += creature1;
		deck1 += creature2;
		deck1 += creature2;
		deck1 += creature3;
		deck1 += creature3;
		deck1 += creature4;
		deck1 += creature4;
		println(creature1);
		println(creature2);
		println(creature3);
		println(creature4);
		println(deck1);
		~ deck1;
		println(deck1);
		deck1.empty();
		println(deck1);
	}

}