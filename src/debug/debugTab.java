package debug;

import java.io.FileWriter;

import application.Main;
import classes.Gearset.GearsetExport;
import fxTabs.Gearsets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import util.system;

public class debugTab {

	private static Tab tab;

	public static Tab getTab() {
		tab = new Tab("Debug");

		Button execFunction = new Button("Open");
		execFunction.setOnAction(e -> {
			FileChooser chooser = new FileChooser();

			try {
				FileWriter writer = new FileWriter(chooser.showSaveDialog(null));
				writer.write(system.objectJSON.toJson(Main.loadedBuild.getCurrentGearset().export(), GearsetExport.class));
				writer.flush();
				writer.close();
			} catch(Exception g) {}

		});

		tab.setContent(execFunction);

		return tab;
	}
}
