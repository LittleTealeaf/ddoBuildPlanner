package interfaces;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class fxImageSelector {

	public static String imagePrompt() {
		return imagePrompt("Select Image");
	}

	public static String imagePrompt(String title) {
		String r = "";

		Dialog<String> dialog = new Dialog<String>();
		dialog.setTitle(title);

		ImageView image = new ImageView();

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
				try {
					new Image(url.getText());
					return url.getText();
				} catch(Exception e) {}
			}
			return "";
		});

		dialog.getDialogPane().setContent(content);
		dialog.getDialogPane().setPrefHeight(800);
		dialog.getDialogPane().setPrefWidth(800);

		dialog.show();

		return r;
	}

}
