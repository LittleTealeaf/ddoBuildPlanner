package fxTabs;

import classes.Build;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Gearsets {
	
	public static Tab tab;
	
	public static Tab getTab() {
		
		final Insets DEFAULTINSETS = new Insets(10,10,10,10);
		
		tab = new Tab("Gearsets");
		BorderPane content = new BorderPane();
		
		ChoiceBox<Build.Gear> gearChoice = new ChoiceBox<Build.Gear>(FXCollections.observableArrayList(Build.gearSets));
		gearChoice.setOnAction(action -> {
			
		});
		
		HBox hHeader = new HBox(gearChoice);
		hHeader.setSpacing(10);
		content.setTop(hHeader);
		
		tab.setContent(content);		
		return tab;
	}
}
