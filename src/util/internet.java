package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * Any and all general utils that relate to the internet
 * 
 * @author Tealeaf
 */
public class internet {

	/**
	 * Converts a string to it's URL equivalent
	 * 
	 * @param string String to convert
	 * @return String as used in the url
	 */
	public static String convertToURL(String string) {
		return URLEncoder.encode(string, Charset.defaultCharset());
	}

	/**
	 * Strips the site contents from the URL
	 * 
	 * @param siteURL
	 * @return
	 */
	public static String getContents(String siteURL) {

		try {
			return getContents(new URL(siteURL));
		} catch(Exception e) {
			return null;
		}

	}

	/**
	 * Strips the site contents from the URL
	 * 
	 * @param siteURL
	 * @return
	 */
	public static String getContents(URL url) {

		try {
			url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

			String ret = "", line = "";

			while((line = reader.readLine()) != null) {
				ret += line;
			}

			return ret;
		} catch(Exception e) {
			return "";
		}

	}
}
