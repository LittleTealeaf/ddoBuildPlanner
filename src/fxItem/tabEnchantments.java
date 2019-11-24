package fxItem;

import classes.Item;
import javafx.scene.control.Tab;

public class tabEnchantments {
	
	private static Item item;
	
	public static Tab getTab() {
		
		item = fxItem.item;
		
		Tab r = new Tab("Enchantments");
		r.setClosable(false);
		return r;
	}
}
