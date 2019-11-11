package classes;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ItemAttribute implements Serializable {

	public String title;
	public String details;
	
	public String attributeName;
	public String attributeType;
	public double attributeValue;
	
	public ItemAttribute(String Title, String Details) {
		title = Title;
		details = Details;
	}
	
	public ItemAttribute(String Name, String Type, double Value) {
		attributeName = Name;
		attributeType = Type;
		attributeValue = Value;
		
		title = Type + " " + Name + " +" + Value;
		details = "Passive: +" + Value + " " + Type + " bonus to " + Name;
	}
}
