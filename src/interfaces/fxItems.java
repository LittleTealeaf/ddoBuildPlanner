package interfaces;

import classes.Item;
import classes.Items;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class fxItems {

	public static Stage stage;

	private static TableView<Item> table;

	public static void open() {
		if(stage != null && stage.isShowing()) stage.close();
		stage = new Stage();
		stage.setTitle("Items");

		BorderPane content = new BorderPane();

		Button create = new Button("Create Item");
		create.setOnAction(e -> fxEditItem.open());

		HBox footer = new HBox(create);
		footer.setPadding(new Insets(10));
		footer.setSpacing(10);

		content.setCenter(itemTable());
		content.setBottom(footer);

		stage.setScene(new Scene(content, 500, 500));

		stage.show();
	}

	public static ScrollPane itemTable() {
		ScrollPane r = new ScrollPane();

		table = Items.itemTable(Items.getAllItems());

		table.setOnMouseClicked(click -> {
			if(click.getClickCount() == 2 && table.getSelectionModel().getSelectedIndex() != -1) openSelected();
		});
		table.setOnKeyPressed(key -> {

			switch (key.getCode()) {
			case ENTER:
				openSelected();
				break;
			case DELETE:
				table.getSelectionModel().getSelectedItem().deleteItem();
				break;
			default:
				break;
			}
		});

		table.prefWidthProperty().bind(r.widthProperty());

		r.setContent(table);
		r.setFitToWidth(true);
		r.setFitToHeight(true);

		return r;
	}

	private static void openSelected() {
		fxEditItem.open(table.getSelectionModel().getSelectedItem());
	}

	public static void updateTable() {
		if(table == null) return;
		table.getItems().clear();
		table.getItems().addAll(FXCollections.observableArrayList(Items.getAllItems()));
	}
}
