package classes;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import util.system;

public class Items {

	private static long lastModified;
	private static List<Item> items;

	/**
	 * Gets an item from the database
	 * @param name Name of the item
	 * @return Item in the Item class. Returns null if the item doens't exist or is in an unreadable state
	 */
	public static Item readItem(String name) {
		try {
			return system.objectJSON.fromJson(new FileReader(getFile(name)), Item.class);
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * Saves an item to the database
	 * @param i Item to save
	 */
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

	/**
	 * Gets a list of all the items in the database
	 * @return List of all items
	 */
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

	private static File getFile(String name) {
		return system.getAppFile("items", name + ".json");
	}
}
