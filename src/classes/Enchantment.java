package classes;

import ddo.EnchantConvert;

public class Enchantment {

	public String name;
	public String type;
	public String value;
	
	public Enchantment() {
		name = "";
		type = "";
		value = "";
	}
	
	public Enchantment(String n, String t, String v) {
		name = n;
		type = t;
		value = v;
	}
	
	public Enchantment(String n, String t, int v) {
		name = n;
		type = t;
		value = v + "";
	}
	
	public String getDescription() {
		return EnchantConvert.getDescription();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String t) {
		type = t;
	}
	
	public String getValue() {
		return value;
	}
	
	public int getValueAsInt() {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public void setValue(int v) {
		value = v + "";
	}
	
	public void setValue(String v) {
		value = v;
	}
}
