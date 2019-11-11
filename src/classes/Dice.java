package classes;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Dice implements Serializable{
	public double firstCoeff;
	public int dieCount;
	public int dieSides;
	public double preAdd;
	public double postAdd;

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
	
	public double getAverage() {
		return firstCoeff * (dieCount * (1 + dieSides) / 2 + preAdd) + postAdd;
	}
}
