package application;

import classes.Build;
import classes.Enchantments;
import classes.Images;
import classes.Inventory;
import classes.SetBonuses;
import classes.Settings;
import debug.Debug;
import interfaces.fxMain;
import util.system;

public class Main {

	public static final String version = "0.0.1";

	public static Build loadedBuild;

	public static void main(String[] args) {
		loadedBuild = new Build();

		Debug.setCrashReporting();
		system.loadData();
		Settings.loadSettings();
		Images.load();
		Inventory.load();
		Enchantments.load();
		SetBonuses.load();

		fxMain.open(args);
	}
}