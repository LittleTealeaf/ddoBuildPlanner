package classes;

import java.util.ArrayList;
import java.util.List;

public class Enchantment {

	private String id; // Universal Identifier (spellpower)
	private String name; // Initial Name (aka. Spell Power)
	private String type; // Sub Type (aka. Fire for "Fire Spell Power")
	private String description; // Description
	private String bonus; // Bonus Type
	private double value; // Value
	private List<AttributeBonus> attributes;

	public Enchantment(String id) {
		this.id = id;
		attributes = new ArrayList<AttributeBonus>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public List<AttributeBonus> getAttributeBonuses() {
		return attributes;
	}
	
	public void addAttributeBonus(AttributeBonus attribute) {
		if(!attributes.contains(attribute)) attributes.add(attribute);
	}
	
	public void removeAttributeBonus(AttributeBonus attribute) {
		if(attributes.contains(attribute)) attributes.remove(attribute);
	}
	
	public List<Attribute> getAttributes() {
		List<Attribute> r = new ArrayList<Attribute>();
		for(AttributeBonus b : attributes) r.add(b.toAttribute(this));
		return r;
	}

	public static class AttributeBonus {

		private String attribute;
		private String bonus;
		private double multiplier;

		public AttributeBonus(String attribute) {
			this(attribute, "", 1);
		}

		public AttributeBonus(String attribute, String bonus, double multiplier) {
			this.attribute = attribute;
			this.bonus = bonus;
			this.multiplier = multiplier;
		}

		public String getAttribute() {
			return attribute;
		}

		public void setAttribute(String attribute) {
			this.attribute = attribute;
		}

		public String getBonus() {
			return bonus;
		}

		public void setBonus(String bonus) {
			this.bonus = bonus;
		}

		public double getmultiplier() {
			return multiplier;
		}

		public void setValue(double multiplier) {
			this.multiplier = multiplier;
		}
		
		public Attribute toAttribute(Enchantment ench) {
			return new Attribute(attribute,bonus.contentEquals("") ? ench.getBonus() : bonus,ench.getValue() * multiplier);
		}
	}
}
