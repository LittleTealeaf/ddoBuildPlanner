package classes;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import interfaces.fxEditEnchantment;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import util.resource;
import util.system;

public class Enchantments {

	public Enchantments() {}

	private static List<Enchantment> enchantments;

	/**
	 * Initially loads Enchantments. This method should only be called by the Main class
	 */
	public static void load() {
		enchantments = new ArrayList<Enchantment>();

		try {

			if(system.enchantments.exists()) {
				system.staticJSON.fromJson(new FileReader(system.enchantments), Enchantments.class);
			} else {
				// Loads from the resource version
				system.staticJSON.fromJson(resource.getBufferedReader("enchantments.json"), Enchantments.class);
				save();
			}

		} catch(Exception e) {}

	}

	/**
	 * Saves all enchantments to the computer
	 */
	public static void save() {
		system.writeFile(system.enchantments, system.staticJSON.toJson(new Enchantments()));
	}

	/**
	 * Gets all the enchantments currently loaded
	 * 
	 * @return List of all loaded enchantments
	 * @see Enchantment
	 */
	public static List<Enchantment> getEnchantments() {
		if(enchantments != null) load();
		return enchantments;
	}

	/**
	 * Gets an enchantment depending on the enchantment's UUID
	 * 
	 * @param uuid the unique identifier of the enchantment
	 * @return Enchantment with that UUID. Returns {@code null} if the enchantment does not exist or is
	 *         not currently in the database.
	 */
	public static Enchantment getEnchantmentUUID(String uuid) {
		for(Enchantment e : getEnchantments()) if(e.getUUID().contentEquals(uuid)) return e;
		return null;
	}

	/**
	 * Gets the enchantment by name, ignoring caps.
	 * 
	 * @param name Name of the enchantment
	 * @return Enchantment by that name. Returns {@code null} if that enchantment does not exist
	 */
	public static Enchantment getEnchantmentName(String name) {
		System.out.println("Search for enchantment: " + name);
		for(Enchantment e : getEnchantments()) if(e.getName() != null && e.getName().toLowerCase().contentEquals(name.toLowerCase())) return e;
		return null;
	}

	/**
	 * Adds a list of enchantments to the database
	 * 
	 * @param adds Adds each enchantment to the database
	 */
	public static void addEnchantments(List<Enchantment> adds) {
		for(Enchantment e : adds) addEnchantment(e);
	}

	/**
	 * Adds a single enchantment to the database, then saves the whole database
	 * 
	 * @param enchantment Enchantment to add to the database
	 */
	public static void addEnchantment(Enchantment enchantment) {
		for(Enchantment e : getEnchantments()) if(e.getUUID().contentEquals(enchantment.getUUID())) {
			updateEnchantment(enchantment);
			return;
		}
		enchantments.add(enchantment);
		save();
	}

	/**
	 * Removes a single enchantment from the database
	 * 
	 * @param uuid {@code UUID} of the enchantment to remove
	 */
	public static void removeEnchantment(String uuid) {
		removeEnchantment(getEnchantmentUUID(uuid));
	}

	/**
	 * Removes a single enchantment from the database
	 * <p>
	 * Will do nothing if the enchantment is already in the database
	 * 
	 * @param enchantment Enchantment to remove.
	 */
	public static void removeEnchantment(Enchantment enchantment) {
		if(enchantments.contains(enchantment)) enchantments.remove(enchantment);
		save();
	}

	/**
	 * Updates the database version of the enchantment. Will replace the enchantment that has the same
	 * {@code UUID} as the given enchantment. If it can not find an existing enchantment with the same
	 * {@code UUID}, then it will add it as a new enchantment
	 * 
	 * @param enchantment
	 */
	public static void updateEnchantment(Enchantment enchantment) {
		for(Enchantment e : enchantments) if(e.getUUID().contentEquals(enchantment.getUUID())) {
			e = enchantment;
			save();
			return;
		}
		addEnchantment(enchantment);
	}

	/**
	 * Loads the enchantment dialog:
	 * 
	 * @see #enchrefDialog(Enchref)
	 * @return New Enchantment dialog
	 */
	public static Enchref enchrefDialog() {
		return enchrefDialog(null);
	}

	/**
	 * Loads the enchantment dialog:
	 * <p>
	 * The enchantment dialog is set up with a name field, display name field, and a list of attributes
	 * / attribute bonuses
	 * 
	 * @param ench Enchantment dialog to load
	 * @return Edited Enchantment Dialog
	 * @see #enchrefDialog()
	 * @see Enchref
	 * @see Enchantment
	 */
	public static Enchref enchrefDialog(Enchref ench) {
		// Creating the dialog
		Dialog<Enchref> dialog = new Dialog<Enchref>();
		dialog.setTitle((ench == null) ? "Add Enchantment" : "Edit Enchantment");
		dialog.setResizable(true);

		// Choice Textfield
		TextField choice = new TextField();
		choice.setText((ench != null) ? ench.getEnchantment().getName() : "");

		// Suggested input field, updates whenever the user types in the choice field
		ListView<String> suggestedInputs = new ListView<String>();
		suggestedInputs.setItems(FXCollections.observableArrayList(getNames()));
		suggestedInputs.setPrefHeight(100);
		suggestedInputs.setOnMouseClicked(click -> {
			if(click.getClickCount() == 2) choice.setText(suggestedInputs.getSelectionModel().getSelectedItem());
		});
		suggestedInputs.visibleProperty().bind(choice.focusedProperty());

		choice.textProperty().addListener((e, o, n) -> {
			suggestedInputs.setItems(FXCollections.observableArrayList(getNames(n)));
			suggestedInputs.getSelectionModel().clearAndSelect(0);
		});
		choice.setOnKeyPressed(key -> {

			switch (key.getCode()) {
			case DOWN:
				suggestedInputs.getSelectionModel().selectNext();
				break;
			case UP:
				suggestedInputs.getSelectionModel().selectPrevious();
				choice.selectEnd();
				break;
			case ENTER:
				if(suggestedInputs.getSelectionModel().getSelectedItem() != null) choice.setText(suggestedInputs.getSelectionModel().getSelectedItem());
				break;
			default:
				break;
			}

		});

		VBox choiceBox = new VBox(choice, suggestedInputs);

		// Field for the Enchantment Type
		TextField type = new TextField();
		type.setText((ench != null) ? ench.getType() : "");

		// Field for the Bonus Type
		TextField bonus = new TextField();
		bonus.setText((ench != null) ? ench.getBonus() : "");

		// Enchantment Value
		Spinner<Double> value = new Spinner<Double>();
		value.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-1000, 1000, (ench != null) ? ench.getValue() : 0));
		value.getEditor().focusedProperty().addListener((e, o, n) -> {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {

					if(n.booleanValue() && !value.getEditor().getText().contentEquals("")) {
						value.getEditor().selectAll();
					}

				}
			});
		});
		value.setPrefWidth(75);
		value.setEditable(true);

		// Organizes all the right-most fields into a grid
		GridPane otherFields = new GridPane();
		otherFields.setHgap(10);
		otherFields.setVgap(10);
		otherFields.add(new Text("Enchantment Type"), 0, 0);
		otherFields.add(new Text("Bonus Type"), 0, 1);
		otherFields.add(new Text("Value"), 0, 2);
		otherFields.add(type, 1, 0);
		otherFields.add(bonus, 1, 1);
		otherFields.add(value, 1, 2);

		// Organizes the choice box and the other fields into a grid
		GridPane content = new GridPane();
		content.setHgap(10);
		content.add(choiceBox, 0, 0);
		content.add(otherFields, 1, 0);

		dialog.getDialogPane().setContent(content);

		ButtonType bAccept = new ButtonType("Accept", ButtonData.OK_DONE);
		ButtonType bCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		dialog.getDialogPane().getButtonTypes().addAll(bAccept, bCancel);

		dialog.setResultConverter(e -> {

			if(e.getButtonData() == ButtonData.OK_DONE) {

				// If the enchantment does not exist, Open up the new enchantment editor
				if(getEnchantmentName(choice.getText()) == null) {
					Enchantment create = new Enchantment(choice.getText());
					addEnchantment(create);
					fxEditEnchantment.openAndWait(create);
				}

				return new Enchref(getEnchantmentName(choice.getText()).getUUID(), type.getText(), bonus.getText(), value.getValue());
			}

			return null;
		});

		Optional<Enchref> res = dialog.showAndWait();
		return (res.isEmpty()) ? null : res.get();
	}

	/**
	 * Gets a list of names of all enchantments
	 * 
	 * @return List of enchantment names, in a {@code List<String>} format
	 * @see #getNames(String in)
	 */
	public static List<String> getNames() {
		return getNames("");
	}

	/**
	 * Gets a list of enchantment names that contain a set parameter
	 * 
	 * @param in String to filter the enchantments by
	 * @return List of enchantment names, in a {@code List<String>} format, that contain the parameter
	 * @see #getNames()
	 */
	public static List<String> getNames(String in) {
		List<String> r = new ArrayList<String>();

		for(Enchantment e : getEnchantments()) {
			if(in.contentEquals("") || (e.getName() != null && e.getName().toLowerCase().contains(in.toLowerCase()))) r.add(e.getName());
		}

		return r;
	}
}
