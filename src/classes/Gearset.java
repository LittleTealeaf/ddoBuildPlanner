package classes;

public class Gearset {

	private String name;

	private Iref goggles, helmet, necklace, trinket, cloak, bracers, belt, ring1, ring2, gloves, armor, mainHand, offHand;

	public Gearset() {
		this("");
	}

	public Gearset(String name) {
		this.name = name;
	}

	private void onEdit() {

	}

	public Item[] getItems() {
		return new Item[] {goggles.getItem(), helmet.getItem(), necklace.getItem(), trinket.getItem(), cloak.getItem(), bracers.getItem(), belt.getItem(), ring1.getItem(), ring2.getItem(), gloves.getItem(), armor.getItem(), mainHand.getItem(), offHand.getItem()};
	}

	public String getName() {
		return name;
	}

	public Item getGoggles() {
		return goggles.getItem();
	}

	public Item getHelmet() {
		return helmet.getItem();
	}

	public Item getNecklace() {
		return necklace.getItem();
	}

	public Item getTrinket() {
		return trinket.getItem();
	}

	public Item getCloak() {
		return cloak.getItem();
	}

	public Item getBracers() {
		return bracers.getItem();
	}

	public Item getBelt() {
		return belt.getItem();
	}

	public Item getRing1() {
		return ring1.getItem();
	}

	public Item getRing2() {
		return ring2.getItem();
	}

	public Item getGloves() {
		return gloves.getItem();
	}

	public Item getArmor() {
		return armor.getItem();
	}

	public Item getMainHand() {
		return mainHand.getItem();
	}

	public Item getOffHand() {
		return offHand.getItem();
	}

	public void setName(String name) {
		this.name = name;
		onEdit();
	}

	public void setGoggles(String name) {
		this.goggles = new Iref(name);
		onEdit();
	}

	public void setHelmet(String name) {
		this.helmet = new Iref(name);
		onEdit();
	}

	public void setNecklace(String name) {
		this.necklace = new Iref(name);
		onEdit();
	}

	public void setTrinket(String name) {
		this.trinket = new Iref(name);
		onEdit();
	}

	public void setCloak(String name) {
		this.cloak = new Iref(name);
		onEdit();
	}

	public void setBracers(String name) {
		this.bracers = new Iref(name);
		onEdit();
	}

	public void setBelt(String name) {
		this.belt = new Iref(name);
		onEdit();
	}

	public void setRing1(String name) {
		this.ring1 = new Iref(name);
		onEdit();
	}

	public void setRing2(String name) {
		this.ring2 = new Iref(name);
		onEdit();
	}

	public void setGloves(String name) {
		this.gloves = new Iref(name);
		onEdit();
	}

	public void setArmor(String name) {
		this.armor = new Iref(name);
		onEdit();
	}

	public void setMainHand(String name) {
		this.mainHand = new Iref(name);
		onEdit();
	}

	public void setOffHand(String name) {
		this.offHand = new Iref(name);
		onEdit();
	}

}
