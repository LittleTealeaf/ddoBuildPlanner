package fxItem;

import classes.Item;
import classes.Item.Enchantment;
import javafx.scene.control.Tab;
import java.util.ArrayList;
import java.util.List;

public class tabEnchantments {
	
	
	public static Tab getTab() {
		
		
		Tab r = new Tab("Enchantments");
		r.setClosable(false);
		return r;
	}
	
	public static List<Enchantment> getEnchantments() {
		List<Enchantment> r = new ArrayList<Enchantment>();
		
		
		return r;
	}
	
}
