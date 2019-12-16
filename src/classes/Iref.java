package classes;

import java.util.ArrayList;
import java.util.List;

//This is gonna be fun

public class Iref {

	public String name;
	
	private List<Craftref> crafting;

	private transient Item temp;

	public Iref(String name) {
		this.name = name;
	}

	public Item getItem() {
		if(temp == null) temp = Items.readItem(name);
		return temp;
	}
	
	
}
