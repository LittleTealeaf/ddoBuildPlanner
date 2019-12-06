package application;

import java.util.Arrays;

import classes.Dice;
import classes.Enchantment;
import classes.Gearset;
import classes.Images;
import classes.Item;
import classes.Items;
import classes.Settings;
import debug.Debug;
import interfaces.fxMain;
import util.system;

public class Main {

	public static final String version = "0.0.1";

	public static void main(String[] args) {

		Debug.setCrashReporting();
		system.loadData();
		Settings.loadSettings();
		Images.load();

		fxMain.open(args);

	}
}