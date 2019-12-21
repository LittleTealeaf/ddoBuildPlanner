package util;

import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Scanner;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;

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
	 * Gets data of the specific field in a HTML editor
	 * 
	 * @param xPath XPath of element
	 * @param url   site URL
	 * @return Contents of that field
	 */
	public static String getXPathContents(String xPath, URL url) {
		return getXPathContents(xPath, getContents(url));
	}

	/**
	 * Gets data of the specific field in a HTML editor
	 * 
	 * @param xPath    XPath of element
	 * @param contents page contents
	 * @return Contents of that field
	 */
	public static String getXPathContents(String xPath, String contents) {
		try {
			String con = contents.replace("&times", "");
			InputSource inputXML = new InputSource(new StringReader(con));
			return XPathFactory.newInstance().newXPath().evaluate(xPath, inputXML);
		} catch(XPathExpressionException e) {
			return "Fail";
		}

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
		String ret = null;

		try {
//			url.openConnection();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
//
//			String ret = "", line = "";
//
//			while((line = reader.readLine()) != null) {
//				ret += line;
//			}
			URLConnection connection = null;
			connection = url.openConnection();
			Scanner scanner = new Scanner(connection.getInputStream());
			scanner.useDelimiter("\\Z");
			ret = scanner.next();
			scanner.close();

			return ret;
		} catch(Exception e) {
			return null;
		}

	}
}
