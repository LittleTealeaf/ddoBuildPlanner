package application;


import javafx.application.Application;
import javafx.stage.Stage;
import resource.Compendium;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import classes.Build;

public class Main {
	
	public static void main(String[] args) {
		

		Compendium.getItem("Legendary Mask of Vulkoorim");

		Build.build = new Build();
		
		//Launch fxMain
		fxMain.open(args);
	}
}