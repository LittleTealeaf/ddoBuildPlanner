package classes;

import java.util.ArrayList;
import java.util.List;

public class ItemExport {

	private Item item;
	private List<Enchantment> enchantments;
	private String icon;
	private String image;

	public ItemExport(Item item) {
		this.item = item;

		enchantments = new ArrayList<Enchantment>();

		for(Enchref ench : item.getEnchantments()) {
			enchantments.add(ench.getEnchantment());
		}

		if(Settings.porting.exporting.includeImages) {
			icon = (item.getIconUUID() != null) ? Images.getImageFileContents(item.getIconUUID()) : null;
			image = (item.getImageUUID() != null) ? Images.getImageFileContents(item.getImageUUID()) : null;
		}

	}

	public void importItem() {
		item.saveItem();

		Enchantments.addEnchantments(enchantments);

		if(icon != null) Images.saveImageFromContents(item.getIconUUID(), icon);
		if(image != null) Images.saveImageFromContents(item.getImageUUID(), image);
	}
}
