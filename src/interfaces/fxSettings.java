package interfaces;

import classes.Dice;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class fxSettings {

	public static Stage stage;
	
	public static void open() {
		if(stage != null && stage.isShowing()) {
			stage.requestFocus();
			return;
		}
		
		stage = new Stage();
		stage.setTitle("Settings");
		
		TabPane tabs = new TabPane();
		
		tabs.getTabs().add(tabDisplay());
		
		stage.setScene(new Scene(tabs));
		stage.show();
	}
	
	private static Text exDiceFormat;
	
	private static Tab tabDisplay() {
		Tab r = new Tab("Display");
		
		//DICE FORMAT
		Text tDiceHeader = new Text("Dice Format");
		tDiceHeader.setFont(new Font(30));
		
		exDiceFormat = new Text();
		updateDiceFormat();
		
		CheckBox cCompact = new CheckBox("Compact Dice Format");
		cCompact.setOnAction(e -> updateDiceFormat());
		
		VBox diceOptions = new VBox(cCompact);
		
		HBox diceFormat = new HBox(diceOptions,exDiceFormat);
		diceFormat.setSpacing(10);
		
		VBox disp = new VBox(tDiceHeader,diceFormat);
		disp.setSpacing(10);
		
		r.setContent(disp);
		return r;
	}
	
	private static void updateDiceFormat() {
		exDiceFormat.setText(new Dice(1,2,3,4,5).toString());
	}
}
