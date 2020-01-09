package util;


public class string {
	
	/**
	 * Takes a string, and returns that string where the first letter of each word is uppercased
	 * @param name
	 * @return
	 */
	public static String properTitle(String name) {
		String r = "";
		boolean space = false;
		for(char c : name.toCharArray()) {
			r+=(space && c != ' ') ? (c + "").toUpperCase() : c;
			space = c == ' ';
		}
		return r;
	}
}
