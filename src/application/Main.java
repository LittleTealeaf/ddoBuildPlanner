package application;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import classes.Settings;
import debug.Debug;
import interfaces.fxMain;

public class Main {

	public static final String version = "0.0.1";

	public static void main(String[] args) {

		Debug.setCrashReporting();
		Data.loadData();
		Settings.loadSettings();

		// Build.gearSets.add(testGear());
		// Build.setGearIndex(0);
		// Launch fxMain

		fxMain.open(args);

	}
}