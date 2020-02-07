package classes;

import util.resource;
import util.system;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SetBonuses {

	public SetBonuses() {}

	private static List<SetBonus> sets;

	public static void load() {
        sets = new ArrayList<>();

        try {

            if (system.setBonuses.exists()) {
                system.staticJSON.fromJson(new FileReader(system.setBonuses), SetBonuses.class);
            } else {
                system.staticJSON.fromJson(resource.getBufferedReader("enchantments.json"), SetBonuses.class);
                save();
            }

        } catch (Exception ignored) {
        }

    }

    public static List<SetBonus> getSets() {
        return sets;
    }

    public static void setSets(List<SetBonus> sets) {
        SetBonuses.sets = sets;
    }

    public static void save() {
        System.out.println("SAVED");
    }
}
