package classes;

import java.util.ArrayList;
import java.util.List;

public class Stat {
	
	public String name;
	public List<Attribute> attributes;
	
	public Stat() {
		name = "";
		attributes = new ArrayList<Attribute>();
	}
	public Stat(String stat) {
		name = stat;
		attributes = new ArrayList<Attribute>();
	}
	
	public Stat(String stat, Attribute attr) {
		name = stat;
		attributes = new ArrayList<Attribute>();
		attributes.add(attr);
	}
	
	public void addAttributes(List<Attribute> attrs) {
		for(Attribute a : attrs) if(a.attribute.contentEquals(name)) addAttribute(a);
	}
	
	public boolean addAttribute(Attribute attr) {
		for(Attribute a : attributes) if(a.type.contentEquals(attr.type)) {
			if(a.value >= attr.value) return false;
			a.value = attr.value;
			return true;
		}
		attributes.add(attr);
		return true;
	}
	
	public int getTotal() {
		int ret = 0;
		for(Attribute a : attributes) {
			ret+=a.value;
		}
		return ret;
	}
	
	public String toString() {
		return name + ": " + getTotal() + " | " + attributes;
	}
}
