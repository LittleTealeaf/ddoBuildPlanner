package interfaces;

import classes.Enchantment;
import classes.Enchantments;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class fxEditEnchantment {
	
	public static Enchantment ench;
	
	public static Stage stage;
	
	public static void open() {
		open(null);
	}
	
	public static void open(Enchantment enchantment) {
		ench = enchantment;
		
		if(stage != null && stage.isShowing()) stage.close();
		stage = new Stage();
		stage.setTitle((ench == null) ? "Create Enchantment" : "Edit Enchantment " + ench.getName());
		if(ench == null) ench = new Enchantment();
		
		Text lName = new Text("Name");
		
		TextField name = new TextField();
		name.setText(ench.getName());
		name.textProperty().addListener((e,o,n) -> ench.setName(n));
		
		HBox hb = new HBox(lName,name);
		
		BorderPane content = new BorderPane();
	}
	
	private static void save() {
		Enchantments.updateEnchantment(ench);
	}
}
