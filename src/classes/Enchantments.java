package classes;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import util.resource;
import util.system;

public class Enchantments {
	
	public Enchantments() {}
	
	private static List<Enchantment> enchantments;
	
	public static void load() {
		enchantments = new ArrayList<Enchantment>();
		
		try {
			if(system.enchantments.exists()) {
				system.staticJSON.fromJson(new FileReader(system.enchantments), Enchantments.class);
			} else {
				system.staticJSON.fromJson(resource.getBufferedReader("enchantments.json"), Enchantments.class);
				save();
			}
		} catch(Exception e) {}
	}
	
	public static void save() {
		try {
			FileWriter writer = new FileWriter(system.enchantments);
			writer.write(system.staticJSON.toJson(new Enchantments()));
			writer.close();
		} catch (Exception e) {}
	}
	
	public static List<Enchantment> getEnchantments() {
		return enchantments;
	}
	
	public static void addEnchantment(Enchantment enchantment) {
		if(!enchantments.contains(enchantment)) enchantments.add(enchantment);
	}
	
	public static void removeEnchantment(Enchantment enchantment) {
		if(enchantments.contains(enchantment)) enchantments.remove(enchantment);
	}
}
