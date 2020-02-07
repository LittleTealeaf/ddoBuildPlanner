package classes;

import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import util.system;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class Images {

	/*
	 * TODO error:
	 * has errors when trying to get external images
	 */

	private static List<extImage> externalImages;

	private static transient final String imageType = ".png";

	/**
	 * Initially Load the Images class
	 */
	public static void load() {
		File externals = system.getAppFile("images", "external.json");

		externalImages = new ArrayList<>();

		if (externals.exists()) try {
			system.staticJSON.fromJson(Files.newBufferedReader(externals.toPath()), Images.class);
		} catch (Exception ignored) {
		}

		deleteImage(".image");
		deleteImage(".icon");
		save();
	}

	/**
	 * Saves the currently loaded external image references to a file.
	 */
	public static void save() {
		File f = system.getAppFile("images", "external.json");

		if (!f.exists())
			if (f.getParentFile().mkdirs()) System.out.println("Created Directory " + f.getParentFile().toString());

		system.writeFile(f, system.staticJSON.toJson(new Images()));
    }

	/**
	 * Saves an image to the database
	 * If set to in settings, saves a local copy, otherwise adds the reference to external.json
	 * 
	 * @param url  URL of the image
	 */
	public static String saveImage(String url) {
		String uuid = UUID.randomUUID().toString();

		if(Settings.saving.images.storeLocal) {
			localizeImage(new extImage(uuid, url));
		} else {
			List<extImage> images = new ArrayList<>();
			for (extImage i : externalImages) if (!uuid.contentEquals(i.getUUID())) images.add(i);
			images.add(new extImage(uuid, url));
			externalImages = images;
		}

		save();
		return uuid;
	}

	/**
	 * Gets an image
	 * 
	 * @return Image as an Image class
	 */
	public static Image getImage(String uuid) {
		File f = null;

		try {
			f = getImagePath(uuid);
		} catch (Exception e) {

			try {
				f = new File(uuid);
			} catch (Exception ignored) {
			}

		}

		assert f != null;
		if (Objects.requireNonNull(f).exists()) return getImageFromURL(f.getPath());

		try {

			for (extImage i : externalImages) {
				if (uuid.contentEquals(i.getUUID())) return getImageFromURL(i.getURL());
			}

		} catch (Exception ignored) {
		}

		return getImageFromURL(uuid);
	}

	/**
	 * Gets the file contents of an image in a Base64
	 * <p>
	 * This will localize the image if the image is not already localized
	 * </p>
	 * 
	 * @param uuid {@code UUID} of the image
	 * @return Base64 representation of the image
	 * @see #localizeImage(extImage)
	 */
	public static String getImageFileContents(String uuid) {
		File f = getImagePath(uuid);

		// Localzies if needed
		if(!f.exists()) for(extImage e : externalImages) if(uuid.contentEquals(e.getUUID())) localizeImage(e);

		String r = null;

		try {
			FileInputStream fileInputStreamReader = new FileInputStream(f);

			byte[] bytes = fileInputStreamReader.readAllBytes();
			r = new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);

			fileInputStreamReader.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return r;
	}

	public static void saveImageFromContents(String uuid, String lines) {

		try {
			if (getImagePath(uuid).exists()) return;
			Files.write(getImagePath(uuid).toPath(), Base64.getDecoder().decode(lines));
		} catch (Exception ignored) {
		}

	}

	/**
	 * Deletes an image in the database
	 *
	 * @param uuid UUID of the image to delete
	 */
	public static void deleteImage(String uuid) {
		if (uuid == null) return;
		List<extImage> n = new ArrayList<>();

		for (extImage i : externalImages) if (!i.getUUID().contentEquals(uuid)) n.add(i);

		externalImages = n;

		File f = getImagePath(uuid);
		if (f.exists()) if (f.delete()) System.out.println("Deleted " + f.toString());

		save();
	}

	/**
	 * Gets the image path depending on the image name
	 * 
	 * @param name Name or {@code UUID} of the image
	 * @return Image File
	 */
	public static File getImagePath(String name) {
		return system.getAppFile("images", name + imageType);
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
		File writeTo = getImagePath(img.getUUID());
		if (writeTo.getParentFile().mkdirs())
			System.out.println("Made Directory " + writeTo.getParentFile().toString());

		try {

			try (var fis = new FileInputStream(img.getURL()); var fos = new FileOutputStream(writeTo)) {
				byte[] buffer = new byte[1024];
				int length;

				while ((length = fis.read(buffer)) > 0) fos.write(buffer, 0, length);
			}

		} catch (Exception e) {

			try {
				URL url = new URL(img.url);
				InputStream is = url.openStream();
				OutputStream os = new FileOutputStream(writeTo);

				byte[] b = new byte[2048];
				int length;

				while ((length = is.read(b)) != -1) {
					os.write(b, 0, length);
				}

				is.close();
				os.close();
			} catch (Exception ignored) {}

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
		} catch (IllegalArgumentException a) {
			return new Image(new File(url).toURI().toString());
		} catch (Exception ignored) {
		}

		return null;
    }

	public static class ImagePrompt extends Dialog<String> {

		private final String oldUUID;
		private final FileChooser fileChooser;
		private final ImageView iView;
		private final TextField urlField;

		private String url;

		public ImagePrompt() {
			this(null);
		}

		public ImagePrompt(String oldUUID) {
			super();

			this.oldUUID = (oldUUID != null && !oldUUID.contentEquals("")) ? oldUUID : null;

			// File Chooser for the browse function
			fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*"), new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));

			// Error Label
			Label errorLabel = new Label();
			errorLabel.setTextFill(Color.RED);

			// Image View
			iView = new ImageView();
			iView.setImage((oldUUID != null) ? Images.getImage(oldUUID) : null);
			iView.setPreserveRatio(true);

			// URL field
			urlField = new TextField();
		}

		private void loadLayout() {
			this.setResizable(true);

			Button loadURL = new Button("Load");
			loadURL.setOnAction(e -> {

				if(displayImage(urlField.getText())) {
					url = urlField.getText();
				}

			});

			Button browseFile = new Button("From file...");
			browseFile.setOnAction(e -> {
				String image = fileChooser.showOpenDialog(getOwner()).getPath();

				if(displayImage(image)) {
					url = image;
					urlField.setText(url);
				}

			});

			HBox headerTop = new HBox(urlField, browseFile, loadURL);
			headerTop.setSpacing(10);

			Button clearImage = new Button("Clear");
			clearImage.setOnAction(e -> {
				url = "";
				displayImage();
			});

			Button revertImage = new Button("Revert");
			revertImage.setOnAction(e -> {
				url = oldUUID;
				displayImage();
			});

			HBox headerBottom = new HBox(clearImage, revertImage);
			headerBottom.setSpacing(10);

			VBox header = new VBox(headerTop, headerBottom);
			header.setSpacing(10);

			BorderPane content = new BorderPane();
			content.setTop(header);
			content.setCenter(iView);

			this.getDialogPane().setContent(content);

			this.getDialogPane().setPrefWidth(500);
			this.getDialogPane().setPrefHeight(500);
			this.getDialogPane().widthProperty().addListener((e, o, n) -> iView.setFitWidth(3 * (double) n / 5));
			this.getDialogPane().heightProperty().addListener((e, o, n) -> iView.setFitHeight(3 * (double) n / 5));

			ButtonType bAccept = new ButtonType("Accept", ButtonData.OK_DONE);
			ButtonType bCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

			this.getDialogPane().getButtonTypes().addAll(bAccept, bCancel);

			this.setResultConverter(e -> {

				if (e.getButtonData() == ButtonData.OK_DONE && url != null && getImageFromURL(url) != null) {
					// If user clicked the ok button, and the url exists and works, save it and send the uuid over
					return saveImage(url);
					// if not, then try to return the oldUUID, otherwise return null
				} else return oldUUID;

			});
		}

		public void displayImage() {
			displayImage("");
		}

		public boolean displayImage(String iURL) {
			Image i = getImageFromURL((iURL != null && !iURL.contentEquals("")) ? iURL : url);
			iView.setImage(i);
			return i != null;
		}

		public String showPrompt() {
			loadLayout();
			Optional<String> out = this.showAndWait();
			if (out.get() == null) return "";
			else return out.get();
		}
	}

	// TODO image selection prompt

	private static class extImage {

		private final String uuid;
		private final String url;

		/**
		 * @param uuid
		 * @param url
		 */
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
