package debug;

import java.io.FileWriter;

import application.Main;
import classes.Build;
import classes.Gearset.GearsetExport;
import fxTabs.Gearsets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import util.system;

public class debugTab {

	private static Tab tab;

	public static Tab getTab() {
		tab = new Tab("Debug");
		
		GridPane content = new GridPane();

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
		
		content.add(execFunction, 0, 0);
		
		
		//Build Test
		TextArea text = new TextArea();
		
		
		Button bPrint = new Button("Print");
		bPrint.setOnAction(e -> {
			try {
				text.setText(system.objectJSON.toJson(Main.loadedBuild,Build.class));
			}catch(Exception f) {}
		});
		
		content.add(bPrint, 0, 1);
		content.add(text, 1, 1);

		tab.setContent(content);

		return tab;
	}
}
