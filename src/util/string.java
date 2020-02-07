package util;

public class string {

	/**
	 * Takes a string, and returns that string where the first letter of each word is uppercased
	 * 
	 * @param name
	 * @return
	 */
	public static String properTitle(String name) {
        StringBuilder r = new StringBuilder();
        boolean space = false;

        for (char c : name.toCharArray()) {
            r.append((space && c != ' ') ? (c + "").toUpperCase() : c);
            space = c == ' ';
        }

        return r.toString();
    }
}
