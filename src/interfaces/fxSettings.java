package interfaces;

import classes.Dice;
import classes.Images;
import classes.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.resource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class fxSettings {

	public static Stage stage;

	private static BorderPane content;

	public static void open() {

		if (stage != null && stage.isShowing()) {
			stage.requestFocus();
			return;
		}

		stage = new Stage();
		stage.setTitle("Settings");
		stage.setOnCloseRequest(e -> Settings.saveSettings());

		ObservableList<settingsPage> pages = FXCollections.observableArrayList();

		pages.addAll(pageAppearance(), pageSaving(), pageItems(), pagePorting(), pageAdvanced());

		// http://www.java2s.com/Code/Java/JavaFX/ListViewselectionlistener.htm
		ListView<settingsPage> pageSelection = new ListView<>(pages);
		pageSelection.setPrefWidth(150);
		pageSelection.getSelectionModel().selectedItemProperty().addListener((e, o, n) -> content.setCenter(n));
		pageSelection.setCellFactory(new Callback<>() {

			@Override
			public ListCell<settingsPage> call(ListView<settingsPage> param) {
				final Label leadLbl = new Label();
				final Tooltip tooltip = new Tooltip();
				return new ListCell<>() {

					@Override
					public void updateItem(settingsPage item, boolean empty) {
						super.updateItem(item, empty);

						if (item != null) {
							leadLbl.setText(item.getName());
							setText(item.getName());
							tooltip.setText(item.getName());
							setTooltip(tooltip);
						}

					}
				};
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
			if (!n) cShowRange.setSelected(true);
			Settings.appearance.dice.showDice = n;
			updateDisplay.apply("");
		});
		cShowRange.selectedProperty().addListener((obs, o, n) -> {
			if (!n) cShowDice.setSelected(true);
			Settings.appearance.dice.showRange = n;
			updateDisplay.apply("");
		});

		CheckBox cCompactDice = new CheckBox("Compact Dice Format");
		cCompactDice.setTooltip(new Tooltip("Compact view of the D&D Dice format\nRemoves all spaces"));
		cCompactDice.setSelected(Settings.appearance.dice.compactDice);
		cCompactDice.selectedProperty().addListener(o -> {
			Settings.appearance.dice.compactDice = cCompactDice.isSelected();
			updateDisplay.apply("");
		});

		r.addSection("Dice Format", Arrays.asList(cShowDice, cShowRange, new Separator(), cCompactDice), Collections.singletonList(diceDisplay));

		// ICONS

		ImageView image = new ImageView();
		image.setImage(resource.getImage("sample_icon.png"));
		Function<Double, Double> updateIcon = a -> {
			image.setFitHeight(a);
			image.setFitWidth(a);
			return a;
		};

		Spinner<Double> sIconSize = new Spinner<>();
		sIconSize.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 300, Settings.appearance.icon.size));
		sIconSize.valueProperty().addListener((e, o, n) -> {
			Settings.appearance.icon.size = n;
			updateIcon.apply(n);
		});
		sIconSize.setEditable(true);
		sIconSize.setPrefWidth(75);

		r.addSection("Icon", Collections.singletonList(sIconSize), Collections.singletonList(image));

		return r.toPage();
	}

	private static settingsPage pageSaving() {
		settingsPage r = new settingsPage("Saving");

		// REGULAR SAVING

		Spinner<Double> sInactive = new Spinner<>();
		sInactive.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 24 * 60, Settings.saving.inactivityTime));
		sInactive.valueProperty().addListener(o -> Settings.saving.inactivityTime = sInactive.getValue());
		sInactive.setEditable(true);
		sInactive.setPrefWidth(75);
		sInactive.setTooltip(new Tooltip("Number of minutes after the last edit before saving the build"));

		CheckBox cInactive = new CheckBox("Save when Inactive (Minutes)");
		cInactive.setTooltip(new Tooltip("Save everything in the build after the a set timeframe after you last edit"));
		cInactive.setSelected(Settings.saving.inactivityTime > 0);
		cInactive.selectedProperty().addListener(o -> {
			if (!cInactive.isSelected()) sInactive.getValueFactory().setValue((double) 0);
		});
		sInactive.disableProperty().bind(cInactive.selectedProperty().not());

		HBox hInactive = new HBox(cInactive, sInactive);
		hInactive.setAlignment(Pos.CENTER_LEFT);
		hInactive.setSpacing(5);

		Spinner<Double> sPeriod = new Spinner<>();
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

		r.addSection("Auto Saving", Arrays.asList(hInactive, hPeriod), null);

		// IMAGES

		CheckBox cStoreLocalImages = new CheckBox("Store Imported Images locally");
		cStoreLocalImages.setSelected(Settings.saving.images.storeLocal);
		cStoreLocalImages.selectedProperty().addListener(a -> Settings.saving.images.storeLocal = cStoreLocalImages.isSelected());

		Button bLocalizeImages = new Button("Localize Images");
		bLocalizeImages.setOnAction(e -> Images.localizeImages());

		r.addSection("Images", Collections.singletonList(cStoreLocalImages), Collections.singletonList(bLocalizeImages));

		return r.toPage();
	}

	private static settingsPage pagePorting() {
		settingsPage r = new settingsPage("Import / Export");

		CheckBox includeImages = new CheckBox("Include Images");
		includeImages.setSelected(Settings.porting.exporting.includeImages);
		includeImages.selectedProperty().addListener((e, o, n) -> Settings.porting.exporting.includeImages = n);

		r.addSection("Items", Collections.singletonList(includeImages), null);

		return r.toPage();
	}

	private static settingsPage pageItems() {
		settingsPage r = new settingsPage("Items");

		CheckBox warnOnDelete = new CheckBox("Warn on deleting an item");
		warnOnDelete.setSelected(Settings.items.warnOnDelete);
		warnOnDelete.selectedProperty().addListener((e, o, n) -> Settings.items.warnOnDelete = n);

		CheckBox deleteImages = new CheckBox("Delete Images with item");
		deleteImages.setTooltip(new Tooltip("Will not delete any image that is part of your system\nonly item references and application-stored images"));
		deleteImages.setSelected(Settings.items.deleteImages);
		deleteImages.selectedProperty().addListener((e, o, n) -> Settings.items.deleteImages = n);

		r.addSection("Deleting", Arrays.asList(warnOnDelete, deleteImages), null);

		return r.toPage();
	}

	private static settingsPage pageAdvanced() {
		settingsPage r = new settingsPage("Advanced");

		CheckBox cDebug = new CheckBox("Show Crash Reports");
		cDebug.setTooltip(new Tooltip("On a crash, display a dialog that includes the crash report\nas well as a button to add an issue to the github"));
		cDebug.setSelected(Settings.advanced.debug.showCrashReports);
		cDebug.selectedProperty().addListener(a -> Settings.advanced.debug.showCrashReports = cDebug.isSelected());

		r.addSection("Debug Mode", Collections.singletonList(cDebug), null);

		return r.toPage();
	}

	public static class settingsPage extends ScrollPane {

		private final String name;

		private final VBox content;

		public settingsPage(String Name) {
			super();
			super.setHbarPolicy(ScrollBarPolicy.NEVER);
			super.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			content = new VBox();
			content.setSpacing(10);
			content.setPadding(new Insets(10));
			name = Name;
		}

		public void addSection(String name, List<Node> options, List<Node> display) {
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

			VBox r = new VBox(header, row);
			r.setSpacing(10);
			content.getChildren().add(r);
		}

		public settingsPage toPage() {
			this.setContent(content);
			return this;
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
