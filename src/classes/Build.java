package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Build implements Serializable {
	//TODO implement Serializable ID to classes
	
	public List<Gear> GearSets;
	
	public static List<Gear> gearSets;
	
	
	
	public Build() {
		gearSets = GearSets;
	}
	
	public static void initialize() {
		gearSets = new ArrayList<Gear>();
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
		public String toString() {
			return name;
		}
		public Gear clone() {
			Gear ret = new Gear();
			ret.name = name;
			ret.goggles = goggles;
			ret.helmet = helmet;
			ret.necklace = necklace;
			ret.trinket = trinket;
			ret.armor = armor;
			ret.cloak = cloak;
			ret.bracers = bracers;
			ret.belt = belt;
			ret.ring1 = ring1;
			ret.ring2 = ring2;
			ret.boots = boots;
			ret.gloves = gloves;
			return ret;
		}
	}
}
