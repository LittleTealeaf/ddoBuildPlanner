package classes;

import java.util.List;

public class Enchref {

	private int id;

	private String type;
	private String bonus;
	private double value;

	private transient Enchantment enchantment;

	public Enchref(int id) {
		this.id = id;
	}

	public Enchantment getEnchantment() {
		return (enchantment == null) ? (enchantment = Enchantments.getEnchantment(id)) : enchantment;
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
	
	public List<Attribute> getAttributes() {
		return getEnchantment().getAttributes(bonus, value);
	}
}
