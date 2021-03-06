package resource;

import classes.*;

import java.util.ArrayList;
import java.util.List;

public class Compendium {
	
	private static final String SITE = "https://ddocompendium.com";
	
	public static Item getItem(String name) {
		Item ret = new Item();
		ret.name = name;
		
		String displayHTML = util.getContents(SITE + "/w/" + name.replace(' ', '_'));
		String editContents = getEditContents(util.getContents(SITE + "/index.php?title=" + name.replace(' ', '_') + "&action=edit"));
		
		//Set up variables if needed
		if(editContents.contains("Template:Armor") || editContents.contains("Template:Shield")) ret.armor = new Item.Armor();
		if(editContents.contains("Template:Weapon") || editContents.contains("Template:Shield")) ret.weapon = new Item.Weapon();
		
		
		for(String[] a : getItemVariables(editContents)) {
			String i = a[1].replace(" ", "");
			switch(a[0].toLowerCase()) { //TODO sort these cases
			case "minlevel": ret.minLevel = Integer.parseInt(i); break;
			case "hardness": ret.hardness = Integer.parseInt(i); break;
			case "durability": ret.durability = Integer.parseInt(i); break;
			case "material": ret.material = a[1]; break;
			case "description": ret.description = a[1]; break;
			case "enchantments": ret.attributes = parseAttributes(a[1]); break;
			case "weight": ret.weight = Double.parseDouble(i); break;
			//Weapon
			case "damage": ret.weapon.attackRoll = new Dice(util.parseTemplate(a[1], false)); break;
			case "damagetype": ret.weapon.damageTypes = util.parseTemplate(a[1],false); break;
			//Armor
			case "armorcheckpenalty": ret.armor.armorCheckPenalty = Integer.parseInt(i); break;
			case "spellfailure": ret.armor.spellFailure = Integer.parseInt(i.replace("%", "")); break;
			case "maxdex": ret.armor.maxDexBonus = Integer.parseInt(i); break;
			case "armorbonus": ret.armor.armorBonus = Integer.parseInt(i); break;
			case "attackpenalty": ret.armor.attackPenalty = Integer.parseInt(i); break;
			case "dr": ret.armor.damageReduction = Integer.parseInt(i); break;
			case "shiedbonus": ret.armor.shieldBonus = Integer.parseInt(i); break;
			//TODO add the rest of the variables
			default: System.out.println(a[0] + " is empty");
			}
		}
		
		
		return ret;
	}
	
	private static String getEditContents(String HTML) { //TODO (also remove anything that's not part of the actual content!!!!)
		final String START = "name=\"wpTextbox1\">", END = "</textarea>";
		return HTML.substring(HTML.indexOf(START) + START.length(), HTML.indexOf(END));
	}
	
	private static List<String[]> getItemVariables(String editContents) {
		List<String[]> ret = new ArrayList<String[]>();
		
		String temp = "";
		int index = 0;
		for(char c : editContents.toCharArray()) {
			if(c == '|' && index == 2) {
				if(temp.contains("=")) {
					String name = temp.substring(0,temp.indexOf('='));
					String data = temp.substring(temp.indexOf('=') + 1);
					if(temp.contains(" =")) name = name.substring(0,name.length() - 1);
					if(temp.contains("= ")) data = data.substring(1);
					System.out.println(name + " " + data);
					ret.add(new String[] {name, data});
				}
				temp = "";
			} else temp+=c;
			if(c == '{') index++;
			if(c == '}') index--;
		}
		return ret;
	}

	private static List<Attribute> parseAttributes(String attributes) {
		List<Attribute> ret = new ArrayList<Attribute>();
		
		int depth = 0;
		String temp = "";
		for(char c : attributes.toCharArray()) {
			if(c == '{') {
				depth++;
				if(depth > 2) temp+=c;
			}
			else if(c == '}') {
				depth--;
				if(depth == 1) {
					ret.addAll(utilComp.parseAttribute(temp));
					temp = "";
				} else if(depth > 1) temp+=c;
			} else if(depth >= 2) temp+=c;
		}
		
		return ret;
	}
	
	
	
	private static String inLineTemplates(String template) {
		//TODO add in-line template writing
		String ret = "";
		return ret;
	}
}
