package fxItem;

import classes.Item;
import classes.Item.Armor;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class tabArmor {
	private static Item item;
	
	public static Tab getTab() {
		item = fxItem.item;

		GridPane grid = new GridPane();
		
		Tab r = new Tab("Armor");
		r.setClosable(false);
		r.disableProperty().bind(fxItem.type.valueProperty().isNotEqualTo("Armor").and(fxItem.type.valueProperty().isNotEqualTo("Shield")));
		return r;
	}
	
	public static Armor getArmor() {
		
		if(fxItem.getTab("Armor").isDisabled()) return null;
		
		Armor r = new Armor();
		
		return r;
	}
}
