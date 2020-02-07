package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Enchantment {

	/**
	 * Static identifier of the enchantment
	 */
	private final String uuid;
	/**
	 * Display name
	 *
	 * @see #parseVars(Enchref, String)
	 */
	private String displayName;
	/**
	 * Internal Name
	 */
	private String name;
	/**
	 * Enchantment Description
	 */
	private String description;
	/**
	 * Enchantment Attributes
	 */
	private List<AttributeBonus> attributes;

	public Enchantment() {
		this("");
	}

	public Enchantment(String name) {
		this.name = name;
		uuid = UUID.randomUUID().toString();
		attributes = new ArrayList<>();
	}

	public String getUUID() {
		return uuid;
	}

	/**
	 * Gets the display name of the enchantment. No variables in the name is parsed before getting the
	 * name
	 * 
	 * @return Display name of the enchantment
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Gets the display name of the enchantment, parsing with the reference
	 * 
	 * @param ench Enchantment Reference
	 * @return Display name with all the variables parsed
	 */
	public String getDisplayName(Enchref ench) {
		return parseVars(ench, displayName);
	}

	/**
	 * Sets the display name of the enchantment
	 * 
	 * @param displayName
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the enchantment name
	 * 
	 * @return Enchantment Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the enchantment name
	 * 
	 * @param name Name to give the enchantment
	 */
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
		attributes.remove(attribute);
	}

	public List<Attribute> getAttributes(String type, String bonus, double value) {
		List<Attribute> r = new ArrayList<>();
		for (AttributeBonus b : attributes) r.add(b.toAttribute(type, bonus, value));
		return r;
	}

	/**
	 * Replaces the user-inputted variables in a string with the correct values set by the reference
	 * <p>
	 * The following variables are currently supported:
	 * <ul>
	 * <li>[type] - Enchantment Type (ex. Ability - Strength, Spell Power - Fire)</li>
	 * <li>[bonus] - Enchantment Bonus (ex. Insightful, Quality, Enchantment, Artifact)</li>
	 * <li>[value] - Value of the bonus</li>
	 * </ul>
	 * </p>
	 * 
	 * @param reference Enchantment Reference that contains the variables
	 * @param string    The string to parse
	 * @return Inputted String, where all variables are replaced by their corresponding values
	 */
	public String parseVars(Enchref reference, String string) {
		if(string == null || string.contentEquals("")) return "";
		String r = string;
		r = r.replace("[type]", reference.getType());
		r = r.replace("[bonus]", reference.getBonus());
		r = r.replace("[value]", reference.getValue() + "");
		return r;
	}

	/**
	 * A representation of an attribute template, uses 'variables' such as [type] [bonus] [value]
	 * @author Tealeaf
	 *@see #toAttribute(String, String, double) toAttribute - converts this class into an attribute class
	 */
	public static class AttributeBonus {

		private String attribute;
		private String bonus;
		private String value;

		public AttributeBonus() {
			this("");
		}

		public AttributeBonus(String attribute) {
			this(attribute, "[bonus]", "[value]");
		}

		public AttributeBonus(String attribute, String bonus, String value) {
			this.attribute = attribute;
			this.bonus = bonus;
			this.value = value;
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

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * Converts to an attribute, parsing out all the variables
		 * 
		 * @param type  Enchantment Type
		 * @param bonus Bonus Type of the value
		 * @param value Value of the enchantment
		 * @return Constructed Attribute
		 * @see Attribute
		 */
		public Attribute toAttribute(String type, String bonus, double value) {
			return new Attribute(attribute.replace("[type]", type), this.bonus.replace("[bonus]", bonus), Double.parseDouble(this.value.replace("[value]", value + "")));
		}
	}
}
