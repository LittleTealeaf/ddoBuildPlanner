package interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import classes.Dice;
import classes.Settings;
import classes.Settings.display;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldListCell;
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
import javafx.util.Callback;

public class fxSettings {

	public static Stage stage;
	
	private static BorderPane content;
	
	public static void open() {
		if(stage != null && stage.isShowing()) {
			stage.requestFocus();
			return;
		}
		
		stage = new Stage();
		stage.setTitle("Settings");
		stage.setOnCloseRequest(e -> Settings.saveSettings());
				
		
		ObservableList<settingsPage> pages = FXCollections.observableArrayList();
		pages.addAll(pageDisplay(),pageSaving());
		
		//http://www.java2s.com/Code/Java/JavaFX/ListViewselectionlistener.htm
		ListView<settingsPage> pageSelection = new ListView<settingsPage>(pages);
		pageSelection.setPrefWidth(100);
		pageSelection.getSelectionModel().selectedItemProperty().addListener((e,o,n) -> content.setCenter(n));
		pageSelection.setCellFactory(new Callback<ListView<settingsPage>, ListCell<settingsPage>>() {
          public ListCell<settingsPage> call(ListView<settingsPage> param) {
            final Label leadLbl = new Label();
            final Tooltip tooltip = new Tooltip();
            final ListCell<settingsPage> cell = new ListCell<settingsPage>() {
              @Override
              public void updateItem(settingsPage item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                  leadLbl.setText(item.getName());
                  setText(item.getName());
                  tooltip.setText(item.getName());
                  setTooltip(tooltip);
                }
              }
            };
            return cell;
          }
        });
		
		
		
		Button bSave = new Button("Save");
		bSave.setOnAction(e -> {
			Settings.saveSettings();
			stage.close();
		});
		
		HBox bottom = new HBox(bSave);
		bottom.setSpacing(10);
		bottom.setPadding(new Insets(10));
		
		content = new BorderPane();
		content.setLeft(pageSelection);
		content.setCenter(pageDisplay());
		content.setBottom(bottom);
		
		pageSelection.getSelectionModel().select(0);
		
		Scene scene = new Scene(content);
		//scene.getStylesheets().add(ClassLoader.getSystemResource("listStyle.css").toExternalForm());
		
		stage.setScene(scene);
		stage.show();
	}

	
	private static VBox settingSection(String name, List<Node> options, List<Node> display) {
		VBox r = new VBox();
		r.setSpacing(10);
		
		Text header = new Text(name);
		header.setFont(new Font(20));
		
		HBox row = new HBox();
		row.setSpacing(10);
		
		if(options != null) {
			VBox lOptions = new VBox();
			lOptions.setSpacing(10);
			lOptions.getChildren().addAll(options);
			row.getChildren().add(lOptions);
		}
		
		if(display != null) {
			VBox lDisplay = new VBox();
			lDisplay.setSpacing(10);
			lDisplay.getChildren().addAll(display);
			row.getChildren().add(lDisplay);
		}		
		
		r.getChildren().addAll(header,row);
		
		return r;
	}
	
	private static settingsPage pageDisplay() {
		settingsPage r = new settingsPage("Display");
		
		VBox content = new VBox();
		content.setSpacing(10);
		content.setPadding(new Insets(10));
		
		//DICE FORMAT
		Text diceDisplay = new Text(new Dice(3,4,5,6,7).toString());
		Function<String, String> updateDisplay = a -> {
			diceDisplay.setText(new Dice(3,4,5,6,7).toString());
			return "";
		};
		
		CheckBox cShowDice = new CheckBox("Show Dice");
		cShowDice.setSelected(Settings.display.dice.showDice);
		CheckBox cShowRange = new CheckBox("Show Range");
		cShowRange.setSelected(Settings.display.dice.showRange);
		
		cShowDice.selectedProperty().addListener((obs,o,n) -> {
			if(!n.booleanValue()) cShowRange.setSelected(true);
			Settings.display.dice.showDice = n.booleanValue();
			updateDisplay.apply("");
		});
		cShowRange.selectedProperty().addListener((obs,o,n) -> {
			if(!n.booleanValue()) cShowDice.setSelected(true);
			Settings.display.dice.showRange = n.booleanValue();
			updateDisplay.apply("");
		});
		
		CheckBox cCompactDice = new CheckBox("Compact Dice Format");
		cCompactDice.setSelected(Settings.display.dice.compactDice);
		cCompactDice.disableProperty().bind(cShowDice.selectedProperty().not());
		cCompactDice.selectedProperty().addListener(o -> {
			Settings.display.dice.compactDice = cCompactDice.isSelected();
			updateDisplay.apply("");
		});
		
		content.getChildren().add(settingSection("Dice Format",Arrays.asList(cShowDice,cShowRange,new Separator(),cCompactDice),Arrays.asList(diceDisplay)));
		
		r.setContent(content);
		
		return r;
	}
	
	private static settingsPage pageSaving() {
		settingsPage r = new settingsPage("Saving");
		
		return r;
	}
	
	public static class settingsPage extends ScrollPane {
		private String name;
		
		public settingsPage(String Name) {
			super();
			super.setHbarPolicy(ScrollBarPolicy.NEVER);
			super.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			name = Name;
		}
		
		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
}
