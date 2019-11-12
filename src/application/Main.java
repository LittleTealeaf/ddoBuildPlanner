package application;

import classes.Build;

public class Main {
	
	public static void main(String[] args) {
		
		Build.build = new Build();
		
		//Launch fxMain
		fxMain.open(args);
	}
}