package classes;

import java.util.Arrays;
import java.util.List;

public class Dice {

	private double coeff;
	private int dieCount;
	private int dieSides;
	private double preAdd;
	private double postAdd;

	private transient boolean isNewDice;

	public Dice() {
		this(0, 0);
		isNewDice = true;
	}

	public Dice(int count, int sides) {
		this(count, sides, 0);
	}

	public Dice(int count, int sides, double bonus) {
		this(1, count, sides, bonus, 0);
	}

	public Dice(double coefficient, int count, int sides, double preAdd, double postAdd) {
		this.coeff = coefficient;
		this.dieCount = count;
		this.dieSides = sides;
		this.preAdd = preAdd;
		this.postAdd = postAdd;
		isNewDice = false;
	}
	
	public Dice(List<String> input) {
		try {
			this.coeff = Double.parseDouble(input.get(0));
			this.dieCount = Integer.parseInt(input.get(1));
			this.dieSides = Integer.parseInt(input.get(2));
			this.preAdd = Double.parseDouble(input.get(3));
			this.postAdd = Double.parseDouble(input.get(4));
		}catch(Exception e) {}
		isNewDice = false;
	}

	/**
	 * Creates a Dice class, and imports values set in a string
	 * 
	 * @param str String to parse into a dice class
	 */
	public Dice(String str) {
		this();

		List<String> parsed = Arrays.asList(str.replace(" ", "").replace("[", "/").replace("d", "/").replace("]+", "/").replace("+", "/").split("/"));

		System.out.println(parsed);
		System.out.println(parsed.size());

		/*
		 * 1d4 -> 2
		 * 1d3+5 -> 3
		 * 5[1d4 + 5] -> 4
		 * 5[1d4 + 5] + 5 -> 5
		 */
		try {

			switch (parsed.size()) {
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

		} catch(Exception e) {}

		isNewDice = false;
	}

	/**
	 * Calculates the minimum value possible
	 * 
	 * @return Minimum Value
	 */
	public double getMinimum() {
		return coeff * (dieCount + preAdd) + postAdd;
	}

	/**
	 * Calculates the maximum value possible
	 * 
	 * @return Maximum Value
	 */
	public double getMaximum() {
		return coeff * (dieCount * dieSides + preAdd) + postAdd;
	}

	/**
	 * Calculates the average roll
	 * 
	 * @return Average
	 */
	public double getAverage() {
		return coeff * (dieCount * ((dieSides - 1) / 2 + 1) + preAdd) + postAdd;
	}

	/**
	 * Returns if the dice variables are set to default
	 * 
	 * @return
	 */
	public boolean isDefault() {

		if(isNewDice = true) {
			if(coeff != 1) isNewDice = false;
			if(dieCount != 0) isNewDice = false;
			if(dieSides != 0) isNewDice = false;
			if(preAdd != 0) isNewDice = false;
			if(postAdd != 0) isNewDice = false;
		}

		return isNewDice;
	}

	@Override
	public String toString() {
		String r = "";

		if(Settings.appearance.dice.showDice) {

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

			if(Settings.appearance.dice.compactDice) r = r.replace(" ", "");
		}

		if(Settings.appearance.dice.showRange) {
			if(!r.contentEquals("")) r += "\n";
			r += "(" + getMinimum() + " - " + getMaximum() + ")";
		}

		return r;
	}

	/**
	 * To a String that will always display only the dice, regardless of setting.
	 * Only settings that affect this are those that specify the dice format
	 * 
	 * @return Human Readable String
	 */
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

		if(Settings.appearance.dice.compactDice) r = r.replace(" ", "");

		return r;
	}

	public double getCoeff() {
		return coeff;
	}

	public void setCoeff(double coeff) {
		this.coeff = coeff;
		isNewDice = false;
	}

	public int getDieCount() {
		return dieCount;
	}

	public void setDieCount(int dieCount) {
		this.dieCount = dieCount;
		isNewDice = false;
	}

	public int getDieSides() {
		return dieSides;
	}

	public void setDieSides(int dieSides) {
		this.dieSides = dieSides;
		isNewDice = false;
	}

	public double getPreAdd() {
		return preAdd;
	}

	public void setPreAdd(double preAdd) {
		this.preAdd = preAdd;
		isNewDice = false;
	}

	public double getPostAdd() {
		return postAdd;
	}

	public void setPostAdd(double postAdd) {
		this.postAdd = postAdd;
		isNewDice = false;
	}
}
