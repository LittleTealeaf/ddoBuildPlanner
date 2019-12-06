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
	
	private static TableView<Item> table;

	public static void open() {
		if(stage != null && stage.isShowing()) stage.close();
		stage = new Stage();
		stage.setTitle("Items");

		BorderPane content = new BorderPane();

		content.setCenter(itemTable());

		stage.setScene(new Scene(content,500,500));

		stage.show();

	}

	@SuppressWarnings("unchecked")
	public static ScrollPane itemTable() {
		ScrollPane r = new ScrollPane();

		table = new TableView<Item>();

		TableColumn<Item, ImageView> cIcon = new TableColumn<Item, ImageView>("Icon");
		cIcon.setCellValueFactory(new PropertyValueFactory<Item, ImageView>("iconViewSmall"));
		
		TableColumn<Item,String> cName = new TableColumn<Item,String>("Name");
		cName.setCellValueFactory(new PropertyValueFactory<Item,String>("name"));

		table.getColumns().addAll(cIcon,cName);

		table.getItems().addAll(FXCollections.observableArrayList(Items.getAllItems()));
		
		table.setOnMouseClicked(click -> {
			if(click.getClickCount() == 2) {
				fxEditItem.open(table.getSelectionModel().getSelectedItem());
			}
		});
		
		table.prefWidthProperty().bind(r.widthProperty());

		r.setContent(table);
		r.setFitToWidth(true);
		r.setFitToHeight(true);

		return r;
	}
	
	public static void updateTable() {
		//SAV ETABLE
	}
}
