package classes;

public class Item {
	
	public String name;
	public int minLevel;
	
	public Armor armor;
	public Weapon weapon;
	
	
	public double baseValue;
	public double weight;
	
	
	
	public Item() {}
	
	
	//Sub Classes
	public static class Armor {
		public String armorType;
		public int armorBonus;
		public int maxDexBonus;
		public int armorCheckPenalty;
		public int spellFailure;
		
		public Armor() {}
		public Armor(String type, int bonus, int maxDex, int checkPenalty, int spellFail) {
			armorType = type;
			armorBonus = bonus;
			maxDexBonus = maxDex;
			armorCheckPenalty = checkPenalty;
			spellFailure = spellFail;
		}
	}
	public static class Weapon {
		public Dice attackRoll;
		
		
		public Weapon() {}
		
		
		public double getBaseDamage() {
			return attackRoll.getAverage();
		}
	}
}
