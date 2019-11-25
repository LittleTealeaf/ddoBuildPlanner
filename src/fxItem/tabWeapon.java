package fxItem;

import java.util.function.Function;

import classes.DDOUtil;
import classes.Dice;
import classes.Item;
import classes.util;
import classes.Item.Armor;
import classes.Item.Weapon;
import javafx.geometry.Insets;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class tabWeapon {
	
	private static diceText die;
	private static Text baseDamage;
	private static Spinner<Integer> critRange;
	private static Spinner<Integer> critMultiplier;
	private static Text attackModifiers;
	private static Text damageModifiers;
	
	public static Tab getTab() {
		
		die = new diceText();
		
		baseDamage = new Text("Base Damage: ");
		
		Text tCritRange = new Text("Crit Range:");
		critRange = new Spinner<Integer>();
		critRange.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 20));
		critRange.setEditable(true);
		critRange.valueProperty().addListener(event -> updateBaseDamage());
		
		Text tCritMultiplier = new Text("Crit Multiplier:");
		critMultiplier = new Spinner<Integer>();
		critMultiplier.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,2));
		critMultiplier.setEditable(true);
		critMultiplier.valueProperty().addListener(event -> updateBaseDamage());
		
		Text tAttackModifiers = new Text("Attack Mods");
		tAttackModifiers.setOnMouseClicked(e -> updateAttackModifiers());
		attackModifiers = new Text("");
		attackModifiers.setOnMouseClicked(e -> updateAttackModifiers());
		
		Text tDamageModifiers = new Text("Damage Mods");
		tDamageModifiers.setOnMouseClicked(e -> updateDamageModifiers());
		damageModifiers = new Text("");
		damageModifiers.setOnMouseClicked(e -> updateDamageModifiers());
		
		GridPane leftGrid = new GridPane();
		leftGrid.setHgap(10);
		leftGrid.setVgap(10);
		leftGrid.add(tCritRange, 0, 0);
		leftGrid.add(critRange, 1, 0);
		leftGrid.add(tCritMultiplier, 0, 1);
		leftGrid.add(critMultiplier, 1, 1);
		leftGrid.add(tAttackModifiers, 0, 2);
		leftGrid.add(attackModifiers, 1, 2);
		leftGrid.add(tDamageModifiers, 0, 3);
		leftGrid.add(damageModifiers,1,3);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(die, 0, 0);
		grid.add(baseDamage, 1, 0);
		grid.add(leftGrid, 0, 1);
		
		
		if(fxItem.item.weapon != null) {
			
		}
		
		Tab r = new Tab("Weapon");
		r.setContent(grid);
		r.setClosable(false);
		r.disableProperty().bind(fxItem.type.valueProperty().isNotEqualTo("Weapon").and(fxItem.type.valueProperty().isNotEqualTo("Shield")));
		return r;
	}
	
	private static void updateAttackModifiers() {
		attackModifiers.setText(util.listToString(DDOUtil.modSelection(util.stringToList(attackModifiers.getText(), "\n"))));
	}
	
	private static void updateDamageModifiers() {
		damageModifiers.setText(util.listToString(DDOUtil.modSelection(util.stringToList(damageModifiers.getText(), "\n"))));
	}
	
	private static void updateBaseDamage() {
		baseDamage.setText(getWeapon().getBaseDamageDisplay());
	}
	
	public static Weapon getWeapon() {
		
		if(fxItem.getTab("Weapon").isDisabled()) return null;
		
		Weapon r = new Weapon();
		
		r.damage = die.getDice();
		r.critRange = critRange.getValue();
		r.critMultiplier = critMultiplier.getValue();
		
		r.attackModifiers = util.stringToList(attackModifiers.getText(), "\n");
		r.damageModifiers = util.stringToList(damageModifiers.getText(), "\n");
		
		return r;
	}
	
	private static class diceText extends Text {
		
		private Dice dice;
		
		public diceText() {
			super();
			super.setText("Damage: ");
			super.setOnMouseClicked(e -> {
				setDice(Dice.fxDice(dice));
				updateBaseDamage();
			});
			dice = null;
		}
		
		public Dice getDice() {
			return dice;
		}
		
		public void setDice(Dice d) {
			dice = d;
			super.setText("Damage: " + dice.toString());
		}
	}
}
