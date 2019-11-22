package application;

import classes.Build;
import classes.Dice;
import classes.Item;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
	private static ChoiceBox<String> type;
	
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
		tabs.getTabs().setAll(generalTab(),enchantmentsTab(),weaponTab(),armorTab());
		
		BorderPane bp = new BorderPane();
		bp.setTop(hHeader);
		bp.setCenter(tabs);
		
		Scene sc = new Scene(bp);
		sItem.setScene(sc);
		sItem.show();
	}
	
	private static Tab generalTab() {
		
		Tab r = new Tab("General");
		r.setClosable(false);
		return r;
	}
	
	private static Tab enchantmentsTab() {
		
		Tab r = new Tab("Enchantments");
		r.setClosable(false);
		return r;
	}
	
	private static Tab weaponTab() {
		
		Text tDamage = new Text("Damage:");
		tDamage.setOnMouseClicked(event -> {
			item.weapon.attackRoll = Dice.fxDice(item.weapon.attackRoll);
			tDamage.setText("Damage: " + item.weapon.attackRoll.toString());
		});
		
		if(item.weapon != null && item.weapon.attackRoll != null) tDamage.setText("Damage: " + item.weapon.attackRoll.toString());
		
		GridPane grid = new GridPane();
		grid.add(tDamage, 0, 0);
		
		Tab r = new Tab("Weapon");
		r.setContent(grid);
		r.setClosable(false);
		r.disableProperty().bind(type.valueProperty().isNotEqualTo("Weapon").and(type.valueProperty().isNotEqualTo("Shield")));
		return r;
	}
	
	private static Tab armorTab() {
		
		Tab r = new Tab("Armor");
		r.setClosable(false);
		r.disableProperty().bind(type.valueProperty().isNotEqualTo("Armor").and(type.valueProperty().isNotEqualTo("Shield")));
		return r;
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
		
		tabs.getTabs().setAll(generalTab(),enchantmentsTab(),weaponTab(),armorTab());
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
