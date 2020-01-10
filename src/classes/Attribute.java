package classes;

import java.util.List;

import util.string;

public class Attribute {

	private String name; // ID
	private String type;
	private double value;

	public List<String> checks; // List of checks required before ability is given
	// TODO Requirement Class

	public Attribute() {
		name = "";
		type = "";
	}

	public Attribute(String name, String type, double value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public Attribute(Attribute a) {
		this.name = a.getName();
		this.type = a.getType();
		this.value = a.getValue();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public List<String> getChecks() {
		return checks;
	}

	public void setChecks(List<String> checks) {
		this.checks = checks;
	}

	public void addCheck(String check) {
		if(!this.checks.contains(check)) this.checks.add(check);
	}

	public void removeCheck(String check) {
		if(this.checks.contains(check)) this.checks.remove(check);
	}

	public String toString() {
		return string.properTitle(value + " " + type + " " + name);
	}
}
