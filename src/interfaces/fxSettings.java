package interfaces;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import classes.Dice;
import classes.Images;
import classes.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class fxSettings {

	public static Stage stage;

	private static BorderPane content;

	public static void open() {
		if(stage != null && stage.isShowing()) {
			stage.requestFocus();
			return;
		}

		stage = new Stage();
		stage.setTitle("Settings");
		stage.setOnCloseRequest(e -> Settings.saveSettings());

		ObservableList<settingsPage> pages = FXCollections.observableArrayList();

		pages.addAll(pageAppearance(), pageSaving(), pageImages(), pageItems(), pageAdvanced());

		// http://www.java2s.com/Code/Java/JavaFX/ListViewselectionlistener.htm
		ListView<settingsPage> pageSelection = new ListView<settingsPage>(pages);
		pageSelection.setPrefWidth(100);
		pageSelection.getSelectionModel().selectedItemProperty().addListener((e, o, n) -> content.setCenter(n));
		pageSelection.setCellFactory(new Callback<ListView<settingsPage>, ListCell<settingsPage>>() {
			@Override
			public ListCell<settingsPage> call(ListView<settingsPage> param) {
				final Label leadLbl = new Label();
				final Tooltip tooltip = new Tooltip();
				final ListCell<settingsPage> cell = new ListCell<settingsPage>() {
					@Override
					public void updateItem(settingsPage item, boolean empty) {
						super.updateItem(item, empty);
						if(item != null) {
							leadLbl.setText(item.getName());
							setText(item.getName());
							tooltip.setText(item.getName());
							setTooltip(tooltip);
						}
					}
				};
				return cell;
			}
		});

		Button bSave = new Button("Save");
		bSave.setOnAction(e -> {
			Settings.saveSettings();
			stage.close();
		});

		HBox bottom = new HBox(bSave);
		bottom.setSpacing(10);
		bottom.setPadding(new Insets(10));

		content = new BorderPane();
		content.setLeft(pageSelection);
		content.setCenter(pageAppearance());
		content.setBottom(bottom);

		pageSelection.getSelectionModel().select(0);

		Scene scene = new Scene(content, 500, 400);

		stage.setScene(scene);
		stage.show();
	}

	private static settingsPage pageAppearance() {
		settingsPage r = new settingsPage("Appearance");

		VBox content = new VBox();
		content.setSpacing(10);
		content.setPadding(new Insets(10));

		// DICE FORMAT
		Text diceDisplay = new Text(new Dice(3, 4, 5, 6, 7).toString());
		Function<String, String> updateDisplay = a -> {
			diceDisplay.setText(new Dice(3, 4, 5, 6, 7).toString());
			return "";
		};

		CheckBox cShowDice = new CheckBox("Show Dice");
		cShowDice.setSelected(Settings.appearance.dice.showDice);
		cShowDice.setTooltip(new Tooltip("When checked, will display the D&D Dice format of the roll\n\nCannot be turned off at the same time as Show Range"));
		CheckBox cShowRange = new CheckBox("Show Range");
		cShowRange.setTooltip(new Tooltip("When checked, will display the range of the roll (min and max)\n\nCannot be turned off at the same time as Show Range"));
		cShowRange.setSelected(Settings.appearance.dice.showRange);

		cShowDice.selectedProperty().addListener((obs, o, n) -> {
			if(!n.booleanValue()) cShowRange.setSelected(true);
			Settings.appearance.dice.showDice = n.booleanValue();
			updateDisplay.apply("");
		});
		cShowRange.selectedProperty().addListener((obs, o, n) -> {
			if(!n.booleanValue()) cShowDice.setSelected(true);
			Settings.appearance.dice.showRange = n.booleanValue();
			updateDisplay.apply("");
		});

		CheckBox cCompactDice = new CheckBox("Compact Dice Format");
		cCompactDice.setTooltip(new Tooltip("Compact view of the D&D Dice format\nRemoves all spaces"));
		cCompactDice.setSelected(Settings.appearance.dice.compactDice);
		cCompactDice.selectedProperty().addListener(o -> {
			updateDisplay.apply("");
			Settings.appearance.dice.compactDice = cCompactDice.isSelected();
		});

		content.getChildren().add(settingSection("Dice Format", Arrays.asList(cShowDice, cShowRange, new Separator(), cCompactDice), Arrays.asList(diceDisplay)));

		r.setContent(content);

		return r;
	}

	private static settingsPage pageSaving() {
		settingsPage r = new settingsPage("Saving");

		VBox content = new VBox();
		content.setPadding(new Insets(10));
		content.setSpacing(10);

		Spinner<Double> sInactive = new Spinner<Double>();
		sInactive.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 24 * 60, Settings.saving.inactivityTime));
		sInactive.valueProperty().addListener(o -> Settings.saving.inactivityTime = sInactive.getValue());
		sInactive.setEditable(true);
		sInactive.setPrefWidth(75);
		sInactive.setTooltip(new Tooltip("Number of minutes after the last edit before saving the build"));

		CheckBox cInactive = new CheckBox("Save when Inactive (Minutes)");
		cInactive.setTooltip(new Tooltip("Save everything in the build after the a set timeframe after you last edit"));
		cInactive.setSelected(Settings.saving.inactivityTime > 0);
		cInactive.selectedProperty().addListener(o -> {
			if(!cInactive.isSelected()) sInactive.getValueFactory().setValue((double) 0);
		});
		sInactive.disableProperty().bind(cInactive.selectedProperty().not());

		HBox hInactive = new HBox(cInactive, sInactive);
		hInactive.setAlignment(Pos.CENTER_LEFT);
		hInactive.setSpacing(5);

		Spinner<Double> sPeriod = new Spinner<Double>();
		sPeriod.setTooltip(new Tooltip("Number of minutes between saves"));
		sPeriod.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 200, Settings.saving.periodicalTime));
		sPeriod.valueProperty().addListener(o -> Settings.saving.periodicalTime = sPeriod.getValue());
		sPeriod.setEditable(true);
		sPeriod.setPrefWidth(75);

		CheckBox cPeriod = new CheckBox("Save regularly (Minutes)");
		cPeriod.setTooltip(new Tooltip("Save the document every set number of minutes\nwhether you edited or not"));
		cPeriod.setSelected(Settings.saving.periodicalTime > 0);
		cPeriod.selectedProperty().addListener(o -> {
			if(!cPeriod.isSelected()) sPeriod.getValueFactory().setValue((double) 0);
		});
		sPeriod.disableProperty().bind(cPeriod.selectedProperty().not());

		HBox hPeriod = new HBox(cPeriod, sPeriod);
		hPeriod.setAlignment(Pos.CENTER_LEFT);
		hPeriod.setSpacing(5);

		content.getChildren().add(settingSection("Auto Saving", Arrays.asList(hInactive, hPeriod), null));

		r.setContent(content);
		return r;
	}

	private static settingsPage pageImages() {
		settingsPage r = new settingsPage("Images");

		VBox content = new VBox();
		content.setPadding(new Insets(10));
		content.setSpacing(10);

		CheckBox cStoreLocalImages = new CheckBox("Store Imported Images locally");
		cStoreLocalImages.setSelected(Settings.images.storeLocal);
		cStoreLocalImages.selectedProperty().addListener(a -> Settings.images.storeLocal = cStoreLocalImages.isSelected());

		Button bLocalizeImages = new Button("Localize Images");
		bLocalizeImages.setOnAction(e -> Images.localizeImages());

		Button clearNull = new Button("Clear broken references");
		clearNull.setOnAction(e -> Images.verifyImages());

		content.getChildren().add(settingSection("Images", Arrays.asList(cStoreLocalImages), Arrays.asList(bLocalizeImages, clearNull)));

		r.setContent(content);

		return r;
	}

	private static settingsPage pageItems() {
		settingsPage r = new settingsPage("Items");

		VBox content = new VBox();
		content.setPadding(new Insets(10));
		content.setSpacing(10);

		CheckBox warnOnDelete = new CheckBox("Warn on deleting an item");
		warnOnDelete.setSelected(Settings.items.warnOnDelete);
		warnOnDelete.selectedProperty().addListener((e, o, n) -> Settings.items.warnOnDelete = n);

		CheckBox deleteImages = new CheckBox("Delete Images with item");
		deleteImages.setTooltip(new Tooltip("Will not delete any image that is part of your system\nonly item references and application-stored images"));
		deleteImages.setSelected(Settings.items.deleteImages);
		deleteImages.selectedProperty().addListener((e, o, n) -> Settings.items.deleteImages = n);

		content.getChildren().add(settingSection("Deleting", Arrays.asList(warnOnDelete, deleteImages), null));

		r.setContent(content);

		return r;
	}

	private static settingsPage pageAdvanced() {
		settingsPage r = new settingsPage("Advanced");

		VBox content = new VBox();
		content.setPadding(new Insets(10));
		content.setSpacing(10);

		CheckBox cDebug = new CheckBox("Show Crash Reports");
		cDebug.setTooltip(new Tooltip("On a crash, display a dialog that includes the crash report\nas well as a button to add an issue to the github"));
		cDebug.setSelected(Settings.advanced.debug.showCrashReports);
		cDebug.selectedProperty().addListener(a -> Settings.advanced.debug.showCrashReports = cDebug.isSelected());

		content.getChildren().add(settingSection("Debug Mode", Arrays.asList(cDebug), null));

		r.setContent(content);

		return r;
	}

	private static VBox settingSection(String name, List<Node> options, List<Node> display) {
		VBox r = new VBox();
		r.setSpacing(10);

		Text header = new Text(name);
		header.setFont(new Font(20));

		HBox row = new HBox();
		row.setSpacing(10);

		if(options != null) {
			VBox lOptions = new VBox();
			lOptions.setSpacing(10);
			lOptions.getChildren().addAll(options);
			row.getChildren().add(lOptions);
		}

		if(display != null) {
			VBox lDisplay = new VBox();
			lDisplay.setSpacing(10);
			lDisplay.getChildren().addAll(display);
			row.getChildren().add(lDisplay);
		}

		r.getChildren().addAll(header, row);

		return r;
	}

	public static class settingsPage extends ScrollPane {
		private String name;

		public settingsPage(String Name) {
			super();
			super.setHbarPolicy(ScrollBarPolicy.NEVER);
			super.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			name = Name;
		}

		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
}
