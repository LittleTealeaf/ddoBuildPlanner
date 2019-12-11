package interfaces;

import classes.Enchantment;
import classes.Enchantments;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
		ench = (enchantment != null) ? enchantment : new Enchantment();
		
		if(stage != null && stage.isShowing()) stage.close();
		stage = new Stage();
		stage.setTitle((enchantment == null) ? "Create Enchantment" : "Edit Enchantment " + ench.getName());
		
		Text lName = new Text("Name");
		
		TextField name = new TextField();
		name.setText(ench.getName());
		name.textProperty().addListener((e,o,n) -> ench.setName(n));
		
		HBox hb = new HBox(lName,name);
		
		Button bSave = new Button("Save");
		bSave.setOnAction(e -> save());
		
		BorderPane content = new BorderPane();
		content.setTop(hb);
		content.setBottom(bSave);
		
		stage.setScene(new Scene(content));
		stage.show();
	}
	
	private static void save() {
		Enchantments.updateEnchantment(ench);
	}
}
