/**
 * 
 * This class represents the prototype of an object of type Golem. These odd creatures are able to control Stones.
 *
 */
public class Golem {

	private String name;
	private int health;
	private int totalDamage; //se decidessimo di decrementare ogni volta la vita non serve, se teniamo la vita costante lavoriamo con i danni che accumula si.
	private Stone[] eatedStones;
	
	public Golem() {
		
		this.setName(name);
		
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
