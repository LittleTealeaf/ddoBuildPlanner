package application;

import classes.Images;
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
	
	public static void mainPostOpen() {
		Images.verifyImages();
	}
}