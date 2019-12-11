package interfaces;

import classes.Enchantment;
import classes.Enchantment.AttributeBonus;
import classes.Enchantments;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class fxEditEnchantment {
	
	private static Enchantment ench;
	
	public static Stage stage;
	
	private static GridPane attributes; 
	
	public static void open() {
		open(null);
	}
	
	public static void open(Enchantment enchantment) {
		ench = (enchantment != null) ? enchantment : new Enchantment();
		
		if(stage != null && stage.isShowing()) stage.close();
		stage = new Stage();
		stage.setTitle((enchantment == null) ? "Create Enchantment" : "Edit Enchantment " + ench.getName());
		
		Text lName = new Text("Name:");
		TextField name = new TextField();
		name.setText(ench.getName());
		name.textProperty().addListener((e,o,n) -> ench.setName(n));
		
		Text lDisplay = new Text("Display:");
		TextField display = new TextField();
		display.setText(ench.getDisplayName());
		display.textProperty().addListener((e,o,n) -> ench.setDisplayName(n));
		
		
		
		GridPane top = new GridPane();
		
		BorderPane content = new BorderPane();
		content.setTop(top);
		content.setCenter(getAttribute());
		content.setPadding(new Insets(10));
		
		Button bSave = new Button("Save");
		bSave.setOnAction(e -> save());

		
		stage.setScene(new Scene(content,500,500));
		stage.show();
	}
	
	private static void save() {
		Enchantments.updateEnchantment(ench);
	}
	
	private static BorderPane getAttribute() {
		BorderPane r = new BorderPane();
		
		attributes = new GridPane();
		attributes.setPadding(new Insets(10));
		attributes.setHgap(10);
		attributes.setVgap(10);
		
		updateAttributes();
		
		return r;
	}
	
	private static void updateAttributes() {
		attributes.getChildren().clear();
		
		attributes.add(new Text("Attribute"), 0, 0);
		attributes.add(new Text("Bonus"), 1, 0);
		attributes.add(new Text("Multiplier"), 2, 0);
		
		int row = 1;
		for(AttributeBonus a : ench.getAttributeBonuses()) {
			//STUFFZ
			
			TextField attrName = new TextField();
			attrName.setText(a.getAttribute());
			attrName.textProperty().addListener((e,o,n) -> a.setAttribute(n));
			
			
			
			row++;
		}
	}
}
