package classes;

import java.util.Arrays;
import java.util.List;

/**
 * An object that represents a given dice roll. It contains a starting roll, followed by adding an
 * number, multiplying that sum by a coefficient, and finally adding a final number
 * 
 * @author Tealeaf
 */
public class Dice {

	/**
	 * First Coefficient of the dice
	 * <br>
	 * <code>X[_d_+_]+_</code>
	 */
	private double coeff;
	/**
	 * Number of dice rolled
	 * <br>
	 * <code>_[Xd_+_]+_</code>
	 */
	private int dieCount;
	/**
	 * Number of sides on the dice
	 * <br>
	 * <code>_[_dX+_]+_</code>
	 */
	private int dieSides;
	/**
	 * Number to add before multiplying by the {@link #coeff}
	 * <br>
	 * <code>_[_d_+X]+_</code>
	 */
	private double preAdd;
	/**
	 * Final number to add to the end of the roll
	 * <br>
	 * <code>_[_d_+_]+X</code>
	 */
	private double postAdd;

	/**
	 * Marks if the dice is in a standard format where it can be removed to save space
	 */
	private transient boolean isNewDice;

	/**
	 * Creates an empty {@link Dice} object
	 * <br>
	 * <code> 1[0d0+0]+0</code>
	 */
	public Dice() {
		this(0, 0);
		isNewDice = true;
	}

	/**
	 * Creates a {@link Dice} object with a set dice count and sides
	 * <br>
	 * <br>
	 * <code> 1 [ {@link #dieCount} d {@link #dieSides} + 0]  + 0</code>
	 * 
	 * @param count {@link #dieCount} Number of dice to roll
	 * @param sides {@link #dieSides} Number of sides on the dice
	 */
	public Dice(int count, int sides) {
		this(count, sides, 0);
	}

	/**
	 * Creates a {@link Dice} object with a set dice count, sides, and bonus
	 * <br>
	 * <br>
	 * <code> 1 [ {@link #dieCount} d {@link #dieSides} + {@link #preAdd} ]  + 0</code>
	 * 
	 * @param count {@link #dieCount} Number of dice to roll
	 * @param sides {@link #dieSides} Number of sides on the dice
	 * @param bonus {@link #preAdd} Number to add to the die roll
	 */
	public Dice(int count, int sides, double bonus) {
		this(1, count, sides, bonus, 0);
	}

	/**
	 * Creates a {@link Dice} object with a set coefficient dice count, sides, initial bonus, and final
	 * bonus
	 * <br>
	 * <br>
	 * <code> {@link #coeff} [ {@link #dieCount} d {@link #dieSides} + {@link #preAdd} ]  + {@link #postAdd}</code>
	 * 
	 * @param coefficient {@link #coeff} Number to multiply the initial sum with
	 * @param count       {@link #dieCount} Number of dice to roll
	 * @param sides       {@link #dieSides} Number of sides on the dice
	 * @param bonus       {@link #preAdd} Number to add to the initial die roll
	 * @param postAdd     {@link #postAdd} Number to add to the final die roll
	 */
	public Dice(double coefficient, int count, int sides, double preAdd, double postAdd) {
		this.coeff = coefficient;
		this.dieCount = count;
		this.dieSides = sides;
		this.preAdd = preAdd;
		this.postAdd = postAdd;
		isNewDice = false;
	}

	/**
	 * Creates a dice object by parsing a string list input
	 * 
	 * @param input
	 */
	public Dice(List<String> input) {
		this();

		System.out.println(input);

		switch (input.size()) {
		case 5:
			if(input.get(4) != null && !input.get(4).contentEquals("")) this.postAdd = Double.parseDouble(input.get(4));
		case 4:
			if(input.get(3) != null && !input.get(3).contentEquals("")) this.preAdd = Double.parseDouble(input.get(3));
		case 3:
			if(input.get(2) != null && !input.get(2).contentEquals("")) this.dieSides = Integer.parseInt(input.get(2));
		case 2:
			if(input.get(1) != null && !input.get(1).contentEquals("")) this.dieCount = Integer.parseInt(input.get(1));
		case 1:
			if(input.get(0) != null && !input.get(0).contentEquals("")) this.coeff = Double.parseDouble(input.get(0));
		}

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
	 * @return <code>True</code> if the dice is in it's default form
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

	/**
	 * Gets the {@link #coeff} of the {@link Dice}
	 * 
	 * @return The set {@link #coeff}
	 */
	public double getCoeff() {
		return coeff;
	}

	/**
	 * Sets the {@link #coeff} of the {@link Dice}
	 * 
	 * @param coeff New {@link #coeff} to assign to the {@link Dice}
	 */
	public void setCoeff(double coeff) {
		this.coeff = coeff;
		isNewDice = false;
	}

	/**
	 * Gets the {@link #dieCount} of the {@link Dice}
	 * 
	 * @return The {@link #dieCount} of the {@link Dice}
	 */
	public int getDieCount() {
		return dieCount;
	}

	/**
	 * Sets the {@link #dieCount} of the {@link Dice}
	 * 
	 * @param coeff New {@link #dieCount} to assign to the {@link Dice}
	 */
	public void setDieCount(int dieCount) {
		this.dieCount = dieCount;
		isNewDice = false;
	}

	/**
	 * Gets the {@link #dieSides} of the {@link Dice}
	 * 
	 * @return The {@link #dieSides} of the {@link Dice}
	 */
	public int getDieSides() {
		return dieSides;
	}

	/**
	 * Sets the {@link #dieSides} of the {@link Dice}
	 * 
	 * @param coeff New {@link #dieSides} to assign to the {@link Dice}
	 */
	public void setDieSides(int dieSides) {
		this.dieSides = dieSides;
		isNewDice = false;
	}

	/**
	 * Gets the {@link #preAdd} of the {@link Dice}
	 * 
	 * @return The {@link #preAdd} of the {@link Dice}
	 */
	public double getPreAdd() {
		return preAdd;
	}

	/**
	 * Sets the {@link #preAdd} of the {@link Dice}
	 * 
	 * @param coeff New {@link #preAdd} to assign to the {@link Dice}
	 */
	public void setPreAdd(double preAdd) {
		this.preAdd = preAdd;
		isNewDice = false;
	}

	/**
	 * Gets the {@link #postAdd} of the {@link Dice}
	 * 
	 * @return The {@link #postAdd} of the {@link Dice}
	 */
	public double getPostAdd() {
		return postAdd;
	}

	/**
	 * Sets the {@link #postAdd} of the {@link Dice}
	 * 
	 * @param coeff New {@link #postAdd} to assign to the {@link Dice}
	 */
	public void setPostAdd(double postAdd) {
		this.postAdd = postAdd;
		isNewDice = false;
	}
}
