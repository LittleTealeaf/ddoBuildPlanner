package classes;

import application.Main;
import util.system;

import java.io.IOException;
import java.nio.file.Files;

@SuppressWarnings({"ALL", "ResultOfMethodCallIgnored"})
//TODO complete rewrite
public class Settings {

	private static String version;

	public Settings() {
		// Variable Names are what dictate what the JSON file reads
		Appearance = new appearance();
		Saving = new saving();
		Advanced = new advanced();
		Items = new items();
		Porting = new porting();

		version = Main.version;
	}

	private static appearance Appearance;

	public static class appearance {

		private static dice Dice;
		private static icon Icon;

		public appearance() {
			Dice = new dice();
			Icon = new icon();
		}

		public static class dice {

			public dice() {}

			public static boolean showDice;
			public static boolean showRange;

			public static boolean compactDice;
		}

		public static class icon {

			public icon() {}

			public static double size;
		}
	}

	private static saving Saving;

	public static class saving {

		public saving() {
			Images = new images();
		}

		public static double inactivityTime;
		public static double periodicalTime;

		private static images Images;

		public static class images {

			public images() {}

			public static boolean storeLocal;
		}
	}

	public static items Items;

	public static class items {

		public items() {}

		public static boolean warnOnDelete;
		public static boolean deleteImages;// TODO do i really need this?
	}

	private static porting Porting;

	public static class porting {

		public porting() {
			Exporting = new exporting();
		}

		private static exporting Exporting;

		public static class exporting {

			public exporting() {}

			public static boolean includeImages;
		}
	}

	private static advanced Advanced;

	public static class advanced {

		public advanced() {
			Debug = new debug();
		}

		private static debug Debug;

		public static class debug {

			public debug() {}

			public static boolean showCrashReports;
		}
	}

	public static void defaultSettings() {
		appearance.dice.showDice = true;
		appearance.dice.compactDice = false;
		appearance.dice.showRange = false;
		appearance.icon.size = 40;

		saving.inactivityTime = 100;
		saving.periodicalTime = 0;
		saving.images.storeLocal = true;

		items.warnOnDelete = true;
		items.deleteImages = true;

		porting.exporting.includeImages = true;

		advanced.debug.showCrashReports = true; // TODO PRODUCTION: change to false
	}

	public static void loadSettings() {
		defaultSettings();

		if(system.settings.exists()) {

			try {
				system.staticJSON.fromJson(Files.newBufferedReader(system.settings.toPath()), Settings.class);

				if (!version.contentEquals(Main.version)) saveSettings();
				trimSettings();
			} catch (IOException ignored) {
			}

		} else {
			system.settings.getParentFile().mkdirs();

			try {
				system.settings.createNewFile();
			} catch (IOException ignored) {
			}

		}

		saveSettings();
	}

	public static void saveSettings() {
		system.writeFile(system.settings, system.staticJSON.toJson(new Settings()));
		System.out.println("Saved Settings to: " + system.settings.getPath());
	}

	private static void trimSettings() {
		saving.inactivityTime = Math.max(saving.inactivityTime, 0);
		saving.periodicalTime = Math.max(saving.periodicalTime, 0);
	}
}
