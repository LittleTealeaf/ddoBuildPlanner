package classes;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Attribute implements Serializable {

	public String title;
	public String details;
	
	public String name;
	public String type;
	public double value;
	
	public Attribute(String Title, String Details) {
		title = Title;
		details = Details;
	}
	
	public Attribute(String Name, String Type, double Value) {
		name = Name;
		type = Type;
		value = Value;
		
		title = Type + " " + Name + " +" + Value;
		details = "Passive: +" + Value + " " + Type + " bonus to " + Name;
	}
	public Attribute() {}
}
