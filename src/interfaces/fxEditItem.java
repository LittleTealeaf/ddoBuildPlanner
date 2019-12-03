package interfaces;

import classes.Iref;
import classes.Item;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class fxEditItem {

	public static Stage stage;
	
	private static Item item;
	
	public static void open() {
		open((Item) null);
	}
	
	public static void open(Iref i) {
		open(i.getItem());
	}
	
	public static void open(Item i) {
		item = i;
		
		if(stage != null && stage.isShowing()) stage.close();
		stage = new Stage();
		if(i != null) stage.setTitle("Editing " + i.getName());
		else {
			stage.setTitle("Create Item");
			item = new Item();
		}
		
		BorderPane content = new BorderPane();
		
		TabPane tabs = new TabPane();
		
	}
}
