package fxTabs;

import java.util.Optional;

import classes.Build;
import classes.Build.Gear;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class Gearsets {
	
	public static Tab tab;
	
	public static Build.Gear gear;
	
	private static ChoiceBox<Build.Gear> gearChoice;
	private static Button bDelete;
	
	public static Tab getTab() {
		
		final Insets DEFAULTINSETS = new Insets(10,10,10,10);
		
		tab = new Tab("Gearsets");
		BorderPane content = new BorderPane();
		
		//Header: Gear Selection, Delete button, Create button
		
		gearChoice = new ChoiceBox<Build.Gear>(FXCollections.observableArrayList(Build.gearSets));
		gearChoice.setOnAction(event -> {
			gear = gearChoice.getValue();
		});
		
		Button bCreate = new Button("Create");
		bCreate.setOnAction(event -> {
			createGearset();
		});
		
		bDelete = new Button("Delete");
		bDelete.setOnAction(event -> {
			deleteGearset();
		});
		
		Region reg = new Region();
		reg.setPrefWidth(50);
		
		HBox hHeader = new HBox(gearChoice, bCreate, reg, bDelete);
		hHeader.setPadding(DEFAULTINSETS);
		hHeader.setSpacing(10);
		content.setTop(hHeader);
		
		tab.setContent(content);		
		return tab;
	}
	private static void updateGearList() {
		Build.Gear current = gearChoice.getValue();
		
		gearChoice.getItems().setAll(FXCollections.observableArrayList(Build.gearSets));
		
		if(gearChoice.getItems().contains(current)) gearChoice.setValue(current);
		else if(gearChoice.getItems().size() > 0) gearChoice.setValue(gearChoice.getItems().get(0));
		gear = gearChoice.getValue();
		bDelete.setDisable(gear == null);
	}
	
	private static void deleteGearset() {
		if(gear == null) return;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete " + gear.name + "?");
		alert.setHeaderText("Do you want to delete " + gear.name + "?");
		alert.setContentText("You will not be able to recover the gearset");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			Build.gearSets.remove(gear);
			updateGearList();
		}
	}
	
	private static void createGearset() {createGearset(false);}
	private static void createGearset(boolean copy) {
		//Using a custom dialog
		Dialog<Build.Gear> createDialog = new Dialog<>();
		createDialog.setTitle("Create Gearset");
		createDialog.setHeaderText("Create a new gearset, either a fresh plate \n or copied from the currently loaded set");
		
		Label label1 = new Label("Gearset Name");
		TextField textName = new TextField();
		CheckBox checkCopy;
		if(gear != null) {
			checkCopy = new CheckBox("Copy " + gear.name + "?");
			if(copy) checkCopy.setSelected(true);
		}
		else checkCopy = new CheckBox("Copy Gearset");
		checkCopy.setDisable(gear == null);
		
		GridPane grid = new GridPane();
		grid.add(label1, 1, 1);
		grid.add(textName, 2, 1);
		grid.add(checkCopy, 2, 2);
		grid.setHgap(10);
		grid.setVgap(10);
		createDialog.getDialogPane().setContent(grid);
		
		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		createDialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		
		createDialog.setResultConverter(r -> {
			if(r == buttonTypeOk) {
				Build.Gear ret = new Build.Gear();
				if(gear != null && checkCopy.isSelected()) ret = gear.clone();
				ret.name = textName.getText();
				return ret;
			}
			return null;
		});
		
		Optional<Build.Gear> newGear = createDialog.showAndWait();
		if(newGear.isPresent() && newGear.get() != null) {
			if(newGear.get().name.contentEquals("")) {
				createGearset(checkCopy.isSelected());
			} else {
				Build.gearSets.add(newGear.get());
				updateGearList();
				gearChoice.setValue(newGear.get());
			}
		}
		
	}
	
}
