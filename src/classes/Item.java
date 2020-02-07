package classes;

import interfaces.fxItems;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.system;
import vars.Ability;
import vars.ItemSlot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that describes a certain item. It contains a unique identifier, name, and other variables
 * specified for that item.
 * <p>
 * Also included are the crafting choices available  for selection.
 * </p>
 *
 * @see Iref Item Reference
 * @author Tealeaf
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
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

	private Weapon weapon;

	/**
	 * Creates an empty {@link Item} object with a given name
	 *
	 * @param name String given as the name of the object
	 */
	public Item(String name) {
		this.name = name;
		enchantments = new ArrayList<>();
		equipSlots = new ArrayList<>();
		crafting = new ArrayList<>();
	}

	/**
	 * Creates an empty {@link Item} object
	 */
	public Item() {
		this("");
	}

	/**
	 * Gets the {@link Item item's} description, trimmed to set number of lines
	 * <br>
	 * <br>
	 * This is mainly used as a display for items in {@link TableView tables} such as
	 * {@link Items#itemTable(List)}
	 *
	 * @return {@link String} representation of the {@link Item item's} description, trimmed to a set
	 * number of lines
	 * @see #getDescription()
	 * @see #setDescription(String)
	 * @see Items#itemTable(List)
	 */
	public String getDescriptionTrimmed() {
		if (description == null || description.contentEquals("")) return "";
		int maxLines = 5;
		String[] array = description.split("\\n");
		if (array.length <= maxLines) return description;
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < maxLines; i++) ret.append(array[i]).append("\n");
		ret = new StringBuilder(ret.substring(0, ret.length() - 1));
		return ret.toString();
	}

	/**
	 * Gets the damage type text, each type is specified on a new line
	 *
	 * @return String of damage types separated by \n
	 */
	public String getDamageTypeText() {
		if (weapon == null) return "";
		StringBuilder r = new StringBuilder();

		for (String l : getDamageTypes()) {
			if (!r.toString().contentEquals("")) r.append("\n");
			r.append(l);
		}

		return r.toString();
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

		if (Settings.items.warnOnDelete) {
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

	/**
	 * Gets the {@link Item Item's} UUID
	 * 
	 * @return The unique identifier of the {@link Item item}, used as the name for saving and loading
	 *         files
	 * @see #setUUID(String)
	 */
	public String getUUID() {
		return uuid;
	}

	/**
	 * Sets the {@link Item Item's} UUID
	 * 
	 * @param id Unique Identifier of the {@link Item item}, used as the name for saving and loading
	 *           files
	 * @see #getUUID()
	 */
	public void setUUID(String id) {
		this.uuid = id;
	}

	/**
	 * Gets the given name of the {@link Item item}
	 * 
	 * @return {@link String} representation of the {@link Item item} name
	 * @see #Item(String)
	 * @see #setName(String)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the {@link Item item}
	 * 
	 * @param name {@link String} representation of the {@link Item item} name
	 * @see #Item(String)
	 * @see #getName()
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the {@link Item item's} type
	 * 
	 * @return {@link String} representation of the {@link Item item} type
	 * @see #setType(String)
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the {@link Item item's} type
	 *
	 * @param type {@link String} representation of the {@link Item item} type
	 * @see #getType()
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the {@link Item item} description
	 *
	 * @return The {@link Item item's} description, given in a single {@link String}
	 * @see #getDescriptionTrimmed()
	 * @see #setDescription(String)
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the {@link Item item's} description
	 *
	 * @param description {@link String} representation of the {@link Item item's} description
	 * @see #getDescription()
	 * @see #getDescriptionTrimmed()
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public List<Ability> getAttackModifiers() {
		if (weapon != null)
			return (weapon.attackModifiers == null) ? (weapon.attackModifiers = new ArrayList<>()) : weapon.attackModifiers;
		else return null;
	}

	public Image getIcon() {
		return Images.getImage(iconUUID);
	}

	public ImageView getIconViewSmall() {
		Image i = Images.getImage(iconUUID);
		if (i == null) return null;
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
		return (armor != null) ? armor.spellFailure : 0;
	}

	public void setSpellFailure(double spellFailure) {
		if(armor == null) armor = new Armor();
		this.armor.spellFailure = spellFailure;
	}

	// Attack Penalty

	public int getAttackPenalty() {
		return (armor != null) ? armor.attackPenalty : 0;
	}

	public void setAttackPenalty(int attackPenalty) {
		if (armor == null) armor = new Armor();
		this.armor.attackPenalty = attackPenalty;
	}

	// Damage Reduction

	public double getDamageReduction() {
		return (armor != null) ? armor.damageReduction : 0;
	}

	public void setAttackModifiers(List<Ability> attackModifiers) {
		if (weapon == null) weapon = new Weapon();
		this.weapon.attackModifiers = attackModifiers;
	}

	public void setDamageReduction(double damageReduction) {
		if (armor == null) armor = new Armor();
		this.armor.damageReduction = damageReduction;
	}

	public Dice getDamage() {
		if (weapon == null) return new Dice();
		return weapon.damage;
	}

	public void setDamage(Dice damage) {
		if (weapon == null) weapon = new Weapon();
		this.weapon.damage = damage;
	}

	public List<String> getDamageTypes() {
		if (weapon == null) return null;
		return weapon.damageTypes;
	}

	public void setDamageTypes(List<String> damageTypes) {
		if (weapon == null) weapon = new Weapon();
		this.weapon.damageTypes = damageTypes;
	}

	/**
	 * Removes a damage type from the damage types
	 *
	 * @param type Damage Type
	 */
	public void removeDamageType(String type) {
		if (weapon != null) this.weapon.damageTypes.remove(type);
	}

	public boolean hasAttackModifier(Ability ability) {
		return (weapon != null) && getAttackModifiers().contains(ability);
	}

	/**
	 * Adds the damage type to the damage types
	 *
	 * @param type Damage Type
	 */
	public void addDamageType(String type) {
		if (weapon == null) weapon = new Weapon();
		this.weapon.damageTypes.add(type);
	}

	// Attack Modifiers

	/**
	 * Sets the damage types from a single String
	 *
	 * @param text Text, damage types should be separated by \n
	 */
	public void setDamageTypesText(String text) {
		setDamageTypes(Arrays.asList(text.split("\n")));
	}

	public List<Ability> getDamageModifiers() {
		if (weapon != null)
			return (weapon.damageModifiers == null) ? (weapon.damageModifiers = new ArrayList<>()) : weapon.damageModifiers;
		else return null;
	}

	public void setDamageModifiers(List<Ability> damageModifiers) {
		if (weapon == null) weapon = new Weapon();
		this.weapon.damageModifiers = damageModifiers;
	}

	public boolean hasDamageModifier(Ability ability) {
		return (weapon != null) && getDamageModifiers().contains(ability);
	}

	public void addAttackModifier(Ability ability) {
		if (weapon == null) weapon = new Weapon();
		if (!getAttackModifiers().contains(ability)) getAttackModifiers().add(ability);
	}

	public void removeAttackModifier(Ability ability) {
		if (weapon != null) getAttackModifiers().remove(ability);
	}

	// Damage Modifiers

	public void setAttackModifier(Ability ability, boolean status) {
		if (status) addAttackModifier(ability);
		else removeAttackModifier(ability);
	}

	public void addDamageModifier(Ability ability) {
		if (weapon == null) weapon = new Weapon();
		if (!getDamageModifiers().contains(ability)) this.weapon.damageModifiers.add(ability);
	}

	public void setCritMultiplier(double critMultiplier) {
		if (weapon == null) weapon = new Weapon();
		this.weapon.critMultiplier = critMultiplier;
	}

	/**
	 * All variables that pertain to any armor or shield.
	 *
	 * @author Tealeaf
	 */
	private static class Armor {

		private String armorType;
		private int armorBonus;
		private int maxDex;
		private int checkPenalty;
		private double spellFailure;
		private int attackPenalty;
		private double damageReduction;

		public Armor() {
			armorType = "";
			maxDex = -1;
		}

		public boolean isEmpty() {
			return armorType.contentEquals("") && armorBonus == 0 && maxDex == -1 && checkPenalty == 0 && spellFailure == 0 && attackPenalty == 0 && damageReduction == 0;
		}
	}

	public void removeDamageModifier(Ability ability) {
		if (weapon != null) getDamageModifiers().remove(ability);
	}

	public void setDamageModifier(Ability ability, boolean status) {
		if (status) addDamageModifier(ability);
		else removeDamageModifier(ability);
	}

	// Low Crit Rolls

	public int getLowCritRoll() {
		if(weapon == null) return 20;
		return weapon.lowCritRoll;
	}

	public void setLowCritRoll(int lowCritRoll) {
		if (weapon == null) weapon = new Weapon();
		this.weapon.lowCritRoll = lowCritRoll;
	}

	public double getCritMultiplier() {
		if (weapon == null) return 2;
		return weapon.critMultiplier;
	}

	/**
	 * All variables that pertain to any weapon
	 *
	 * @author Tealeaf
	 */
	private static class Weapon {

		private Dice damage;
		private List<String> damageTypes;
		private int lowCritRoll;
		private double critMultiplier;
		private List<Ability> attackModifiers;
		private List<Ability> damageModifiers;

		public Weapon() {
			damageTypes = new ArrayList<>();
			damage = new Dice();
			lowCritRoll = 20;
			critMultiplier = 2;
			attackModifiers = new ArrayList<>();
			damageModifiers = new ArrayList<>();
		}

		public boolean isEmpty() {
			return damage.isDefault() && damageTypes.size() == 0 && lowCritRoll == 0 && critMultiplier == 0.0;
		}
	}

	/**
	 * An exportable version of the {@link #item} class, along the item itself, this class contains the
	 * following
	 * <ul>
	 * <li>All Referenced Enchantments</li>
	 * <li>Content/Text versions of the image and icon, if specified in {@link Settings}</li>
	 * </ul>
	 *
	 * @author Tealaef
	 */
	public static class ItemExport {

		private final Item item;
		private List<Enchantment> enchantments;
		private String icon;
		private String image;

		public ItemExport(Item item) {
			this.item = item;

			enchantments = new ArrayList<>();

			for (Enchref ench : item.getEnchantments()) enchantments.add(ench.getEnchantment());
			for (Craftable craft : item.getCrafting())
				for (Enchref ench : craft.getChoices()) enchantments.add(ench.getEnchantment());

			if (Settings.porting.exporting.includeImages) {
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