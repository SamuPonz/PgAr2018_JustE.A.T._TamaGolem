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
	
	private Balance balance;
	private Player[] players = new Player[2];
	private int difficultyLevel;
	
	private int eatableStones = (int)Math.ceil((difficultyLevel + 1)/3 + 1);
	private int maxNumberOfgolems = (int)Math.ceil(((difficultyLevel-1)*(difficultyLevel-2))/2*eatableStones);
	private int stonesPerElement = (int)Math.ceil((2*maxNumberOfgolems*eatableStones)/difficultyLevel);
	
	Match(String name1, String name2, int difficultyLevel) {
		
		this.difficultyLevel = difficultyLevel;
		
		balance = new Balance(difficultyLevel);
		
		Player player1 = new Player(name1, maxNumberOfgolems, stonesPerElement);
		Player player2 = new Player(name2, maxNumberOfgolems, stonesPerElement);
		
		players[0] = player1;
		players[1] = player2;
		
		coinToss();
		
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
				System.out.println("\nTAIL! " + players[1].getName() + "will be the first to summon a Golem.");
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


	public Player winner() {
		
		return
	}

}
