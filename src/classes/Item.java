package classes;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import javafx.scene.image.Image;

@SuppressWarnings("serial")
public class Item implements Serializable {
	
	public String name;
	public int minLevel;
	public String proficiency;
	public double hardness;
	public double durability;
	public double baseValue;
	public double weight;
	public String material;
	
	public Image icon;
	public Image cosmetic;
	
	public String description;
	List<String> equipSlots;
	
	public Armor armor;
	public Weapon weapon;
	
	public List<String> dropLocations;
	public List<Enchantment> enchantments;
	
	public Item() {
		dropLocations = new ArrayList<String>();
		enchantments = new ArrayList<Enchantment>();
	}
	
	public void setSlots(String[] slots) {
		equipSlots = new ArrayList<String>();
		for(String s : slots) equipSlots.add(s);
	}
	
	
	
	
	//Sub Classes
	public static class Armor implements Serializable {
		public String armorType;
		public int armorBonus;
		public int shieldBonus;
		public int maxDexBonus;
		public int armorCheckPenalty;
		public int spellFailure;
		public int damageReduction;
		public int attackPenalty;
		
		public Armor() {}

		public Armor(String type, int bonus, int maxDex, int checkPenalty, int spellFail, int damageReduction) {
			armorType = type;
			armorBonus = bonus;
			maxDexBonus = maxDex;
			armorCheckPenalty = checkPenalty;
			spellFailure = spellFail;
		}
	}
	
	public static class Weapon implements Serializable {
		public Dice attackRoll;
		
		public List<String> damageTypes;
		
		public int critRange;
		public int critMultiplier;
		
		
		public Weapon() {}
		
		public Weapon(Dice attack, int CritRange, int CritMultiplier, List<String> DamageTypes) {
			attackRoll = attack;
			critRange = CritRange;
			critMultiplier = CritMultiplier;
			damageTypes = DamageTypes;
		}
		
		
		public double getBaseDamage() {
			double critChance = (double) (21 - critRange) / 20;
			double critDamage = critChance * critMultiplier * attackRoll.getAverage();
			
			return attackRoll.getAverage() + critDamage;
		}
	}
	
	public static class Enchantment implements Serializable {
		public List<Attribute> attributes;
		public String name;
		public String description;
		
		
		public Enchantment() {
			attributes = new ArrayList<Attribute>();
			name = "";
			description = "";
		}
		
		public String getDescription() {
			if(!description.contentEquals("")) return description;
			else if(attributes.size() == 1) return attributes.get(0).getDescription();
			else return "No Description Set";
		}
		public String getName() {
			if(!name.contentEquals("")) return name;
			else if(attributes.size() > 0) return attributes.get(0).getTitle();
			else return null;
		}
	}
}
