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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import util.system;

public class Images {

	private static List<extImage> externalImages;

	public static void load() {
		File externals = system.getAppFile("images", "external.json");

		externalImages = new ArrayList<extImage>();

		if(externals.exists()) try {
			system.staticJSON.fromJson(Files.newBufferedReader(externals.toPath()), Images.class);
		} catch(Exception e) {

		}
		
		deleteImage(".image");
		deleteImage(".icon");
	}

	public static void save() {
		File f = system.getAppFile("images", "external.json");
		if(!f.exists()) f.getParentFile().mkdirs();
		try {
			FileWriter writer = new FileWriter(f);
			writer.write(system.staticJSON.toJson(new Images()));
			writer.close();
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Image getImage(String name) {
		File f = null;

		try {
			f = system.getAppFile("images", name);
		} catch(Exception e) {
			try {
				f = new File(name);
			} catch(Exception d) {
				return null;
			}
		}

		if(f.exists()) return getImageFromURL(f.getPath());

		for(extImage i : externalImages) {
			if(name.contentEquals(i.name)) return getImageFromURL(i.url);
		}

		return null;
	}

	public static void renameImage(String from, String to) {
		File f = system.getAppFile("images", from);

		if(f.exists()) f.renameTo(system.getAppFile("images", to));
		else for(extImage i : externalImages) if(from.contentEquals(i.getName())) i.setName(to);
		save();
	}

	public static void deleteImage(String image) {
		List<extImage> n = new ArrayList<extImage>();
		for(extImage i : externalImages) if(!i.name.contentEquals(image)) n.add(i);
		externalImages = n;

		File f = system.getAppFile("images", image);
		if(f.exists()) f.delete();

		save();
	}

	public static void localizeImages() {
		for(extImage i : externalImages) {
			localizeImage(i);
		}
		externalImages.clear();
		save();
	}

	public static void saveImage(String name, String url) {
		if(Settings.advanced.images.storeLocal) localizeImage(new extImage(name, url));
		else {
			List<extImage> images = new ArrayList<extImage>();
			for(extImage i : externalImages) if(!name.contentEquals(i.getName())) images.add(i);
			images.add(new extImage(name, url));
			externalImages = images;
		}
		save();
	}

	private static void localizeImage(extImage img) {
		File writeTo = system.getAppFile("images", img.name);
		writeTo.getParentFile().mkdirs();

		try {
			try(var fis = new FileInputStream(img.url); var fos = new FileOutputStream(writeTo)) {

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

	private static Image getImageFromURL(String url) {
		try {
			return new Image(url);
		} catch(IllegalArgumentException a) {
			return new Image(new File(url).toURI().toString());
		} catch(Exception a) {}
		return null;
	}

	public static class ImagePrompt extends Dialog<String> {

		private String oldImage;
		private FileChooser fileChooser;
		private Label errorLabel;
		private ImageView image;
		private TextField urlField;

		public ImagePrompt() {
			this("");
		}

		/**
		 * 
		 * @param loadImage Name of the old image, which is found in the oldImage area
		 */
		public ImagePrompt(String loadImage) {
			super();
			this.setResizable(true);

			oldImage = loadImage;

			fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*"), new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));

			errorLabel = new Label();
			errorLabel.setTextFill(Color.RED);

			image = new ImageView();
			image.setImage(getImage(loadImage));
			image.setPreserveRatio(true);
			image.setFitWidth(300);
			image.setFitHeight(300);

			urlField = new TextField();
			urlField.setText(loadImage);
			HBox.setHgrow(urlField, Priority.ALWAYS);

			Button browse = new Button("Browse...");
			browse.setOnAction(e -> {
				String newURL = fileChooser.showOpenDialog(null).getPath();
				if(getImage(newURL) != null) urlField.setText(newURL);
				displayImage();
			});

			Button tryDisplay = new Button("Preview");
			tryDisplay.setOnAction(e -> displayImage());

			Button clear = new Button("Clear Field");
			clear.setOnAction(e -> {
				urlField.setText("");
				displayImage();
			});

			HBox header = new HBox(urlField, errorLabel, browse, tryDisplay, clear);
			header.setSpacing(10);

			BorderPane content = new BorderPane();

			content.setTop(header);
			content.setCenter(image);

			this.getDialogPane().setContent(content);
			this.setResultConverter(button -> {
				if(button.getButtonData() == ButtonData.OK_DONE) {
					if(getImage(urlField.getText()) != null) return urlField.getText();
					else return "";
				} else return oldImage;
			});

			ButtonType bAccept = new ButtonType("Accept", ButtonData.OK_DONE);
			ButtonType bCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

			this.getDialogPane().getButtonTypes().addAll(bAccept, bCancel);
			this.getDialogPane().setPrefWidth(500);
			this.getDialogPane().setPrefHeight(500);

		}

		public String prompt() {
			Optional<String> out = this.showAndWait();
			if(out.get() == null) return "";
			else return out.get();
		}

		private void displayImage() {
			image.setImage(getImage(urlField.getText()));
		}
	}

	// TODO fix issue where it's not updating the name for items in extImage

	private static class extImage {
		private String name;
		private String url;

		public extImage(String name, String url) {
			this.name = name;
			this.url = url;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
