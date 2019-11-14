package application;


import classes.Build;

public class Main {
	
	public static void main(String[] args) {
		
		Build.initialize();
		
		Build.gearSets.add(new Build.Gear("Testing"));
		
		//Launch fxMain
		fxMain.open(args);
	}
}