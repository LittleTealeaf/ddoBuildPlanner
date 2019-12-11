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

		// Menu
		MenuBar menu = new MenuBar();

		Menu file = new Menu("File");

		MenuItem settings = new MenuItem("Settings");
		settings.setOnAction(e -> fxSettings.open());

		MenuItem items = new MenuItem("Items");
		items.setOnAction(e -> fxItems.open());

		MenuItem inventory = new MenuItem("Inventory");
		inventory.setOnAction(e -> fxInventory.open());
		
		MenuItem editEnchantment = new MenuItem("Enchantment");
		editEnchantment.setOnAction(e -> fxEditEnchantment.open());

		file.getItems().addAll(settings, items, inventory, editEnchantment);

		menu.getMenus().addAll(file);

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