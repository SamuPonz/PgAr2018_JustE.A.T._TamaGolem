import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import it.unibs.fp.mylib.InputDati;
/**
 * 
 * This class represents a generic match between two players.
 *
 */
public class Match {
	
	private Element[] elements;
	
	private Equilibrium equilibrium;
	private Player[] players = new Player[2];
	private Stone[][] commonStock;
	private int difficultyLevel;
	
	private int eatableStones; 
	private int maxNumberOfGolems; 
	private int stonesPerElement;
	
	Match(String name1, String name2, int difficultyLevel) {
		
		this.difficultyLevel = difficultyLevel;
		
		eatableStones = (int)Math.ceil((double)(difficultyLevel + 1)/3 + 1);
		maxNumberOfGolems = (int)Math.ceil((double)((difficultyLevel-1)*(difficultyLevel-2))/(2*eatableStones));
		stonesPerElement = (int)Math.ceil((double)(2*maxNumberOfGolems*eatableStones)/difficultyLevel);
		
		commonStock = new Stone[difficultyLevel][stonesPerElement]; 
		elements = new Element[difficultyLevel];
		equilibrium = new Equilibrium(difficultyLevel);
		
		Player player1 = new Player(name1, maxNumberOfGolems, eatableStones);
		Player player2 = new Player(name2, maxNumberOfGolems, eatableStones);
		
		players[0] = player1;
		players[1] = player2;
		
		elementsInit();
		commonStockInit(); 
		coinToss();
		
	}
	
	public void elementsInit() {
		Set<Integer> excluded = new HashSet<Integer>();
		Random value = new Random();
		int elementValue;
		for(int i = 0; i < difficultyLevel; i++) {
			elementValue = value.nextInt(Element.numberOfElements());
			if(excluded.contains(elementValue))
				i--;
			else {
				elements[i] = Element.fromValueToElement(elementValue);
				excluded.add(elementValue);
			}
		}
	}
	
	public void commonStockInit() {
		for(int i = 0; i < difficultyLevel; i++) {
			for(int j = 0; j < stonesPerElement; j++) {
				commonStock[i][j] = new Stone(elements[i]);
			}
		}
	}
	
	public void commonStockPrint() {
		for(int i = 0; i < difficultyLevel; i++) {
			for(int j = 0; j < stonesPerElement; j++) {
				System.out.println(commonStock[i][j].name);
			}
			
		}
	}
	
	public void coinToss() {
		Random coin = new Random();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			System.out.println("\nThe coin will decide who will be the first to play:\n -if it comes out HEAD, " + players[0].getName() + " will start;\n -if it comes out TAIL, " + players[1].getName() + " will start;\n\nPress enter to toss the coin: ");
			in.readLine();
			System.out.println("Coin tossed...\n");
			for(int i= 0; i < 3; i++) {
				System.out.println("...");
				Thread.sleep(500);
			}
			if(coin.nextBoolean()) {
				System.out.println("\nHEAD! " + players[0].getName() + " will be the first to summon a Golem.");
			}
			else {
				System.out.println("\nTAIL! " + players[1].getName() + " will be the first to summon a Golem.");
				Player temp = players[0];
				players[0] = players[1];
				players[1] = temp;
			}
		}
		catch(IOException ex) {
			System.out.println("Errore");
		}
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	public Element[] getElements() {
		return elements;
	}

	public Player winner() {
		  
		return
	}

}
