package vars;

public enum GearSlot {

    GOGGLES("Goggles", ItemSlot.GOGGLES),
    HELMET("Helmet", ItemSlot.HELMET),
    NECKLACE("Necklace", ItemSlot.NECKLACE),
    TRINKET("Trinket", ItemSlot.TRINKET),
    ARMOR("Armor", ItemSlot.ARMOR),
    CLOAK("Cloak", ItemSlot.CLOAK),
    BRACERS("Bracers", ItemSlot.BRACERS),
    BELT("Belt", ItemSlot.BELT),
    RING1("Ring", ItemSlot.RING),
    RING2("Ring", ItemSlot.RING),
    GLOVES("Gloves", ItemSlot.GLOVES),
    BOOTS("Boots", ItemSlot.BOOTS),
    MAINHAND("Main Hand", ItemSlot.MAINHAND),
    OFFHAND("Off Hand", ItemSlot.OFFHAND);

    private final String displayName;
    private final ItemSlot itemSlot;

    GearSlot(String displayName, ItemSlot itemSlot) {
        this.displayName = displayName;
        this.itemSlot = itemSlot;
    }

    public String displayName() {
        return displayName;
    }

    public ItemSlot getItemSlot() {
        return itemSlot;
    }

    public String toString() {
        return displayName;
    }
}
