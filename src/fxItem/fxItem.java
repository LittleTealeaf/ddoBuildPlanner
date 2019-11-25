package fxItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import classes.Build;
import classes.DDOUtil;
import classes.Dice;
import classes.Item;
import classes.util;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class fxItem {

	public static Stage sItem;
	public static Item item;
	
	private static TabPane tabs;
	
	private static TextField name;
	public static ChoiceBox<String> type;
	
	public static void open() {
		open(null);
	}
	
	public static void open(Item i) {
		
		item = i;
		if(item == null) item = new Item();
		
		if(sItem != null && sItem.isShowing()) sItem.close();
		sItem = new Stage();
		sItem.setTitle("Edit Item");
		if(item == null) sItem.setTitle("Create Item");
		
		name = new TextField();
		name.setTooltip(new Tooltip("The Name of the Item, properly including the \"Legendary\" or \"Heroic\" words"));
		
		type = new ChoiceBox<String>(FXCollections.observableArrayList("Item","Weapon","Armor","Shield"));
		type.valueProperty().addListener((e, o, n) -> {
			if(item.weapon == null && (n.contentEquals("Weapon") || n.contentEquals("Shield"))) item.weapon = new Item.Weapon();
			if(item.armor == null && (n.contentEquals("Armor") || n.contentEquals("Shield"))) item.armor = new Item.Armor();
		});
		type.setValue("Item");
		
		Region regionA = new Region();
		HBox.setHgrow(regionA, Priority.ALWAYS);
		
		Button bCompendium = new Button("Import Compendium");
		bCompendium.setTooltip(new Tooltip("Imports the item to the best of the program's \nability from the DDO Compendium\ninto an item format"));
		bCompendium.setOnAction(event -> importCompendium());
		
		HBox hHeader = new HBox(name,type,regionA,bCompendium);
		hHeader.setSpacing(10);
		
		tabs = new TabPane();
		tabs.getTabs().setAll(getTabs());
		
		Button bClose = new Button("Close");
		bClose.setOnAction(e -> sItem.close());
		
		Region rFooter = new Region();
		HBox.setHgrow(rFooter, Priority.ALWAYS);
		
		Button bSave = new Button("Save");
		bSave.setOnAction(e -> saveItem());
		
		HBox hFooter = new HBox(bClose,rFooter,bSave);
		hFooter.setPadding(new Insets(5));
		
		BorderPane bp = new BorderPane();
		bp.setTop(hHeader);
		bp.setCenter(tabs);
		bp.setBottom(hFooter);
		
		Scene sc = new Scene(bp);
		sItem.setScene(sc);
		sItem.show();
	}

	
	private static List<Tab> getTabs() {
		return Arrays.asList(new Tab[] {tabGeneral.getTab(),tabEnchantments.getTab(),tabWeapon.getTab(),tabArmor.getTab()});
	}
	
	private static void saveItem() {
		tabGeneral.updateItem();
		item.weapon = tabWeapon.getWeapon();
		item.armor = tabArmor.getArmor();
		item.enchantments = tabEnchantments.getEnchantments();
	}
	
	public static Tab getTab(String title) {
		switch(title) {
		case "General": return tabs.getTabs().get(0);
		case "Enchantments": return tabs.getTabs().get(1);
		case "Weapon": return tabs.getTabs().get(2);
		case "Armor": return tabs.getTabs().get(3);
		default: return null;
		}
	}
	
	//Updates all fields
	private static void updateFields() {
		if(item == null) return;
		
		//Setting Type
		type.setValue("Item");
		if(item.armor != null) {

			if(item.weapon != null) type.setValue("Shield");
			else type.setValue("Armor");
		} else if(item.weapon != null) type.setValue("Weapon");
		
		tabs.getTabs().setAll(getTabs());
	}
	
	private static void importCompendium() {
		if(name.getText().contentEquals("")) return;
		
		Item res = resource.Compendium.getItem(name.getText());
		
		if(res != null) {
			
			item = res;
			updateFields();
		} else {
			
			//TODO Add Alert if importing from compendium fails
		}
	}
}
