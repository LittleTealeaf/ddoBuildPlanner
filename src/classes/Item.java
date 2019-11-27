package classes;

import java.util.ArrayList;
import java.util.List;

public class Item {

	public String name;
	public String description;
	public String bindStatus;
	public String material;
	public String iconURL;
	public String imageURL;
	public int minLevel;
	public int absoluteMinLevel;
	public double hardness;
	public double durability;
	public double weight;
	public List<Enchantment> enchantments;
	
	public Item() {
		name = "";
		description = "";
		bindStatus = "";
		material = "";
		iconURL = "";
		imageURL = "";
		minLevel = 0;
		absoluteMinLevel = 0;
		hardness = 0;
		durability = 0;
		weight = 0;
		enchantments = new ArrayList<Enchantment>();
	}
	
	public static class Armor extends Item {
		
		public String armorType;
		public int armorBonus;
		public int maxDex;
		public int checkPenalty;
		public double spellFailure;
		
		public Armor() {
			super();
		}
	}
}