package fxTabs;

import java.util.Optional;

import classes.Build;
import classes.Build.Gear;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Gearsets {
	
	public static Tab tab;
	
	private static ChoiceBox<Build.Gear> gearChoice;
	private static Button bDelete;
	private static Button bRename;
	
	public static Tab getTab() {
		
		final Insets DEFAULTINSETS = new Insets(10,10,10,10);
		
		tab = new Tab("Gearsets");
		BorderPane content = new BorderPane();
		
		//Header: Gear Selection, Delete button, Create button, Rename Button
		
		gearChoice = new ChoiceBox<Build.Gear>(FXCollections.observableArrayList(Build.gearSets));
		gearChoice.setValue(Build.getGear());
		gearChoice.setOnAction(event -> {
			Build.setGear(gearChoice.getValue());
			Stats.updateStats();
		});
		
		Button bCreate = new Button("Create");
		bCreate.setOnAction(event -> {
			createGearset();
		});
		
		bRename = new Button("Rename");
		bRename.setOnAction(event -> {
			renameGearset();
		});
		
		bDelete = new Button("Delete");
		bDelete.setOnAction(event -> {
			deleteGearset();
		});
		
		Region reg = new Region();
		reg.setPrefWidth(50);
		
		HBox hHeader = new HBox(gearChoice, bCreate, reg, bRename, bDelete);
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
		Build.setGear(gearChoice.getValue());
		bDelete.setDisable(Build.getGear() == null);
	}
	
	private static void deleteGearset() {
		if(Build.getGear() == null) return;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete " + Build.getGear().name + "?");
		alert.setHeaderText("Do you want to delete " + Build.getGear().name + "?");
		alert.setContentText("You will not be able to recover the gearset");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			Build.gearSets.remove(Build.getGear());
			updateGearList();
		}
	}
	
	private static void createGearset() {createGearset(false);}
	private static void createGearset(boolean copy) {createGearset(copy,"");}
	private static void createGearset(boolean copy, String error) {
		//Using a custom dialog
		Dialog<Build.Gear> createDialog = new Dialog<>();
		createDialog.setTitle("Create Gearset");
		createDialog.setHeaderText("Create a new gearset, either a fresh plate \n or copied from the currently loaded set");
		
		Label label1 = new Label("Gearset Name");
		TextField textName = new TextField();
		CheckBox checkCopy;
		if(Build.getGear() != null) {
			checkCopy = new CheckBox("Copy " + Build.getGear().name + "?");
			if(copy) checkCopy.setSelected(true);
		}
		else checkCopy = new CheckBox("Copy Gearset");
		checkCopy.setDisable(Build.getGear() == null);

		GridPane grid = new GridPane();
		grid.add(label1, 1, 1);
		grid.add(textName, 2, 1);
		grid.add(checkCopy, 2, 2);
		grid.setHgap(10);
		grid.setVgap(10);
		if(error.contentEquals("")) createDialog.getDialogPane().setContent(grid);
		else {
			Text errText = new Text(error);
			errText.setFill(Color.RED);
			
			BorderPane bp = new BorderPane();
			bp.setTop(errText);
			bp.setCenter(grid);
			
			createDialog.getDialogPane().setContent(bp);
		}
		
		
		ButtonType buttonTypeOk = new ButtonType("Create", ButtonData.OK_DONE);
		createDialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		
		createDialog.setResultConverter(r -> {
			if(r == buttonTypeOk) {
				Build.Gear ret = new Build.Gear();
				if(Build.getGear() != null && checkCopy.isSelected()) ret = Build.getGear().clone();
				ret.name = textName.getText();
				return ret;
			}
			return null;
		});
		
		Optional<Build.Gear> newGear = createDialog.showAndWait();
		if(newGear.isPresent() && newGear.get() != null) {
			if(newGear.get().name.contentEquals("")) {
				createGearset(checkCopy.isSelected(),"Please enter a name");
			} else if(!uniqueSetName(newGear.get().name)) {
				createGearset(checkCopy.isSelected(),"Name must be unique");
			} else {
				Build.gearSets.add(newGear.get());
				updateGearList();
				gearChoice.setValue(newGear.get());
			}
		}
	}
	
	private static final String NO_RESPONSE = "�";
	
	private static void renameGearset() { renameGearset(""); }
	private static void renameGearset(String error) {
		
		Dialog<String> renDialog = new Dialog<String>();
		renDialog.setTitle("Rename Gearset");
		renDialog.setHeaderText("Type the new name for the gearset \'" + Build.getGear().name + "\'");
		
		TextField newName = new TextField(Build.getGear().name);
		BorderPane content = new BorderPane();
		content.setCenter(newName);
		if(!error.contentEquals("")) {
			Text errText = new Text(error);
			errText.setFill(Color.RED);
			content.setTop(errText);
		}
		
		ButtonType buttonTypeOk = new ButtonType("Create", ButtonData.OK_DONE);
		renDialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		
		renDialog.getDialogPane().setContent(content);
		renDialog.setResultConverter(r -> {
			if(r != buttonTypeOk) return NO_RESPONSE;
			return newName.getText();
		});
		
		Optional<String> input = renDialog.showAndWait();
		if(input.isPresent()) {
			if(input.get().contentEquals(NO_RESPONSE)) return;
			else if(input.get().contentEquals("")) renameGearset("Please specify a new name");
			else if(input.get().contentEquals(Build.getGear().name)) renameGearset("Cannot be the same name");
			else if(!uniqueSetName(input.get())) renameGearset("Name is not unique");
			else {
				Build.getGear().name = input.get();
				updateGearList();
			}
		}
	}
	
	private static boolean uniqueSetName(String name) {
		for(Gear g : Build.gearSets) if(g.name.contentEquals(name)) return false;
		return true;
	}
}
