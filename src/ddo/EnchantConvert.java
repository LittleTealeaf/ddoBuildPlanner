package ddo;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import util.resource;

public class EnchantConvert {

	private static List<String[]> enchantMap;

	public static String getDescription(String enchantment) {
		updateMap();

		for (String[] m : enchantMap)
			try {
				if (enchantment.contentEquals(m[0]))
					return m[2];
			} catch (Exception e) {}

		return "";
	}

	private static void updateMap() {
		if (enchantMap == null)
			getMap();
	}

	private static void getMap() {
		enchantMap = new ArrayList<String[]>();
		try {
			BufferedReader br = resource.getResource("enchantments");
			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.toCharArray()[0] != '#')
					enchantMap.add(line.split("|"));
			}
		} catch (Exception e) {}
	}
}
