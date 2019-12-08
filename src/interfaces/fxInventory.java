package interfaces;

import javafx.stage.Stage;

public class fxInventory {

	public static Stage stage;
	
	public static void open() {
		if(stage != null && stage.isShowing()) stage.close();
		stage = new Stage();
		
		
	}
}
