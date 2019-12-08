package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javafx.scene.image.Image;

public class resource {

	public static BufferedReader getText(String name) {
		return new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(name)));
	}

	public static Image getImage(String name) {
		return new Image(ClassLoader.getSystemResourceAsStream(name));
	}
	
}
