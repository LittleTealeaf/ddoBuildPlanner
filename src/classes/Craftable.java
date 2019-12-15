package classes;

import java.util.ArrayList;
import java.util.List;

public class Craftable {

	private String name;
	
	private int ID;
	
	private List<Enchantment> choices;
	
	public Craftable() {
		this(0);
	}
	
	public Craftable(int id) {
		this("",id);
	}

	public Craftable(String name, int id) {
		this(name,id,new ArrayList<Enchantment>());
	}
	
	public Craftable(String name, int id, List<Enchantment> choices) {
		this.name = name;
		this.ID = id;
		this.choices = choices;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int id) {
		this.ID = id;
	}

	public List<Enchantment> getChoices() {
		return choices;
	}

	public Enchantment getChoice(int index) {
		if(index < choices.size()) return choices.get(index);
		else return null;
	}

	public void setChoices(List<Enchantment> choices) {
		this.choices = choices;
	}

	public void addChoice(Enchantment choice) {
		this.choices.add(choice);
	}

	public void removeChoice(Enchantment choice) {
		this.choices.remove(choice);
	}
}
