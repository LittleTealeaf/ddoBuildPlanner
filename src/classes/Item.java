package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Item {

	private String name;
	private String type;
	private String description;
	private String proficiency;
	private String bindStatus;
	private String material;
	private String iconURL;
	private String imageURL;
	private int minLevel;
	private int absoluteMinLevel;
	private double hardness;
	private double durability;
	private double weight;
	private List<Enchantment> enchantments;
	private List<String> equipSlots;

	// Armor Values
	private Armor armor;

	private class Armor {
		public Armor() {
			armorType = "";
		}

		public boolean isEmpty() {
			return armorType.contentEquals("") && armorBonus == 0 && maxDex == 0 && checkPenalty == 0 && spellFailure == 0;
		}

		public String armorType;
		public int armorBonus;
		public int maxDex;
		public int checkPenalty;
		public double spellFailure;
	}

	// Weapon Values
	private Weapon weapon;

	private class Weapon {
		public Weapon() {
			damageTypes = new ArrayList<String>();
			damage = new Dice();
			lowCritRoll = 20;
			critMultiplier = 2;
		}

		public boolean isEmpty() {
			return damage.isDefault() && damageTypes.size() == 0 && lowCritRoll == 0 && critMultiplier == 0.0;
		}

		private Dice damage;
		private List<String> damageTypes;
		private int lowCritRoll;
		private double critMultiplier;
	}

	public Item() {
		enchantments = new ArrayList<Enchantment>();
		equipSlots = new ArrayList<String>();
	}

	public Item(String name) {
		this.name = name;
		enchantments = new ArrayList<Enchantment>();
		equipSlots = new ArrayList<String>();
	}

	public boolean saveItem() {
		cleanItem();
		if(!(name == null || name.contentEquals(""))) {
			Items.saveItem(this);
			return true;
		} else return false;
	}

	public void cleanItem() {
		if(armor != null && armor.isEmpty()) armor = null;
		if(weapon != null && weapon.isEmpty()) weapon = null;
		//TODO clear empty fields in damage types
	}

	// GETTERS AND SETTERS
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProficiency() {
		return proficiency;
	}

	public void setProficiency(String proficiency) {
		this.proficiency = proficiency;
	}

	public String getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(String bindStatus) {
		this.bindStatus = bindStatus;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getIconURL() {
		return iconURL;
	}

	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

	public int getAbsoluteMinLevel() {
		return absoluteMinLevel;
	}

	public void setAbsoluteMinLevel(int absoluteMinLevel) {
		this.absoluteMinLevel = absoluteMinLevel;
	}

	public double getHardness() {
		return hardness;
	}

	public void setHardness(double hardness) {
		this.hardness = hardness;
	}

	public double getDurability() {
		return durability;
	}

	public void setDurability(double durability) {
		this.durability = durability;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public List<Enchantment> getEnchantments() {
		return enchantments;
	}

	public void setEnchantments(List<Enchantment> enchantments) {
		this.enchantments = enchantments;
	}

	public List<String> getEquipSlots() {
		return equipSlots;
	}

	public void setEquipSlots(List<String> equipSlots) {
		this.equipSlots = equipSlots;
	}

	public void removeEquipSlot(String slot) {
		while(equipSlots.contains(slot)) equipSlots.remove(slot);
	}

	public void addEquipSlot(String slot) {
		if(!equipSlots.contains(slot)) equipSlots.add(slot);
	}

	public void setEquipSlot(String slot, boolean equipped) {
		if(equipped) addEquipSlot(slot);
		else removeEquipSlot(slot);
	}

	public String getArmorType() {
		if(armor == null) return "";
		return armor.armorType;
	}

	public void setArmorType(String armorType) {
		if(armor == null) armor = new Armor();
		this.armor.armorType = armorType;
	}

	public int getArmorBonus() {
		if(armor == null) return 0;
		return armor.armorBonus;
	}

	public void setArmorBonus(int armorBonus) {
		if(armor == null) armor = new Armor();
		this.armor.armorBonus = armorBonus;
	}

	public int getMaxDex() {
		if(armor == null) return 0;
		return armor.maxDex;
	}

	public void setMaxDex(int maxDex) {
		if(armor == null) armor = new Armor();
		this.armor.maxDex = maxDex;
	}

	public int getCheckPenalty() {
		if(armor == null) return 0;
		return armor.checkPenalty;
	}

	public void setCheckPenalty(int checkPenalty) {
		if(armor == null) armor = new Armor();
		this.armor.checkPenalty = checkPenalty;
	}

	public double getSpellFailure() {
		if(armor == null) return 0;
		return armor.spellFailure;
	}

	public void setSpellFailure(double spellFailure) {
		if(armor == null) armor = new Armor();
		this.armor.spellFailure = spellFailure;
	}

	public Dice getDamage() {
		if(weapon == null) return new Dice();
		return weapon.damage;
	}

	public void setDamage(Dice damage) {
		if(weapon == null) weapon = new Weapon();
		this.weapon.damage = damage;
	}

	public List<String> getDamageTypes() {
		if(weapon == null) return null;
		return weapon.damageTypes;
	}
	
	public String getDamageTypeText() {
		if(weapon == null) return "";
		String r = "";
		for(String l : getDamageTypes()) {
			if(!r.contentEquals("")) r+="\n";
			r+=l;
		}
		return r;
	}

	public void setDamageTypes(List<String> damageTypes) {
		if(weapon == null) weapon = new Weapon();
		this.weapon.damageTypes = damageTypes;
	}
	
	public void setDamageTypesText(String text) {
		setDamageTypes(Arrays.asList(text.split("\n")));
	}

	public int getLowCritRoll() {
		if(weapon == null) return 20;
		return weapon.lowCritRoll;
	}

	public void setLowCritRoll(int lowCritRoll) {
		if(weapon == null) weapon = new Weapon();
		this.weapon.lowCritRoll = lowCritRoll;
	}

	public double getCritMultiplier() {
		if(weapon == null) return 2;
		return weapon.critMultiplier;
	}

	public void setCritMultiplier(double critMultiplier) {
		if(weapon == null) weapon = new Weapon();
		this.weapon.critMultiplier = critMultiplier;
	}

}