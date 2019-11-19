package fxTabs;

import classes.Build;
import classes.Stat;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;

public class Stats {
	public static Tab getTab() {
		Tab ret = new Tab("Stats");
		
		String text = "";
		
		for(Stat s : statCalc.getStats(Build.getAllAttributes())) {
			text+="\n" + s;
		}
		ret.setContent(new Text(text));
		
		return ret;
	}
}
