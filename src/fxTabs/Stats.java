package fxTabs;

import classes.Build;
import classes.Stat;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;

public class Stats {
	
	private static Text statText;
	
	public static Tab getTab() {
		Tab ret = new Tab("Stats");
		
		statText = new Text();
		
		ret.setContent(statText);
		
		return ret;
	}


	public static void updateStats() {
		String text = "";
		
		for(Stat s : statCalc.getStats(Build.getAllAttributes())) {
			text+="\n" + s;
		}
		statText.setText(text);
	}
}
