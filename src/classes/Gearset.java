package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import classes.Item.ItemExport;
import vars.GearSlot;

/**
 * An object that represents a gear set, or a set of items worn at a given time. This class includes
 * any item crafting options
 * 
 * @see Iref
 * @author Tealeaf
 */
public class Gearset {

	private String name;

	/**
	 * Inventory Reference Slot
	 * <p>
	 * This is a reference of the set item, in order to reduce file size and include unique identifiers
	 * such as crafting choices
	 * </p>
	 */
	private Iref goggles, helmet, necklace, trinket, cloak, bracers, belt, ring1, ring2, gloves, boots, armor, mainHand, offHand;

	public Gearset() {
		this("");
	}

	public Gearset(String name) {
		this.name = name;
	}

	/**
	 * Returns all the item references of the gearset
	 * 
	 * @return All item references {@link Iref}
	 */
	public List<Iref> getAllIrefs() {
		return Arrays.asList(goggles, helmet, necklace, trinket, cloak, bracers, belt, ring1, ring2, gloves, boots, armor, mainHand, offHand);
	}

	public List<Attribute> getAllAttributes() {
		List<Attribute> r = new ArrayList<Attribute>();
		for(Iref i : getAllIrefs()) if(i != null) for(Enchref e : i.getEnchantments()) r.addAll(e.getAttributes());
		return r;
	}

	/**
	 * Exports the gearset into the exported class
	 * 
	 * @return Gearset Exported class
	 */
	public GearsetExport export() {
		return new GearsetExport(this);
	}

	/**
	 * Gets the item in the selected slot
	 * 
	 * @param slot {@code GearSlot} of the item
	 * @return Item currently in that {@code GearSlot}
	 * @see vars.GearSlot
	 */
	public Iref getItemBySlot(GearSlot slot) {

		switch (slot) {
		case GOGGLES:
			return goggles;
		case HELMET:
			return helmet;
		case NECKLACE:
			return necklace;
		case TRINKET:
			return trinket;
		case CLOAK:
			return cloak;
		case BRACERS:
			return bracers;
		case BELT:
			return belt;
		case RING1:
			return ring1;
		case RING2:
			return ring2;
		case GLOVES:
			return gloves;
		case ARMOR:
			return armor;
		case MAINHAND:
			return mainHand;
		case OFFHAND:
			return offHand;
		case BOOTS:
			return boots;
		}

		return null;
	}

	/**
	 * Sets the item in the specified slot, overwriting whatever item was already in that slot
	 * 
	 * @param item Item to place in the slot
	 * @param slot {@code GearSlot} of the item
	 * @see vars.GearSlot
	 * @see Item
	 */
	public void setItemBySlot(Item item, GearSlot slot) {
		setItemBySlot(new Iref(item), slot);
	}

	/**
	 * Sets the item in the specified slot, overwriting whatever item was already in that slot
	 * 
	 * @param item Item to place in the slot
	 * @param slot {@code GearSlot} of the item
	 * @see vars.GearSlot
	 * @see Iref
	 */
	public void setItemBySlot(Iref item, GearSlot slot) {

		switch (slot) {
		case GOGGLES:
			setGoggles(item);
			break;
		case HELMET:
			setHelmet(item);
			break;
		case NECKLACE:
			setNecklace(item);
			break;
		case TRINKET:
			setTrinket(item);
			break;
		case CLOAK:
			setCloak(item);
			break;
		case BRACERS:
			setBracers(item);
			break;
		case BELT:
			setBelt(item);
			break;
		case RING1:
			setRing1(item);
			break;
		case RING2:
			setRing2(item);
			break;
		case GLOVES:
			setGloves(item);
			break;
		case BOOTS:
			setBoots(item);
			break;
		case ARMOR:
			setArmor(item);
			break;
		case MAINHAND:
			setMainHand(item);
			break;
		case OFFHAND:
			setOffHand(item);
			break;
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Iref getGoggles() {
		return goggles;
	}

	public void setGoggles(Iref goggles) {
		this.goggles = goggles;
	}

	public void setGoggles(Item goggles) {
		this.goggles = new Iref(goggles);
	}

	public Iref getHelmet() {
		return helmet;
	}

	public void setHelmet(Iref helmet) {
		this.helmet = helmet;
	}

	public void setHelmet(Item helmet) {
		this.helmet = new Iref(helmet);
	}

	public Iref getNecklace() {
		return necklace;
	}

	public void setNecklace(Iref necklace) {
		this.necklace = necklace;
	}

	public void setNecklace(Item necklace) {
		this.necklace = new Iref(necklace);
	}

	public Iref getTrinket() {
		return trinket;
	}

	public void setTrinket(Iref trinket) {
		this.trinket = trinket;
	}

	public void setTrinket(Item trinket) {
		this.trinket = new Iref(trinket);
	}

	public Iref getCloak() {
		return cloak;
	}

	public void setCloak(Iref cloak) {
		this.cloak = cloak;
	}

	public void setCloak(Item cloak) {
		this.cloak = new Iref(cloak);
	}

	public Iref getBracers() {
		return bracers;
	}

	public void setBracers(Iref bracers) {
		this.bracers = bracers;
	}

	public void setBracers(Item bracers) {
		this.bracers = new Iref(bracers);
	}

	public Iref getBelt() {
		return belt;
	}

	public void setBelt(Iref belt) {
		this.belt = belt;
	}

	public void setBelt(Item belt) {
		this.belt = new Iref(belt);
	}

	public Iref getRing1() {
		return ring1;
	}

	public void setRing1(Iref ring1) {
		this.ring1 = ring1;
	}

	public void setRing1(Item ring1) {
		this.ring1 = new Iref(ring1);
	}

	public Iref getRing2() {
		return ring2;
	}

	public void setRing2(Iref ring2) {
		this.ring2 = ring2;
	}

	public void setRing2(Item ring2) {
		this.ring2 = new Iref(ring2);
	}

	public Iref getGloves() {
		return gloves;
	}

	public void setGloves(Iref gloves) {
		this.gloves = gloves;
	}

	public void setGloves(Item gloves) {
		this.gloves = new Iref(gloves);
	}

	public Iref getBoots() {
		return boots;
	}

	public void setBoots(Iref boots) {
		this.boots = boots;
	}

	public void setBoots(Item boots) {
		this.boots = new Iref(boots);
	}

	public Iref getArmor() {
		return armor;
	}

	public void setArmor(Iref armor) {
		this.armor = armor;
	}

	public void setArmor(Item armor) {
		this.armor = new Iref(armor);
	}

	public Iref getMainHand() {
		return mainHand;
	}

	public void setMainHand(Iref mainHand) {
		this.mainHand = mainHand;
	}

	public void setMainHand(Item mainHand) {
		this.mainHand = new Iref(mainHand);
	}

	public Iref getOffHand() {
		return offHand;
	}

	public void setOffHand(Iref offHand) {
		this.offHand = offHand;
	}

	public void setOffHand(Item offHand) {
		this.offHand = new Iref(offHand);
	}

	public String toString() {
		return name;
	}

	/**
	 * An exportable version of the gearset that includes the following:
	 * <ul>
	 * <li>The Gearset itself, including the item references</li>
	 * <li>All referenced Items in the gearset item list</li>
	 * <li>All referenced Enchantments in each of the items</li>
	 * </ul>
	 * 
	 * @author Tealeaf
	 */
	public static class GearsetExport {

		private Gearset gearset;
		private List<ItemExport> items;
		private List<Enchantment> enchantments;

		public GearsetExport(Gearset gearset) {
			this.gearset = gearset;
			items = new ArrayList<ItemExport>();
			enchantments = new ArrayList<Enchantment>();

			for(Iref i : gearset.getAllIrefs()) {

				if(i != null) {
					ItemExport ie = new ItemExport(i.getItem());

					for(Enchantment e : ie.getEnchantments()) {
						if(!enchantments.contains(e)) enchantments.add(e);
					}

					ie.setEnchantments(null);

					items.add(ie);
				}

			}

		}

		public List<ItemExport> getItems() {
			return items;
		}

		public List<Enchantment> getEnchantments() {
			return enchantments;
		}

		/**
		 * Imports the gearset, including importing all required items and enchantments
		 * 
		 * @return The final imported gearset
		 */
		public Gearset importGearset() {
			Enchantments.addEnchantments(enchantments);
			for(ItemExport i : items) i.importItem();
			return gearset;
		}
	}
}
