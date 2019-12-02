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

@SuppressWarnings("unused")
public class Settings {

	private static String version;

	public Settings() {
		// Variable Names are what dictate what the JSON file reads
		Display = new display();
		Saving = new saving();
		Advanced = new advanced();
		
		version = Main.version;
	}

	private static display Display;

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

	private static saving Saving;
	
	public static class saving {
		public saving() {}

		public static double inactivityTime;
		public static double periodicalTime;
	}
	
	private static advanced Advanced;
	
	public static class advanced {
		public advanced() {
			Debug = new debug();
		}
		
		public static debug Debug;
		public static class debug {
			public debug() {}
			
			public static boolean showCrashReports;
		}
	}

	public static void defaultSettings() {
		display.dice.showDice = true;
		display.dice.compactDice = false;
		display.dice.showRange = false;

		saving.inactivityTime = 100;
		saving.periodicalTime = 0;
		
		advanced.debug.showCrashReports = true; //TODO PRODUCTION: change to false
	}

	public static void loadSettings() {
		defaultSettings();
		if(Data.settings.exists()) {
			try {
				Data.staticJSON.fromJson(Files.newBufferedReader(Data.settings.toPath()), Settings.class);

				if(!version.contentEquals(Main.version)) saveSettings();
				trimSettings();

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

	private static void trimSettings() {
		saving.inactivityTime = Math.max(saving.inactivityTime, 0);
		saving.periodicalTime = Math.max(saving.periodicalTime, 0);
	}
}
