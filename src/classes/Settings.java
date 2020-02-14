package classes;

import application.Main;
import util.system;

import java.io.IOException;
import java.nio.file.Files;

public class Settings {

	public static String version = "0.0.1";

	public static void loadSettings() {

		if (system.settings.exists()) {

			try {
				system.staticJSON.fromJson(Files.newBufferedReader(system.settings.toPath()), Settings.class);

				if (!version.contentEquals(Main.version)) saveSettings();
				trimSettings();
			} catch (IOException ignored) {
			}

		} else {
			if (system.settings.getParentFile().mkdirs())
				System.out.println("Directory created: " + system.settings.getParentFile().getPath());

			try {
				if (system.settings.createNewFile()) System.out.println("Created File: " + system.settings.toPath());
			} catch (IOException ignored) {
			}

		}

		saveSettings();
	}

	public static class appearance {
		public static class dice {
			public static boolean showDice = true;
			public static boolean compactDice = true;
			public static boolean showRange = true;
		}

		public static class icon {
			public static double size = 40;
		}
	}

	public static class saving {
		public static double inactivityTime = 100;
		public static double periodicalTime = 0;

		public static class images {
			public static boolean storeLocal = true;
		}
	}

	public static class items {
		public static boolean warnOnDelete = true;
		public static boolean deleteImages = true;
	}

	public static class porting {
		public static class exporting {
			public static boolean includeImages = true;
		}
	}

	public static class advanced {
		public static class debug {
			public static boolean showCrashReports = true;
		}
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
