package classes;

import java.util.ArrayList;
import java.util.List;

public class GearsetExport {

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

	public Gearset importGearset() {
		Enchantments.addEnchantments(enchantments);
		for(ItemExport i : items) i.importItem();
		return gearset;
	}
}
