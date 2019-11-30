package application;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import classes.Settings;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

public class Data {
	
	private static AppDirs appDirs;
	
	public static Gson staticJSON;
	
	public static File settings;
	
	public static void loadData() {
		appDirs = AppDirsFactory.getInstance();
		
		GsonBuilder b = new GsonBuilder();
		b.excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT);
		staticJSON = b.create();
		
		settings = getDataFile("settings.json");
	}
	
	public static File getDataFile(String name) {
		return new File(appDirs.getUserDataDir("DDO Build Planner", Settings.version, "Tealeaf",true) + "\\" + name);
	}
}
