package interfaces;

import classes.Item;
import classes.Items;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class fxItems {

	public static Stage stage;

	public static void open() {
		if(stage != null && stage.isShowing()) stage.close();
		stage = new Stage();
		stage.setTitle("Items");

		BorderPane content = new BorderPane();

		content.setCenter(itemTable());

		stage.setScene(new Scene(content));

		stage.show();

	}

	@SuppressWarnings("unchecked")
	public static ScrollPane itemTable() {
		ScrollPane r = new ScrollPane();

		TableView<Item> table = new TableView<Item>();

		TableColumn<Item, ImageView> cIcon = new TableColumn<Item, ImageView>();
		// cTStart.setCellValueFactory(new PropertyValueFactory<Team,
		// String>("startFXM"));
		cIcon.setCellValueFactory(new PropertyValueFactory<Item, ImageView>("iconView"));
		// TODO get image to be smaller

		table.getColumns().addAll(cIcon);

		table.getItems().addAll(FXCollections.observableArrayList(Items.getAllItems()));

		r.setContent(table);

		return r;
	}
}
