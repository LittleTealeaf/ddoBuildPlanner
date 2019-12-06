package vars;

public enum Ability {
	STRENGTH("Strength"),
	DEXTERITY("Dexterity"),
	CONSTITUTION("Constitution"),
	INTELLIGENCE("Intelligence"),
	WISDOM("Wisdom"),
	CHARISMA("Charisma");
	
	private String displayName;

	Ability(String displayName) {
		this.displayName = displayName;
	}

	public String displayName() {
		return displayName();
	}

	public String toString() {
		return displayName;
	}
}