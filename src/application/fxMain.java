package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;


public class fxMain extends Application {
	
	public static Stage sMainRef;
	
	private static TabPane tabs;
	
	public static void open(String[] args) {
		launch(args);
		
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		sMainRef = primaryStage;
			
			//Menu
			MenuBar menu = new MenuBar();
			
			
			
			//Center Area
			tabs = new TabPane();
			
			//Tome, Leveling, Enhancement Trees (sub tab in there), Gearsets
			tabs.getTabs().addAll(getTomeTab(), getLevelingTab(), getEnhancementsTab(), getGearsetsTab());
			
			for(Tab t : tabs.getTabs()) {
				t.setClosable(false);
			}
			
			BorderPane root = new BorderPane();
			root.setTop(menu);
			root.setCenter(tabs);
		
			Scene scene = new Scene(root,400,400);
			try { 
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			} catch(Exception e) {}
			
			primaryStage.setScene(scene);
			primaryStage.show();
		
	}
	
	private Tab getTomeTab() {
		Tab ret = new Tab("Tomes");
		return ret;
	}
	
	private Tab getLevelingTab() {
		Tab ret = new Tab("Leveling");
		
		return ret;
	}
	
	private Tab getEnhancementsTab() {
		Tab ret = new Tab("Enhancements");
		
		return ret;
	}
	
	private Tab getGearsetsTab() {
		Tab ret = new Tab("Gearsets");
		
		return ret;
	}
	
}