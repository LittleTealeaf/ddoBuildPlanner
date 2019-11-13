package classes;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Attribute implements Serializable {
	public String name;
	public String description;
	
	public String attribute;
	public double value;
	public String type;
	
	public Attribute() {}
	public Attribute(String Name, String Description) {
		name = Name;
		description = Description;
	}
	public Attribute(String Attribute, double Value, String Type) {
		attribute = Attribute;
		value = Value;
		type = Type;
	}
	
	
	public String getTitle() {
		if(name.contentEquals("")) return type + " " + attribute + " " + resource.util.getSign(value) + value;
		return name;
	}
	
	public String getDescription() {
		if(description.contentEquals("")) {
			String sign = resource.util.getSign(value);
			String ret = "Passive: " + sign + value + " " + type + " ";
			if(sign.contentEquals("+")) ret+= "bonus";
			else if(sign.contentEquals("-")) ret+="penalty";
			return ret + " to " + attribute;
		}
		return description;
	}
}
