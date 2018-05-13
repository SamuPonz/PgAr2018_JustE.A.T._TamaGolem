/**
 * 
 * This class represents the prototype of a type Golem object. These odd creatures are able to eat and spit out Stones.
 *
 */
public class Golem {

	private static final int INITIAL_HEALTH = 10;  //valore arbitrario
	
	private String name;
	private int health = INITIAL_HEALTH;
	private Stone[] eatenStones;
	
	public Golem(int eatableStones) {
		this.setName(name);
		eatenStones = new Stone[eatableStones];
	}
	
	public int shoot(int index, Golem otherGolem) { //Aggiungere qui la perdita di vita!!!!
		Stone attackingStone = eatenStones[index];
		Stone defendingStone = otherGolem.eatenStones[index];
		
		int healthReduction = damageDealt(attackingStone, defendingStone);
		otherGolem.health -= healthReduction;
		
		return healthReduction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getHealth() {
		return health;
	}
	
	public Stone[] getEatenStones() {
		return eatenStones;
	}
	
	public boolean isDead() {
		if(health <= 0)
			return true;
		else return false;
	}
	
	public void feed(Element[] kindOfStones) {
		for(int i = 0; i < eatenStones.length; i++)
			eatenStones[i] = new Stone(kindOfStones[i]);	
	}
	
	public int damageDealt(Stone attackingStone, Stone defendingStone) {
		int damage;
		damage = 3; //Associare alla classe Equilibrium per capire l'entità del danno
		return damage;
	}
	
}
