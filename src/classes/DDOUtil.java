package classes;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public class DDOUtil {
	
	public static final String[] spellTypes = new String[] {"Fire","Acid","Positive","Negative","Cold","Force","Electric","Negative","Light","Repair","Sonic"};
	public static final String[] abilities = new String[] {"Strength","Dexterity","Constitution","Wisdom","Intelligence","Charisma"};
	
	public static int getMod(int statScore) {
		return ((int) (statScore / 2) - 5);
	}
	
	public static List<String> modSelection() {
		return modSelection(new ArrayList<String>());
	}
	
	public static List<String> modSelection(List<String> selMods) {
		
		
		Dialog<List<String>> dialog = new Dialog<List<String>>();
		
		List<CheckBox> cbs = new ArrayList<CheckBox>();
		
		for(String s : abilities) {
			CheckBox a = new CheckBox(s);
			a.setSelected(selMods.contains(s));
			cbs.add(a);
		}
		
		GridPane grid = new GridPane();
		grid.add(cbs.get(0), 0, 0);
		grid.add(cbs.get(1), 0, 1);
		grid.add(cbs.get(2), 0, 2);
		grid.add(cbs.get(3), 1, 0);
		grid.add(cbs.get(4), 1, 1);
		grid.add(cbs.get(5), 1, 2);
		
		dialog.setResultConverter(r -> {
			List<String> mods = new ArrayList<String>();
			
			for(CheckBox c : cbs) {
				if(c.isSelected()) mods.add(c.getText());
			}
			
			return mods;
		});
		
		ButtonType buttonTypeOk = new ButtonType("Close", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		
		return dialog.showAndWait().get();
	}
}
