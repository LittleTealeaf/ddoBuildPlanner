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
import util.system;
import vars.ItemSlot;

/**
 * Writing and Reading of the local storage of {@link Item items}<br>
 * Items are stored under the 'Items' folder of the workspace
 * 
 * @author Tealeaf
 * @see Item
 * @see system
 */
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
	 * Directory of the items folder
	 * 
	 * @see system#getAppFile(String...)
	 */
	private static File dir = system.getAppFile("items");

	/**
	 * All the currently loaded {@link Item Items}
	 * 
	 * @see Item
	 * @see #getAllItems()
	 */
	private static List<Item> items;

	/**
	 * Returns an {@link Item} from the local database
	 * 
	 * @param UUID Unique Identifier of the {@link Item}
	 * @return {@link Item} with the given UUID
	 * @see system
	 */
	private static Item readItem(String UUID) {

		try {
			return system.objectJSON.fromJson(new FileReader(getFile(UUID)), Item.class);
		} catch(Exception e) {
			return null;
		}

	}

	/**
	 * Saves an {@link Item} into the database, writing a JSON. This will assign the {@link Item} a
	 * random UUID if the item has no existing UUID
	 * 
	 * @param i {@link Item} to save
	 * @see {@link Item}
	 * @see {@link system}
	 */
	public static void saveItem(Item i) {

		try {
			if(i.getUUID() == null || i.getUUID().contentEquals("")) i.setUUID(UUID.randomUUID().toString());
			File file = getFile(i.getUUID());
			file.getParentFile().mkdirs();
			system.writeFile(file, system.staticJSON.toJson(i));
			System.out.println("Saved to: " + file.getPath());
		} catch(Exception e) {}

	}

	//TODO finish this comment
	/**
	 * Gets an item from a UUID<br><br>
	 * First checks if the loaded items have been updated
	 * 
	 * @param uuid
	 * @return
	 */
	public static Item getItem(String uuid) {

		if(dir.lastModified() == lastModified) {
			for(Item i : items) if(i.getUUID().contentEquals(uuid)) return i;
		} else {
			for(int i = 0; i < items.size(); i++) if(items.get(i).getUUID().contentEquals(uuid)) {
				items.remove(i);
				i--;
			}
			Item r = readItem(uuid);
			items.add(r);
			return r;
		}
		return null;
	}

	public static List<Item> getAllItems() {
		List<Item> r = new ArrayList<Item>();

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

		// TODO cell factory with worth wrap

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
