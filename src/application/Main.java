package application;



import classes.Build;
import classes.Build.Gear;

public class Main {
	
	public static void main(String[] args) {
		
		Build.initialize();
		
		
		//Launch fxMain
		fxMain.open(args);
	}
}