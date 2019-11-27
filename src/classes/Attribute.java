package classes;

import java.util.List;

public class Attribute {

	public String name; // ID
	public String type;
	public int value;

	public List<String> checks; // List of checks required before ability is given
	// TODO Requirement Class

	public Attribute() {
		name = "";
		type = "";
		value = 0;
	}
}
