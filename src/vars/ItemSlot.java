package vars;

public enum ItemSlot {

	GOGGLES("Goggles"),
	HELMET("Helmet"),
	NECKLACE("Necklace"),
	TRINKET("Trinket"),
	ARMOR("Armor"),
	CLOAK("Cloak"),
	BRACERS("Bracers"),
	BELT("Belt"),
	RING("Ring"),
	GLOVES("Gloves"),
	BOOTS("Boots"),
	MAINHAND("Main Hand"),
	OFFHAND("Off Hand");

	private String displayName;

	ItemSlot(String displayName) {
		this.displayName = displayName;
	}

	public String displayName() {
		return displayName();
	}

	public String toString() {
		return displayName;
	}
}
