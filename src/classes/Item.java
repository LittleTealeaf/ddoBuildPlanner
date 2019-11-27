package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Item {

	public String name;
	public String type;
	public String description;
	public String proficiency;
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
	public List<String> equipSlots;

	public Item() {
		name = "";
		description = "";
		bindStatus = "";
		material = "";
		iconURL = "";
		imageURL = "";
		enchantments = new ArrayList<Enchantment>();
	}

	public static class Armor extends Item {

		public int armorBonus;
		public int maxDex;
		public int checkPenalty;
		public double spellFailure;

		public Armor() {
			super();

			// TODO Correct shield slot with proper slot identifiers
			equipSlots = Arrays.asList(new String[] { "Armor" });

			maxDex = 10;
		}
	}

	public static class Weapon extends Item {
		public Dice damage;
		public List<String> damageTypes;
		public int lowCritRoll;
		public double critMultiplier;

		public Weapon() {
			super();

			// TODO Correct shield slot with proper slot identifiers
			equipSlots = Arrays.asList(new String[] { "Main Hand", "Off Hand" });

			damage = new Dice();
			damageTypes = new ArrayList<String>();
			lowCritRoll = 20;
			critMultiplier = 1;
		}
	}

	// Includes Orbs
	public static class Shield extends Item {

		public int shieldBonus;
		public int maxDex;
		public int checkPenalty;
		public double spellFailure;

		public Shield() {
			super();

			// TODO Correct shield slot with proper slot identifiers
			equipSlots = Arrays.asList(new String[] { "Off Hand" });

			maxDex = 10;
		}
	}
}