package interfaces;

import java.util.Arrays;
import java.util.List;

import classes.Dice;
import classes.Settings;
import classes.Settings.display;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class fxSettings {

	public static Stage stage;
	
	public static void open() {
		if(stage != null && stage.isShowing()) {
			stage.requestFocus();
			return;
		}
		
		stage = new Stage();
		stage.setTitle("Settings");
		stage.setOnCloseRequest(e -> Settings.saveSettings());
		
		TabPane tabs = new TabPane();
		tabs.setSide(Side.LEFT);
		
		tabs.getTabs().add(tabDisplay());
		
		Button bSave = new Button("Save");
		bSave.setOnAction(e -> {
			Settings.saveSettings();
			stage.close();
		});
		
		HBox bottom = new HBox(bSave);
		//TODO make this orient to the right
		bottom.setSpacing(10);
		bottom.setPadding(new Insets(0,10,10,0));
		
		BorderPane content = new BorderPane();
		content.setCenter(tabs);
		content.setBottom(bottom);
		
		stage.setScene(new Scene(content));
		stage.show();
	}
	
	private static Tab tabDisplay() {
		Tab r = new Tab("Display");
		r.setClosable(false);
		
		VBox content = new VBox();
		content.setSpacing(10);
		content.setPadding(new Insets(10));
		
		//DICE FORMAT
		Text diceDisplay = new Text(new Dice(3,4,5,6,7).toString());
		
		CheckBox cCompactDice = new CheckBox("Compact Dice Format");
		cCompactDice.setSelected(Settings.display.dice.compactDice);
		cCompactDice.selectedProperty().addListener(o -> {
			Settings.display.dice.compactDice = cCompactDice.isSelected();
			diceDisplay.setText(new Dice(3,4,5,6,7).toString());
		});
		
		content.getChildren().add(settingSection("Dice Format",Arrays.asList(cCompactDice),Arrays.asList(diceDisplay)));
		
		r.setContent(content);
		return r;
	}
	
	private static VBox settingSection(String name, List<Node> options, List<Node> display) {
		VBox r = new VBox();
		r.setSpacing(10);
		
		Text header = new Text(name);
		header.setFont(new Font(20));
		
		VBox lOptions = new VBox();
		lOptions.setSpacing(10);
		lOptions.getChildren().addAll(options);
		
		VBox lDisplay = new VBox();
		lDisplay.setSpacing(10);
		lDisplay.getChildren().addAll(display);
		
		HBox row = new HBox(lOptions,lDisplay);
		row.setSpacing(10);
		
		
		r.getChildren().addAll(header,row);
		
		return r;
	}
}
