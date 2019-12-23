package scrapers;

import java.util.ArrayList;
import java.util.List;

public class sUtil {

	public static List<String> parseTemplate(String template) {
		return parseTemplate(template, true);
	}

	public static List<String> parseTemplate(String template, boolean includeName) {
		List<String> ret = new ArrayList<String>();
		String tmp = "";

		for(char c : template.toCharArray()) {

			if(c != '{' && c != '}') {

				if(c == '|') {
					if(tmp.replace(" ", "").contentEquals("")) tmp = "";
					ret.add(tmp);
					tmp = "";
				} else tmp += c;

			}

		}

		ret.add(tmp);
		if(!includeName) ret.remove(0);
		return ret;
	}
}
