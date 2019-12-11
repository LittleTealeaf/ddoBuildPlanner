package classes;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
	
	public static Enchantment getEnchantment(String name) {
		for(Enchantment e : enchantments) if(e.getName().toLowerCase().contentEquals(name.toLowerCase())) return e;
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
		dialog.setResizable(true);
		
		TextField choice = new TextField();
		choice.setText((ench != null) ? ench.getEnchantment().getName() : "");
		
		ListView<String> suggestedInputs = new ListView<String>();
		suggestedInputs.setItems(FXCollections.observableArrayList(getNames()));
		suggestedInputs.setPrefHeight(100);
		suggestedInputs.setOnMouseClicked(click -> {
			if(click.getClickCount() == 2) choice.setText(suggestedInputs.getSelectionModel().getSelectedItem());
		});
		suggestedInputs.visibleProperty().bind(choice.focusedProperty());
		
		
		choice.textProperty().addListener((e,o,n) -> {
			suggestedInputs.setItems(FXCollections.observableArrayList(getNames(n)));
			suggestedInputs.getSelectionModel().clearAndSelect(0);
		});
		choice.setOnKeyPressed(key -> {
			switch(key.getCode()) {
			case DOWN:
				suggestedInputs.getSelectionModel().selectNext();				
				break;
			case UP:
				suggestedInputs.getSelectionModel().selectPrevious();
				choice.selectEnd();
				break;
			case ENTER:
				if(suggestedInputs.getSelectionModel().getSelectedItem() != null) choice.setText(suggestedInputs.getSelectionModel().getSelectedItem());
				break;
			default: break;
			}
		});
		
		VBox choiceBox = new VBox(choice,suggestedInputs);

		TextField bonus = new TextField();
		bonus.setText((ench != null) ? ench.getBonus() : "");
		
		Spinner<Double> value = new Spinner<Double>();
		value.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-1000, 1000, (ench != null) ? ench.getValue() : 0));
		
		GridPane otherFields = new GridPane();
		otherFields.setHgap(10);
		otherFields.setVgap(10);
		otherFields.add(new Text("Bonus Type"), 0, 0);
		otherFields.add(new Text("Value"), 0, 1);
		otherFields.add(bonus, 1, 0);
		otherFields.add(value, 1, 1);
		
		
		
		GridPane content = new GridPane();
		content.setHgap(10);
		content.add(choiceBox, 0, 0);
		content.add(otherFields, 1, 0);
		
		
		dialog.getDialogPane().setContent(content);
		
		dialog.show();
		
		return null;
	}
	
	public static List<String> getNames() {
		return getNames("");
	}
	
	public static List<String> getNames(String in) {
		List<String> r = new ArrayList<String>();
		for(Enchantment e : getEnchantments()) {
			if(in.contentEquals("") || e.getName().toLowerCase().contains(in.toLowerCase())) r.add(e.getName());
		}
		return r;
	}
}
