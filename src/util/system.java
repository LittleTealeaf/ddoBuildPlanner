package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class system {

    public static Gson staticJSON;
    public static Gson objectJSON;
    public static File settings;
    public static File inventory;
    public static File enchantments;
    public static File setBonuses;
    private static AppDirs appDirs;

    // TODO add save/load file methods

    public static void loadData() {
        appDirs = AppDirsFactory.getInstance();

        staticJSON = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT).setPrettyPrinting().create();
        objectJSON = new GsonBuilder().setPrettyPrinting().create();

        settings = getAppFile("Settings.json");
        inventory = getAppFile("Inventory.json");
        enchantments = getAppFile(true, "data", "Enchantments.json");
        setBonuses = getAppFile(true, "data", "SetBonuses.json");
    }

    public static void createFileDirs(String... path) {
        File f = getAppFile(path);
        f.getParentFile().mkdirs();
    }

    public static File getAppFile(String... path) {
        return getAppFile(false, path);
    }

    public static File getAppFile(boolean create, String... path) {
        File f = new File(java.nio.file.Paths.get(dataDir(), path).toString());
        f.getParentFile().mkdirs();
        return f;
    }

    private static String dataDir() {
        return appDirs.getUserDataDir("DDO Build Planner", "", "Tealeaf", true);
    }

    public static void openExtFile(File file) {

        try {
            Desktop.getDesktop().open(file);
        } catch (Exception ignored) {
        }

    }

    public static void writeFile(File file, String data) {

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.close();
        } catch (Exception ignored) {
        }

    }
}
