package interfaces;

import classes.Enchantment;
import javafx.stage.Stage;

public class fxEditEnchantment {

	public static Stage stage;
	
	private static Enchantment enchant;

	public static void open(Enchantment ench) {
		enchant = ench;
		
		if(stage != null && stage.isShowing()) stage.close();
		stage = new Stage();
		stage.setTitle((ench == null) ? "Create New Enchantment" : "Editing Enchantment " + ench.getName());
	}
}
