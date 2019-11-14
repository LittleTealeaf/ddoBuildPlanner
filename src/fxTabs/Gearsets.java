package fxTabs;

import java.util.Optional;

import classes.Build;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class Gearsets {
	
	public static Tab tab;
	
	private static ChoiceBox<Build.Gear> gearChoice;
	private static Button bDelete;
	
	public static Tab getTab() {
		
		final Insets DEFAULTINSETS = new Insets(10,10,10,10);
		
		tab = new Tab("Gearsets");
		BorderPane content = new BorderPane();
		
		//Header: Gear Selection, Delete button, Create button
		
		gearChoice = new ChoiceBox<Build.Gear>(FXCollections.observableArrayList(Build.gearSets));
		gearChoice.setOnAction(event -> {
			Build.currentGear = gearChoice.getValue();
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
		Build.currentGear = gearChoice.getValue();
		bDelete.setDisable(Build.currentGear == null);
	}
	
	private static void deleteGearset() {
		if(Build.currentGear == null) return;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete " + Build.currentGear.name + "?");
		alert.setHeaderText("Do you want to delete " + Build.currentGear.name + "?");
		alert.setContentText("You will not be able to recover the gearset");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			Build.gearSets.remove(Build.currentGear);
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
		if(Build.currentGear != null) {
			checkCopy = new CheckBox("Copy " + Build.currentGear.name + "?");
			if(copy) checkCopy.setSelected(true);
		}
		else checkCopy = new CheckBox("Copy Gearset");
		checkCopy.setDisable(Build.currentGear == null);
		
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
				if(Build.currentGear != null && checkCopy.isSelected()) ret = Build.currentGear.clone();
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