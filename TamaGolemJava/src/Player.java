import it.unibs.fp.mylib.InputDati;

/**
 * 
 * This class represents a prototype of a player.
 *
 */
public class Player {
	
	private static final String ELEMENT_REQUEST = "Please, choose the kind of stone you want to feed your golem:";
	private final int maxNumberOfGolems;
	private final int eatableStones;
	
	private String name;
	private int defeatedGolems = 0;
	private Golem[] golems;
	
	public Player(String name, int maxNumberOfGolems, int eatableStones) {
		this.name = name;
		this.maxNumberOfGolems = maxNumberOfGolems;
		this.eatableStones = eatableStones;
		golems = new Golem[maxNumberOfGolems];
		for(int i= 0; i < maxNumberOfGolems; i++) {
			golems[i] = new Golem(eatableStones);
		}
	}

	public Golem summon() {
		
		//during a game, golems are summoned in a sequential way, they are taken from the array from 0 to the array length - 1,
		//the variable defeatedGolems represents the pointer of the current golem.
		if(!isDefeated()) {
			Golem currentGolem = golems[defeatedGolems];	
			for(int i = 0; i < eatableStones; i++) {
				//scegli le pietre fino al numero di pietre che può contenere il golem
			}
		}
		else {
			System.out.println("Nope");
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


}
