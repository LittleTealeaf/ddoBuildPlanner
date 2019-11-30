package application;

import classes.Settings;
import interfaces.fxMain;

public class Main {

	public static void main(String[] args) {
		
		UserData.loadPaths();
		Settings.loadSettings();
		
		// Build.gearSets.add(testGear());
		// Build.setGearIndex(0);
		// Launch fxMain
		fxMain.open(args);
		
	}
}