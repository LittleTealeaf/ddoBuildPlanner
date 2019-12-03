package application;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

public class Data {

	private static AppDirs appDirs;

	public static Gson staticJSON;
	public static Gson objectJSON;

	public static File settings;

	public static void loadData() {
		appDirs = AppDirsFactory.getInstance();

		staticJSON = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT).setPrettyPrinting().create();
		objectJSON = new GsonBuilder().setPrettyPrinting().create();

		settings = getDataFile("settings.json");
	}

	public static File getDataFile(String name) {
		return new File(appDirs.getUserDataDir("DDO Build Planner", "", "Tealeaf", true) + "\\" + name);
	}
}
