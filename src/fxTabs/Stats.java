package fxTabs;

import javafx.scene.control.Tab;
import javafx.scene.text.Text;

public class Stats {

	public static Tab getTab() {
        Tab ret = new Tab("Stats");

        Text statText = new Text();

        ret.setContent(statText);
        return ret;
    }
}
