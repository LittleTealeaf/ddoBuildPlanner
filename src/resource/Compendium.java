package resource;

import classes.*;

import java.net.*;
import java.io.*;

public class Compendium {
	
	private static final String SITE = "https://ddocompendium.com";
	
	public static Item getItem(String name) {
		Item ret = new Item();
		ret.name = name;
		
		String displayHTML = util.getContents(SITE + "/w/" + name.replace(' ', '_'));
		String editHTML = util.getContents(SITE + "/index.php?title=" + name.replace(' ', '_') + "&action=edit");
		
		
		return ret;
	}
	
	private static String getEditContents(String HTML) {
		final String START = "name=\"wpTextbox1\">", END = "</textarea>";
		return HTML.substring(HTML.indexOf(START) + START.length(), HTML.indexOf(END));
	}
}
