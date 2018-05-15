import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import it.unibs.fp.mylib.MyMenu;

/**
 * 
 * <p>Classe che rappresenta il prototipo di un oggetto di tipo Partita</p> 
 * 
 * Rappresenta la singola partita tra due giocatori
 * L'esistenza di questa classe permette di istanziare piu' partite senza dover terminare il programma di volta in volta
 * 
 * @author Just E.A.T.
 * 
 */
public class Match {
	
	private static final String COMMON_STOCK_MESSAGE = "These are the stones available in the common stock:\n";
	private static final String SUMMON_MENU_TITLE = "\nWhich stone do you want to feed your golem with?\n";
	private static final String FRAME = "-------------------------------------------";
	private static final String COIN_TOSS_MESSAGE1 = "The coin will decide who will be the first to play:\n -if it comes out HEAD, %s will start;\n -if it comes out TAIL, %s will start.";
	private static final String COIN_TOSS_MESSAGE2 = "Press enter to toss the coin: ";
	private static final String COIN_TOSS_MESSAGE3 = "Coin tossed...\n";
	private static final String COIN_TOSS_MESSAGE4 = "\nHEAD! %s will be the first to summon a Golem.\n";
	private static final String COIN_TOSS_MESSAGE5 = "\nTAIL! %s will be the first to summon a Golem.\n";
	
	private static final String CHOOSE_STONE = "Choose a stone of the following type: ";
	private static final String CHOSEN_STONE = "\nYou have chosen the following stones: ";
	
	
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
	
	/**
	 * Costruttore di oggetti di tipo Partita
	 * 
	 * @param name1 Il nome del primo giocatore
	 * @param name2 Il nome del secondo giocatore
	 * @param difficultyLevel Il livello di difficolta', pari al numero di elementi evocabili nella specifica partita
	 * 
	 */
	Match(String name1, String name2, int difficultyLevel) {
		
		this.difficultyLevel = difficultyLevel;
		
		eatableStones = (int)Math.ceil((double)(difficultyLevel + 1)/3) + 1;
		maxNumberOfGolems = (int)Math.ceil((double)((difficultyLevel-1)*(difficultyLevel-2))/(2*eatableStones));
		stonesPerElement = (int)Math.ceil((double)(2*maxNumberOfGolems*eatableStones)/difficultyLevel);
		 
		elements = new Element[difficultyLevel];
		equilibrium = new Equilibrium(difficultyLevel, Golem.initialHealth);
		
		Player player1 = new Player(name1, maxNumberOfGolems);
		Player player2 = new Player(name2, maxNumberOfGolems);
		
		players[0] = player1;
		players[1] = player2;
		
		elementsInit();
		commonStockInit(); 
		coinToss();	
	}
	
	/**
	 * Metodo che inizializza l'Array di elementi evocabili nella specifica partita.
	 * La scelta dell'array è giustificata dal fatto che gli elementi, una volta inizializzati, non vengono piu' modificati
	 * 
	 */
	private void elementsInit() {
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
	
	/**
	 * Metodo che inizializza l'ArrayList di ArrayList di Pietre che rappresenta la scorta comune ai due giocatori
	 * 
	 */
	private void commonStockInit() {
		for(int i = 0; i < difficultyLevel; i++) {
			commonStock.add(new ArrayList<Stone>());
			for(int j = 0; j < stonesPerElement; j++) {
				commonStock.get(i).add(new Stone(elements[i]));
			}
		}
	}
	
	/**
	 * Metodo che stampa la scorta comune ai due giocatori, indicando il numero di pietre rimaste per ogni elemento
	 * 
	 */
	public void commonStockPrint() {
		System.out.println();
		System.out.println(COMMON_STOCK_MESSAGE);
		for(ArrayList<Stone> stones : commonStock) {
			if(!stones.isEmpty())
				System.out.println(printStoneSymbol(stones.size(), stones.get(0)));
		}
		System.out.println();
	}
	
	/**
	 * Metodo che resituisce una stringa che rappresenta graficamente, per ogni elemento, il numero di pietre rimaste nella scorta
	 * 
	 * @param i Il numero di pietre rimaste
	 * @param stone Il tipo di pietra
	 * @return stock La stringa formattata
	 */
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
	
  	/**
  	 * Metodo che "lancia una moneta" per decidere quale giocatore deve evocare per primo il suo golem
  	 * 
  	 */
	private void coinToss() {
		Random coin = new Random();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			System.out.println();
			System.out.println(String.format(COIN_TOSS_MESSAGE1, players[0].getName(), players[1].getName()));
			System.out.println(COIN_TOSS_MESSAGE2);
			in.read();
			System.out.println(COIN_TOSS_MESSAGE3);
			for(int i = 0; i < 3; i++) {
				System.out.println("...");
				Thread.sleep(500);
			}
			if(coin.nextBoolean()) {
				System.out.println(String.format(COIN_TOSS_MESSAGE4, players[0].getName()));
			}
			else {
				System.out.println(String.format(COIN_TOSS_MESSAGE5, players[1].getName()));
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
	
	/**
	 * Metodo che restituisce il giocatore corrispondente all'indice indicato
	 * 
	 * @param index L'indice
	 * @return Il Giocatore
	 * 
	 */
	public Player getPlayer(int index) {
		return players[index];
	}
	
	/**
	 * Metodo che restituisce l'Array di Elementi
	 * 
	 * @return elements L'Array di Elementi
	 */
	public Element[] getElements() {
		return elements;
	}
	
	/**
	 * Metodo che restituisce il massimo numero di pietre ingurgitabili da un golem
	 * 
	 * @return eatableStones Il numero di pietre
	 */
	public int getEatableStones() {
		return eatableStones;
	}
	
	/**
	 * Metodo per l'evocazione di un golem da parte di un giocatore
	 * 
	 * @param playerNumber Il giocatore che esegue l'evocazione
	 */
	public void summon(int playerNumber) {
		String[] summonOptions = new String[difficultyLevel];
		Element[] eatenStones = new Element[eatableStones];
		
		for(int i = 0; i < difficultyLevel; i++)
			summonOptions[i] = CHOOSE_STONE + elements[i];
	
		MyMenu summonMenu = new MyMenu(SUMMON_MENU_TITLE, summonOptions, SUMMON_MENU_TITLE.length());
		int choice;
		
		for(int i = 0; i < eatableStones; i++) {
			commonStockPrint();
			boolean error = false;
			do {
				choice = summonMenu.chooseWithNoExitOption();
				if(commonStock.get(choice - 1).isEmpty()) {
					System.out.println(NO_MORE_STONES_LEFT);
					commonStockPrint();
					error = true;
				}
				else error = false;
			} while(error);
			
			eatenStones[i] = elements[choice - 1];
			commonStock.get(choice - 1).remove(0);
		}
		System.out.println(CHOSEN_STONE);
		for(int i = 0; i < eatenStones.length; i++)
			System.out.print(eatenStones[i] + "  ");
		System.out.println();
		
		players[playerNumber].getGolems().add(new Golem(eatableStones, equilibrium));
		players[playerNumber].getGolems().get(players[playerNumber].getDefeatedGolems()).feed(eatenStones);
	}
	
	/**
	 * Metodo che presenta all'utente la richiesta di evocazione di un golem, eseguendola immediatamente dopo
	 * 
	 * @param playerNumber Il giocatore che esegue l'evocazione
	 * @param turnNumber Il turno corrente
	 */
	public void summonSequence(int playerNumber, int turnNumber) {
		System.out.println();
		System.out.println(FRAME);
		System.out.println(getPlayer(playerNumber).getName() + "'s turn " + turnNumber + ": feed your Golem with " + getEatableStones() + " stones");
		System.out.println(FRAME);
		summon(playerNumber);
	}
	
	/**
	 * Metodo che gestisce lo scontro, provvedendo ad alternare evocazione e battaglia fra golem.
	 * Lo scontro si interrompe quando uno dei due giocatori muore oppure le pietre rimaste nella scorta sono insufficienti all'evocazione di due golems
	 * 
	 */
	public void clash() {
		int turn = 1;
		
		summonRequest();
		
		summonSequence(0, turn);
		summonSequence(1, turn);
		
		while(!players[0].isDefeated() && !players[1].isDefeated() &&  turn <= (maxNumberOfGolems * 2 - 1) && !notEnoughStonesLeft()) {
			
			
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
				System.out.println(players[0].getName() + "'s golem deals a " + golem1Damage + " lifepoints damage (" + golem1.getEatenStones()[i].getName() + " --> " + golem2.getEatenStones()[i].getName() + ")"); 
				System.out.println(players[1].getName() + "'s golem deals a " + golem2Damage + " lifepoints damage (" + golem2.getEatenStones()[i].getName() + " --> " + golem1.getEatenStones()[i].getName() + ")");
				//System.out.println(players[0].getName() + "'s golem's remaining health: " + golem1.getHealth());  Stampe a video utili a scopo di debugging. Queste informazioni non vanno mostrate all'utente
				//System.out.println(players[1].getName() + "'s golem's remaining health: " + golem2.getHealth());
				i++;
				
				if(golem1Damage == golem2Damage)
					nullInteractionCounter++;
				else
					nullInteractionCounter = 0;
				if(nullInteractionCounter == eatableStones) {
					System.out.println(NULL_INTERACTION_ERROR);
					golem1.kill();
					golem2.kill();
					break;
				}
			}
			if(golem1.isDead()) {
				players[0].increaseDefeatedGolems();
				System.out.println(players[0].getName() + " has lost a golem!");
				if(!players[0].isDefeated()) {
					summonRequest();
					summonSequence(0, turn);
				}
			}
			if(golem2.isDead()) {
				players[1].increaseDefeatedGolems();
				System.out.println(players[1].getName() + " has lost a golem!");
				if(!players[1].isDefeated()) {
					summonRequest();
					summonSequence(1, turn);
				}
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
	
	/**
	 * Metodo che restituisce l'esito della partita: vittoria di uno o dell'altro giocatore oppure pareggio
	 * 
	 * @return message Il messaggio contenente il risultato dello scontro
	 */
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
	
	
	public Equilibrium getEquilibrium() {
		return equilibrium;
	}

	/**
	 * Metodo che controlla se sono rimaste meno pietre di quelle necessarie per far evocare un Golem a ciascun giocatore.
	 * In tal caso la partita si interrompe
	 * 
	 * @return true se non ci sono pietre a sufficienza
	 */
	public boolean notEnoughStonesLeft() {
		int counter = 0;
		for(int i = 0; i < difficultyLevel; i++)
			for(int j = 0; j < stonesPerElement; j++)
				counter++;
		if (players.length * counter < eatableStones)
			return true;
		else return false;
	}
	
	/**
	 * Metodo che trova l'indice dell'elemento passato come parametro
	 * 
	 * @param element L'elemento di cui trovare l'indice
	 * @return i L'indice corrispondente
	 */
	private int findElementIndex(Element element) {
		for(int i = 0; i < elements.length; i++)
			if(elements[i] == element)
				return i;
		return -1;
	}
	
	/**
	 * Metodo che richiede all'utente di premere "enter" per procedere all'evocazione di un nuovo golem
	 * 
	 */
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