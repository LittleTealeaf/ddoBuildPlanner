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

	public static Item grabItem(String name) {
		try {
			return system.objectJSON.fromJson(new FileReader(getFile(name)), Item.class);
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

	private static File getFile(String name) {
		return system.getAppFile("items", name, ".json");
	}
}
