package classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.gson.GsonBuilder;

import application.Data;
import application.Main;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

public class Settings {
	
	private static String version;
	
	//SETTINGS
	
	public static boolean compactDice;
	
	
	//SETTINGS METHODS
	
	public Settings() {}
	
	public static void defaultSettings() {
		compactDice = false;
	}
	
	public static void loadSettings() {
		if(Data.settings.exists()) {
			
			try {
				List<String> lines = Files.readAllLines(Data.settings.toPath());
				
				if(lines.size() == 0) return;
				String jsonString = "";
				for(String l : lines) jsonString += l;
				
				Data.staticJSON.fromJson(jsonString, Settings.class);

			} catch(IOException e) {}
			
		} else {
			defaultSettings();
			Data.settings.getParentFile().mkdirs();
			
			try {
				Data.settings.createNewFile();
			} catch(IOException e) {}
			
			saveSettings();
		}
	}
	
	public static void saveSettings() {
		version = Main.version;
		try {
			FileWriter writer = new FileWriter(Data.settings);
			writer.write(Data.staticJSON.toJson(new Settings()));
			writer.close();
			System.out.println("Saved Settings to: " + Data.settings.getPath());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
