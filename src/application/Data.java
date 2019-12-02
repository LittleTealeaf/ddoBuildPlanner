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

		staticJSON = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT).setPrettyPrinting().create();

		settings = getDataFile("settings.json");
	}

	public static File getDataFile(String name) {
		return new File(appDirs.getUserDataDir("DDO Build Planner", "", "Tealeaf", true) + "\\" + name);
	}
}
