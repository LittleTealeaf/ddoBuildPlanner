package classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import application.Data;
import application.Main;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

public class Settings {
	
	private static String version;
	
	private static display Display;
	
	
	public Settings() {
		Display = new display();
		version = Main.version;
	}
	
	public static class display {
		private static dice Dice;
		public display() {
			Dice = new dice();
		}
		
		public static class dice {
			public dice() {}
			
			public static boolean showDice;
			public static boolean showRange;
			
			public static boolean compactDice;
		}
	}

	
	
	public static void defaultSettings() {
		display.dice.showDice = true;
		display.dice.compactDice = false;
		display.dice.showRange = false;
	}
	
	public static void loadSettings() {
		defaultSettings();
		if(Data.settings.exists()) {
			try {
				
				Data.staticJSON.fromJson(Files.newBufferedReader(Data.settings.toPath()), Settings.class);
				
				if(!version.contentEquals(Main.version)) saveSettings();
				
			} catch(IOException e) {}
			
		} else {
			Data.settings.getParentFile().mkdirs();
			
			try {
				Data.settings.createNewFile();
			} catch(IOException e) {}
			
			saveSettings();
		}
	}
	
	public static void saveSettings() {
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
