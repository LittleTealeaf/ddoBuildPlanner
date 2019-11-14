package fxTabs;

import java.util.Optional;

import classes.*;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Gearsets {
	public static Tab getTab() {
		Tab ret = new Tab("Gearsets");
		BorderPane content = new BorderPane();
		
		ChoiceBox<Build.Gear> gearChoice = new ChoiceBox<Build.Gear>(FXCollections.observableArrayList(Build.gearSets));
		
		HBox hHeader = new HBox(gearChoice);
		hHeader.setSpacing(10);
		content.setTop(hHeader);
		
		ret.setContent(content);		
		return ret;
	}
}
