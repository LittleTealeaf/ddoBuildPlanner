package classes;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import util.resource;
import util.system;

public class SetBonuses {

	public SetBonuses() {}

	private static List<SetBonus> sets;

	public static void load() {
		sets = new ArrayList<SetBonus>();

		try {

			if(system.setBonuses.exists()) {
				system.staticJSON.fromJson(new FileReader(system.setBonuses), SetBonuses.class);
			} else {
				system.staticJSON.fromJson(resource.getBufferedReader("enchantments.json"), SetBonuses.class);
				save();
			}

		} catch(Exception e) {}

	}

	public static void save() {}
}
