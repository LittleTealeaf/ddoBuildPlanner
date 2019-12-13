package classes;

public class Iref {

	public String name;
	
	
	
	private transient Item temp;

	public Iref(String name) {
		this.name = name;
	}

	public Item getItem() {
		if(temp == null) temp = Items.readItem(name);
		return temp;
	}
}
