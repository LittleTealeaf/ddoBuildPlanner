package ddo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EnchantConvert {
	
	private static List<String[]> enchantMap;
	
	public static String getDescription() {
		
		return "";
	}
	
	
	private static void getMap() {
		enchantMap = new ArrayList<String[]>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("enchantments")));
			String line = "";
			while((line = br.readLine()) != null) {
				if(line.toCharArray()[0] != '#') enchantMap.add(line.split("|"));
			}
		} catch(Exception e) {}
	}
}
