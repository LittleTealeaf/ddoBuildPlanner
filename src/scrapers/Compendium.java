package scrapers;

import classes.*;
import util.internet;
import vars.Ability;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Compendium {

	public static Item scrapeItem(String itemName) {
		Item item = new Item();
		item.setName(itemName);

		String contents = internet.getContents(getURL(itemName));
		// System.out.println(contents);
		String iconXPath = "//*[@class=\"icon\"]/p/img/@src";
		String imageXPath = "//*[@class=\"item-image\"]/span/img/@src";

		item.setIconUUID(Images.saveImage("https://ddocompendium.com" + internet.getXPathContents(iconXPath, Objects.requireNonNull(contents))));
		item.setImageUUID(Images.saveImage("https://ddocompendium.com" + internet.getXPathContents(imageXPath, contents)));

		// Get all the variables of an item
		List<String[]> vars = new ArrayList<>();
		StringBuilder temp = new StringBuilder();
		int index = 0;
		String xPath = "//*[@id=\"wpTextbox1\"]/text()";
		String editContents = internet.getXPathContents(xPath, getEditURL(itemName));

		for (char c : editContents.toCharArray()) {

			if (c == '|' && index == 2) {

				if (temp.toString().contains("=")) {
					String name = temp.substring(0, temp.toString().indexOf('='));
					String data = temp.substring(temp.toString().indexOf('=') + 1);
					if (temp.toString().contains(" =")) name = name.substring(0, name.length() - 1);
					if (temp.toString().contains("= ")) data = data.substring(1);
					System.out.println(name + " " + data);
					vars.add(new String[]{name, data});
				}

				temp = new StringBuilder();
			} else temp.append(c);

			if(c == '{') index++;
			if(c == '}') index--;
		}

		// Parse Attributes
		for(String[] a : vars) {
			String i = a[1].replace(" ", "");
			String s = a[1].replace("\n", "");

			try {

				switch (a[0].toLowerCase()) { // TODO sort these cases
				case "minlevel":
					item.setMinLevel(Integer.parseInt(i));
					break;
				case "absoluteminlevel":
					if(!i.contentEquals("")) item.setAbsoluteMinLevel(Integer.parseInt(i));
					break;
				case "type":
					item.setType(s);
					break;
				case "proficiency":
					item.setProficiency(s);
					break;
				case "hardness":
					item.setHardness(Double.parseDouble(i));
					break;
				case "durability":
					item.setDurability(Double.parseDouble(i));
					break;
				case "material":
					item.setMaterial(s);
					break;
				case "description":
					item.setDescription(s);
					break;
				case "weight":
					item.setWeight(Double.parseDouble(i));
					break;
				// WEAPON
				case "damage":
					item.setDamage(new Dice(sUtil.parseTemplate(s, false)));
					break;
				case "damagetype":
					item.setDamageTypes(sUtil.parseTemplate(s, false));
					break;
				case "critprofile":
					List<String> t = sUtil.parseTemplate(s, false);
					item.setLowCritRoll(Integer.parseInt(t.get(0)));
					item.setCritMultiplier(Double.parseDouble(t.get(1)));
					break;
				case "attackmod":
					item.setAttackModifiers(Ability.parseAbilities(s));
					break;
				case "damagemod":
					item.setDamageModifiers(Ability.parseAbilities(s));
					break;

				// TODO add attack and damage modifiers to item
				// ARMOR
				case "armorcheckpenalty":
					item.setCheckPenalty(Integer.parseInt(i));
					break;
				case "spellfailure":
					item.setSpellFailure(Double.parseDouble(i.replace("%", "")));
					break;
					case "maxdex":
						item.setMaxDex(Integer.parseInt(i));
						break;
					case "armorbonus":
					case "shieldbonus":
						item.setArmorBonus(Integer.parseInt(i));
						break;
					case "attackpenalty":
						item.setAttackPenalty(Integer.parseInt(i));
						break;
					case "dr":
						item.setDamageReduction(Double.parseDouble(i));
						break;
					// TODO add the rest of the variables
					case "enchantments":
						item.setEnchantments(parseEnchantments(a[1]));
						break;
					default: // System.out.println(a[0] + " is empty");
				}

			} catch (Exception ignored) {
			}

		}

		return item;
	}

	private static URL getURL(String pageName) {

		try {
			return new URL("https://ddocompendium.com/w/" + internet.convertToURL(pageName).replace("+", "_"));
		} catch(MalformedURLException e) {
			return null;
		}

	}

	private static URL getEditURL(String pageName) {

		// https://ddocompendium.com/index.php?title=Legendary_Bracers_of_the_Fallen_Hero&action=edit
		try {
			return new URL("https://ddocompendium.com/index.php?title=" + internet.convertToURL(pageName).replace("+", "_") + "&action=edit");
		} catch(MalformedURLException e) {
			return null;
		}

	}

	private static List<Enchref> parseEnchantments(String input) {
		List<Enchref> r = new ArrayList<>();

		for (List<String> t : sUtil.parseTemplates(input)) {
			String[] a = new String[t.size()];
			for (int i = 0; i < t.size(); i++) a[i] = t.get(i);
			System.out.println(t);

			Enchref e = null;

			// Splits the string if there's upper case letters
			if (!a[0].contains(" ")) {
				String[] uppercase = a[0].split("(?=\\p{Upper})");
				a[0] = "";

				for (String s : uppercase) {
					if (!a[0].contentEquals("")) a[0] += " ";
					a[0] += s;
				}

			}

			System.out.println(a[0]);

			try {

				switch (a[0].toLowerCase()) {
					// Template Structure: [enchantment], [type], [value], [bonus]
					case "ability":
					case "skill":
						e = new Enchref("aa21c96d-3d82-4bfc-b604-fb70bf674fb6");
						if (e.getEnchantment() == null)
							e = new Enchref(Objects.requireNonNull(Enchantments.getEnchantmentName("Standard")));
						e.setType(a[1]);
						e.setValue(parseDouble(a[2]));
						if (a.length > 3) e.setBonus(a[3]);
						r.add(e);
						break;
					case "fortification":
						e = new Enchref("aa21c96d-3d82-4bfc-b604-fb70bf674fb6");
						if (e.getEnchantment() == null)
							e = new Enchref(Objects.requireNonNull(Enchantments.getEnchantmentName("Standard")));
						e.setType(a[0]);
						if (a.length > 1) e.setValue(parseDouble(a[1]));
						if (a.length > 2) e.setBonus(a[2]);
						r.add(e);
						break;
					case "augment":
					case "spell power":
					case "spell lore":
					case "spell focus":
						e = new Enchref(Objects.requireNonNull(Enchantments.getEnchantmentName(a[0])).getUUID());
						if (a.length > 1) e.setType(a[1]);
						if (a.length > 2) try {
							e.setValue(parseDouble(a[2]));
						} catch (Exception ignored) {
						}
						if (a.length > 3) e.setBonus(a[3]);
						r.add(e);
						break;
					case "deadly":
					case "accuracy":
					case "shatter":
					case "stunning":
						e = new Enchref(Objects.requireNonNull(Enchantments.getEnchantmentName(a[0])).getUUID());
						if (a.length > 1) e.setValue(parseDouble(a[1]));
						if (a.length > 2) e.setBonus(a[2]);
						r.add(e);
						break;
					// Custom Vars
					case "sheltering":
						e = new Enchref(Objects.requireNonNull(Enchantments.getEnchantmentName(a[0])).getUUID());
						if (a.length > 1) try {
							e.setValue(parseDouble(a[1]));
						} catch (Exception ignored) {
						}
						if (a.length > 2) e.setType(a[2]);
						if (a.length > 3) e.setBonus(a[3]);
						r.add(e);
					default:
				}

			} catch (Exception f) {
				f.printStackTrace();
			}

		}

		return r;
	}

	private static double parseDouble(String text) {
		return Double.parseDouble(text.replace(" ", ""));
	}
}
