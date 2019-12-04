package classes;

import java.util.Arrays;
import java.util.List;

public class Dice {

	private double coeff;
	private int dieCount;
	private int dieSides;
	private double preAdd;
	private double postAdd;

	public Dice() {
		coeff = 1;
		dieCount = 0;
		dieSides = 0;
	}

	public Dice(int Count, int Sides) {
		dieCount = Count;
		dieSides = Sides;
	}

	public Dice(int Count, int Sides, double Bonus) {
		dieCount = Count;
		dieSides = Sides;
		preAdd = Bonus;
	}

	public Dice(double Coefficient, int Count, int Sides, double PreAdd, double PostAdd) {
		coeff = Coefficient;
		dieCount = Count;
		dieSides = Sides;
		preAdd = PreAdd;
		postAdd = PostAdd;
	}
	
	public Dice(String str) {
		coeff = 1;
		dieCount = 0;
		dieSides = 0;
		
		
		
		List<String> parsed = Arrays.asList(str.replace(" ", "").replace("[", "/").replace("d", "/").replace("]+", "/").replace("+", "/").split("/"));
		/**
		 * 1d4 -> 2
		 * 1d3+5 -> 3
		 * 5[1d4 + 5] -> 4
		 * 5[1d4 + 5] + 5 -> 5
		 */
		try {
			System.out.println(parsed);
			switch(parsed.size()) {
			case 3:
				preAdd = Double.parseDouble(parsed.get(2));
			case 2:
				dieCount = (int) Double.parseDouble(parsed.get(0));
				dieSides = (int) Double.parseDouble(parsed.get(1));
				break;
			case 5:
				postAdd = Double.parseDouble(parsed.get(4));
			case 4:
				coeff = Double.parseDouble(parsed.get(0));
				dieCount = (int) Double.parseDouble(parsed.get(1));
				dieSides = (int) Double.parseDouble(parsed.get(2));
				preAdd = Double.parseDouble(parsed.get(3));
				break;
			default:
				System.out.println("could not parse");
				break;
			}
			
		} catch (Exception e) {
			
		}
		
	}

	public double getMinimum() {
		return coeff * (dieCount + preAdd) + postAdd;
	}

	public double getMaximum() {
		return coeff * (dieCount * dieSides + preAdd) + postAdd;
	}

	public double getAverage() {
		return coeff * (dieCount * ((dieSides - 1) / 2 + 1) + preAdd) + postAdd;
	}
	
	public boolean isDefault() {
		if(coeff != 1) return false;
		if(dieCount != 0) return false;
		if(dieSides != 0) return false;
		if(preAdd != 0) return false;
		if(postAdd != 0) return false;
		return true;
	}

	@Override
	public String toString() {
		String r = "";
		if(Settings.display.dice.showDice) {
			if(coeff == 1) {
				double add = preAdd + postAdd;
				r += dieCount + "d" + dieSides;
				if(add != 0) r += " + " + add;
			} else {
				r += coeff + " [ " + dieCount + "d" + dieSides;
				if(preAdd != 0) r += " + " + preAdd;
				r += " ]";
				if(postAdd != 0) r += " + " + postAdd;
			}

			if(Settings.display.dice.compactDice) r = r.replace(" ", "");
		}
		if(Settings.display.dice.showRange) {
			if(!r.contentEquals("")) r += "\n";
			r += "(" + getMinimum() + " - " + getMaximum() + ")";
		}
		return r;
	}
	
	public String toEditString() {
		
		if(isDefault()) return "";
		
		String r = "";
		
		if(coeff == 1) {
			double add = preAdd + postAdd;
			r += dieCount + "d" + dieSides;
			if(add != 0) r += " + " + add;
		} else {
			r += coeff + " [ " + dieCount + "d" + dieSides;
			if(preAdd != 0) r += " + " + preAdd;
			r += " ]";
			if(postAdd != 0) r += " + " + postAdd;
		}
		
		if(Settings.display.dice.compactDice) r = r.replace(" ", "");
		
		return r;
	}

	public double getCoeff() {
		return coeff;
	}

	public void setCoeff(double coeff) {
		this.coeff = coeff;
	}

	public int getDieCount() {
		return dieCount;
	}

	public void setDieCount(int dieCount) {
		this.dieCount = dieCount;
	}

	public int getDieSides() {
		return dieSides;
	}

	public void setDieSides(int dieSides) {
		this.dieSides = dieSides;
	}

	public double getPreAdd() {
		return preAdd;
	}

	public void setPreAdd(double preAdd) {
		this.preAdd = preAdd;
	}

	public double getPostAdd() {
		return postAdd;
	}

	public void setPostAdd(double postAdd) {
		this.postAdd = postAdd;
	}

}
