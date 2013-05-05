// Code Listing 1: Java klasse
public class Person {
}
// Code Listing 2: Scala klasse
class Person {
}
// Code Listing 3: Java klasse met publiek veld
public class Person {
	public String name;
	public Person(String name) {
		this.name = name;
	}
}
Person p = new Person("name"); // Constructor
p.name; // Getter
p.name = "new name"; // Setter
// Code Listing 4: Scala klasse met publiek veld
class Person(var name: String){
}
val p: Person = new Person("name") // Constructor
p.name // Getter
p.name = "new name" //Setter
// Code Listing 5: Java klasse met privaat veld
public class Person {
	private String name;
	public Person(String name) {
		this.name = name;
	}
	public String name() { // Getter
		return name;
	}
	public void name(String name) { // Setter
		this.name = name;
	}
}
p.name(); // Getter
p.name("new name"); // Setter
// Code Listing 6: Scala klasse met privaat veld
class Person(name: String) {
	private var _name: String = name
	public def name: String = _name // Getter
	public def name_=(name: String) = _name = name // Setter
}
p.name // Getter
p.name = "new name" // Setter
// Code Listing 7: Anonieme functie
i => i * i * i
// Code Listing 8: Hogere-orde functie
def max(comparator: (Int, Int) => Int, 
	values: ArrayBuffer[Int]): Int = {// Implementatie
}
//Code Listing 9: Infix en postfix operatoren
class Creature {
	def attacks(other: Creature) {
		// Implementation
	}
	def dies = {
		// Implementation
	}
}
// Code Listing 10: Gebruik van infix en postfix operatoren
val creature: Creature = new Creature
val enemy: Creature = new Creature
creature attacks enemy // Infix operator
creature.attacks(enemy) // OO-equivalent
enemy dies // Postfix operator
enemy.dies() // OO-equivalent
// Code Listing 11: Vrije methodenamen
class Fraction(numerator: Int, denominator: Int) {
	def +(other: Fraction): Fraction = {
		new Fraction(numerator * other denominator
			+ denominator * other numerator, 
			denominator * other denominator
		)
	}
}
val quarter: Fraction = new Fraction(1, 4)
val third: Fraction = new Fraction(1, 3)
quarter + third // = Fraction(7, 12)
// Code Listing 12: Automatische constructie van closures
def my_while(cond: => Boolean)(body: => Unit): Unit = {
	if(cond) {
		body
		my_while(cond)(body)
	}
}
var i = 10
my_while(i > 0) {
	println(i)
	i -= 1
}















