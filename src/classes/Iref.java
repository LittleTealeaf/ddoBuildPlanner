package classes;

import java.util.ArrayList;
import java.util.List;

public class Iref {

	public String name;

	private List<Integer> craftingChoices;
	
	private transient List<Craftref> crafting;

	private transient Item temp;

	public Iref(String name) {
		this.name = name;
	}

	public Item getItem() {
		if(temp == null) temp = Items.readItem(name);
		return temp;
	}
	
	public List<Craftref> getCrafting() {
		crafting = new ArrayList<Craftref>();
		int index = 0;
		for(Craftable c : getItem().getCrafting()) {
			crafting.add(new Craftref(c,craftingChoices.get(index)));
			index++;
		}
		return crafting;
	}
}