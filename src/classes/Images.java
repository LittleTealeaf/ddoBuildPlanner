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

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import util.system;

public class Images {

	private static List<extImage> externalImages;

	public static void load() {
		File externals = system.getAppFile(new String[] {"images", "external.json"});

		externalImages = new ArrayList<extImage>();

		if(externals.exists()) try {
			system.staticJSON.fromJson(Files.newBufferedReader(externals.toPath()), Images.class);
		} catch(Exception e) {

		}
	}

	private static void save() {
		File f = system.getAppFile(new String[] {"images", "external.json"});
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
		// First try localized
		File f = system.getAppFile(new String[] {"images", name});

		if(f.exists()) return getImageFromURL(f.getPath());

		for(extImage i : externalImages) {
			if(name.contentEquals(i.name)) return getImageFromURL(i.url);
		}

		return null;
	}

	public static void renameImage(String from, String to) {
		File f = system.getAppFile(new String[] {"images", from});

		if(f.exists()) f.renameTo(system.getAppFile(new String[] {"images", to}));
		else for(extImage i : externalImages) if(from.contentEquals(i.getName())) i.setName(to);
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
		File writeTo = system.getAppFile(new String[] {"images", img.name});
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

	public static String imagePrompt() {
		return imagePrompt("Select Image");
	}

	public static String imagePrompt(String title) {
		return imagePrompt(title, "");
	}

	public static String imagePrompt(String title, String selImage) {

		Dialog<String> dialog = new Dialog<String>();
		dialog.setTitle(title);

		ImageView image = new ImageView();
		image.setPreserveRatio(true);
		image.setFitHeight(500);

		FileChooser imageChooser = new FileChooser();
		imageChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		imageChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*"), new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));

		TextField url = new TextField();
		url.setPrefWidth(500);
		url.textProperty().addListener((e, o, n) -> {
			try {
				image.setImage(new Image(n));
			} catch(IllegalArgumentException a) {
				image.setImage(new Image(new File(n).toURI().toString()));
			} catch(Exception a) {}
		});

		Button bBrowse = new Button("Browse");
		bBrowse.setOnAction(e -> url.setText(imageChooser.showOpenDialog(null).getPath()));

		HBox top = new HBox(url, bBrowse);
		top.setSpacing(10);
		top.setPadding(new Insets(10));

		BorderPane content = new BorderPane();

		content.setTop(top);
		content.setCenter(image);

		ButtonType bAccept = new ButtonType("Accept", ButtonData.OK_DONE);
		ButtonType bCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		dialog.getDialogPane().getButtonTypes().addAll(bAccept, bCancel);

		dialog.setResultConverter(button -> {
			if(button.getButtonData() == ButtonData.OK_DONE) {
				if(image.getImage() != null) return url.getText();
				return null;
			}
			return "";
		});

		dialog.getDialogPane().setContent(content);
		dialog.setResizable(true);
		dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

		Optional<String> out = dialog.showAndWait();

		if(out.get() == null) return "";
		else return out.get();
	}

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

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}
}
