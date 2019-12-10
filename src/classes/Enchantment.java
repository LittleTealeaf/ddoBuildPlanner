package classes;

import java.util.List;

public class Enchantment {
	
	private String id;
	private String type;
	private String bonus;
	private String value;
	
	private String name;
	private String description;
	private List<Attribute> attributes;
	
	public Enchantment(String id) {
		this.id = id;
	}
}
