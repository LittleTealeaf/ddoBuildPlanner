package classes;

import java.util.ArrayList;
import java.util.List;

import classes.Item.Enchantment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Build {
	
	public static List<Gear> gearSets;
	private static int gearIndex;
	
	//Saving Variables
	public List<Gear> GearSets;
	public int GearIndex;
	
	public Build() {
		GearSets = gearSets;
		GearIndex = gearIndex;
	}

	
	public static void initialize() {
		gearSets = new ArrayList<Gear>();
		gearIndex = 0;
	}
	
	public static List<Attribute> getAllAttributes() {
		ObservableList<Attribute> r = FXCollections.observableArrayList();
		
		//Gets all the items, and for each enchantment adds the attributes to the list
		if(gearSets.size() > 0) {
			for(Item i : getGear().getItems()) for(Enchantment e : i.enchantments) try {
				r.addAll(e.attributes);
			} catch(Exception a) {}
		}
		
		return r;
	}
	
	public static Gear getGear() {
		if(gearSets.size() == 0) return null;
		if(gearIndex + 1 > gearSets.size()) gearIndex = 0;
		return gearSets.get(gearIndex);
	}
	public static void setGearIndex(int index) {
		gearIndex = index;
	}
	public static void setGear(Gear set) {
		if(gearSets.contains(set)) gearIndex = gearSets.indexOf(set);
	}

	
	public static class Gear {
		public String name;
		
		public Item goggles, helmet, necklace, trinket, armor, cloak, bracers, belt, ring1, ring2, boots, gloves, mainhand, offhand, quiver;
		
		public Gear() {}
		public Gear(String Name) {name = Name;}
		public Gear(String Name, Item Goggles, Item Helmet, Item Necklace, Item Trinket, Item Armor, Item Cloak, Item Bracers, Item Belt, Item Ring1, Item Ring2, Item Boots, Item Gloves, Item Mainhand, Item Offhand, Item Quiver) {
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
			mainhand = Mainhand;
			offhand = Offhand;
			quiver = Quiver;
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
			ret.mainhand = mainhand;
			ret.offhand = offhand;
			ret.quiver = quiver;
			return ret;
		}
		
		public List<Item> getItems() {
			ObservableList<Item> r = FXCollections.observableArrayList();
			r.addAll(goggles, helmet, necklace, trinket, armor, cloak, bracers, belt, ring1, ring2, boots, gloves, mainhand, offhand, quiver);
			while(r.contains(null)) r.remove(null);
			return r;
		}
	}
}
