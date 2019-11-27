package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class util {

	public static String listToString(List<String> list) {
		String ret = "";
		final String sep = "\n";
		
		for(String s : list) {
			if(!ret.contentEquals("")) ret+=sep;
			ret+=s;
		}
		
		return ret;
	}
	
	public static List<String> stringToList(String in, String separator) {
		List<String> r = new ArrayList<String>();
		Collections.addAll(r, in.split(separator));
		return r;
	}
}
