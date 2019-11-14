package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Build implements Serializable {
	//TODO implement Serializable ID to classes
	
	public static Build build;
	
	public Build() {
		gearSets = new ArrayList<Gear>();
	}
	
	public List<Gear> gearSets;
	
	
	public void removeGearSet(String name) {
		for(Gear g : gearSets) if(g.name.contentEquals(name)) {
			gearSets.remove(g);
			return;
		}
	}
	
	public static class Gear implements Serializable {
		public String name;
		
		public Item goggles, helmet, necklace, trinket, armor, cloak, bracers, belt, ring1, ring2, boots, gloves;
		
		public Gear() {}
		public Gear(String Name) {name = Name;}
		public Gear(String Name, Item Goggles, Item Helmet, Item Necklace, Item Trinket, Item Armor, Item Cloak, Item Bracers, Item Belt, Item Ring1, Item Ring2, Item Boots, Item Gloves) {
			name = Name;
			goggles = Goggles;
			helmet = Helmet;
			necklace = Necklace;
			trinket = Trinket;
			armor = Armor;
			cloak = Cloak;
			bracers = Bracers;
			belt = Belt;
			ring1 = Ring1;
			ring2 = Ring2;
			boots = Boots;
			gloves = Gloves;
		}
	}
}
