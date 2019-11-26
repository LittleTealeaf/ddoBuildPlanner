package application;



import classes.Build;
import classes.Build.Gear;
import wiki.Compendium;

public class Main {
	
	public static void main(String[] args) {
		
		Build.initialize();
	
		//Build.gearSets.add(testGear());
		//Build.setGearIndex(0);
		//Launch fxMain
		fxMain.open(args);
	}
	
	private static Gear testGear() {
		Build.Gear gear = new Build.Gear("Test");
		gear.helmet = Compendium.getItem("Legendary Standard Issue Faceplate");
		gear.necklace = Compendium.getItem("Legendary Standard Issue Sigil");
		gear.goggles = Compendium.getItem("Legendary Collective Sight");
		gear.armor = Compendium.getItem("Legendary Watch Captain's Platemail");
		gear.cloak = Compendium.getItem("Mysterious Cloak (ML 21)");
		gear.bracers = Compendium.getItem("Legendary Bracers of the Fallen Hero");
		gear.belt = Compendium.getItem("Legendary Silverthread Belt");
		gear.ring1 = Compendium.getItem("Legendary Deathwarden");
		gear.ring2 = Compendium.getItem("Legendary Cursebane Ring");
		gear.boots = Compendium.getItem("Legendary Softsole Slippers");
		gear.gloves = Compendium.getItem("Legendary Gauntlets of Innate Arcanum");
		gear.mainhand = Compendium.getItem("Morninglord's Sceptre");
		gear.offhand = Compendium.getItem("Dethek Runestone");
		return gear;
	}
}