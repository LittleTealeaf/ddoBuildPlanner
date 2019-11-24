package fxItem;

import classes.Item;
import javafx.scene.control.Tab;

public class tabGeneral {
	
	private static Item item;
	
	public static Tab getTab() {
		item = fxItem.item;
		
		Tab r = new Tab("General");
		r.setClosable(false);
		return r;
	}
}
