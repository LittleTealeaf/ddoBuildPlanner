package interfaces;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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

		/*
		 * File
		 * > save
		 * > save as
		 * > open
		 * > preferences
		 * > exit
		 * Database
		 * > Items
		 * > Enchantments
		 * Build
		 * > Inventory
		 */
		
		
		// Menu
		MenuBar menu = new MenuBar();

//		Menu file = new Menu("File");
//
//		MenuItem settings = new MenuItem("Settings");
//		settings.setOnAction(e -> fxSettings.open());
//
//		MenuItem items = new MenuItem("Items");
//		items.setOnAction(e -> fxItems.open());
//
//		MenuItem inventory = new MenuItem("Inventory");
//		inventory.setOnAction(e -> fxInventory.open());
//
//		MenuItem enchantments = new MenuItem("Enchantments");
//		enchantments.setOnAction(e -> fxEnchantments.open());
//
//		file.getItems().addAll(settings, items, inventory, enchantments);

		//File Menu
		Menu file = new Menu("File");
		
		MenuItem save = new MenuItem("Save");
		
		MenuItem saveAs = new MenuItem("Save As");
		
		MenuItem open = new MenuItem("Open");
		
		MenuItem settings = new MenuItem("Settings");
		settings.setOnAction(e -> fxSettings.open());
		
		MenuItem exit = new MenuItem("Exit");
		
		file.getItems().addAll(save,saveAs,open,settings,exit);
		
		//Database Menu
		Menu database = new Menu("Database");
		
		MenuItem items = new MenuItem("Items");
		items.setOnAction(e -> fxItems.open());
		
		MenuItem enchantments = new MenuItem("Enchantments");
		enchantments.setOnAction(e -> fxEnchantments.open());
		
		database.getItems().addAll(items,enchantments);
		
		//Build Menu
		Menu build =new Menu("Build");
		
		MenuItem inventory = new MenuItem("Inventory");
		inventory.setOnAction(e -> fxInventory.open());
		
		build.getItems().addAll(inventory);
		
		menu.getMenus().addAll(file, database, build);

		// Center Area
		tabs = new TabPane();

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