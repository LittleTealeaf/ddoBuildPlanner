package resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class util {
	
	/**
	 * Strips the site contents from the URL
	 * @param siteURL
	 * @return
	 */
	public static String getContents(String siteURL) {
		try {
			URL url = new URL(siteURL);
			url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String ret = "", line = "";
			while((line = reader.readLine()) != null) {
				ret+=line;
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static List<String> parseTemplate(String template) { return parseTemplate(template,true); }
	public static List<String> parseTemplate(String template, boolean includeName) {
		List<String> ret = new ArrayList<String>();
		String tmp = "";
		for(char c : template.toCharArray()) {
			if(c != '{' && c != '}') {
				if(c == '|') {
					ret.add(tmp);
					tmp = "";
				} else tmp+=c;
			}
		}
		ret.add(tmp);
		if(!includeName) ret.remove(0);
		return ret;
	}
}
