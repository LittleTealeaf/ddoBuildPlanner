package ddo;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.resource;
import util.system;

public class EnchantConvert {

	public static List<converter> data;

	public static void load() {
		//TODO converter reads from a system file that copies the enchantment file if it's needed
		data = new ArrayList<converter>();

		BufferedReader reader = resource.getText("enchantments.txt");

		String line;

		try {

			while((line = reader.readLine()) != null) {
				if(line.toCharArray()[0] != '#') data.add(new converter(line));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static class converter {

		private String id;
		private String name;
		private String description;
		private List<conAttribute> attrBonuses;

		public converter(String id, String name, String description) {
			this.id = id;
			this.name = name;
			this.description = description;
		}

		public converter(String line) {
			String[] data = line.split("\\");
			id = data[0];
			name = data[1];
			description = data[2];
			attrBonuses = new ArrayList<conAttribute>();

			// Parse the conAttributes
			int pos = 0;
			String temp = "";

			for(char c : data[3].toCharArray()) {

				switch (pos) {
				case 0:
					if(c == '\"') pos = 1;
					break;
				case 1:
					if(c == '\"') {
						pos = 0;
						String[] parsed = temp.split(":");
						attrBonuses.add(new conAttribute(parsed[0],parsed[1]));
						temp = "";
					} else temp += c;
					break;
				}
			}
		}

		private static class conAttribute {

			private String attribute;
			private double multiplier;

			public conAttribute(String attribute, String multiplier) {
				this.attribute = attribute;
				this.multiplier = Double.parseDouble(multiplier);
			}

			public String getAttribute() {
				return attribute;
			}

			public double getMultiplier() {
				return multiplier;
			}
		}
	}
}
