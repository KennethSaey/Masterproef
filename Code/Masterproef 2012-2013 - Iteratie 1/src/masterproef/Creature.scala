package masterproef
/**
 * @author Kenneth
 *
 */
class Creature(name: String = "Unnamed Creature", defaultAttack: Int, defaultHealth: Int) extends Card(name) {
	var modifiedAttack: Int = defaultAttack;
	var modifiedHealth: Int = defaultHealth;
	var currentAttack: Int = defaultAttack;
	var currentHealth: Int = defaultHealth;
	var alive: Boolean 	= true;
	var state: CardState.Value = CardState.TABBED;
	
	def takeDamage(damage: Int): Int = {
		currentHealth = math.min(0, defaultHealth - damage);
		if(currentHealth == 0){
			die();
		}
		currentHealth;
	}
	
	def attack(opponent: Creature): Unit = {	
		opponent.takeDamage(currentAttack)
		if(opponent.alive) {
			takeDamage(opponent.currentAttack);
		}
	}
	
	def die(): Unit = {
		alive = false;
	}
	
	def activate(): Unit = {
		state = CardState.ACTIVE;
	}
	
	def tab(): Unit = {
		state = CardState.TABBED;
	}
	
	override def toString() = {
		"[Creature] " + name + " (" + currentAttack + "/" + currentHealth + ")";
	}
	
	
}