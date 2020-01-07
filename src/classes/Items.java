package classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

	/**
	 * Last modified time of the items folder
	 */
	private static long lastModified;

	/**
	 * Loaded Items
	 */
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
			if(i.getUUID() == null || i.getUUID().contentEquals("")) i.setUUID(UUID.randomUUID().toString());
			File file = getFile(i.getUUID());
			file.getParentFile().mkdirs();
			system.writeFile(file, system.staticJSON.toJson(i));
			System.out.println("Saved to: " + file.getPath());
		} catch(Exception e) {}

	}

	public static Item getItem(String uuid) {

		try {
			return system.objectJSON.fromJson(new FileReader(getFile(uuid)), Item.class);
		} catch(JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			return null;
		}

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

	@SuppressWarnings("unchecked")
	public static TableView<Item> itemTable(List<Item> items) {
		TableView<Item> table = new TableView<Item>();

		// Setting up the columns

		TableColumn<Item, ImageView> cIcon = new TableColumn<Item, ImageView>("Icon");
		cIcon.setCellValueFactory(new PropertyValueFactory<Item, ImageView>("iconViewSmall"));
		cIcon.setMaxWidth(Settings.appearance.icon.size);
		cIcon.setMinWidth(Settings.appearance.icon.size);

		TableColumn<Item, String> cName = new TableColumn<Item, String>("Name");
		cName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		cName.setMinWidth(100);

		//TODO cell factory with worth wrap
		
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
	
	private static class cellDescription extends ListCell<Item> {

		private TextArea content;

		public cellDescription() {
			super();

			content.setWrapText(true);
			
		}

		@Override
		protected void updateItem(Item item, boolean empty) {
			super.updateItem(item, empty);

			if(item != null && !empty) {
				
				content.setText(item.getDescriptionTrimmed());

				setGraphic(content);
			} else {
				setGraphic(null);
			}

		}
	}
}
