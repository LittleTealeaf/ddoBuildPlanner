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
		content.setPadding(new Insets(10));
		content.setSpacing(10);
		
		Text lName = new Text("Name");
		
		TextField name = new TextField();
		name.setText(ench.getName());
		name.textProperty().addListener((e,o,n) -> ench.setName(n));
		
		HBox hName = new HBox(lName,name);
		hName.setSpacing(10);
		content.getChildren().add(hName);
		
		content.getChildren().add(variablePane());
		
		Text lDisplayName = new Text("Display Name");
		
		TextField displayName = new TextField();
		displayName.setText(ench.getDisplayName());
		displayName.textProperty().addListener((e,o,n) -> ench.setDisplayName(n));
		
		HBox hDisplay = new HBox(lDisplayName,displayName);
		hDisplay.setSpacing(10);
		content.getChildren().add(hDisplay);
		
		
		Button bSave = new Button("Save");
		bSave.setOnAction(e -> save());

		
		stage.setScene(new Scene(content,500,500));
		stage.show();
	}
	
	private static void save() {
		Enchantments.updateEnchantment(ench);
	}
	
	private static TitledPane variablePane() {
		TitledPane r= new TitledPane();
		r.setText("Variable Names");
		r.setExpanded(false);
		
		Text text = new Text();
		
		String cont = "";
		cont+="The following variables can be used:";
		cont+="\nEnchantment Sub-Type: \"[type]\"";
		cont+="\nBonus Type (Insightful, Quality, etc): \"[bonus]\"";
		cont+="\nValue: \"[value]\"";
		
		text.setText(cont);
		
		r.setContent(text);
		r.setPadding(new Insets(10));
		
		return r;
	}
}
