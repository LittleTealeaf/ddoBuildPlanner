package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class fxMain extends Application {

	public static Stage sMainRef;

	private static TabPane tabs;

	public static void open(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage sMain) {
		sMainRef = sMain;

		//TODO preferences! (including the display type of dice values)
		
		// Menu
		MenuBar menu = new MenuBar();

		Menu file = new Menu("File");

		menu.getMenus().addAll(file);

		// Center Area
		tabs = new TabPane();

		// Tome, Leveling, Enhancement Trees (sub tab in there), Gearsets
		tabs.getTabs().addAll(fxTabs.Tabs.getMainTabs());

		for(Tab t : tabs.getTabs()) {
			t.setClosable(false);
		}

		BorderPane root = new BorderPane();
		root.setTop(menu);
		root.setCenter(tabs);

		Scene scene = new Scene(root, 400, 400);
		try {
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		} catch(Exception e) {}

		sMain.setScene(scene);
		sMain.show();
		sMain.setMaximized(true);
	}

}