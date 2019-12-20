package classes;

import java.util.List;

public class Enchref {

	/**
	 * {@code UUID} of the referenced enchantment
	 */
	private String uuid;

	/**
	 * Enchantment type
	 */
	private String type;
	/**
	 * Bonus type of the enchantment
	 */
	private String bonus;
	/**
	 * Value of the enchantment
	 */
	private double value;

	/**
	 * Temporary variable of the enchantment
	 * <p>
	 * This is used to keep from reading whenever we want to get the enchantment
	 * </p>
	 */
	private transient Enchantment enchantment;

	public Enchref(String uuid) {
		this.uuid = uuid;
	}

	public Enchref(String uuid, String type, String bonus, double value) {
		this.uuid = uuid;
		this.type = type;
		this.bonus = bonus;
		this.value = value;
	}

	public Enchantment getEnchantment() {
		return (enchantment == null) ? (enchantment = Enchantments.getEnchantmentUUID(uuid)) : enchantment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	/**
	 * Gets the display name, according to the enchantment referenced
	 * 
	 * @return Display Name
	 * @see Enchantment#getDisplayName(Enchref)
	 */
	public String getDisplayName() {
		return (getEnchantment() != null) ? getEnchantment().getDisplayName(this) : "";
	}

	/**
	 * Gets all attribtues from the enchantment
	 * 
	 * @return Attributes of referenced enchantment
	 */
	public List<Attribute> getAttributes() {
		return getEnchantment().getAttributes(bonus, value);
	}

	@Override
	public String toString() {
		return getDisplayName();
	}
}
