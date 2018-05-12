/**
 * 
 * This class represents a prototype of an element's stone. 
 * 
 * Different stones are stored by Golems that use them to release their true power.
 *
 */

public class Stone {
	
	private Element element;
	
	Stone(Element element) {
		
		this.element = element;
		
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	
	
}
