package resource;

import java.util.ArrayList;
import java.util.List;

import classes.Attribute;

public class utilComp {
	public static List<Attribute> parseAttribute(String template) {
		List<Attribute> r = new ArrayList<Attribute>();
		Attribute ret = new Attribute();
		
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
		
		//The great big switch case
		switch(v[0]) {
		case "Ability": case "Skill":
			ret.attribute = v[1];
			ret.value = Integer.parseInt(v[2]);
			if(v.length >= 4) ret.type = v[3];
			if(v.length >= 5) ret.name = v[4];
			break;
		case "SpellPower": case "SpellLore": 
			ret.attribute =  util.nameConversion(v[1]) + " Spell " + v[0].substring(5);
			ret.value = Integer.parseInt(v[2]);
			if(v.length >= 4) ret.type = v[3];
			break;
		case "SpellFocus":
			ret.attribute = util.nameConversion(v[1]) + " Focus";
			ret.value = Integer.parseInt(v[2]);
			if(v.length >= 4) ret.type = v[3];
			break;
		case "AC":
			ret.attribute = "Armor Class";
			ret.value = Integer.parseInt(v[2]);
			if(v.length >= 4) ret.type = v[3] + " " + v[1];
			break;
		case "Augment":
			ret.attribute = "Augment";
			ret.type = "Green";
			break;
		case "FalseLife":
			ret.attribute = "Health";
			if(v.length >= 3) ret.type = v[2] + " "; else ret.type = "";
			ret.type+="False Life";
			switch(v[1].toLowerCase()) {
			case "lesser": ret.value = 5; break;
			case "improved": ret.value = 20; break;
			case "greater": ret.value = 30; break;
			case "superior": ret.value = 40; break;
			case "epic": ret.value = 45; break;
			default: ret.value = Integer.parseInt(v[3]); break;
			}
			break;
		case "Sheltering":
			int value = Integer.parseInt(v[1]);
			
			break;
		default:
			try {
				ret.attribute = getAttributeName(v[0]);
				ret.value = Integer.parseInt(v[1]);
				if(v.length > 2) ret.type = v[2];
			} catch (Exception e) {}
		}
		System.out.println(ret.toString());
		r.add(ret);
		return r;
	}
	
	private static String getAttributeName(String template) {
		switch(template) {
			default: 
				String r = "";
				for(char c : template.toCharArray()) {
					if(java.lang.Character.isUpperCase(c)) r += " ";
					r += c;
				}
				
				return r;
		}
	}
}
