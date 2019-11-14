package application;



import classes.Build;
import classes.Build.Gear;
import resource.Compendium;

public class Main {
	
	public static void main(String[] args) {
		
		Build.initialize();
		
		Compendium.getItem("Legendary Black Velvet Capelet");
		
		//Launch fxMain
		fxMain.open(args);
	}
}