package classes;

import java.util.List;

public class Enchref {

	private String uuid;

	private String type;
	private String bonus;
	private double value;

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
		return (enchantment == null) ? (enchantment = Enchantments.getEnchantment(uuid)) : enchantment;
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

	public String getDisplayName() {
		return getEnchantment().getDisplayName(this);
	}

	public List<Attribute> getAttributes() {
		return getEnchantment().getAttributes(bonus, value);
	}
}
