package classes;

import java.util.List;

public class ItemExport {
	
	private Item item;
	private List<Enchantment> enchantments;
	
	public ItemExport(Item item) {
		this.item = item;
		for(Enchref ench : item.getEnchantments()) enchantments.add(ench.getEnchantment());
	}
	
	public Item importItem() {
		
		return null;
	}
	
	public void exportItem() {
		
	}
}
