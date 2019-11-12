package resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

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
}
