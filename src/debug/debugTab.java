package debug;

import com.google.gson.Gson;

import classes.Build;
import fxTabs.Stats;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class debugTab {
	
	public static Tab tab;

	private static TextArea text;
	
	public static Tab getTab() {
		tab = new Tab();
		tab.setText("debug json");
		text = new TextArea();
		text.setWrapText(true);	
		tab.setContent(text);
		updateJSON();
		return tab;
	}
	public static void updateJSON() {
		text.setText(new Gson().toJson(new Build()));
	}
}
