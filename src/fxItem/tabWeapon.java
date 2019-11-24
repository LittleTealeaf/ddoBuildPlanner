package fxItem;

import java.util.function.Function;

import classes.DDOUtil;
import classes.Dice;
import classes.Item;
import classes.util;
import javafx.geometry.Insets;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class tabWeapon {

	private static Item item;
	
	public static Tab getTab() {
		item = fxItem.item;
		
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
		r.disableProperty().bind(fxItem.type.valueProperty().isNotEqualTo("Weapon").and(fxItem.type.valueProperty().isNotEqualTo("Shield")));
		return r;
	}
}
