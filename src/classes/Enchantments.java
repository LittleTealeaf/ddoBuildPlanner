package classes;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
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
	
	public static Enchantment getEnchantment(int id) {
		for(Enchantment e : enchantments) if(e.getId() == id) return e;
		return null;
	}
	
	public static void addEnchantment(Enchantment enchantment) {
		if(!enchantments.contains(enchantment)) enchantments.add(enchantment);
		save();
	}
	
	public static void removeEnchantment(int id) {
		removeEnchantment(getEnchantment(id));
		save();
	}
	
	public static void removeEnchantment(Enchantment enchantment) {
		if(enchantments.contains(enchantment)) enchantments.remove(enchantment);
		save();
	}
	
	public static void updateEnchantment(Enchantment enchantment) {
		for(Enchantment e : enchantments) if(e.getId() == enchantment.getId()) {
			e = enchantment;
			save();
			return;
		}
		addEnchantment(enchantment);
	}
	
	public static int getNewID() {
		List<Integer> takenID = new ArrayList<Integer>();
		
		for(Enchantment e : enchantments) takenID.add(e.getId());
		
		int i = 0;
		while(takenID.contains(i)) i++;
		
		return i;
	}
	
	public static Enchref enchrefDialog() {
		return enchrefDialog(null);
	}
	
	public static Enchref enchrefDialog(Enchref ench) {
		Dialog<Enchref> dialog = new Dialog<>();
		dialog.setTitle((ench == null) ? "Add Enchantment" : "Edit Enchantment");
		
		//TODO enchantment selection
		
		dialog.show();
		
		return null;
	}
}
