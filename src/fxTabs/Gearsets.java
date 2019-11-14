package fxTabs;

import java.util.Optional;

import classes.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

public class Gearsets {
	public static Tab getTab() {
		Tab ret = new Tab("Gearsets");
		
		TabPane setTabs = new TabPane();
		
		for(Build.Gear gear : Build.build.gearSets) {
			Tab gsTab = new Tab(gear.name);
			gsTab.setOnCloseRequest(event -> {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText("This will delete this gearset");
				alert.setContentText("Are you ok with this?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					Build.build.removeGearSet(((Tab) event.getSource()).getText());					
				} else {
				    event.consume();
				}
			});
			
			setTabs.getTabs().add(gsTab);
		}
		
		ret.setContent(setTabs);
		
		return ret;
	}
}
