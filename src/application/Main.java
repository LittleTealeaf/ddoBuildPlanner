package application;

import classes.Settings;
import interfaces.fxMain;

public class Main {
	
	public static final String version = "0.0.1";

	public static void main(String[] args) {
		
		Data.loadData();
		Settings.loadSettings();
		
		// Build.gearSets.add(testGear());
		// Build.setGearIndex(0);
		// Launch fxMain
		fxMain.open(args);
		
	}
}