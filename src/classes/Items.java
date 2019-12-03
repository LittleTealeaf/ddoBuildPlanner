package classes;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import application.Data;

public class Items {

	public static Item grabItem(String name) {
		try {
			return Data.objectJSON.fromJson(new FileReader(getFile(name)), Item.class);
		} catch(Exception e) {
			return null;
		}
	}

	public static void saveItem(Item i) {
		try {
			File file = getFile(i.getName());
			file.getParentFile().mkdirs();
			FileWriter writer = new FileWriter(file);
			writer.write(Data.staticJSON.toJson(i));
			writer.close();
		} catch(Exception e) {}
	}

	private static File getFile(String name) {
		return Data.getDataFile("items\\" + name + ".item");
	}
}
