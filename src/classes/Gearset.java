package classes;

public class Gearset {

	public String name;
	public Item goggles, helmet, necklace, trinket, armor, cloak, bracers, belt, ring1, ring2, boots, gloves;
	
	public Gearset() {
		for(Item a : getItems()) a = null;
	}
	
	public Gearset(String name) {
		for(Item a : getItems()) a = null;
		name = "";
	}
	
	public Item[] getItems() {
		return new Item[] {goggles, helmet, necklace, trinket, armor, cloak, bracers, belt, ring1, ring2, boots, gloves};
	}
}
