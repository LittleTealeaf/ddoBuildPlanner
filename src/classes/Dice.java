package classes;

public class Dice {

	public double coeff;
	public int dieCount;
	public int dieSides;
	public double preAdd;
	public double postAdd;

	public Dice() {
		coeff = 1;
		dieCount = 1;
		dieSides = 2;
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

	public double getMinimum() {
		return coeff * (dieCount + preAdd) + postAdd;
	}

	public double getMaximum() {
		return coeff * (dieCount * dieSides + preAdd) + postAdd;
	}

	public double getAverage() {
		return coeff * (dieCount * ((dieSides - 1) / 2 + 1) + preAdd) + postAdd;
	}

	@Override
	public String toString() {
		String r = "";
		if(coeff == 1) {
			double add = preAdd + postAdd;
			r += dieCount + "d" + dieSides;
			if(add != 0) r += " + " + add;
		} else {
			r += coeff + " [ " + dieCount + "d" + dieSides;
			if(preAdd != 0) r += " + " + preAdd;
			r += "]";
			if(postAdd != 0) r += " + " + postAdd;
		}
		//Current option for settings
		if(Settings.compactDice) r.replace(" ", "");
		return r;
	}

}
