package classes;

import java.util.Properties;

import com.google.gson.GsonBuilder;

public class Settings {

	
	public static boolean compactDice;
	
	public Settings() {}
	
	
	public static void defaultSettings() {
		compactDice = false;
	}
	
	public static void saveSettings() {
		System.out.println(System.getProperty("user.home"));
	}
	
	public static void loadSettings() {
		
	}
	
	public static String toJSON() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		
		//Enables saving of static variables
		gsonBuilder.excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT);
		
		return gsonBuilder.create().toJson(new Settings());
	}
}
