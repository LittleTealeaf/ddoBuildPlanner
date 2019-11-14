package classes;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Attribute implements Serializable {
	public String name;
	public String description;
	
	public String attribute;
	public int value;
	public String type;
	public String stringValue;
	
	public Attribute() {
		name = "";
		description = "";
		attribute = "";
		value = 0;
		type = "";
	}
	public Attribute(String Name, String Description) {
		name = Name;
		description = Description;
		attribute = "";
		value = 0;
		type = "";
		stringValue = "";
	}
	public Attribute(String Attribute, int Value, String Type) {
		attribute = Attribute;
		value = Value;
		type = Type;
		name = "";
		description = "";
		stringValue = "";
	}
	
	//TODO fix these
	public String getTitle() {
		if(!name.contentEquals("")) return name;
		if(value == 0 || attribute.contentEquals("")) return "";
		if(type.contentEquals("")) return attribute + " " + resource.util.getSign(value) + value;
		return type + " " + attribute + " " + resource.util.getSign(value) + value;
	}
	
	public String getDescription() {
		if(!description.contentEquals("")) return description;
		if(value == 0 || attribute.contentEquals("")) return "";
		String sign = resource.util.getSign(value);
		String ret = "Passive: " + sign + value + " " + type + " ";
		if(sign.contentEquals("+")) ret+= "bonus";
		else if(sign.contentEquals("-")) ret+="penalty";
		ret = ret.replace("  ", " ");
		return ret + " to " + attribute;
	}
	
	public String toString() {
		return getTitle() + ": " + getDescription();
	}
}
