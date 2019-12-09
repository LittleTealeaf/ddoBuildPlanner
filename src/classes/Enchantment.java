package classes;

public class Enchantment {
	
	private String id;
	private String type;
	private String bonus;
	private String value;
	
	private String name;
	private String description;
	
	public Enchantment(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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
	
	public String getValue() {
		return value;
	}
	
	public double getNumericValue() {
		return Double.parseDouble(value);
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setValue(double value) {
		this.value = value + "";
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void clearName() {
		this.name = null;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void clearDescription() {
		this.description = null;
	}
}
