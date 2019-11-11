package classes;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Attribute implements Serializable {

	public String title;
	public String details;
	
	public String attributeName;
	public String attributeType;
	public double attributeValue;
	
	public Attribute(String Title, String Details) {
		title = Title;
		details = Details;
	}
	
	public Attribute(String Name, String Type, double Value) {
		attributeName = Name;
		attributeType = Type;
		attributeValue = Value;
		
		title = Type + " " + Name + " +" + Value;
		details = "Passive: +" + Value + " " + Type + " bonus to " + Name;
	}
}
