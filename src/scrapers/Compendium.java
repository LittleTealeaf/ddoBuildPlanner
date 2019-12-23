package scrapers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import classes.Dice;
import classes.Images;
import classes.Item;
import util.internet;
import vars.Ability;

public class Compendium {

	public static Item scrapeItem(String itemName) {
		Item item = new Item();
		item.setName(itemName);

		String contents = internet.getContents(getURL(itemName));
		// System.out.println(contents);
		String iconXPath = "//*[@class=\"icon\"]/p/img/@src";
		String imageXPath = "//*[@class=\"item-image\"]/span/img/@src";

		item.setIconUUID(Images.saveImage("https://ddocompendium.com" + internet.getXPathContents(iconXPath, contents)));
		item.setImageUUID(Images.saveImage("https://ddocompendium.com" + internet.getXPathContents(imageXPath, contents)));

		// Get all the variables of an item
		List<String[]> vars = new ArrayList<String[]>();
		String temp = "";
		int index = 0;
		String xPath = "//*[@id=\"wpTextbox1\"]/text()";
		String editContents = internet.getXPathContents(xPath, getEditURL(itemName));

		for(char c : editContents.toCharArray()) {

			if(c == '|' && index == 2) {

				if(temp.contains("=")) {
					String name = temp.substring(0, temp.indexOf('='));
					String data = temp.substring(temp.indexOf('=') + 1);
					if(temp.contains(" =")) name = name.substring(0, name.length() - 1);
					if(temp.contains("= ")) data = data.substring(1);
					System.out.println(name + " " + data);
					vars.add(new String[] {name, data});
				}

				temp = "";
			} else temp += c;

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

				// case "enchantments": i.enchantments = parseEnchantments(a[1]); break;
				default: // System.out.println(a[0] + " is empty");
				}

			} catch(Exception e) {}

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
}
