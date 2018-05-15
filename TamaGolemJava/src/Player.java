import java.util.ArrayList;

/**
 * 
 * Classe che rappresenta il prototipo di un oggetto di tipo Giocatore
 * 
 * @author Just E.A.T.
 *
 */

public class Player {
	
	private String name;
	private ArrayList<Golem> golems;
	private int maxNumberOfGolems;
	private int defeatedGolems = 0;
	
	/**
	 * Costruttore della classe giocatore
	 * 
	 * @param name Nome del giocatore
	 * @param maxNumberOfGolems Massimo numero di golem di un giocatore
	 * 
	 */
	
	public Player(String name, int maxNumberOfGolems) {
		this.name = name;
		this.maxNumberOfGolems = maxNumberOfGolems;
		golems = new ArrayList<Golem>();
	}
	
	/**
	 * 
	 * Metodo che controlla se un giocatore e' stato sconfitto 
	 * 
	 * @return Ritorna true se il giocatore e' stato sconfitto, false se il giocatore e' ancora in vita
	 * 
	 */

	
	public boolean isDefeated() {
		if(defeatedGolems == maxNumberOfGolems)
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * Metodo che restituisce il nome del giocatore
	 * 
	 * @return Ritorna il nome del giocatore
	 */
	
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * Metodo che restituisce la lista di golem del giocatore
	 * 
	 * @return Ritorna la lista di golem di un giocatore
	 */
	
	public ArrayList<Golem> getGolems() {
		return golems;
	}
	
	/**
	 * 
	 * Metodo che incrementa il numero di golem sconfitti di un giocatore
	 * 
	 */
	
	public void increaseDefeatedGolems() {
		defeatedGolems ++;
	}
	
	/**
	 * 
	 * Metodo che restituisce il numero di golem sconfitti di un giocatore
	 * 
	 * @return Ritorna il numero di golem scontiffi di un giocatore
	 * 
	 */
	
	public int getDefeatedGolems() {
		return defeatedGolems;
	}
	
}
