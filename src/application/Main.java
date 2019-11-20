package application;



import classes.Build;
import classes.Build.Gear;
import resource.Compendium;

public class Main {
	
	public static void main(String[] args) {
		
		Build.initialize();
		
		Build.Gear gear = new Build.Gear("Test");
		gear.gloves = Compendium.getItem("Legendary Knifepalm");
		gear.trinket = Compendium.getItem("Echo of Ravenkind");
		gear.mainhand = Compendium.getItem("Duality, the Moral Compass");
		gear.offhand = Compendium.getItem("Vengeful Protector");
		Build.gearSets.add(gear);
		Build.setGearIndex(0);
		//Launch fxMain
		fxMain.open(args);
	}
}