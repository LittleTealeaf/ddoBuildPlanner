package debug;

import classes.Items;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;

public class debugTab {

	private static Tab tab;

	public static Tab getTab() {
		tab = new Tab("Debug");

		Button execFunction = new Button("Open");
		execFunction.setOnAction(e -> {
			Items.selectItemPrompt();
		});

		tab.setContent(execFunction);

		return tab;
	}
}
