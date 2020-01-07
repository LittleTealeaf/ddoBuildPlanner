package fxTabs;

import java.util.ArrayList;
import java.util.List;
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
import classes.Settings;
import interfaces.ItemPrompt;
import interfaces.fxEditItem;
import interfaces.fxMain;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import vars.GearSlot;

/**
 * This class is the display/ui used for the gearsets tab of the main application
 * 
 * @author Tealeaf
 */
public class Gearsets {

	private static Tab tab;

	// private static GridPane grid;
	private static ListView<slotSelection> slotList;
	private static GridPane itemGrid;
	private static slotSelection displayItem;

	private static Build build;

	public static Tab getTab() {
		build = Main.loadedBuild;

		tab = new Tab("Gearsets");
		
		

		BorderPane content = new BorderPane();
		content.setPadding(new Insets(15));
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

	/**
	 * The content of the top portion of the tab display
	 * 
	 * @return GridPane content to show
	 */
	private static GridPane gridTop() {
		GridPane r = new GridPane();
		r.setHgap(10);
		r.setPadding(new Insets(10));

		ComboBox<Gearset> choice = new ComboBox<Gearset>();

		choice.setItems(FXCollections.observableArrayList(build.getGearsets()));
		choice.getSelectionModel().select(build.getCurrentGearset());
		choice.getSelectionModel().selectedItemProperty().addListener((e, o, n) -> {
			build.setCurrentGearset(n);
			updateContent();
		});

		Button bDelete = new Button("Delete");
		bDelete.setVisible(build.getGearsets().size() > 1);
		bDelete.setOnAction(e -> {
			// Create the alert dialog
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Gearset " + build.getCurrentGearset().getName());
			alert.setHeaderText("Do you really want to delete the gearset " + build.getCurrentGearset().getName() + "?");
			Optional<ButtonType> option = alert.showAndWait();

			if(option.get() == ButtonType.OK) {
				// Remove the gearset
				build.removeGearset(build.getCurrentGearset());

				// Update Display
				choice.setItems(FXCollections.observableArrayList(build.getGearsets()));
				bDelete.setVisible(build.getGearsets().size() > 1);
				choice.getSelectionModel().select(build.getCurrentGearset());
			}

		});

		Button bCreate = new Button("Create");
		bCreate.setOnAction(e -> {
			// Get a name prompt
			String name = namePrompt("Create Gearset");

			// If didn't cancel, add the gearset
			if(name != null) build.addGearset(new Gearset(name));

			// Update Display
			choice.setItems(FXCollections.observableArrayList(build.getGearsets()));
			choice.getSelectionModel().select(build.getCurrentGearset());
			bDelete.setVisible(build.getGearsets().size() > 1);
		});

		Button bRename = new Button("Rename");
		bRename.disableProperty().bind(choice.getSelectionModel().selectedItemProperty().isNull());
		bRename.setOnAction(e -> {
			// Get a name
			String name = namePrompt("Rename " + build.getCurrentGearset());

			// If didn't cancel, update the name
			if(name != null) build.getCurrentGearset().setName(name);

			// Update Display
			choice.setItems(FXCollections.observableArrayList(build.getGearsets()));
		});

		r.add(choice, 0, 0);
		r.add(bCreate, 1, 0);
		r.add(bRename, 2, 0);
		r.add(bDelete, 3, 0);

		return r;
	}

	private static HBox gridContent() {
		slotList = new ListView<slotSelection>();
		slotList.setCellFactory(new Callback<ListView<slotSelection>, ListCell<slotSelection>>() {

			@Override
			public ListCell<slotSelection> call(ListView<slotSelection> listView) {
				return new listCell();
			}
		});
		slotList.getSelectionModel().selectedItemProperty().addListener((e, o, n) -> displayItem(n));
		slotList.prefHeightProperty().bind(fxMain.sMainRef.heightProperty().multiply(0.7));
		slotList.setPrefWidth(400);

		itemGrid = new GridPane();
		itemGrid.setPadding(new Insets(10));
		itemGrid.setHgap(5);
		itemGrid.setVgap(5);
		
		TabPane tabs = new TabPane();
		tabs.getTabs().add(breakdownTab());

		VBox vb = new VBox(itemGrid,tabs);
		vb.setSpacing(5);
		vb.setPadding(new Insets(5));

		HBox r = new HBox(slotList, vb);
		r.setPadding(new Insets(10));
		r.setSpacing(5);

		updateContent();

		return r;
	}
	
	private static Tab breakdownTab() {
		Tab r = new Tab("Gear Breakdowns");
		r.setClosable(false);
		
		
		
		return r;
	}

	private static void updateContent() {

		if(build.getCurrentGearset() != null) {
			List<slotSelection> slots = new ArrayList<slotSelection>();

			for(GearSlot s : GearSlot.values()) slots.add(new slotSelection(s, build.getCurrentGearset().getItemBySlot(s)));

			slotList.setItems(FXCollections.observableArrayList(slots));

			// Updates display item
			if(displayItem != null) {
				for(slotSelection s : slots) if(displayItem.getSlot() == s.getSlot()) displayItem(s);
			}

		}

	}

	private static void displayItem(slotSelection slotsel) {
		if(slotsel != null) displayItem = slotsel;

		itemGrid.getChildren().clear();

		Button bSelect = new Button("Select " + displayItem.getSlot().displayName());
		bSelect.setDisable(build.getCurrentGearset() == null);
		bSelect.setOnAction(e -> {
			Item i = new ItemPrompt().setSlot(displayItem.getSlot().getItemSlot()).setItem(build.getCurrentGearset().getItemBySlot(displayItem.getSlot())).showPrompt();

			if(i != null) {
				build.getCurrentGearset().setItemBySlot(i, displayItem.getSlot());
				updateContent();
			}

		});
		
		HBox buttons = new HBox(bSelect);
		buttons.setSpacing(5);
		buttons.setPadding(new Insets(5));

		if(displayItem.getIref() != null) {

			try {
				itemGrid.add(displayItem.getIref().getItem().getIconViewSmall(), 0, 0);
			} catch(Exception e) {}

			Item item = displayItem.getIref().getItem();

			Text itemName = new Text(item.getName());
			itemName.setFont(new Font(14));

			Text enchantments = new Text();

			for(Enchref e : displayItem.getIref().getItem().getEnchantments()) {
				enchantments.setText(enchantments.getText() + e.getDisplayName() + "\n");
			}
			
			VBox attributeField = new VBox(enchantments);
			attributeField.setSpacing(2.5);
			
			for(Craftref cref : displayItem.getIref().getCrafting()) {
				// Craft Display
				HBox h = new HBox(new Text(item.getCraft(cref.getUUID()).getName()), new craftingChoice(displayItem.getIref(), cref).toComboBox());
				h.setSpacing(2.5);
				attributeField.getChildren().add(h);
			}
			
			
			itemGrid.add(itemName, 1, 0);
			itemGrid.add(buttons, 2, 0);
			itemGrid.add(attributeField, 1, 1);
			

		} else {
			itemGrid.add(buttons, 0, 0);
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

	/**
	 * This class is a representation of a craftign choice used to display
	 * 
	 * @author Tealeaf
	 */
	private static class craftingChoice extends ComboBox<Enchref> {

		private Craftref ref;
		private Craftable craftable;

		public craftingChoice(Iref iref, Craftref ref) {
			super();
			this.ref = ref;
			this.craftable = iref.getItem().getCraft(ref.getUUID());
		}

		/**
		 * Converts the crafting choice to a combo box
		 * 
		 * @return ComboBox of the crafting choice
		 */
		public ComboBox<Enchref> toComboBox() {
			this.getItems().addAll(craftable.getChoices());
			this.getSelectionModel().select(craftable.getChoice(ref.getIndex()));
			this.getSelectionModel().selectedIndexProperty().addListener((e, o, n) -> ref.setIndex(n.intValue()));

			return this;
		}
	}

	/**
	 * Custom cell used to display on the item selection
	 * 
	 * @see https://stackoverflow.com/questions/27438629/listview-with-custom-content-in-javafx
	 * @author Tealeaf
	 */
	private static class listCell extends ListCell<slotSelection> {

		private HBox content;
		private ImageView image;
		private Text name;
		private Text slot;

		public listCell() {
			super();

			image = new ImageView();
			image.setPreserveRatio(true);
			image.setFitWidth(Settings.appearance.icon.size);
			image.setFitHeight(Settings.appearance.icon.size);

			name = new Text();
			name.setFont(new Font(12));

			slot = new Text();
			slot.setFont(new Font(10));

			VBox vb = new VBox(name, slot);
			vb.setSpacing(5);

			content = new HBox(image, vb);
			content.setSpacing(7.5);
		}

		@Override
		protected void updateItem(slotSelection item, boolean empty) {
			super.updateItem(item, empty);

			if(item != null && !empty) {
				// <== test for null item and empty parameter

				slot.setText(item.getSlot().displayName());

				if(item.getIref() != null) {

					try {
						name.setText(item.getIref().getItem().getName());
						image.setImage(item.getIref().getItem().getIcon());
					} catch(Exception e) {}

				} else {
					name.setText("");
					image.setImage(null);
				}

				setGraphic(content);
			} else {
				setGraphic(null);
			}

		}
	}

	/**
	 * Class that contains an iref and the slot it was specified with inside the gearset
	 * 
	 * @author Tealeaf
	 * @see Iref
	 * @see GearSlot
	 */
	private static class slotSelection {

		private GearSlot slot;
		private Iref iref;

		public slotSelection(GearSlot slot, Iref iref) {
			this.slot = slot;
			this.iref = iref;
		}

		public GearSlot getSlot() {
			return slot;
		}

		public Iref getIref() {
			return iref;
		}
	}
}
