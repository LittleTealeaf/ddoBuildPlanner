package application;

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
		
		Text tDamageRating = new Text("Base Damage:");
		Function<String,String> updateBaseDamage = str -> {
			try {
				tDamageRating.setText("Base Damage: " + item.weapon.getBaseDamageDisplay());
			} catch(Exception e) {}
			return null;
		};
		
		Text tDamage = new Text("Damage:");
		tDamage.setOnMouseClicked(event -> {
			item.weapon.attackRoll = Dice.fxDice(item.weapon.attackRoll);
			tDamage.setText("Damage: " + item.weapon.attackRoll.toString());
			updateBaseDamage.apply(null);
		});
		
		Text tCritRange = new Text("Crit Range:");
		
		Spinner<Integer> sCritRange = new Spinner<Integer>();
		sCritRange.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 20));
		sCritRange.valueProperty().addListener((obs,o,n) -> {
			item.weapon.critRange = n;
			updateBaseDamage.apply(null);
		});
		
		Text tCritMultiplier = new Text("Crit Multiplier:");
		
		Spinner<Integer> sCritMultiplier = new Spinner<Integer>();
		sCritMultiplier.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,2));
		sCritMultiplier.valueProperty().addListener((obs,o,n) -> {
			item.weapon.critMultiplier = n;
			updateBaseDamage.apply(null);
		});
		
		Text tAttackMods = new Text("");
		tAttackMods.setOnMouseClicked(event -> {
			item.weapon.attackModifiers = DDOUtil.modSelection(item.weapon.attackModifiers);
			tAttackMods.setText(util.listToString(item.weapon.attackModifiers));
		});
		
		Text lAttackMods = new Text("Attck Modifiers:");
		lAttackMods.setOnMouseClicked(event -> {
			item.weapon.attackModifiers = DDOUtil.modSelection(item.weapon.attackModifiers);
			tAttackMods.setText(util.listToString(item.weapon.attackModifiers));
		});
		
		Text tDamageMods = new Text("");
		tDamageMods.setOnMouseClicked(event -> {
			item.weapon.damageModifiers = DDOUtil.modSelection(item.weapon.damageModifiers);
			tDamageMods.setText(util.listToString(item.weapon.damageModifiers));
		});
		
		Text lDamageMods = new Text("Damage Modifiers:");
		lDamageMods.setOnMouseClicked(event -> {
			item.weapon.damageModifiers = DDOUtil.modSelection(item.weapon.damageModifiers);
			tDamageMods.setText(util.listToString(item.weapon.damageModifiers));
		});
		
		
		GridPane statGrid = new GridPane();
		statGrid.add(tCritRange, 0, 0);
		statGrid.add(sCritRange, 1, 0);
		statGrid.add(tCritMultiplier, 0, 1);
		statGrid.add(sCritMultiplier, 1, 1);
		statGrid.add(lAttackMods, 0, 2);
		statGrid.add(tAttackMods, 1, 2);
		statGrid.add(lDamageMods, 0, 3);
		statGrid.add(tDamageMods, 1, 3);
		statGrid.setHgap(10);
		statGrid.setVgap(10);
		
		TextArea damageTypes = new TextArea();
		damageTypes.textProperty().addListener((obs,o,n) -> {
			item.weapon.damageTypes = util.stringToList(n, "\n");
		});
		damageTypes.setWrapText(true);
		damageTypes.setPrefWidth(80);
		
		GridPane grid = new GridPane();
		grid.add(tDamage, 0, 0);
		grid.add(tDamageRating, 1, 0);
		grid.add(statGrid, 0, 1);
		grid.add(damageTypes, 1, 1);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));
		
		if(item.weapon != null) {
			if(item.weapon.attackRoll != null) tDamage.setText("Damage: " + item.weapon.attackRoll.toString());
			updateBaseDamage.apply(null);
			if(item.weapon.damageTypes != null) damageTypes.setText(util.listToString(item.weapon.damageTypes));
			if(item.weapon.attackModifiers != null) tAttackMods.setText(util.listToString(item.weapon.attackModifiers));
			if(item.weapon.damageModifiers != null) tDamageMods.setText(util.listToString(item.weapon.damageModifiers));
		}
		
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
