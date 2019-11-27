package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class resource {

	public static BufferedReader getResource(String name) {
		return new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(name)));
	}
}
