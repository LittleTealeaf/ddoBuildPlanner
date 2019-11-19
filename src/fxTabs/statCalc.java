package fxTabs;

import java.util.ArrayList;
import java.util.List;
import classes.*;

public class statCalc {
	
	public List<Stat> updateStats(List<Stat> stats) {		
		boolean repeat = true;
		
		final String type = " Calculation";
		
		do {
			List<Attribute> temp = new ArrayList<Attribute>();
			
			for(Stat s : stats) {
				switch(s.name.toLowerCase()) {
				case "intelligence":
					temp.add(new Attribute("Spellcraft",DDOUtil.getMod(s.getTotal()), s.name + type));
					break;
				}
			}
			
			if(temp.size() > 0) for(Stat s : stats) for(Attribute a : temp) {
				repeat = repeat || s.addAttribute(a);
			}
		} while(repeat == true);
		
		return stats;
	}
}
