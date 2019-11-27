package classes;

public class Gearset {

	public String name;
	public Item goggles, helmet, necklace, trinket, armor, cloak, bracers, belt, ring1, ring2, boots, gloves;

	public Gearset() {}

	public Gearset(String name) {
		name = "";
	}

	public Item[] getItems() {
		return new Item[] { goggles, helmet, necklace, trinket, armor, cloak, bracers, belt, ring1, ring2, boots, gloves };
	}
}
