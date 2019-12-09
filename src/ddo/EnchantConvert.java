package ddo;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import classes.Enchantment;
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
	
	public static String getName(Enchantment ench) {
		for(converter c : data) {
			if(c.getId().contentEquals(ench.getId())) return repPlaceHolders(ench,c.getName());
		}	
		return "";
	}
	
	public static String getDescription(Enchantment ench) {
		for(converter c : data) {
			if(c.getId().contentEquals(ench.getId())) return repPlaceHolders(ench,c.getDescription());
		}	
		return "";
	}
	
	private static String repPlaceHolders(Enchantment ench, String text) {
		return text.replace("<type>", ench.getType()).replace("<value>", ench.getValue()).replace("<bonus>", ench.getBonus());
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
		
		public String getId() {return id;}
		public String getName() {return name;}
		public String getDescription() {return description;}

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
