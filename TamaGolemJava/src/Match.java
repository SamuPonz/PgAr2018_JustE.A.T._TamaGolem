import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
/**
 * 
 * This class represents a generic match between two players.
 *
 */
public class Match {
	
	private static final String COMMON_STOCK_MESSAGE = "These are the stones available in the common stock:\n";
	private static final String SUMMON_MENU_TITLE = "\nWhich stone do you want to feed your golem with?\n";
	private static final String FRAME = "-------------------------------------------";
	
	private static final String NO_MORE_STONES_LEFT = "There are no more stones of this species! Please choose a different one ";
	private static final String NOT_ENOUGH_STONES_TO_SUMMON = "There aren't enough stones left to summon other two golems!";
	private static final String NULL_INTERACTION_ERROR = "The golems have been throwing the same stones in the same order for hours...let's stop them :(";
	
	private static final String END_TURN_MESSAGE = "END TURN %d";
	
	private Element[] elements;
	
	private Player[] players = new Player[2];
	private ArrayList<ArrayList<Stone>> commonStock = new ArrayList<ArrayList<Stone>>();
	private int difficultyLevel;
	
	private Equilibrium equilibrium;
	private int eatableStones; 
	private int maxNumberOfGolems; 
	private int stonesPerElement;
	
	Match(String name1, String name2, int difficultyLevel) {
		
		this.difficultyLevel = difficultyLevel;
		
		eatableStones = (int)Math.ceil((double)(difficultyLevel + 1)/3) + 1;
		maxNumberOfGolems = (int)Math.ceil((double)((difficultyLevel-1)*(difficultyLevel-2))/(2*eatableStones));
		stonesPerElement = (int)Math.ceil((double)(2*maxNumberOfGolems*eatableStones)/difficultyLevel);
		 
		elements = new Element[difficultyLevel];
		equilibrium = new Equilibrium(difficultyLevel, Golem.INITIAL_HEALTH);
		
		Player player1 = new Player(name1, maxNumberOfGolems);
		Player player2 = new Player(name2, maxNumberOfGolems);
		
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
			commonStock.add(new ArrayList<Stone>());
			for(int j = 0; j < stonesPerElement; j++) {
				commonStock.get(i).add(new Stone(elements[i]));
			}
		}
	}
	
	public void commonStockPrint() {
		System.out.println();
		System.out.println(COMMON_STOCK_MESSAGE);
		for(ArrayList<Stone> stones : commonStock) {
			if(!stones.isEmpty())
				System.out.println(printStoneSymbol(stones.size(), stones.get(0)));
		}
		System.out.println();
	}
	
  	private String printStoneSymbol(int i, Stone stone) {
    	StringBuffer stock = new StringBuffer();
    	stock.append(" @ " + stone.getElement().toString().toLowerCase() + "\t");
    	
	    if(stone.getElement().toString().length() <= 4)
	     	stock.append("\t");
	     	
	    stock.append("[ ");
	    
	    for(int j = 0; j < i; j++)
	     	stock.append("0 ");
	     	
	    stock.append("]\n");
	    
	    return stock.toString();
	}
	
	public void coinToss() {
		Random coin = new Random();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			System.out.println("\nThe coin will decide who will be the first to play:\n -if it comes out HEAD, " + players[0].getName() + " will start;\n -if it comes out TAIL, " + players[1].getName() + " will start.");
			System.out.println("Press enter to toss the coin: ");
			in.read();
			System.out.println("Coin tossed...\n");
			for(int i = 0; i < 3; i++) {
				System.out.println("...");
				Thread.sleep(500);
			}
			if(coin.nextBoolean()) {
				System.out.println("\nHEAD! " + players[0].getName() + " will be the first to summon a Golem.\n");
			}
			else {
				System.out.println("\nTAIL! " + players[1].getName() + " will be the first to summon a Golem.\n");
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
	
	public Player getPlayer(int index) {
		return players[index];
	}
	
	public Element[] getElements() {
		return elements;
	}
	
	public int getEatableStones() {
		return eatableStones;
	}
	
	public void summon(int playerNumber) {
		String[] summonOptions = new String[difficultyLevel];
		Element[] eatenStones = new Element[eatableStones];
		
		for(int i = 0; i < difficultyLevel; i++)
			summonOptions[i] = "Choose a stone of the following type: " + elements[i];
	
		MyMenu summonMenu = new MyMenu(SUMMON_MENU_TITLE, summonOptions, SUMMON_MENU_TITLE.length());
		int choice;
		
		for(int i = 0; i < eatableStones; i++) {
			
			boolean error = false;
			do {
				choice = summonMenu.chooseWithNoExitOption();
				if(commonStock.get(choice - 1).isEmpty()) {
					System.out.println(NO_MORE_STONES_LEFT);
					error = true;
				}
				else error = false;
			} while(error);
			
			eatenStones[i] = elements[choice - 1];
			commonStock.get(choice - 1).remove(0);
		}
		System.out.println("\nYou have chosen the following stones: ");
		for(int i = 0; i < eatenStones.length; i++)
			System.out.print(eatenStones[i] + "  ");
		System.out.println();
		
		players[playerNumber].getGolems().add(new Golem(eatableStones, equilibrium));
		players[playerNumber].getGolems().get(players[playerNumber].getDefeatedGolems()).feed(eatenStones);
	}
	
	public void summonSequence(int playerNumber, int turnNumber) {
		System.out.println();
		System.out.println(FRAME);
		System.out.println(getPlayer(playerNumber).getName() + "'s turn " + turnNumber + ": feed your Golem with " + getEatableStones() + " stones");
		System.out.println(FRAME);
		summon(playerNumber);
	}
	
	public void clash() {
		int turn = 1;
		int newEvocation = 2;
		
		summonRequest();
		
		commonStockPrint();
		summonSequence(0, turn);
		commonStockPrint();
		summonSequence(1, turn);
		
		while(!players[0].isDefeated() && !players[1].isDefeated() &&  turn <= (maxNumberOfGolems * 2 - 1) && !notEnoughStonesLeft()) {
			if(newEvocation == 0) {
				commonStockPrint();
				summonSequence(0, turn);
			}
			if(newEvocation == 1) {
				commonStockPrint();
				summonSequence(1, turn);
			}
			
			Golem golem1 = players[0].getGolems().get(players[0].getDefeatedGolems());
			Golem golem2 = players[1].getGolems().get(players[1].getDefeatedGolems());
			
			int i = 0, nullInteractionCounter = 0;
			while(!golem1.isDead() && !golem2.isDead()) { 
				if(i == eatableStones)
					i = 0;
				
				int attackingElementIndex = findElementIndex(golem1.getEatenStones()[i].getElement());
				int defendingElementIndex = findElementIndex(golem2.getEatenStones()[i].getElement());
				int golem1Damage = golem1.shoot(attackingElementIndex, defendingElementIndex, golem2);
				int golem2Damage = golem2.shoot(defendingElementIndex, attackingElementIndex, golem1);
				
				System.out.println();
				System.out.println(players[0].getName() + "'s golem deals a " + golem1Damage + " lifepoints damage (" + golem1.getEatenStones()[i].getName() + " --> " + golem2.getEatenStones()[i].getName() + ")"); //Il metodo shoot restituisce un intero,
				System.out.println(players[1].getName() + "'s golem deals a " + golem2Damage + " lifepoints damage (" + golem2.getEatenStones()[i].getName() + " --> " + golem1.getEatenStones()[i].getName() + ")"); //ma esegue già la sottrazione dei lifepoints
				System.out.println(players[0].getName() + "'s golem's remaining health: " + golem1.getHealth()); //Non va mostrato all'utente, serve solo a noi per controllo
				System.out.println(players[1].getName() + "'s golem's remaining health: " + golem2.getHealth());
				i++;
				
				if(golem1Damage == golem2Damage)
					nullInteractionCounter++;
				if(nullInteractionCounter == eatableStones) {
					System.out.println(NULL_INTERACTION_ERROR);
					players[0].increaseDefeatedGolems();
					players[1].increaseDefeatedGolems();
					summonRequest();
					commonStockPrint();
					summonSequence(0, turn);
					commonStockPrint();
					summonSequence(1, turn);
					System.out.println(players[0].getName() + " has lost a golem!");
					System.out.println(players[1].getName() + " has lost a golem!");
					break;
				}
			}
			if(golem1.isDead()) {
				players[0].increaseDefeatedGolems();
				System.out.println(players[0].getName() + " has lost a golem!");
				newEvocation = 0;
				summonRequest();
			}
			if(golem2.isDead()) {
				players[1].increaseDefeatedGolems();
				System.out.println(players[1].getName() + " has lost a golem!");
				newEvocation = 1;
				summonRequest();
			}
			
			System.out.println(players[0].getName() + " has " + (maxNumberOfGolems - players[0].getDefeatedGolems()) + " golem left");
			System.out.println(players[1].getName() + " has " + (maxNumberOfGolems - players[1].getDefeatedGolems()) + " golem left");
			
			System.out.println();
			System.out.println(String.format(END_TURN_MESSAGE, turn));
			System.out.println();
			
			if(notEnoughStonesLeft())
				System.out.println(NOT_ENOUGH_STONES_TO_SUMMON);
			
			turn++;
		}
				
	}
	
	public String proclaimWinner() {
		if(players[0].isDefeated() && !players[1].isDefeated())
			return players[1].getName();		
		
		else if(players[1].isDefeated() && !players[0].isDefeated())
			return players[0].getName();		
		
		else {
			StringBuffer message = new StringBuffer("nobody! ");
			if(players[1].isDefeated() && players[0].isDefeated())
				message.append("Both players have died in the same turn!");
			else  message.append("There aren't enough stones left for another turn!");
			
			return message.toString();
		}
	}
	
	public boolean notEnoughStonesLeft() { //Se sono rimaste meno pietre di quelle necessarie per far evocare un Golem a ciascun giocatore, la partita si interrompe
		int counter = 0;
		for(int i = 0; i < difficultyLevel; i++)
			for(int j = 0; j < stonesPerElement; j++)
				counter++;
		if (players.length * counter < eatableStones)
			return true;
		else return false;
	}
	
	private int findElementIndex(Element element) {
		for(int i = 0; i < elements.length; i++)
			if(elements[i] == element)
				return i;
		return -1;
	}
	
	private void summonRequest() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Press enter to summon a new golem...");
			in.readLine();
		}
		catch (IOException ex) {
			System.out.println("Errore");
		}
	}
}