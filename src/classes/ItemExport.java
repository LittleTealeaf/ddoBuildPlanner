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

		for(Enchref ench : item.getEnchantments()) enchantments.add(ench.getEnchantment());
		for(Craftable craft : item.getCrafting()) for(Enchref ench : craft.getChoices()) enchantments.add(ench.getEnchantment());

		if(Settings.porting.exporting.includeImages) {
			icon = (item.getIconUUID() != null) ? Images.getImageFileContents(item.getIconUUID()) : null;
			image = (item.getImageUUID() != null) ? Images.getImageFileContents(item.getImageUUID()) : null;
		}

	}

	public void importItem() {
		if(enchantments != null) Enchantments.addEnchantments(enchantments);

		if(icon != null) Images.saveImageFromContents(item.getIconUUID(), icon);
		else item.setIconUUID(null);
		if(image != null) Images.saveImageFromContents(item.getImageUUID(), image);
		else item.setImageUUID(null);

		item.saveItem();
	}

	// Values that can be up-rooted
	public List<Enchantment> getEnchantments() {
		return enchantments;
	}

	public void setEnchantments(List<Enchantment> enchantments) {
		this.enchantments = enchantments;
	}
}
