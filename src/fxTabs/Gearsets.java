package fxTabs;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;

public class Gearsets {
	
	private static Tab tab;

	public static Tab getTab() {
		
		final Insets DEFAULTINSETS = new Insets(10,10,10,10);
		tab = new Tab("Gearsets");
		
		return tab;
	}
}
