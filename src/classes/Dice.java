package classes;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Dice implements Serializable{
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
		if(val.get(0) != "") firstCoeff = Double.parseDouble(val.get(0));
		if(val.get(1) != "") dieCount = Integer.parseInt(val.get(1));
		if(val.get(2) != "") dieSides = Integer.parseInt(val.get(2));
		if(val.size() > 3) preAdd = Double.parseDouble(val.get(3));
		if(val.size() > 4) postAdd = Double.parseDouble(val.get(4));
	}
	
	public double getAverage() {
		return firstCoeff * (dieCount * (1 + dieSides) / 2 + preAdd) + postAdd;
	}
}
