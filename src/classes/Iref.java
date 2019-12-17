package classes;

import java.util.ArrayList;
import java.util.List;


public class Iref {

	public String uuid;

	private List<Craftref> crafting;

	private transient Item temp;

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
