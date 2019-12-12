package classes;

import java.util.ArrayList;
import java.util.List;

public class Enchantment {

	private int id; // static identifier
	private String displayName; // display / selection name
	private String name; // Initial Name (aka. Spell Power)
	private String description; // Description
	private List<AttributeBonus> attributes;

	public Enchantment() {
		this("");
	}

	public Enchantment(String name) {
		this.name = name;
		id = Enchantments.getNewID();
		attributes = new ArrayList<AttributeBonus>();
	}

	public int getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getDisplayName(Enchref ench) {
		return parseVars(ench, displayName);
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public String getDescription(Enchref ref) {
		return parseVars(ref, description);
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<Attribute> getAttributes(String bonus, double value) {
		List<Attribute> r = new ArrayList<Attribute>();
		for(AttributeBonus b : attributes) r.add(b.toAttribute(bonus, value));
		return r;
	}

	public String parseVars(Enchref reference, String string) {
		if(string == null || string.contentEquals("")) return "";
		String r = string;
		r = r.replace("[type]", reference.getType());
		r = r.replace("[bonus]", reference.getBonus());
		r = r.replace("[value]", reference.getValue() + "");
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

		public AttributeBonus() {
			this.attribute = "";
			this.bonus = "";
			this.multiplier = 1;
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

		public Attribute toAttribute(String bonus, double value) {
			return new Attribute(attribute, this.bonus.contentEquals("") ? bonus : this.bonus, value * multiplier);
		}
	}
}
