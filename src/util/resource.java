package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.scene.image.Image;

public class resource {

	public static InputStream getInputStream(String name) {
		return ClassLoader.getSystemResourceAsStream(name);
	}

	public static BufferedReader getBufferedReader(String name) {
		return new BufferedReader(getInputStreamReader(name));
	}

	public static InputStreamReader getInputStreamReader(String name) {
		return new InputStreamReader(getInputStream(name));
	}

	public static Image getImage(String name) {
		return new Image(getInputStream(name));
	}
}
