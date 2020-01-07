package interfaces;

import classes.Item;
import javafx.collections.FXCollections;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import scrapers.Compendium;

public class fxScraper {

	public static Item scrapePrompt() {
		return scrapePrompt("");
	}
	
	public static Item scrapePrompt(String initialName) {
		Dialog<Item> dialog = new Dialog<Item>();
		dialog.setTitle("Import Item");
		dialog.setHeaderText("Import an Item from a website");

		Text lName = new Text("Item Name:");

		TextField name = new TextField();
		name.setText(initialName);

		HBox hb = new HBox(lName, name);
		hb.setSpacing(10);

		ChoiceBox<Website> website = new ChoiceBox<Website>();
		website.setItems(FXCollections.observableArrayList(Website.values()));

		VBox vb = new VBox(hb, website);
		vb.setSpacing(10);

		ButtonType bSelect = new ButtonType("Select", ButtonData.OK_DONE);
		ButtonType bCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		dialog.getDialogPane().getButtonTypes().addAll(bSelect, bCancel);
		dialog.getDialogPane().setContent(vb);
		dialog.getDialogPane().setPrefWidth(500);
		dialog.getDialogPane().setPrefHeight(500);

		dialog.setResultConverter(b -> {

			if(b.getButtonData() == ButtonData.OK_DONE) {

				switch (website.getSelectionModel().getSelectedItem()) {
				case COMPENDIUM:
					return Compendium.scrapeItem(name.getText());
				default:
					return null;
				}

			} else return null;

		});

		try {
			Item i = dialog.showAndWait().get();
			i.saveItem();
			return i;
		} catch(Exception e) {
			return null;
		}

	}

	private static enum Website {
		COMPENDIUM,
		WIKI;
	}
}
