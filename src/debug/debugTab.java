package debug;

import javafx.scene.control.Tab;

public class debugTab {

	private static Tab tab;
	
	public static Tab getTab() {
		tab = new Tab("Debug");
		
		return tab;
	}
}
