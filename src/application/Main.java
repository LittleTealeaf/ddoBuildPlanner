package application;

import java.util.Arrays;

import classes.Dice;
import classes.Enchantment;
import classes.Gearset;
import classes.Item;
import classes.Items;
import classes.Settings;
import debug.Debug;
import interfaces.fxMain;

public class Main {

	public static final String version = "0.0.1";

	public static void main(String[] args) {

		Debug.setCrashReporting();
		Data.loadData();
		Settings.loadSettings();

		Item a = new Item("Duality, the Moral Compass");
		a.setDamage(new Dice(7, 1, 6, 6, 15));
		a.setEquipSlots(Arrays.asList("Main Hand"));
		a.getEnchantments().add(new Enchantment("Enhancement Bonus Weapon","Enhancement","15"));

		Items.saveItem(a);

		fxMain.open(args);

	}
}