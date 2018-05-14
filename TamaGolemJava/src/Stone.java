/**
 * 
 *Classe che rappresenta il prototipo dell'oggetto Pietra
 *
 * @author Just E.A.T.
 *
 */

public class Stone {
	
	private Element element;
	private String name;
	
	/**
	 *
	 * Costruttore della classe
	 * 
	 * @param element Elemento che rappresenta il tipo di pietra
	 * 
	 */
	
	Stone(Element element) {
		
		this.element = element;
		name = element.toString() + "'s stone";
		
	}

	/**
	 * 
	 * Metodo che restituisce il tipo di pietra
	 * 
	 * @return Ritorna il tipo di pietra 
	 * 
	 */
	
	public Element getElement() {
		return element;
	}
	
	
	/**
	 * 
	 * Metodo che restituisce il nome della pietra
	 * 
	 * @return Ritorna il nome della pietra
	 * 
	 */
	
	public String getName() {
		return name;
	}

	
	
}
