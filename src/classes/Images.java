package classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import util.system;

public class Images {

	private static List<extImage> externalImages;
	
	/*
	 * TODO 0.0.1r2 plans
	 * Whenever you save an image, you save that image with a UUID (local storage name is UUID)
	 * Saving an image has a return value of the UUID string for future use
	 * When an image is requested, it does pretty much the same thing as it does, where
	 * 	it looks to see if the specific file exists, and then looks to see if it's in the
	 * 	external images list
	 * For the image prompt, it doesn't display the UUID as the loaded
	 * 	This will need to be a complete rework of the entire prompt, including
	 * 	buttons for loading the previous image, clearing, and separate files
	 * 	for "load from URL", "load from file". 
	 */

	/**
	 * Initially Load the Images class
	 */
	public static void load() {
		File externals = system.getAppFile("images", "external.json");

		externalImages = new ArrayList<extImage>();

		if(externals.exists()) try {
			system.staticJSON.fromJson(Files.newBufferedReader(externals.toPath()), Images.class);
		} catch(Exception e) {}

		deleteImage(".image");
		deleteImage(".icon");
		save();
	}

	/**
	 * Saves the currently loaded external image references to a file.
	 */
	public static void save() {
		File f = system.getAppFile("images", "external.json");

		if(!f.exists()) f.getParentFile().mkdirs();

		try {
			FileWriter writer = new FileWriter(f);
			writer.write(system.staticJSON.toJson(new Images()));
			writer.close();
		} catch(IOException e) {}
	}

	/**
	 * Saves an image to the database
	 * If set to in settings, saves a local copy, otherwise adds the reference to external.json
	 * 
	 * @param name Name to set it to
	 * @param url  URL of the image
	 */
	public static String saveImage(String url) {
		String uuid = UUID.randomUUID().toString();

		if(Settings.saving.images.storeLocal) {
			localizeImage(new extImage(uuid, url));
		} else {
			List<extImage> images = new ArrayList<extImage>();
			for(extImage i : externalImages) if(!uuid.contentEquals(i.getUUID())) images.add(i);
			images.add(new extImage(uuid, url));
			externalImages = images;
		}

		save();
		return uuid;
	}

	/**
	 * Gets an image
	 * 
	 * @param name Either the URL, file path, or name of the image in the database
	 * @return Image as an Image class
	 */
	public static Image getImage(String uuid) {
		File f = null;

		try {
			f = system.getAppFile("images", uuid);
		} catch(Exception e) {

			try {
				f = new File(uuid);
			} catch(Exception d) {}
		}

		if(f.exists()) return getImageFromURL(f.getPath());

		for(extImage i : externalImages) {
			if(uuid.contentEquals(i.getUUID())) return getImageFromURL(i.getURL());
		}

		return getImageFromURL(uuid);
	}

	/**
	 * Deletes an image in the database
	 * 
	 * @param image Name of the image to delete
	 */
	public static void deleteImage(String uuid) {
		List<extImage> n = new ArrayList<extImage>();

		for(extImage i : externalImages) if(!i.getUUID().contentEquals(uuid)) n.add(i);

		externalImages = n;

		File f = system.getAppFile("images", uuid + ".image");
		if(f.exists()) f.delete();

		save();
	}

	/**
	 * Copies any external-referenced-images, whether online or in the directory, to a local file
	 */
	public static void localizeImages() {
		for(extImage i : externalImages) localizeImage(i);
		externalImages.clear();
		save();
	}

	/**
	 * Copies an image in the external.json to the local database
	 * 
	 * @param img
	 */
	private static void localizeImage(extImage img) {
		File writeTo = system.getAppFile("images", img.getUUID());
		writeTo.getParentFile().mkdirs();

		try {

			try(var fis = new FileInputStream(img.getURL()); var fos = new FileOutputStream(writeTo)) {
				byte[] buffer = new byte[1024];
				int length;

				while((length = fis.read(buffer)) > 0) fos.write(buffer, 0, length);
			}
		} catch(Exception e) {

			try {
				URL url = new URL(img.url);
				InputStream is = url.openStream();
				OutputStream os = new FileOutputStream(writeTo);

				byte[] b = new byte[2048];
				int length;

				while((length = is.read(b)) != -1) {
					os.write(b, 0, length);
				}

				is.close();
				os.close();
			} catch(Exception f) {}
		}
	}

	/**
	 * Gets the image from a URL
	 * 
	 * @param url Either the system path to the file or a URL link
	 * @return Image
	 */
	private static Image getImageFromURL(String url) {

		try {
			return new Image(url);
		} catch(IllegalArgumentException a) {
			return new Image(new File(url).toURI().toString());
		} catch(Exception a) {}

		return null;
	}

	public static class ImagePrompt extends Dialog<String> {

		private String oldUUID;
		private FileChooser fileChooser;
		private Label errorLabel;
		private ImageView iView;
		private TextField urlField;

		public ImagePrompt() {
			this(null);
		}
		
		public ImagePrompt(String oldUUID) {
			this.oldUUID = (oldUUID != null) ? oldUUID : null;
			
			// File Chooser for the browse function
			fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))); //TODO find the one for image default!
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*"), new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));

			// Error Label
			errorLabel = new Label();
			errorLabel.setTextFill(Color.RED);
			
			//Image View
			iView = new ImageView();
			iView.setImage((oldUUID != null) ? Images.getImage(oldUUID) : null);
			
			//URL field
			urlField = new TextField();
		}

		private void loadLayout() {
			
			Button loadURL = new Button("From URL:");
			
			HBox headerTop = new HBox(loadURL, urlField);
			headerTop.setSpacing(10);
			
			Button loadFile = new Button("From file...");
			
			Button clearImage = new Button("Clear");
			
			Button revertImage = new Button("Revert");
			
			HBox headerBottom = new HBox(loadFile,clearImage,revertImage);
			headerBottom.setSpacing(10);
			
			VBox header = new VBox(headerTop, headerBottom);
			header.setSpacing(10);
		}
		
		public String showPrompt() {
			loadLayout();
			Optional<String> out = this.showAndWait();
			if(out.get() == null) return "";
			else return out.get();
		}
	}

	private static class extImage {

		private String uuid;
		private String url;

		public extImage(String uuid, String url) {
			this.uuid = uuid;
			this.url = url;
		}

		public String getUUID() {
			return uuid;
		}
		
		public String getURL() {
			return url;
		}
	}
}
