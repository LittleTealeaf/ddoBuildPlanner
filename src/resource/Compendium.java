package resource;

import classes.*;
import classes.Item.Armor;
import classes.Item.Weapon;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

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
			switch(a[0].toLowerCase()) { //TODO sort these cases
			case "minlevel": ret.minLevel = Integer.parseInt(a[1].replace(" ","")); break;
			case "hardness": ret.hardness = Integer.parseInt(a[1].replace(" ", "")); break;
			case "durability": ret.durability = Integer.parseInt(a[1].replace(" ", "")); break;
			case "material": ret.material = a[1]; break;
			case "description": ret.description = a[1]; break;
			case "weight": ret.weight = Double.parseDouble(a[1].replace(" ", "")); break;
			case "damage": ret.weapon.attackRoll = new Dice(util.parseTemplate(a[1], false)); break;
			case "damagetype": ret.weapon.damageTypes = util.parseTemplate(a[1],false); break;
			//TODO add the rest of the variables
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
					System.out.println(name + "|" + data);
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
		
		return ret;
	}
	
	private static Attribute parseAttribute(String template) {
		Attribute ret = new Attribute();
		
		return ret;
	}
}
