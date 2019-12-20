package classes;

import java.util.ArrayList;
import java.util.List;

import vars.ItemSlot;

public class Iref {

	// TODO mythic / reaper stats

	private String uuid;

	private List<Craftref> crafting;

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

	public List<Enchref> getEnchantments() {
		List<Enchref> r = getItem().getEnchantments();
		r.addAll(getCraftingEnchantments());
		return r;
	}

	public List<Enchref> getCraftingEnchantments() {
		List<Enchref> r = new ArrayList<Enchref>();

		for(Craftref c : crafting) {
			r.add(getItem().getCraft(c.getUUID()).getChoice(c.getIndex()));
		}

		return r;
	}
}
