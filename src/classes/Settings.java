package classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.google.gson.GsonBuilder;

import application.UserData;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

public class Settings {
	
	public static final String version = "0.0.1";
	
	public static boolean compactDice;
	
	public Settings() {}
	
	
	public static void defaultSettings() {
		compactDice = false;
	}
	
	public static void loadSettings() {
		if(UserData.settings.exists()) {
			
		} else {
			defaultSettings();
			UserData.settings.getParentFile().mkdirs();
			
			try {
				UserData.settings.createNewFile();
			} catch(IOException e) {}
			
			saveSettings();
		}
	}
	
	public static void saveSettings() {
		try {
			FileWriter writer = new FileWriter(UserData.settings);
			writer.write(toJSON());
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String toJSON() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		
		//Enables saving of static variables
		gsonBuilder.excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT);
		
		return gsonBuilder.create().toJson(new Settings());
	}
}
