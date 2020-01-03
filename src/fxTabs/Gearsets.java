package fxTabs;

import java.util.Optional;

import application.Main;
import classes.Build;
import classes.Craftable;
import classes.Craftref;
import classes.Enchref;
import classes.Gearset;
import classes.Iref;
import classes.Item;
import classes.Items;
import interfaces.ItemPrompt;
import interfaces.fxEditItem;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import vars.GearSlot;

public class Gearsets {

	private static Tab tab;

	private static GridPane grid;

	private static Build build;

	public static Tab getTab() {
		build = Main.loadedBuild;

		tab = new Tab("Gearsets");

		BorderPane content = new BorderPane();
		content.setTop(gridTop());
		content.setCenter(gridContent());
		content.setOnKeyPressed(key -> {
			if(key.getCode() == KeyCode.F5) updateContent();
		});

		// TODO build the gearset into the build class

		ScrollPane scrollContent = new ScrollPane();
		scrollContent.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollContent.setContent(content);

		tab.setContent(scrollContent);

		return tab;
	}

	private static GridPane gridTop() {
		GridPane r = new GridPane();

		ComboBox<Gearset> choice = new ComboBox<Gearset>();

		choice.setItems(FXCollections.observableArrayList(build.getGearsets()));
		choice.getSelectionModel().select(build.getCurrentGearset());
		choice.getSelectionModel().selectedItemProperty().addListener((e, o, n) -> {
			build.setCurrentGearset(n);
			updateContent();
		});
		
		Button bDelete = new Button("Delete");		
		bDelete.setDisable(build.getGearsets().size() <= 1);
		bDelete.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Gearset "  + build.getCurrentGearset().getName());
			alert.setHeaderText("Do you really want to delete the gearset " + build.getCurrentGearset().getName() + "?");
			Optional<ButtonType> option = alert.showAndWait();
			if(option.get() == ButtonType.OK) {
				build.removeGearset(build.getCurrentGearset());
				choice.setItems(FXCollections.observableArrayList(build.getGearsets()));
				System.out.println(build.getGearsets().size());
				bDelete.setDisable(build.getGearsets().size() <= 1);
				choice.getSelectionModel().select(build.getCurrentGearset());
			}
		});
		
		Button bCreate = new Button("Create");
		bCreate.setOnAction(e -> {
			String name = namePrompt("Create Gearset");
			if(name != null) build.addGearset(new Gearset(name));
			choice.setItems(FXCollections.observableArrayList(build.getGearsets()));
			bDelete.setDisable(build.getGearsets().size() <= 1);
		});

		r.add(choice, 0, 0);
		r.add(bCreate, 1, 0);
		r.add(bDelete, 2, 0);

		return r;
	}

	private static GridPane gridContent() {
		/*
		 * Setup:
		 * name | enchantments? | crafting (choices) | item set ? | [choose] | [delete]
		 */

		grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));

		updateContent();

		return grid;
	}

	public static void updateContent() {
		grid.getChildren().clear();

		Text[] headers = {new Text("Slot"), new Text(""), new Text("Name"), new Text("Enchantments"), new Text("Crafting"), new Text("Set")};

		// Headers
		grid.addRow(0, headers);

		// per slot:
		int row = 1;

		for(GearSlot slot : GearSlot.values()) {
			Iref ref = (build.getCurrentGearset() == null) ? null : build.getCurrentGearset().getItemBySlot(slot);

			ImageView icon = (ref != null && ref.getItem().getIconViewSmall() != null) ? ref.getItem().getIconViewSmall() : new ImageView();

			Text name = new Text();
			name.setText((ref != null) ? ref.getItem().getName() : "");
			name.setOnMouseClicked(click -> {

				if(click.getClickCount() == 2) {
					fxEditItem.open(ref);
				}

			});

			Text enchantments = new Text();
			if(ref != null) for(Enchref e : ref.getItem().getEnchantments()) enchantments.setText(enchantments.getText() + e.getDisplayName() + "\n");

			Button bSelect = new Button("Select " + slot.displayName() + "...");
			bSelect.setDisable(build.getCurrentGearset() == null);
			bSelect.setOnAction(e -> {
				Item i = new ItemPrompt().setSlot(slot.getItemSlot()).setItem(build.getCurrentGearset().getItemBySlot(slot)).showPrompt();

				if(i != null) {
					build.getCurrentGearset().setItemBySlot(i, slot);
					updateContent();
				}

			});

			VBox craftingChoices = new VBox();
			craftingChoices.setSpacing(10);

			if(ref != null && ref.getCrafting() != null) {

				for(Craftref cref : ref.getCrafting()) {
					HBox h = new HBox(new Text(ref.getItem().getCraft(cref.getUUID()).getName()), new craftingChoice(ref, cref).toComboBox());
					h.setSpacing(2.5);
					craftingChoices.getChildren().add(h);
				}

			}

			Button bClear = new Button("Clear");
			bClear.setDisable(build.getCurrentGearset() == null);
			bClear.setOnAction(e -> {
				build.getCurrentGearset().setItemBySlot((Iref) null, slot);
				updateContent();
			});

			grid.addRow((row++), new Text(slot.displayName()), icon, name, enchantments, craftingChoices, bSelect, bClear);
		}

	}

	/**
	 * Displays a prompt for the gearset name
	 * 
	 * @param title Custom title of the prompt
	 * @return String, returns null if cancelled or empty
	 */
	private static String namePrompt(String title) {
		Dialog<String> dialog = new Dialog<String>();
		dialog.setTitle(title);

		Text name = new Text("Gearset Name");
		TextField nameField = new TextField();

		HBox hb = new HBox(name, nameField);
		hb.setSpacing(10);
		hb.setPadding(new Insets(10));
		dialog.getDialogPane().setContent(hb);

		ButtonType bAccept = new ButtonType("Accept", ButtonData.OK_DONE);
		ButtonType bCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(bAccept, bCancel);

		dialog.setResultConverter(result -> (result.getButtonData() == ButtonData.OK_DONE) ? nameField.getText() : null);
		Optional<String> r = dialog.showAndWait();
		return r.isEmpty() ? null : r.get();
	}

	private static class craftingChoice extends ComboBox<Enchref> {

		private Craftref ref;
		private Craftable craftable;

		public craftingChoice(Iref iref, Craftref ref) {
			super();
			this.ref = ref;
			this.craftable = iref.getItem().getCraft(ref.getUUID());
		}

		public ComboBox<Enchref> toComboBox() {
			this.getItems().addAll(craftable.getChoices());
			this.getSelectionModel().select(craftable.getChoice(ref.getIndex()));
			this.getSelectionModel().selectedIndexProperty().addListener((e, o, n) -> ref.setIndex(n.intValue()));

			return this;
		}
	}
}
