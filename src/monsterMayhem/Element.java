package monsterMayhem;

public class Element {
	private String elementName;
	
	private Element(String elementName) {
		this.elementName = elementName;
	}
	
	// element types
	private static Element Ice = new Element("Ice");
	private static Element Fire = new Element("Fire");
	private static Element Water = new Element("Water");
	
	public static Element getIce() {
		return Ice;
	}
	
	public static Element getFire() {
		return Fire;
	}
	
	public static Element getWater() {
		return Water;
	}

	public String getElementName() {
		return elementName;
	}
	
	/**
	 * @param element1: attackers element
	 * @param element2: attackees element
	 * Note: Element can be either player weapon attribute or monster type
	 * @return true if the attacker will do double damage
	 * Ice > Water
	 * Fire > Ice
	 * Water > Fire
	 */
	public static boolean evaluateBuff(Element element1, Element element2) {
		if (element1.getElementName() == "Ice" && element2.getElementName() == "Water") {
			return true;
		}
		else if (element1.getElementName() == "Fire" && element2.getElementName() == "Ice") {
			return true;
		}
		else if (element1.getElementName() == "Water" && element2.getElementName() == "Fire") {
			return true;
		}
		else
			return false;
	}
}
