/**
 * 
 * This class represents the prototype of a type Golem object. These odd creatures are able to eat and spit out Stones.
 *
 */
public class Golem {

	public static final int MIN_INITIAL_HEALTH = 10; //valore arbitrario
	public static final int MAX_INITIAL_HEALTH = 25; //valore arbitrario
	public static int INITIAL_HEALTH = 15;
	
	private String name;
	private int health = INITIAL_HEALTH;
	private Stone[] eatenStones;
	private Equilibrium equilibrium;
	
	public Golem(int eatableStones, Equilibrium equilibrium) {
		this.setName(name);
		eatenStones = new Stone[eatableStones];
		this.equilibrium = equilibrium;
	}
	
	public int shoot(int attackingStoneIndex, int defendingStoneIndex, Golem otherGolem) {
		
		int healthReduction = damageDealt(attackingStoneIndex, defendingStoneIndex);
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
	
	private int damageDealt(int attackingIndex, int defendingIndex) {
		int damage = 0;
		equilibrium.printer(); //La stampa serve solo a noi per controllo, va tolta!
		
		int HypotheticalDamage = equilibrium.getElementsTable()[attackingIndex][defendingIndex];
		
		if(HypotheticalDamage > 0)
			return HypotheticalDamage;
		
		else return damage;
	}
	
	public boolean hasTheSameStones(Golem other) {
		for(int i = 0; i < eatenStones.length; i++)
			if(eatenStones[i].getName().equals(other.getEatenStones()[i].getName()))
					return true;
		return false;
	}
}
