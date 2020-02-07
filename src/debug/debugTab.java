package debug;

import application.Main;
import classes.Build;
import classes.Gearset.GearsetExport;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import util.system;

public class debugTab {

	public static Tab getTab() {
        Tab tab = new Tab("Debug");

        GridPane content = new GridPane();

        // Build Test
        TextArea text = new TextArea();
        text.setPrefWidth(500);
        text.setPrefHeight(500);

        Button execFunction = new Button("Print Export");
        execFunction.setOnAction(e -> text.setText(system.objectJSON.toJson(Main.loadedBuild.getCurrentGearset().export(), GearsetExport.class)));

        content.add(execFunction, 0, 0);

        Button bPrint = new Button("Print");
        bPrint.setOnAction(e -> {

            try {
                text.setText(system.objectJSON.toJson(Main.loadedBuild, Build.class));
            } catch (Exception ignored) {
            }

        });

        content.add(bPrint, 0, 1);
        content.add(text, 1, 1);

		tab.setContent(content);

		return tab;
	}
}
