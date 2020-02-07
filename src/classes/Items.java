package classes;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import util.system;
import vars.ItemSlot;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Writing and Reading of the local storage of {@link Item items}<br>
 * Items are stored under the 'Items' folder of the workspace
 * 
 * @author Tealeaf
 * @see Item
 * @see system
 */
@SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
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
	private static final File dir = system.getAppFile("items");

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
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * Saves an {@link Item} into the database, writing a JSON. This will assign the {@link Item} a
	 * random UUID if the item has no existing UUID
	 *
	 * @param i {@link Item} to save
	 * @see Item
	 * @see system
	 */
	public static void saveItem(Item i) {

		try {
			if (i.getUUID() == null || i.getUUID().contentEquals("")) i.setUUID(UUID.randomUUID().toString());
			File file = getFile(i.getUUID());
			file.getParentFile().mkdirs();
			system.writeFile(file, system.staticJSON.toJson(i));
			System.out.println("Saved to: " + file.getPath());
		} catch (Exception ignored) {}

	}

	// TODO finish this comment
	/**
	 * Gets an {@link Item item} from a given UUID<br>
	 * <br>
	 * First checks if the loaded {@link Item items} have been updated<br>
	 * If it needs to update the {@link Item item}, then it will read the {@link Item item} from the
	 * saved file<br>
	 * Otherwise it will just pull the currently loaded {@link Item item}
	 * 
	 * @param uuid {@link Item#getUUID() UUID} of the item
	 * @return {@link Item} given
	 * @see Item
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

	/**
	 * Gets all {@link Item items} located in the directory<br>
	 * <br>
	 * If the directory has been updates since the last time this function is called<br>
	 * it will read and update all the {@link Item items}
	 * 
	 * @return List of all {@link Item items}
	 * @see Item
	 */
	public static List<Item> getAllItems() {
		List<Item> r = new ArrayList<>();

		if (dir.lastModified() == lastModified) return items;

		System.out.println("Directory updated, loading new");

		for (File f : dir.listFiles()) {

			try {
				Item n = system.objectJSON.fromJson(new FileReader(f), Item.class);
				if (n != null) r.add(n);
			} catch (Exception ignored) {}

		}

		items = r;
		lastModified = dir.lastModified();

		return r;
	}

	/**
	 * Gets all {@link Item items} by the given {@link ItemSlot slots}
	 * 
	 * <br><br>This calls the {@link #getAllItems()} function in order to get an updates list of items
	 * 
	 * @param slots Array of {@link ItemSlot Item Slots} to get items from. An item must have at least one of these in order to be added to the list
	 * @return List of all {@link Item items} that have at least one of the given {@link ItemSlot slots}
	 * @see ItemSlot Item Slot
	 * @see Item
	 */
	public static List<Item> getItemsBySlot(ItemSlot... slots) {
		List<Item> r = new ArrayList<>();

		for (Item i : getAllItems()) if (i.hasEquipSlot(slots)) r.add(i);

        return r;
	}

	/**
	 * Gets a {@link File} of a given name in the {@link Item item} directory
	 * @param name Name of the {@link Item item} to get
	 * @return {@link File} of that item
	 */
	public static File getFile(String name) {
		return system.getAppFile("items", name + ".json");
	}

	/**
	 * A {@link TableView table} representation of a list of {@link Item items}.<br><br>
	 * 
	 * The {@link TableView table} is configured to have 3 separate {@link TableColumn columns}<p><ul>
	 * <li>{@link Item#getIconViewSmall() Icon} of the {@link Item}
	 * <li>{@link Item#getName() Name} of the {@link Item}
	 * <li>{@link Item#getDescriptionTrimmed() Description} of the {@link Item}
	 * </ul>
	 * @param items List of {@link Item items} to include in the {@link TableView table}. <br>Modification of the {@link TableView table} is perfectly fine after calling this function
	 * @return {@link TableView Table} of items containing the given {@link Item items}
	 */
	@SuppressWarnings("unchecked")
	public static TableView<Item> itemTable(List<Item> items) {
		TableView<Item> table = new TableView<>();

		// Setting up the columns

		TableColumn<Item, ImageView> cIcon = new TableColumn<>("Icon");
		cIcon.setCellValueFactory(new PropertyValueFactory<>("iconViewSmall"));
		double iconWidth = Settings.appearance.icon.size + 5;
		cIcon.setMaxWidth(iconWidth);
		cIcon.setMinWidth(iconWidth);

		TableColumn<Item, String> cName = new TableColumn<>("Name");
		cName.setCellValueFactory(new PropertyValueFactory<>("name"));
		cName.setMinWidth(100);

		// TODO cell factory with worth wrap

		TableColumn<Item, String> cDescription = new TableColumn<>("Description");
		cDescription.setCellValueFactory(new PropertyValueFactory<>("descriptionTrimmed"));

		table.getColumns().addAll(cIcon, cName, cDescription);

		for (TableColumn<Item, ?> col : table.getColumns()) col.setReorderable(false);

		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		try {
			table.getItems().addAll(FXCollections.observableArrayList(items));
		} catch (Exception ignored) {}

		return table;
	}
}
