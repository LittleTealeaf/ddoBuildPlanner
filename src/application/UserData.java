package application;

import java.io.File;

import classes.Settings;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

public class UserData {
	
	private static AppDirs appDirs;
	
	public static File settings;
	
	public static void loadPaths() {
		appDirs = AppDirsFactory.getInstance();
		
		settings = getDataFile("settings.json");
	}
	
	public static File getDataFile(String name) {
		return new File(appDirs.getUserDataDir("DDO Build Planner", Settings.version, "Tealeaf",true) + "\\" + name);
	}
}
