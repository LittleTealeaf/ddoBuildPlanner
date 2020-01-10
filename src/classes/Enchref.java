package classes;

import java.util.List;

/**
 * An object that references a given {@link Enchantment} with a set type, bonus, and value
 * @author Tealeaf
 *
 */
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

	/**
	 * Creates an {@link Enchref} object that references a given {@link Enchantment}
	 * @param e {@link Enchantment} to reference
	 */
	public Enchref(Enchantment e) {
		this(e.getUUID());
	}

	/**
	 * Creates a {@link Enchantment} object that references a set {@link UUID}
	 * @param uuid
	 */
	public Enchref(String uuid) {
		this.uuid = uuid;
		this.type = "";
		this.bonus = "";
		this.value = 0;
	}

	//TODO finish commenting 
	
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
		return getEnchantment().getAttributes(type, bonus, value);
	}

	@Override
	public String toString() {
		return getDisplayName();
	}
}
