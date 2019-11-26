package classes;

public class Attribute {

	public String attribute;
	public double value;
	public String type;
	public String stringValue;
	
	public Attribute() {
		attribute = "";
		value = 0;
		type = "";
		stringValue = "";
	}
	public Attribute(String Attribute, double Value, String Type) {
		attribute = Attribute;
		value = Value;
		type = Type;
		stringValue = "";
	}
	
	public String getTitle() {
		if((value == 0 && stringValue.contentEquals(""))|| attribute.contentEquals("")) return "";
		else if(value == 0 && !stringValue.contentEquals("")) return attribute;
		if(type.contentEquals("")) return attribute + " " + getSign(value) + value;
		return type + " " + attribute + " " + getSign(value) + value;
	}
	
	public String getDescription() {
		if((value == 0 && stringValue.contentEquals(""))|| attribute.contentEquals("")) return "";
		else if(value == 0) return stringValue;
		String sign = getSign(value);
		String ret = "Passive: " + sign + value + " " + type + " ";
		if(sign.contentEquals("+")) ret+= "bonus";
		else if(sign.contentEquals("-")) ret+="penalty";
		ret = ret.replace("  ", " ");
		return ret + " to " + attribute;
	}
	
	private static String getSign(double number) {
		if(number < 0) return "";
		return "+";
	}
	
	public String toString() {
		return getTitle() + ": " + getDescription();
	}
	
	public String getAttribute() {
		return attribute;
	}
	
	public String getType() {
		return type;
	}
	
	public String getValue() {
		if(value != 0) return value + "";
		else return stringValue;
	}
}
