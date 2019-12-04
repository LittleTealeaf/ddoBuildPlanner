package classes;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

import util.system;

public class Items {

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

	private static File getFile(String name) {
		return system.getAppFile(new String[] {"items", name + ".json"});
	}
}
