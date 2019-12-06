package classes;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import application.Main;
import util.system;

@SuppressWarnings("unused")
public class Settings {

	private static String version;

	public Settings() {
		// Variable Names are what dictate what the JSON file reads
		Display = new display();
		Saving = new saving();
		Advanced = new advanced();
		Items = new items();
		Images = new images();

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

	public static images Images;

	public static class images {
		public images() {}

		public static boolean storeLocal;
	}

	public static items Items;

	public static class items {
		public items() {}

		public static boolean warnOnDelete;
		public static boolean deleteImages;
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

		images.storeLocal = true;

		items.warnOnDelete = true;
		items.deleteImages = true;

		advanced.debug.showCrashReports = true; // TODO PRODUCTION: change to false
	}

	public static void loadSettings() {
		defaultSettings();
		if(system.settings.exists()) {
			try {
				system.staticJSON.fromJson(Files.newBufferedReader(system.settings.toPath()), Settings.class);

				if(!version.contentEquals(Main.version)) saveSettings();
				trimSettings();

			} catch(IOException e) {}

		} else {
			system.settings.getParentFile().mkdirs();

			try {
				system.settings.createNewFile();
			} catch(IOException e) {}

			saveSettings();
		}
	}

	public static void saveSettings() {
		try {
			FileWriter writer = new FileWriter(system.settings);
			writer.write(system.staticJSON.toJson(new Settings()));
			writer.close();
			System.out.println("Saved Settings to: " + system.settings.getPath());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void trimSettings() {
		saving.inactivityTime = Math.max(saving.inactivityTime, 0);
		saving.periodicalTime = Math.max(saving.periodicalTime, 0);
	}
}
