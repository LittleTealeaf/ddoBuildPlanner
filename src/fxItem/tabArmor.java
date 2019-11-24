package fxItem;

import classes.Item;
import javafx.scene.control.Tab;

public class tabArmor {
	private static Item item;
	
	public static Tab getTab() {
		item = fxItem.item;
		
		Tab r = new Tab("Armor");
		r.setClosable(false);
		r.disableProperty().bind(fxItem.type.valueProperty().isNotEqualTo("Armor").and(fxItem.type.valueProperty().isNotEqualTo("Shield")));
		return r;
	}
}
