import java.util.ArrayList;

/**
 * 
 * This class represents a prototype of a player.
 *
 */
public class Player {
	
	private String name;
	private ArrayList<Golem> golems;
	private int maxNumberOfGolems;
	private int defeatedGolems = 0;
	
	public Player(String name, int maxNumberOfGolems) {
		this.name = name;
		this.maxNumberOfGolems = maxNumberOfGolems;
		golems = new ArrayList<Golem>();
	}

	public boolean isDefeated() {
		if(defeatedGolems == maxNumberOfGolems)
			return true;
		else
			return false;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Golem> getGolems() {
		return golems;
	}
	
	public void increaseDefeatedGolems() {
		defeatedGolems ++;
	}
	
	public int getDefeatedGolems() {
		return defeatedGolems;
	}
	
}
