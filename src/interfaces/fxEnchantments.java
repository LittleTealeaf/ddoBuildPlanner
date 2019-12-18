package interfaces;

import classes.Enchantment;
import classes.Enchantments;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class fxEnchantments {

	public static Stage stage;

	private static ListView<Enchantment> table;

	public static void open() {
		if(stage != null && stage.isShowing()) stage.close();
		stage = new Stage();
		stage.setTitle("Enchantments");

		Button bCreate = new Button("Create");
		bCreate.setOnAction(e -> fxEditEnchantment.open());

		HBox hbottom = new HBox(bCreate);

		BorderPane content = new BorderPane();
		content.setPadding(new Insets(10));
		content.setCenter(getEnchantmentTable());
		content.setBottom(hbottom);

		stage.setScene(new Scene(content));

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

		table.setOnMouseClicked(click -> {

			if(click.getClickCount() == 2) {
				fxEditEnchantment.open(table.getSelectionModel().getSelectedItem());
			}
		});

		updateTable();

		return table;
	}

	public static void updateTable() {
		table.setItems(FXCollections.observableArrayList(Enchantments.getEnchantments()));
	}
}
