package application;



import classes.Build;
import classes.Build.Gear;
import resource.Compendium;

public class Main {
	
	public static void main(String[] args) {
		
		Build.initialize();
		
//		Build.Gear gear = new Build.Gear();
//		gear.gloves = Compendium.getItem("Bluescale Guides");
//		gear.trinket = Compendium.getItem("Echo of Ravenkind");
//		Build.gearSets.add(gear);
//		Build.currentGear = gear;
		//Launch fxMain
		fxMain.open(args);
	}
}