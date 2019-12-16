package classes;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import util.system;
import vars.ItemSlot;

public class Items {

	/*
	 * Requires Functions:
	 * getItem -- gets the item
	 * saveItem -- saves the item
	 * getAllItems -- gets all items
	 * getItemsBySlot -- gets items by slot
	 * prompt -- select item prompt
	 * item table -- item table
	 */

	private static long lastModified;
	private static List<Item> items;

	public static Item readItem(String ID) {

		try {
			return system.objectJSON.fromJson(new FileReader(getFile(ID)), Item.class);
		} catch(Exception e) {
			return null;
		}
	}

	public static void saveItem(Item i) {

		try {
			File file = getFile(i.getName());
			file.getParentFile().mkdirs();
			FileWriter writer = new FileWriter(file);
			writer.write(system.staticJSON.toJson(i));
			writer.close();
			System.out.println("Saved to: " + file.getPath());
		} catch(Exception e) {}
	}

	public static List<Item> getAllItems() {
		List<Item> r = new ArrayList<Item>();

		File dir = system.getAppFile("items");

		if(dir.lastModified() == lastModified) return items;

		System.out.println("Directory updated, loading new");

		for(File f : dir.listFiles()) {

			try {
				Item n = system.objectJSON.fromJson(new FileReader(f), Item.class);
				if(n != null) r.add(n);
			} catch(Exception e) {}
		}

		items = r;
		lastModified = dir.lastModified();

		return r;
	}

	public static List<Item> getItemsBySlot(ItemSlot... slots) {
		List<Item> r = new ArrayList<Item>();

		for(Item i : getAllItems()) if(i.hasEquipSlot(slots)) r.add(i);

		return r;
	}

	public static File getFile(String name) {
		return system.getAppFile("items", name + ".json");
	}

	public static Item selectItemPrompt() {
		return selectItemPrompt(null, (ItemSlot[]) null);
	}

	public static Item selectItemPrompt(ItemSlot... slots) {
		return selectItemPrompt(null, slots);
	}

	public static Item selectItemPrompt(Item selItem, ItemSlot... slots) {
		Dialog<Item> dialog = new Dialog<Item>();
		dialog.setTitle("Select Item");
		dialog.setHeaderText("Select an item");

		// Table of all the items selected via slots
		TableView<Item> table = itemTable((slots == null) ? getAllItems() : getItemsBySlot(slots));
		table.setOnMouseClicked(click -> {
			if(click.getClickCount() == 2) dialog.getResult();
		});

		ButtonType bSelect = new ButtonType("Select", ButtonData.OK_DONE);
		ButtonType bCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		dialog.getDialogPane().getButtonTypes().addAll(bSelect, bCancel);
		dialog.getDialogPane().setContent(table);
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

	@SuppressWarnings("unchecked")
	public static TableView<Item> itemTable(List<Item> items) {
		TableView<Item> table = new TableView<Item>();

		// Setting up the columns

		TableColumn<Item, ImageView> cIcon = new TableColumn<Item, ImageView>("Icon");
		cIcon.setCellValueFactory(new PropertyValueFactory<Item, ImageView>("iconViewSmall"));
		cIcon.setPrefWidth(Settings.appearance.icon.size);

		TableColumn<Item, String> cName = new TableColumn<Item, String>("Name");
		cName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));

		TableColumn<Item, String> cDescription = new TableColumn<Item, String>("Description");
		cDescription.setCellValueFactory(new PropertyValueFactory<Item, String>("descriptionTrimmed"));

		table.getColumns().addAll(cIcon, cName, cDescription);

		for(TableColumn<Item, ?> col : table.getColumns()) col.setReorderable(false);

		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		try {
			table.getItems().addAll(FXCollections.observableArrayList(items));
		} catch(Exception e) {}

		return table;
	}
}
