package interfaces;

import classes.Inventory;
import classes.Items;
import classes.Inventory.invItem;
import classes.Item;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class fxInventory {

	public static Stage stage;

	private static TableView<invItem> items;

	public static void open() {
		if(stage != null && stage.isShowing()) stage.close();
		stage = new Stage();

		createTable();

		Button bAddItem = new Button("Add Item");
		bAddItem.setOnAction(e -> addItem(Items.selectItemPrompt()));

		HBox bottom = new HBox();
		bottom.getChildren().add(bAddItem);
		bottom.setPadding(new Insets(10));
		bottom.setSpacing(10);

		BorderPane content = new BorderPane();

		content.setTop(new Text("Note that this feature is currently in it's early days. It's just a mock-up and \nprobably has a bunch of bugs that I'll have to work out. Don't complain if things don't work"));
		content.setCenter(items);
		content.setBottom(bottom);

		stage.setScene(new Scene(content, 500, 500));
		stage.show();
	}

	@SuppressWarnings("unchecked")
	private static void createTable() {
		items = new TableView<invItem>();

		TableColumn<invItem, ImageView> cIcon = new TableColumn<>("Icon");
		cIcon.setCellValueFactory(new PropertyValueFactory<invItem, ImageView>("iconViewSmall"));

		TableColumn<invItem, String> cName = new TableColumn<>("Name");
		cName.setCellValueFactory(new PropertyValueFactory<invItem, String>("name"));

		TableColumn<invItem, String> cDescription = new TableColumn<>("Description");
		cDescription.setCellValueFactory(new PropertyValueFactory<invItem, String>("description"));

		TableColumn<invItem, String> cCount = new TableColumn<>("Count");
		cCount.setCellValueFactory(new PropertyValueFactory<invItem, String>("countView"));

		items.getColumns().addAll(cIcon, cName, cDescription, cCount);

		for(TableColumn<invItem, ?> col : items.getColumns()) col.setReorderable(false);

		items.setOnKeyPressed(key -> {

			if(key.getCode() == KeyCode.DELETE) {
				Inventory.removeItem(items.getSelectionModel().getSelectedItem().getName());
				updateTable();
			}

		});

		updateTable();
	}

	private static void updateTable() {
		items.getItems().clear();
		items.getItems().addAll(Inventory.getItems());
	}

	private static void addItem(Item item) {
		// TODO count / location
		Inventory.addItem(item.getName());
		updateTable();
	}
}
