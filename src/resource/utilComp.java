package resource;

import java.util.ArrayList;
import java.util.List;

import classes.Attribute;
import classes.Item;

public class utilComp {
	public static Item.Enchantment parseEnchantment(String template) {
		Item.Enchantment r = new Item.Enchantment();
		Attribute att = new Attribute();
		
		//Parse Template
		List<String> vList = new ArrayList<String>();
		String tmp = "";
		int ind = 0;
		for(char c : template.toCharArray()) {
			if(c == '{') ind++;
			else if(c == '}') ind--;
			if(ind == 0 && c == '|') {
				vList.add(tmp);
				tmp = "";
			}
			else tmp+=c;
		}
		vList.add(tmp);
		
		String[] v = vList.toArray(String[]::new);
		
		//Name Changes
		switch(v[0]) {
		case "Deadly": v[0] = "Damage"; break;
		case "Accuracy": v[0] = "Attack"; break;
		case "HealingAmp": v[1] += " Healing Amplification"; break;
//		case "Ability":
//			if(v[1].contentEquals("Well Rounded")) {
//				for(String s : new String[] {"Strength","Dexterity","Constitution","Wisdom","Intelligence","Charisma"}) {
//					ret = new Attribute();
//					ret.attribute = s;
//					ret.value = Integer.parseInt(v[2]);
//					if(v.length > 3) ret.type = v[3];
//					r.add(ret);
//				}
//				return r;
//			}
//			break;
		}
		
		//The great big switch case
		switch(v[0]) {
		case "SpellPower": case "SpellLore": 
			att.attribute =  util.nameConversion(v[1]) + " Spell " + v[0].substring(5);
			att.value = Integer.parseInt(v[2]);
			if(v.length >= 4) att.type = v[3];
			break;
		case "SpellFocus":
			att.attribute = util.nameConversion(v[1]) + " Focus";
			if(v[1].contentEquals("Mastery")) att.attribute = "Spell Focus";
			att.value = Integer.parseInt(v[2]);
			if(v.length >= 4) att.type = v[3];
			break;
		case "AC":
			att.attribute = "Armor Class";
			att.value = Integer.parseInt(v[2]);
			if(v.length >= 4) att.type = v[3] + " " + v[1];
			else att.type = v[1];
			break;
		case "Augment":
			att.attribute = "Augment";
			att.stringValue = v[1];
			break;
		case "FalseLife":
			att.attribute = "Health";
			if(v.length >= 3) att.type = v[2] + " "; else att.type = "";
			att.type+="False Life";
			switch(v[1].toLowerCase()) {
			case "lesser": att.value = 5; break;
			case "improved": att.value = 20; break;
			case "greater": att.value = 30; break;
			case "superior": att.value = 40; break;
			case "epic": att.value = 45; break;
			default: att.value = Integer.parseInt(v[3]); break;
			}
			break;
		case "Sheltering":
			String type = "";
			if(v.length <= 2 || v[2].contentEquals("") || v[2].toLowerCase().contentEquals("both")) type = "";
			else type = v[2].toLowerCase();
			//TODO Enchantment title / description
			if(type.contentEquals("physical") || type.contentEquals("")) {
				att.value = Integer.parseInt(v[1]);
				if(v.length >= 4) att.type = v[3];
				if(v.length >= 5) r.name = v[4];
				att.attribute = "Physical Sheltering";
				if(v.length >= 6) r.description = att.getDescription() + v[5];
				r.attributes.add(att);
			}
			att = new Attribute();
			if(type.contentEquals("magical") || type.contentEquals("")) {
				att.value = Integer.parseInt(v[1]);
				if(v.length >= 4) att.type = v[3];
				if(v.length >= 5) r.name = v[4];
				att.attribute = "Magical Sheltering";
				if(v.length >= 6) r.description = att.getDescription() + v[5];
				r.attributes.add(att);
			}
			return r;
		case "Parrying":
			//TODO Add Enchantment Description
			r.attributes.add(new Attribute("Armor Class",Integer.parseInt(v[1]),"Insightful"));
			r.attributes.add(new Attribute("Reflex",Integer.parseInt(v[1]),"Insightful"));
			r.attributes.add(new Attribute("Fortitude",Integer.parseInt(v[1]),"Insightful"));
			r.attributes.add(new Attribute("Will",Integer.parseInt(v[1]),"Insightful"));
			return r;
		case "Incite": case "Diversion":
			double value = Integer.parseInt(v[1]);
			if(v[0].contentEquals("Diversion")) value*=-1;
			String attrType = "Competence";
			String sep = "";
			if(v.length > 2) sep = v[2];
			if(v.length > 3) attrType = v[3];
			if(sep.toLowerCase().contains("melee") || sep.toLowerCase().contentEquals("all") || sep.replace(" ", "").contentEquals("")) 
				r.attributes.add(new Attribute("Melee Threat Generation",value,attrType));
			if(sep.toLowerCase().contains("spell") || sep.toLowerCase().contentEquals("all")) 
				r.attributes.add(new Attribute("Spell Threat Generation",value,attrType));
			if(sep.toLowerCase().contains("ranged") || sep.toLowerCase().contentEquals("all")) 
				r.attributes.add(new Attribute("Ranged Threat Generation",value,attrType));
			r.name = attrType + " " + v[0] + " " + value + "%";
			return r;
		case "ShieldBash":
			att.attribute = "Shield Bash " + v[1];
			att.value = Integer.parseInt(v[2]);
			if(v.length > 3) att.type = v[3];
			break;
		case "ElementalAbsorb":
			att.attribute = v[1] + " Absorption";
			att.value = Integer.parseInt(v[2]);
			if(v.length > 3) att.type = v[3];
			break;
		case "SkillGroupBonus":
			String[] skills = null;
			switch(v[1].toLowerCase()) {
			case "alluring":
				skills = new String[] {"Bluff","Diplomacy","Haggle","Intimidate","Perform"};
				break;
			case "nimble":
				skills = new String[] {"Balance","Hide","Move Silently","Open Lock","Tumble"};
				break;
			}
			if(skills != null) for(String s : skills) {
				att = new Attribute();
				att.attribute = s;
				att.value = Integer.parseInt(v[2]);
				if(v.length > 3) att.type = v[3];
				r.attributes.add(att);
			}
			break;
		case "Immune":
			att.attribute = "Immune";
			att.stringValue = v[1];
		default:
			try {
				if(isInt(v[1].replace(" ", ""))) {
					att.attribute = getAttributeName(v[0]);
					att.value = Integer.parseInt(v[1]);
					if(v.length > 2) att.type = v[2];
				} else if(isInt(v[2].replace(" ", ""))) {
					att.attribute = getAttributeName(v[1]);
					att.value = Integer.parseInt(v[2]);
					if(v.length > 3) att.type = v[3];
				}
			} catch (Exception e) {
				
			}
			//Word Changes
		}
		r.attributes.add(att);
	
		return r;
	}
	
	/**
	 * Gets the attribute name in the Attribute form
	 * @param template
	 * @return
	 */
	private static String getAttributeName(String template) {
		switch(template) {
		case "Spellsight": return "Spellcraft";
			default: 
				String r = "";
				for(char c : template.toCharArray()) {
					if(java.lang.Character.isUpperCase(c)) r += " ";
					r += c;
				}
				//Remove extra space
				r = r.substring(1).replace("  ", " ");
				return r;
		}
	}
	
	private static boolean isInt(String a) {
		try {
			Integer.parseInt(a);
			return true;
		} catch (Exception e) {return false;}
	}
}
