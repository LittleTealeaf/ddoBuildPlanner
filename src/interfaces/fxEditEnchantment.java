package interfaces;

import classes.Enchantment;
import classes.Enchantments;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
		
		
		VBox content = new VBox();
		
		Text lName = new Text("Name");
		
		TextField name = new TextField();
		name.setText(ench.getName());
		name.textProperty().addListener((e,o,n) -> ench.setName(n));
		
		HBox hName = new HBox(lName,name);
		hName.setSpacing(10);
		content.getChildren().add(content);
		
		content.getChildren().add(variablePane());
		
		
		
		Button bSave = new Button("Save");
		bSave.setOnAction(e -> save());

		
		stage.setScene(new Scene(content));
		stage.show();
	}
	
	private static void save() {
		Enchantments.updateEnchantment(ench);
	}
	
	private static TitledPane variablePane() {
		TitledPane r= new TitledPane();
		
		Text text = new Text();
		
		r.setContent(text);
		r.setPadding(new Insets(10));
		
		return r;
	}
}
