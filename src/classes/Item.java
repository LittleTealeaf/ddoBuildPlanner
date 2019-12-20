package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import interfaces.fxItems;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.system;
import vars.ItemSlot;

/**
 * Class that describes a certain item. It contains a unique identifier, name, and other variables
 * specified for that item.
 * <p>
 * Also included are the crafting choices avaliable for selection.
 * </p>
 * 
 * @see Iref Item Reference
 * @author Tealeaf
 */
public class Item {

	private String uuid;
	private String name;
	private String type;
	private String description;
	private String proficiency;
	private String bindStatus;
	private String material;
	private String iconUUID;
	private String imageUUID;
	private int minLevel;
	private int absoluteMinLevel;
	private double hardness;
	private double durability;
	private double weight;
	private List<Enchref> enchantments;
	private List<ItemSlot> equipSlots;
	private List<Craftable> crafting;

	private Armor armor;

	/**
	 * All variables that pertain to any armor or shield.
	 * 
	 * @author Tealeaf
	 */
	private class Armor {

		public Armor() {
			armorType = "";
			maxDex = -1;
		}

		public boolean isEmpty() {
			return armorType.contentEquals("") && armorBonus == 0 && maxDex == -1 && checkPenalty == 0 && spellFailure == 0;
		}

		public String armorType;
		public int armorBonus;
		public int maxDex;
		public int checkPenalty;
		public double spellFailure;
	}

	private Weapon weapon;

	/**
	 * All variables that pertain to any weapon
	 * 
	 * @author Tealeaf
	 */
	private class Weapon {

		public Weapon() {
			damageTypes = new ArrayList<String>();
			damage = new Dice();
			lowCritRoll = 20;
			critMultiplier = 2;
		}

		public boolean isEmpty() {
			return damage.isDefault() && damageTypes.size() == 0 && lowCritRoll == 0 && critMultiplier == 0.0;
		}

		private Dice damage;
		private List<String> damageTypes;
		private int lowCritRoll;
		private double critMultiplier;
	}

	public Item() {
		this("");
	}

	public Item(String name) {
		this.name = name;
		enchantments = new ArrayList<Enchref>();
		equipSlots = new ArrayList<ItemSlot>();
		crafting = new ArrayList<Craftable>();
	}

	/**
	 * Saves the item to the local database
	 * 
	 * @return Returns true if successful or false if unsuccessful
	 */
	public void saveItem() {
		Items.saveItem(this);
		fxItems.updateTable();
	}

	/**
	 * Deletes the item from the database. Asks for confirmation if specified to do so in settings
	 */
	public void deleteItem() {

		if(Settings.items.warnOnDelete) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete " + name + "?");
			alert.setHeaderText("Delete Item?");
			alert.setContentText("Do you really want to delete the following item? \n   " + name);

			if(alert.showAndWait().get().getButtonData() != ButtonData.OK_DONE) return;
		}

		if(Settings.items.deleteImages) {
			Images.deleteImage(imageUUID);
			Images.deleteImage(iconUUID);
		}

		system.getAppFile("items", uuid + ".json").delete();

		fxItems.updateTable();
	}

	/**
	 * Cleans the item, removing any unnecessary classes
	 */
	public void cleanItem() {
		if(armor != null && armor.isEmpty()) armor = null;
		if(weapon != null && weapon.isEmpty()) weapon = null;
		minLevel = Math.max(absoluteMinLevel, minLevel);

		// TODO clear empty fields in damage types
	}

	public String getUUID() {
		return uuid;
	}

	public void setUUID(String id) {
		this.uuid = id;
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

	public String getDescriptionTrimmed() {
		if(description == null || description.contentEquals("")) return "";
		int maxLines = 5;
		String[] array = description.split("\\n");
		if(array.length <= maxLines) return description;
		String ret = "";
		for(int i = 0; i < maxLines; i++) ret += array[i] + "\n";
		ret = ret.substring(0, ret.length() - 1);
		return ret;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Image getIcon() {
		return Images.getImage(iconUUID);
	}

	public ImageView getIconViewSmall() {
		Image i = Images.getImage(iconUUID);
		if(i == null) return null;
		ImageView r = new ImageView(i);
		r.setPreserveRatio(true);
		r.setFitWidth(Settings.appearance.icon.size);
		r.setFitHeight(Settings.appearance.icon.size);
		return r;
	}

	public String getIconUUID() {
		return iconUUID;
	}

	public void setIconUUID(String iconUUID) {
		this.iconUUID = iconUUID;
	}

	public Image getImage() {
		return Images.getImage(imageUUID);
	}

	public String getImageUUID() {
		return imageUUID;
	}

	public void setImageUUID(String imageUUID) {
		this.imageUUID = imageUUID;
	}

	public String getProficiency() {
		return proficiency;
	}

	public void setProficiency(String proficiency) {
		this.proficiency = proficiency;
	}

	public String getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(String bindStatus) {
		this.bindStatus = bindStatus;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

	public int getAbsoluteMinLevel() {
		return absoluteMinLevel;
	}

	public void setAbsoluteMinLevel(int absoluteMinLevel) {
		this.absoluteMinLevel = absoluteMinLevel;
	}

	public double getHardness() {
		return hardness;
	}

	public void setHardness(double hardness) {
		this.hardness = hardness;
	}

	public double getDurability() {
		return durability;
	}

	public void setDurability(double durability) {
		this.durability = durability;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	// Enchantments

	public List<Enchref> getEnchantments() {
		return enchantments;
	}

	public void setEnchantments(List<Enchref> enchantments) {
		this.enchantments = enchantments;
	}

	public void addEnchantment(Enchref enchantment) {
		this.enchantments.add(enchantment);
	}

	public void removeEnchantment(Enchref enchantment) {
		if(this.enchantments.contains(enchantment)) enchantments.remove(enchantment);
	}

	public void updateEnchantment(Enchref previous, Enchref post) {
		if(this.enchantments.contains(previous)) this.enchantments.set(enchantments.indexOf(previous), post);
	}

	// Crafting

	public List<Craftable> getCrafting() {
		return crafting;
	}

	public Craftable getCraft(String uuid) {
		for(Craftable c : crafting) if(uuid.contentEquals(c.getUUID())) return c;
		return null;
	}

	public void setCrafting(List<Craftable> crafting) {
		for(Craftable c : crafting) if(c.getUUID().contentEquals("")) c.newUUID();

		this.crafting = crafting;
	}

	public void addCraftable(Craftable craftable) {
		if(craftable != null) this.crafting.add(craftable);
	}

	public void updateCraftable(Craftable previous, Craftable updated) {

		if(updated == null) {
			removeCraftable(previous);
		} else {
			if(crafting.contains(previous)) crafting.set(crafting.indexOf(previous), updated);
			else addCraftable(updated);
		}

	}

	public void removeCraftable(Craftable craftable) {
		this.crafting.remove(craftable);
	}

	// Equip Slots

	public List<ItemSlot> getEquipSlots() {
		return equipSlots;
	}

	public boolean hasEquipSlot(ItemSlot... slots) {

		for(ItemSlot s : slots) {
			if(equipSlots.contains(s)) return true;
		}

		return false;
	}

	public void setEquipSlots(List<ItemSlot> equipSlots) {
		this.equipSlots = equipSlots;
	}

	public void removeEquipSlot(ItemSlot slot) {
		while(equipSlots.contains(slot)) equipSlots.remove(slot);
	}

	public void addEquipSlot(ItemSlot slot) {
		if(!equipSlots.contains(slot)) equipSlots.add(slot);
	}

	public void setEquipSlot(ItemSlot slot, boolean equippable) {
		if(equippable) addEquipSlot(slot);
		else removeEquipSlot(slot);
	}

	// Armor Types

	public String getArmorType() {
		if(armor == null) return "";
		return armor.armorType;
	}

	public void setArmorType(String armorType) {
		if(armor == null) armor = new Armor();
		this.armor.armorType = armorType;
	}

	// Armor Bonus

	public int getArmorBonus() {
		if(armor == null) return 0;
		return armor.armorBonus;
	}

	public void setArmorBonus(int armorBonus) {
		if(armor == null) armor = new Armor();
		this.armor.armorBonus = armorBonus;
	}

	// Max Dex

	public int getMaxDex() {
		if(armor == null) return 0;
		return armor.maxDex;
	}

	public void setMaxDex(int maxDex) {
		if(armor == null) armor = new Armor();
		this.armor.maxDex = maxDex;
	}

	// Check Penalty

	public int getCheckPenalty() {
		if(armor == null) return 0;
		return armor.checkPenalty;
	}

	public void setCheckPenalty(int checkPenalty) {
		if(armor == null) armor = new Armor();
		this.armor.checkPenalty = checkPenalty;
	}

	// Spell Failure

	public double getSpellFailure() {
		if(armor == null) return 0;
		return armor.spellFailure;
	}

	public void setSpellFailure(double spellFailure) {
		if(armor == null) armor = new Armor();
		this.armor.spellFailure = spellFailure;
	}

	public Dice getDamage() {
		if(weapon == null) return new Dice();
		return weapon.damage;
	}

	public void setDamage(Dice damage) {
		if(weapon == null) weapon = new Weapon();
		this.weapon.damage = damage;
	}

	public List<String> getDamageTypes() {
		if(weapon == null) return null;
		return weapon.damageTypes;
	}

	/**
	 * Gets the damage type text, each type is specified on a new line
	 * 
	 * @return String of damage types separated by \n
	 */
	public String getDamageTypeText() {
		if(weapon == null) return "";
		String r = "";

		for(String l : getDamageTypes()) {
			if(!r.contentEquals("")) r += "\n";
			r += l;
		}

		return r;
	}

	/**
	 * Adds the damage type to the damage types
	 * 
	 * @param type Damage Type
	 */
	public void addDamageType(String type) {
		if(weapon == null) weapon = new Weapon();
		this.weapon.damageTypes.add(type);
	}

	/**
	 * Removes a damage type from the damage types
	 * 
	 * @param type Damage Type
	 */
	public void removeDamageType(String type) {
		if(weapon != null) this.weapon.damageTypes.remove(type);
	}

	public void setDamageTypes(List<String> damageTypes) {
		if(weapon == null) weapon = new Weapon();
		this.weapon.damageTypes = damageTypes;
	}

	/**
	 * Sets the damage types from a single String
	 * 
	 * @param text Text, damage types should be separated by \n
	 */
	public void setDamageTypesText(String text) {
		setDamageTypes(Arrays.asList(text.split("\n")));
	}

	public int getLowCritRoll() {
		if(weapon == null) return 20;
		return weapon.lowCritRoll;
	}

	public void setLowCritRoll(int lowCritRoll) {
		if(weapon == null) weapon = new Weapon();
		this.weapon.lowCritRoll = lowCritRoll;
	}

	public double getCritMultiplier() {
		if(weapon == null) return 2;
		return weapon.critMultiplier;
	}

	public void setCritMultiplier(double critMultiplier) {
		if(weapon == null) weapon = new Weapon();
		this.weapon.critMultiplier = critMultiplier;
	}

	/**
	 * An exportable version of the {@link #item} class, along the item itself, this class contains the
	 * following
	 * <ul>
	 * <li>All Referenced Enchantments</li>
	 * <li>Content/Text versions of the image and icon, if specified in {@link Settings}</li>
	 * </ul>
	 * 
	 * @author Creep
	 */
	public static class ItemExport {

		private Item item;
		private List<Enchantment> enchantments;
		private String icon;
		private String image;

		public ItemExport(Item item) {
			this.item = item;

			enchantments = new ArrayList<Enchantment>();

			for(Enchref ench : item.getEnchantments()) enchantments.add(ench.getEnchantment());
			for(Craftable craft : item.getCrafting()) for(Enchref ench : craft.getChoices()) enchantments.add(ench.getEnchantment());

			if(Settings.porting.exporting.includeImages) {
				icon = (item.getIconUUID() != null) ? Images.getImageFileContents(item.getIconUUID()) : null;
				image = (item.getImageUUID() != null) ? Images.getImageFileContents(item.getImageUUID()) : null;
			}

		}

		/**
		 * Imports the item, saving any images and enchantments in the database
		 */
		public void importItem() {
			if(enchantments != null) Enchantments.addEnchantments(enchantments);

			if(icon != null) Images.saveImageFromContents(item.getIconUUID(), icon);
			else item.setIconUUID(null);
			if(image != null) Images.saveImageFromContents(item.getImageUUID(), image);
			else item.setImageUUID(null);

			item.saveItem();
		}
		
		public List<Enchantment> getEnchantments() {
			return enchantments;
		}

		public void setEnchantments(List<Enchantment> enchantments) {
			this.enchantments = enchantments;
		}
	}
}