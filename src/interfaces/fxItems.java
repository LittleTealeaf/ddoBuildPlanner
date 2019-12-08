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

	@SuppressWarnings("unchecked")
	public static ScrollPane itemTable() {
		ScrollPane r = new ScrollPane();

		table = new TableView<Item>();

		//Setting up the columns
		
		TableColumn<Item, ImageView> cIcon = new TableColumn<Item, ImageView>("Icon");
		cIcon.setCellValueFactory(new PropertyValueFactory<Item, ImageView>("iconViewSmall"));

		TableColumn<Item, String> cName = new TableColumn<Item, String>("Name");
		cName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));

		TableColumn<Item, String> cDescription = new TableColumn<Item, String>("Description");
		cDescription.setCellValueFactory(new PropertyValueFactory<Item, String>("descriptionTrimmed"));

		table.getColumns().addAll(cIcon, cName, cDescription);
		
		//Adding the item and keybinds

		table.getItems().addAll(FXCollections.observableArrayList(Items.getAllItems()));

		table.setOnMouseClicked(click -> {
			if(click.getClickCount() == 2) openSelected();
		});
		table.setOnKeyPressed(key -> {
			if(key.getCode() == KeyCode.ENTER) openSelected();
			else if(key.getCode() == KeyCode.DELETE) table.getSelectionModel().getSelectedItem().deleteItem();
		});

		table.prefWidthProperty().bind(r.widthProperty());
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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
