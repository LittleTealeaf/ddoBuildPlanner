package util;

import java.io.File;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

public class system {
	private static AppDirs appDirs;

	public static Gson staticJSON;
	public static Gson objectJSON;

	public static File settings;

	public static void loadData() {
		appDirs = AppDirsFactory.getInstance();

		staticJSON = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT).setPrettyPrinting().create();
		objectJSON = new GsonBuilder().setPrettyPrinting().create();

		settings = getAppFile("Settings.json");

	}

	public static File getAppFile(String name) {
		return new File(java.nio.file.Paths.get(dataDir(), name).toString());
	}

	public static File getAppFile(String[] path) {
		return new File(java.nio.file.Paths.get(dataDir(), path).toString());
	}

	private static String dataDir() {
		return appDirs.getUserDataDir("DDO Build Planner", "", "Tealeaf", true);
	}
}
