
public enum Element {
	
	FIRE(0),
	WATER(1),
	GRASS(2),
	ELECTRIC(3),
	FLYING(4),
	GROUND(5),
	DRAGON(6),
	FIGHTING(7),
    GHOST(8),
	POISON(9);
	
	public final int value;
	
	Element(final int value) {
		this.value = value;
	}
	
	public static Element fromValueToElement(int value) {
		for(Element element : Element.values()) {
			if(element.value == value) {
				return element;
			}
		}
		return null;
	}
	
	public static int numberOfElements() {
		int i = 0;
		for(Element element : Element.values()) {
			i++;
		}
		return i;
	}
	
	//problema, devo sceglierli in ogni partita in modo casuale, non posso usare il metodo sottostante
	
	public static void printElements() {
		System.out.println();
		int i = 0;
		for(Element element : Element.values()) {
			System.out.println(element + ": " + i);
			i++;
		}
	}
	
	
}
