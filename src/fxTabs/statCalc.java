package fxTabs;

import java.util.ArrayList;
import java.util.List;
import classes.*;

public class statCalc {
	
	public static List<Stat> getStats(List<Attribute> attributes) {
		List<Stat> ret = new ArrayList<Stat>();
		
		for(Attribute a : attributes) {
			boolean exists = false;
			for(Stat s : ret) {
				if(s.name.contentEquals(a.attribute)) {
					exists = true;
					s.addAttribute(a);
				}
			}
			if(!exists) {
				ret.add(new Stat(a.attribute,a));
			}
		}
		ret = updateStats(ret);
		return ret;
	}
	
	public static List<Stat> updateStats(List<Stat> stats) {		
		boolean repeat = false;
		
		final String type = " Calculation";
		
		do {
			repeat = false;
			List<Attribute> temp = new ArrayList<Attribute>();
			
			for(Stat s : stats) {
				switch(s.name.toLowerCase()) {
				//UNIVERSAL STATS
				case "well rounded":
					for(Attribute a : s.attributes) {
						temp.addAll(newAttributes(DDOUtil.abilities,a.value,a.type));
					}
					break;
				case "universal spell power": case "implement spell power":
					for(String t : DDOUtil.spellTypes) {
						temp.add(new Attribute(t + " Spell Power",s.getTotal(),s.name.replace(" Spell Power","")));
					}
					break;
				case "universal spell lore":
					for(String t : DDOUtil.spellTypes) {
						temp.add(new Attribute(t + " Spell Lore",s.getTotal(),"Universal"));
					}
					break;
					//ABILITY TO SKILL
				case "strength": temp.addAll(newAttributes(new String[] {"Jump","Swim"},DDOUtil.getMod(s.getTotal()),"Strength")); break;
				case "dexterity": temp.addAll(newAttributes(new String[] {"Balance","Hide","Move Silently","Open Lock","Tumble","Reflex","Armor Class"},DDOUtil.getMod(s.getTotal()),"Dexterity")); break;
				case "constitution": temp.addAll(newAttributes(new String[] {"Fortitude","Concentration"},DDOUtil.getMod(s.getTotal()),"Constitution")); break;
				case "intelligence": temp.addAll(newAttributes(new String[] {"Spellcraft","Search","Disable Device","Repair"},DDOUtil.getMod(s.getTotal()),"Intelligence")); break;
				case "wisdom": temp.addAll(newAttributes(new String[] {"Will","Heal","Listen","Spot"},DDOUtil.getMod(s.getTotal()),"Intelligence")); break;
				case "charisma": temp.addAll(newAttributes(new String[] {"Bluff","Diplomacy","Haggle","Intimidate","Perform","Use Magical Device"},DDOUtil.getMod(s.getTotal()),"Intelligence")); break;
					//SKILLS
				case "spellcraft":
					temp.addAll(newAttributes(
						new String[] {"Acid Spell Power", "Cold Spell Power", "Electric Spell Power", "Fire Spell Power", "Force Spell Power", "Light Spell Power"},
						s.getTotal(),
						"Spellcraft")); break;
				case "heal": temp.addAll(newAttributes(new String[] {"Positive Spell Power","Negative Spell Power"},s.getTotal(),"Heal (Skill)")); break;
				case "perform": temp.add(new Attribute("Sonic Spell Power",s.getTotal(),"Perform")); break;
				case "repair": temp.addAll(newAttributes(new String[] {"Repair Spell Power","Rust Spell Power"},s.getTotal(),"Repair")); break;
				}
			}
			
			//Updates attributes, if there is no change in the attribute value then doesn't set repeat to true
			if(temp.size() > 0) {
				boolean exists = false;
				for(Attribute a : temp) {
					exists = false;
					for(Stat s : stats) if(a.attribute.contentEquals(s.name)) {
						repeat = repeat || s.addAttribute(a);
						exists = true;
					}
					if(!exists) {
						stats.add(new Stat(a.attribute,a));
						repeat = true;
					}
				}
			}
		} while(repeat == true);
		
		return stats;
	}
	
	private static List<Attribute> newAttributes(String[] attributes, double value, String type) {
		List<Attribute> ret = new ArrayList<Attribute>();
		for(String s : attributes) {
			ret.add(new Attribute(s,value,type));
		}
		return ret;
	}
}
