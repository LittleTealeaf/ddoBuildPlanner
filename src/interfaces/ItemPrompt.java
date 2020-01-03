package interfaces;

import java.util.List;

import classes.Iref;
import classes.Item;
import classes.Items;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import vars.ItemSlot;

public class ItemPrompt {

	private ItemSlot[] slots;
	private Item item;

	public ItemPrompt() {}

	public ItemPrompt setSlot(ItemSlot slot) {
		slots = new ItemSlot[] {slot};
		return this;
	}

	public ItemPrompt setSlots(ItemSlot... itemSlots) {
		slots = itemSlots;
		return this;
	}

	public ItemPrompt setItem(Item i) {
		this.item = i;
		return this;
	}

	public ItemPrompt setItem(Iref i) {
		if(i == null) return this;
		return setItem(i.getItem());
	}

	public Item showPrompt() {
		Dialog<Item> dialog = new Dialog<Item>();
		dialog.setTitle("Select Item");
		dialog.setHeaderText("Select an item");

		List<Item> fullList = (slots == null) ? Items.getAllItems() : Items.getItemsBySlot(slots);

		// Table of all the items selected via slots
		TableView<Item> table = Items.itemTable(Items.getAllItems());
		table.getItems().clear();
		for(Item i : fullList) table.getItems().add(i);
		table.setOnMouseClicked(click -> {
			if(click.getClickCount() == 2) dialog.getResult();
		});
		table.getSelectionModel().select(item);

		Text lSort = new Text("Sort:");
		// TODO sort by enhancement values too

		TextField sort = new TextField();
		sort.textProperty().addListener((e, o, n) -> {
			table.getItems().clear();
			for(Item i : fullList) if(i.getName().contains(n)) table.getItems().add(i);
		});

		HBox.setHgrow(sort, Priority.ALWAYS);

		HBox top = new HBox(lSort, sort);
		top.setSpacing(10);
		top.setPadding(new Insets(10));

		BorderPane content = new BorderPane();
		content.setTop(top);
		content.setCenter(table);

		ButtonType bSelect = new ButtonType("Select", ButtonData.OK_DONE);
		ButtonType bCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		dialog.getDialogPane().getButtonTypes().addAll(bSelect, bCancel);
		dialog.getDialogPane().setContent(content);
		dialog.getDialogPane().setPrefWidth(500);
		dialog.getDialogPane().setPrefHeight(500);

		dialog.setResultConverter(b -> {

			if(b.getButtonData() == ButtonData.OK_DONE) {
				return table.getSelectionModel().getSelectedItem();
			} else return null;

		});

		try {
			return dialog.showAndWait().get();
		} catch(Exception e) {
			return null;
		}

	}
}
