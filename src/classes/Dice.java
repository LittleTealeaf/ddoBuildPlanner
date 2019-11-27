package classes;

import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Dice {
	public double firstCoeff;
	public int dieCount;
	public int dieSides;
	public double preAdd;
	public double postAdd;

	
	public Dice() {}
	/**
	 * In the format 1[2d3 + 4] + 5 
	 * @param a 1
	 * @param b 2
	 * @param c 3
	 * @param d 4
	 * @param e 5
	 */
	public Dice(double a, int b, int c, double d, double e) {
		firstCoeff = a;
		dieCount = b;
		dieSides = c;
		preAdd = d;
		postAdd = e;
	}
	public Dice(List<String> val) {
		firstCoeff = 1;
		dieCount = 1;
		dieSides = 2;
		preAdd = 0;
		postAdd = 0;
		if(val.get(0) != "") firstCoeff = Double.parseDouble(val.get(0));
		if(val.get(1) != "") dieCount = Integer.parseInt(val.get(1));
		if(val.get(2) != "") dieSides = Integer.parseInt(val.get(2));
		if(val.size() > 3) preAdd = Double.parseDouble(val.get(3));
		if(val.size() > 4) postAdd = Double.parseDouble(val.get(4));
	}
	
	public double getAverage() {
		return firstCoeff * (dieCount * (1 + dieSides) / 2 + preAdd) + postAdd;
	}
	
	public String toString() {
		String ret = dieCount + "d" + dieSides;
		if(preAdd != 0) ret+=" + " + preAdd;
		if(firstCoeff > 1) ret = firstCoeff + "[" + ret + "]";
		if(postAdd != 0) ret+=" + " + postAdd;
		return ret;
	}
	
	public static Dice fxDice() {
		return fxDice(null);
	}
	
	public static Dice fxDice(Dice dice) {
		
		Dialog<Dice> dialog = new Dialog<Dice>();
		dialog.setTitle("Edit Dice");
		if(dice == null) dialog.setTitle("New Dice");
		
		Label lCoef = new Label("Coefficient");
		Label lCount = new Label("Dice Count");
		Label lSides = new Label("Die Sides");
		Label lPreAdd = new Label("Pre Add");
		Label lPostAdd = new Label("Post Add");
		
		double iCoef = 1, iPreAdd = 0, iPostAdd = 0;
		int iCount = 1, iSides = 2;
		
		if(dice != null) {
			iCoef = dice.firstCoeff;
			iCount = dice.dieCount;
			iSides = dice.dieSides;
			iPreAdd = dice.preAdd;
			iPostAdd = dice.postAdd;
		}
		
		Spinner<Double> tCoef = new Spinner<Double>();
		tCoef.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 100, iCoef));
		
		Spinner<Integer> tCount = new Spinner<Integer>();
		tCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, iCount));
		
		Spinner<Integer> tSides = new Spinner<Integer>();
		tSides.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 1000, iSides));
		
		Spinner<Double> tPreAdd = new Spinner<Double>();
		tPreAdd.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-100, 100,iPreAdd));
		
		Spinner<Double> tPostAdd = new Spinner<Double>();
		tPostAdd.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-100, 100,iPostAdd));
		
		
		for(Spinner<?> s : new Spinner<?>[] {tCoef,tCount,tSides,tPreAdd,tPostAdd}) {
			s.setEditable(true);
			s.setPrefWidth(75);
		}
		
		dialog.setResultConverter(n -> {
			return new Dice(tCoef.getValue(),tCount.getValue(),tSides.getValue(),tPreAdd.getValue(),tPostAdd.getValue());
		});
		
		Text tDisplay = new Text();
		//Next Line is binding the tDisplay to put the spinners in a readable dice format
		tDisplay.textProperty().bind(tCoef.valueProperty().asString().concat("[").concat(tCount.valueProperty().asString()).concat("d").concat(tSides.valueProperty().asString().concat(" + ").concat(tPreAdd.valueProperty().asString()).concat("] + ").concat(tPostAdd.valueProperty().asString())));
		
		ButtonType buttonTypeOk = new ButtonType("Close", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		
		GridPane grid = new GridPane();
		grid.add(lCoef, 0, 0);
		grid.add(lCount, 1, 0);
		grid.add(lSides, 2, 0);
		grid.add(lPreAdd, 3, 0);
		grid.add(lPostAdd, 4, 0);
		grid.add(tCoef, 0, 1);
		grid.add(tCount, 1, 1);
		grid.add(tSides, 2, 1);
		grid.add(tPreAdd, 3, 1);
		grid.add(tPostAdd, 4, 1);
		
		BorderPane bp = new BorderPane();
		bp.setTop(tDisplay);
		bp.setCenter(grid);
		
		dialog.getDialogPane().setContent(bp);
		
		
		
		return dialog.showAndWait().get();
	}
}
