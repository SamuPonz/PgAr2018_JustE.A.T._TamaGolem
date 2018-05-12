/**
 * 
 * This class represents the prototype of an object of type Golem. These odd creatures are able to control Stones.
 *
 */
public class Golem {

	private String name;
	private final int health = 10; //valore arbitrario
	private int totalDamage; //se decidessimo di decrementare ogni volta la vita non serve, se teniamo la vita costante lavoriamo con i danni che accumula si.
	private Stone[] eatedStones;
	
	public Golem(int eatableStones) {
		
		this.setName(name);
		
		eatedStones = new Stone[eatableStones];
		
	}
	
	public Stone shoot() {
		
		return
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
