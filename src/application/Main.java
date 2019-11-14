package application;


import javafx.application.Application;
import javafx.stage.Stage;
import resource.Compendium;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import classes.Build;

public class Main {
	
	public static void main(String[] args) {
		
		Build.initialize();
		
		Build.gearSets.add(new Build.Gear("Testing"));
		
		//Launch fxMain
		fxMain.open(args);
	}
}