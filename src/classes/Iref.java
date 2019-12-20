package classes;

import java.util.ArrayList;
import java.util.List;

/**
 * A reference object used to indirectly reference an item, while keeping crafting options separate
 * and reducing the amount of data stored
 * 
 * @author Tealeaf
 */
public class Iref {

	// TODO mythic / reaper stats

	/**
	 * Universal Identifier of the Item
	 */
	private String uuid;

	/**
	 * Crafting choices of the item
	 * 
	 * @see Craftref
	 */
	private List<Craftref> crafting;

	/**
	 * Temporary item variable
	 */
	private transient Item temp;

	public Iref(Item item) {
		if(Items.getItem(item.getUUID()) != null) item.saveItem();
		crafting = new ArrayList<Craftref>();
		if(item.getCrafting() != null) for(Craftable c : item.getCrafting()) crafting.add(new Craftref(c, 0));
		this.uuid = item.getUUID();
	}

	public Iref(String uuid) {
		this.uuid = uuid;
	}

	public Item getItem() {
		return (temp == null) ? (temp = Items.readItem(uuid)) : temp;
	}

	public List<Craftref> getCrafting() {
		return crafting;
	}

	public void setCrafting(List<Craftref> crafting) {
		this.crafting = crafting;
	}

	public void addCraftref(Craftref craftref) {
		this.crafting.add(craftref);
	}

	public void removeCraftRef(Craftref craftref) {
		this.crafting.remove(craftref);
	}

	public void updateCraftRef(Craftref craftref) {

		for(Craftref c : crafting) {
			if(craftref.getUUID().contentEquals(c.getUUID())) c.setIndex(craftref.getIndex());
		}

	}

	/**
	 * Gets all the enchantments, including the crafting enchantments
	 * 
	 * @return All Item Enchantments
	 * @see Enchref
	 * @see #getCraftingEnchantments()
	 */
	public List<Enchref> getEnchantments() {
		List<Enchref> r = getItem().getEnchantments();
		r.addAll(getCraftingEnchantments());
		return r;
	}

	/**
	 * Gets all the Enchantments depending on the item crafting options
	 * 
	 * @see Craftable
	 * @see Craftref
	 * @see Enchref
	 * @return
	 */
	public List<Enchref> getCraftingEnchantments() {
		List<Enchref> r = new ArrayList<Enchref>();

		for(Craftref c : crafting) {
			r.add(getItem().getCraft(c.getUUID()).getChoice(c.getIndex()));
		}

		return r;
	}
}
