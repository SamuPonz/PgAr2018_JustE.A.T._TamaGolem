/**
 * 
 * Classe che rappresenta il prototipo di un oggetto di tipo golem
 * 
 * @author Just E.A.T.
 *
 */

public class Golem {

	public static final int MIN_INITIAL_HEALTH = 10; //valore arbitrario
	public static final int MAX_INITIAL_HEALTH = 25; //valore arbitrario
	public static int initialHealth = 15;
	
	private int health = initialHealth;
	private Stone[] eatenStones;
	private Equilibrium equilibrium;
	
	/**
	 * 
	 * Costruttore della classe golem
	 * 
	 * @param eatableStones Numero massimo di pietre che un golem puo' mangiare
	 * @param equilibrium Equilibrio instaurato all'inizio della partita
	 * 
	 */
	
	public Golem(int eatableStones, Equilibrium equilibrium) {
		eatenStones = new Stone[eatableStones];
		this.equilibrium = equilibrium;
	}
	
	/**
	 * 
	 * Metodo che riceve in ingresso la posizione nella tabella dell'equilibrio degli elementi associati alle pietre lanciate da due golem  
	 *
	 * @param attackingStoneIndex Posizione nella tabella dell'equilibrio dell'elemento associato alla pietra del golem attaccante
	 * @param defendingStoneIndex Posizione nella tabella dell'equilibrio dell'elemento associato alla pietra del golem difensore
	 * @param otherGolem Golem che subisce l'attacco
	 * 
	 * @return Ritorna il danno subito dal golem attaccato
	 * 
	 */
	
	public int shoot(int attackingStoneIndex, int defendingStoneIndex, Golem otherGolem) {
		
		int healthReduction = damageDealt(attackingStoneIndex, defendingStoneIndex);
		otherGolem.health -= healthReduction;
		
		return healthReduction;
	}

	/**
	 * 
	 * Metodo che ritrona la vita del golem
	 * 
	 * @return Ritorna la vita del golem
	 * 
	 */
	
	public int getHealth() {
		return health;
	}
	
	/**
	 * 
	 * Metodo che ritorna la lista di pietre mangiate dal golem
	 * 
	 * @return Ritorna la lista di pietre mangiate dal golem
	 * 
	 */
	
	public Stone[] getEatenStones() {
		return eatenStones;
	}
	
	/**
	 * 
	 * Metodo che controlla se il golem e' morto
	 * 
	 * @return Ritorna true se il golem e' morto, false se il golem e' ancora in vita
	 * 
	 */
	
	public boolean isDead() {
		if(health <= 0)
			return true;
		else return false;
	}
	
	/**
	 * 
	 * Metodo che nutre il golem con delle pietre di un determinato tipo di elemento
	 * 
	 * @param Tipo di pietre con cui si vuole nutrire il golem: elemento che definisce il tipo di pietra
	 */
	
	public void feed(Element[] kindOfStones) {
		for(int i = 0; i < eatenStones.length; i++)
			eatenStones[i] = new Stone(kindOfStones[i]);	
	}
	
	/**
	 * 
	 * Metodo che calcola il danno che subisce il golem perdente 
	 * 
	 * @param attackingIndex Indice relativo all'elemento della pietra del golem attaccante
	 * @param defendingIndex Indice relativo all'elemento della pietra del golem difensore
	 * 
	 * @return Ritrona il danno che subisc eil golem perdente
	 *  
	 */
	
	private int damageDealt(int attackingIndex, int defendingIndex) {
		int damage = 0;
        //equilibrium.printer(); //Stampe a video a scopo di debugging. Queste informazioni non vanno  mostrate all'utente
		int HypotheticalDamage = equilibrium.getElementsTable()[attackingIndex][defendingIndex];
		if(HypotheticalDamage > 0)
			return HypotheticalDamage;
		
		else return damage;
	}
	
	/**
	 * 
	 * Metodo che controlla se due golem hanno le stesse pietre
	 * 
	 * @param Secondo golem con cui viene confrontato il primo golem
	 * 
	 * @return Ritrona true se i golem hanno le stesse pietre nello stesso ordine, false se i golem hanno pietre diverse
	 * 
	 */
	
	public boolean hasTheSameStones(Golem other) {
		for(int i = 0; i < eatenStones.length; i++)
			if(eatenStones[i].getName().equals(other.getEatenStones()[i].getName()))
					return true;
		return false;
	}
}
