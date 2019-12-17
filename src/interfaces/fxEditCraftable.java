package interfaces;

import java.util.Optional;

import classes.Craftable;
import classes.Enchantments;
import classes.Enchref;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class fxEditCraftable {
	
	private static Craftable craft;

	public static Craftable openEditor() {
		return openEditor(null);
	}

	public static Craftable openEditor(Craftable c) {
		Dialog<Craftable> editor = new Dialog<Craftable>();
		
		editor.setTitle((c != null) ? "Editing " + c.getName() : "Create Craftable");
		
		craft = (c != null) ? c : new Craftable();
		
		Text lName = new Text("Name:");
		
		TextField name = new TextField();
		name.setText((c != null) ? c.getName() : "");
		name.textProperty().addListener((e,o,n) -> craft.setName(n));
		
		
		HBox top = new HBox(lName,name);
		top.setSpacing(10);
		
		BorderPane content = new BorderPane();
		content.setTop(top);
		content.setCenter(choiceView());
		
		editor.getDialogPane().setContent(content);
		
		editor.setResultConverter(e -> (e.getButtonData() == ButtonData.OK_DONE) ? craft : c);
		
		ButtonType bAccept = new ButtonType("Accept", ButtonData.OK_DONE);
		ButtonType bCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		editor.getDialogPane().getButtonTypes().addAll(bAccept, bCancel);

		Optional<Craftable> r = editor.showAndWait();
		return (!r.isEmpty()) ? r.get() : c;
	}
	
	private static BorderPane choiceView() {
		ListView<Enchref> r = new ListView<>();
		
		r.setItems(FXCollections.observableArrayList(craft.getChoices()));
		r.setOnMouseClicked(click -> {

			if(click.getClickCount() == 2) {
				Enchref i = r.getSelectionModel().getSelectedItem();
				Enchref n = Enchantments.enchrefDialog(i);
				if(n != null) craft.updateChoice(i, n);
				r.setItems(FXCollections.observableArrayList(craft.getChoices()));
			}
		});
		r.setCellFactory(param -> new ListCell<Enchref>() {

			protected void updateItem(Enchref item, boolean empty) {
				super.updateItem(item, empty);
				if(empty || item == null || item.getDisplayName() == null || item.getDisplayName().contentEquals("")) setText(null);
				else setText(item.getDisplayName());
			}
		});

		Button bCreate = new Button("Create");
		bCreate.setOnAction(e -> {
			Enchref ench = Enchantments.enchrefDialog();
			if(ench != null) craft.addChoice(ench);
			r.setItems(FXCollections.observableArrayList(craft.getChoices()));
		});

		Button bDelete = new Button("Delete");
		bDelete.disableProperty().bind(r.selectionModelProperty().isNull());
		bDelete.setOnAction(e -> {
			craft.removeChoice(r.getSelectionModel().getSelectedItem());
			r.setItems(FXCollections.observableArrayList(craft.getChoices()));
		});

		HBox buttons = new HBox(bCreate, bDelete);
		buttons.setSpacing(10);

		BorderPane content = new BorderPane();
		content.setCenter(r);
		content.setBottom(buttons);
		
		return content;
	}
}
