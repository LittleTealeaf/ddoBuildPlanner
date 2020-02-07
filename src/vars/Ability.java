package vars;

import java.util.ArrayList;
import java.util.List;

public enum Ability {

    STRENGTH("Strength"),
    DEXTERITY("Dexterity"),
    CONSTITUTION("Constitution"),
    INTELLIGENCE("Intelligence"),
    WISDOM("Wisdom"),
    CHARISMA("Charisma");

    private final String displayName;

    Ability(String displayName) {
        this.displayName = displayName;
    }

    public static List<Ability> parseAbilities(String input) {
        String formatIn = input.replace("{", "").replace("}", "").replace("WeaponMod", "").toLowerCase();
        formatIn = formatIn.replace("strength", "str").replace("dexterity", "dex").replace("constitution", "con");
        formatIn = formatIn.replace("intelligence", "int").replace("wisdom", "wis").replace("charisma", "cha");

        List<Ability> r = new ArrayList<>();
        if (formatIn.contains("str")) r.add(STRENGTH);
        if (formatIn.contains("dex")) r.add(DEXTERITY);
        if (formatIn.contains("con")) r.add(CONSTITUTION);
        if (formatIn.contains("int")) r.add(INTELLIGENCE);
        if (formatIn.contains("wis")) r.add(WISDOM);
        if (formatIn.contains("cha")) r.add(CHARISMA);
        return r;
    }

    public String displayName() {
        return displayName;
    }

    public String toString() {
        return displayName;
    }
}