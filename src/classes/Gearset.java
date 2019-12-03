package classes;

import classes.Item.Armor;
import classes.Item.Weapon;

public class Gearset {

	private String name;
	
	private Item goggles, helmet, necklace, trinket, cloak, bracers, belt, ring1, ring2, gloves;
	private Armor armor;
	private Weapon mainHand, offHand;
	
	public Gearset() {
		name = "";
	}
	
	public Gearset(String name) {
		this.name = name;
	}
	
	private void onEdit() {
		
	}
	
	public Item[] getItems() {
		return new Item[] {goggles, helmet, necklace, trinket, cloak, bracers, belt, ring1, ring2, gloves, armor, mainHand, offHand};
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		onEdit();
	}

	public Item getGoggles() {
		return goggles;
	}

	public void setGoggles(Item goggles) {
		this.goggles = goggles;
		onEdit();
	}

	public Item getHelmet() {
		return helmet;
	}

	public void setHelmet(Item helmet) {
		this.helmet = helmet;
		onEdit();
	}

	public Item getNecklace() {
		return necklace;
	}

	public void setNecklace(Item necklace) {
		this.necklace = necklace;
		onEdit();
	}

	public Item getTrinket() {
		return trinket;
	}

	public void setTrinket(Item trinket) {
		this.trinket = trinket;
		onEdit();
	}

	public Item getCloak() {
		return cloak;
	}

	public void setCloak(Item cloak) {
		this.cloak = cloak;
		onEdit();
	}

	public Item getBracers() {
		return bracers;
	}

	public void setBracers(Item bracers) {
		this.bracers = bracers;
		onEdit();
	}

	public Item getBelt() {
		return belt;
	}

	public void setBelt(Item belt) {
		this.belt = belt;
	}

	public Item getRing1() {
		return ring1;
	}

	public void setRing1(Item ring1) {
		this.ring1 = ring1;
	}

	public Item getRing2() {
		return ring2;
	}

	public void setRing2(Item ring2) {
		this.ring2 = ring2;
	}

	public Item getGloves() {
		return gloves;
	}

	public void setGloves(Item gloves) {
		this.gloves = gloves;
	}

	public Armor getArmor() {
		return armor;
	}

	public void setArmor(Armor armor) {
		this.armor = armor;
	}

	public Weapon getMainHand() {
		return mainHand;
	}

	public void setMainHand(Weapon mainHand) {
		this.mainHand = mainHand;
		onEdit();
	}

	public Weapon getOffHand() {
		return offHand;
	}

	public void setOffHand(Weapon offHand) {
		this.offHand = offHand;
		onEdit();
	}
}
