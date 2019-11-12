package classes;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Item implements Serializable {
	
	public String name;
	public int minLevel;
	
	public double hardness;
	public double durability;
	public double baseValue;
	public double weight;
	public String material;
	
	List<String> equipSlots;
	
	public Armor armor;
	public Weapon weapon;
	
	public List<String> dropLocations;
	public List<Attribute> attributes;
	
	public Item() {
		dropLocations = new ArrayList<String>();
		attributes = new ArrayList<Attribute>();
	}
	
	public void setSlots(String[] slots) {
		equipSlots = new ArrayList<String>();
		for(String s : slots) equipSlots.add(s);
	}
	
	
	//Sub Classes
	public static class Armor implements Serializable {
		public String armorType;
		public int armorBonus;
		public int maxDexBonus;
		public int armorCheckPenalty;
		public int spellFailure;
		public int damageReduction;
		public String proficiency;
		
		public Armor() {}

		public Armor(String type, int bonus, int maxDex, int checkPenalty, int spellFail, int damageReduction, String Proficiency) {
			armorType = type;
			armorBonus = bonus;
			maxDexBonus = maxDex;
			armorCheckPenalty = checkPenalty;
			spellFailure = spellFail;
			proficiency = Proficiency;
		}
	}
	
	public static class Weapon implements Serializable {
		public Dice attackRoll;
		
		public List<String> damageTypes;
		
		public int critRange;
		public int critMultiplier;
		
		public String proficiency;
		
		
		public Weapon() {}
		
		public Weapon(Dice attack, int CritRange, int CritMultiplier, List<String> DamageTypes, String Proficiency) {
			attackRoll = attack;
			critRange = CritRange;
			critMultiplier = CritMultiplier;
			damageTypes = DamageTypes;
			proficiency = Proficiency;
		}
		
		
		public double getBaseDamage() {
			double critChance = (double) (21 - critRange) / 20;
			double critDamage = critChance * critMultiplier * attackRoll.getAverage();
			
			return attackRoll.getAverage() + critDamage;
		}
	}
}
