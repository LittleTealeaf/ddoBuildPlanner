package interfaces;

import classes.Enchantment;
import classes.Enchantments;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class fxEnchantments {

	public static Stage stage;
	
	private static ListView<Enchantment> table;

	public static void open() {
		if(stage != null && stage.isShowing()) stage.close();
		stage = new Stage();
		stage.setTitle("Enchantments");
		
		stage.setScene(new Scene(getEnchantmentTable()));

		stage.show();
	}

	public static ListView<Enchantment> getEnchantmentTable() {
		table = new ListView<>();

		table.setCellFactory(param -> new ListCell<Enchantment>() {

			protected void updateItem(Enchantment item, boolean empty) {
				super.updateItem(item, empty);
				if(empty || item == null || item.getName() == null) setText(null);
				else setText(item.getName());
			}
		});
		
		updateTable();

		return table;
	}
	
	public static void updateTable() {
		table.setItems(FXCollections.observableArrayList(Enchantments.getEnchantments()));
	}
}
