/**
 * Classe che rappresenta l'entita' "Elemento": e' costituita da un enum contentente tutti e dieci gli elementi esistenti nel mondo
 * 
 * @author Just E.A.T.
 * 
 */
public enum Element {
	
	FIRE(0),
	WATER(1),
	GRASS(2),
	ELECTRIC(3),
	FLYING(4),
	GROUND(5),
	DRAGON(6),
	STEEL(7),
    GHOST(8),
	POISON(9);
	
	public final int value;
	
	/**
	 * Costruttore di un oggetto di tipo "Elemento"
	 * 
	 * @param value L'indice che rappresenta l'elemento
	 * 
	 */
	Element(final int value) {
		this.value = value;
	}
	
	/**
	 * Metodo che associa a un valore numerico l'elemento corrispondente
	 *  
	 * @param value L'indice a cui associare un elemento
	 * @return L'elemento corrispondente
	 * 
	 */
	public static Element fromValueToElement(int value) {
		for(Element element : Element.values()) {
			if(element.value == value) {
				return element;
			}
		}
		return null;
	}
	
	/**
	 * Metodo che restituisce il numero di elementi esistenti nel mondo
	 * 
	 * @return i Il numero di elementi
	 * 
	 */
	public static int numberOfElements() {
		int i;
		for(i = 0; i < Element.values().length; i++);
		return i;
	}
	
	/**
	 * Metodo per stampare tutti gli elementi esistenti e il loro relativo valore numerico 
	 * 
	 */
	public static void printElements() {
		System.out.println();
		int i = 0;
		for(Element element : Element.values()) {
			System.out.println(element + ": " + i);
			i++;
		}
	}
	
}
