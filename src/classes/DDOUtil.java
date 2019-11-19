package classes;

public class DDOUtil {
	
	public static String[] stats = new String[] {"Strength","Dexterity","Constitution","Intelligence","Wisdom","Charisma"};
	public static String[] spellTypes = new String[] {"Fire","Acid","Positive","Negative","Cold","Force","Electric","Negative","Light","Repair","Sonic"};
	public static String[] abilities = new String[] {"Strength","Dexterity","Constitution","Wisdom","Intelligence","Charisma"};
	
	public static int getMod(int statScore) {
		return ((int) (statScore / 2) - 5);
	}
}
