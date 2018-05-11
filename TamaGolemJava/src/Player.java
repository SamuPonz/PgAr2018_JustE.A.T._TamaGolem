/**
 * 
 * This class represents a prototype of a player.
 *
 */
public class Player {
	
	private final int maxNumberOfGolems;
	private final int stonesPerElement;
	
	private String name;
	private int defeatedGolems = 0;
	private Golem[] golems;
	
	public Player(String name, int maxNumberOfGolems, int stonesPerElement) {
		this.name = name;
		this.stonesPerElement = stonesPerElement;
		this.maxNumberOfGolems = maxNumberOfGolems;
		golems = new Golem[maxNumberOfGolems];
	}

	public Golem summon() {
		if(isDefeated) {
			
		}
		else {
			System.out.println();
		}

	}
	
	public Stone selectStone(Element element) {
		
		return
	}
	
	public boolean isDefeated() {
		if(defeatedGolems >= maxNumberOfGolems)
			return true;
		else
			return false;
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
