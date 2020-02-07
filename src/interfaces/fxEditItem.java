package interfaces;

import classes.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.system;
import vars.Ability;
import vars.ItemSlot;

import java.util.Arrays;

public class fxEditItem {

	public static Stage stage;

	private static Item item;

	private static TextField itemName;
	private static Label errorText;
	private static ImageView iImage;
	private static ListView<Enchref> enchantmentView;
	private static ListView<Craftable> crafting;

	public static void open() {
		open((Item) null);
	}

	public static void open(Iref i) {
		open(i.getItem());
	}

	public static void open(Item i) {
		item = i;

		if(stage != null && stage.isShowing()) stage.close();
		stage = new Stage();

		if(i != null) {
			stage.setTitle("Editing " + i.getName());
			Image icon = item.getIcon();
			if(icon != null) stage.getIcons().add(icon);
		} else {
			stage.setTitle("Create Item");
			item = new Item();
		}

		// Page Contents

		BorderPane content = new BorderPane();
		content.setTop(contentHeader());
		content.setCenter(new HBox(contentCenter(), contentRight()));
		content.setBottom(contentFooter());

		// Setting up the stage

		stage.setScene(new Scene(content, 1200, 600));
		stage.show();
	}

	private static void saveItem() {
		errorText.setText("");

		if(itemName.getText().contentEquals("")) {
			// If it didn't save
			errorText.setText("Please include a name");
			itemName.requestFocus();
			return;
		} else {
			item.saveItem();
		}

		stage.close();
	}

	/**
	 * Content of the header
	 * 
	 * @return HBox of the header content
	 */
	private static HBox contentHeader() {
		HBox r = new HBox();
		r.setSpacing(10);
		r.setPadding(new Insets(10));

		Label labelName = new Label("Item Name:");

		itemName = new TextField(item.getName());
		itemName.textProperty().addListener((e, o, n) -> item.setName(n));
		HBox.setHgrow(itemName, Priority.ALWAYS);

		errorText = new Label("");
		errorText.setTextFill(Color.RED);

		Button openJSON = new Button("Open .JSON");
		openJSON.setOnAction(e -> system.openExtFile(Items.getFile(item.getUUID())));

		Button export = new Button("Export");
		export.setOnAction(e -> Porting.exportItem(item));

		r.getChildren().addAll(labelName, itemName, errorText, openJSON, export);

		return r;
	}

	/**
	 * Content of the footer
	 * 
	 * @return HBox of the footer content
	 */
	private static HBox contentFooter() {
		HBox r = new HBox();
		r.setSpacing(10);
		r.setPadding(new Insets(10));

		Button save = new Button("Save");
		save.setOnAction(e -> saveItem());

		r.getChildren().add(save);

		return r;
	}

	/**
	 * Content of the center
	 * 
	 * @return Grid Pane of the center content
	 */
	private static GridPane contentCenter() {
		GridPane r = new GridPane();
		r.setHgap(10);
		r.setVgap(10);
		r.setPadding(new Insets(10));
		r.setAlignment(Pos.TOP_CENTER);

		// Item Type
		Text tType = new Text("Type:");
		TextField type = new TextField();
		type.setText(item.getType());
		type.textProperty().addListener((e, o, n) -> item.setType(n));

		// Item Proficiency
		Text tProficiency = new Text("Proficiency:");
		TextField proficiency = new TextField();
		proficiency.setText(item.getProficiency());
		proficiency.textProperty().addListener((e, o, n) -> item.setProficiency(n));

		// Item Minimum Level
		Text tMinLevel = new Text("Minimum Level:");
		Spinner<Integer> minLevel = new Spinner<>();
		minLevel.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, item.getMinLevel()));
		minLevel.valueProperty().addListener((e, o, n) -> item.setMinLevel(n));
		minLevel.setEditable(true);
		minLevel.setPrefWidth(100);
		minLevel.setMinWidth(75);

		// Item Absolute Minimum Level
		Text tAbsMinLevel = new Text("Absolute Minimum Level:");
		Spinner<Integer> absMinLevel = new Spinner<>();
		absMinLevel.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, item.getAbsoluteMinLevel()));
		absMinLevel.valueProperty().addListener((e, o, n) -> item.setAbsoluteMinLevel(n));
		absMinLevel.setEditable(true);
		absMinLevel.setPrefWidth(100);
		absMinLevel.setMinWidth(75);

		// Item Bind Status
		Text tBindStatus = new Text("Bind Status:");
		// TODO change to enumerator (also in the item class)
		ChoiceBox<String> bindStatus = new ChoiceBox<>(FXCollections.observableList(Arrays.asList("Unbound", "Bound to Account on Acquire", "Bound to Account on Equip", "Bound to Character on Acquire", "Bound to Character on Equip")));
		bindStatus.setValue(item.getBindStatus());
		bindStatus.valueProperty().addListener((e, o, n) -> item.setBindStatus(n));

		// Set Icon button
		Button bSetIcon = new Button("Set Icon");
		bSetIcon.setOnAction(e -> {
			item.setIconUUID(new Images.ImagePrompt(item.getIconUUID()).showPrompt());
			if (item.getIcon() != null) stage.getIcons().add(item.getIcon());
			else stage.getIcons().clear();
		});

		// Set Image Button
		Button bSetImage = new Button("Set Image");
		bSetImage.setOnAction(e -> {
			item.setImageUUID(new Images.ImagePrompt(item.getImageUUID()).showPrompt());
			iImage.setImage(item.getImage());
		});

		// Item Material
		Text tMaterial = new Text("Material:");
		TextField material = new TextField();
		material.setText(item.getMaterial());
		material.textProperty().addListener((e, o, n) -> item.setMaterial(n));

		// Item Weight
		Text tWeight = new Text("Weight (lbs):");
		Spinner<Double> weight = new Spinner<>();
		weight.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100000, item.getWeight()));
		weight.valueProperty().addListener((e, o, n) -> item.setWeight(n));
		weight.setPrefWidth(100);
		weight.setMinWidth(75);
		weight.setEditable(true);

		// Item Hardness
		Text tHardness = new Text("Hardness:");
		Spinner<Double> hardness = new Spinner<>();
		hardness.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10000, item.getHardness()));
		hardness.valueProperty().addListener((e, o, n) -> item.setHardness(n));
		hardness.setPrefWidth(100);
		hardness.setMinWidth(75);
		hardness.setEditable(true);

		// Item Durability
		Text tDurability = new Text("Durability:");
		Spinner<Double> durability = new Spinner<>();
		durability.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10000, item.getDurability()));
		durability.valueProperty().addListener((e, o, n) -> item.setDurability(n));
		durability.setPrefWidth(100);
		durability.setMinWidth(75);
		durability.setEditable(true);

		r.add(tType, 0, 0);
		r.add(type, 1, 0);
		r.add(tMinLevel, 2, 0);
		r.add(minLevel, 3, 0);

		r.add(tProficiency, 0, 1);
		r.add(proficiency, 1, 1);
		r.add(tAbsMinLevel, 2, 1);
		r.add(absMinLevel, 3, 1);

		r.add(tBindStatus, 0, 2);
		r.add(bindStatus, 1, 2);
		r.add(bSetIcon, 2, 2);
		r.add(bSetImage, 3, 2);

		r.add(tMaterial, 0, 3);
		r.add(material, 1, 3);
		r.add(tWeight, 2, 3);
		r.add(weight, 3, 3);

		r.add(tHardness, 0, 4);
		r.add(hardness, 1, 4);
		r.add(tDurability, 2, 4);
		r.add(durability, 3, 4);

		return r;
	}

	/**
	 * Content of the accordion pane on the right of the image editing screen
	 * 
	 * @return
	 */
	private static Accordion contentRight() {
		Accordion r = new Accordion();

		TitledPane expandedPane = contentDescription();

		r.getPanes().addAll(contentEquipSlots(), contentEnchantments(), contentCrafting(), contentWeapon(), contentArmor(), expandedPane, contentImage());
		r.setExpandedPane(expandedPane);

		return r;
	}

	private static TitledPane contentEquipSlots() {
		TitledPane r = new TitledPane();
		r.setExpanded(false);
		r.setText("Equip Slots");

		FlowPane content = new FlowPane();
		content.setHgap(10);
		content.setVgap(10);

		for(ItemSlot s : ItemSlot.values()) {
			CheckBox checkBox = new CheckBox(s.toString());
			checkBox.setSelected(item.hasEquipSlot(s));
			checkBox.selectedProperty().addListener((e, o, n) -> item.setEquipSlot(s, n));
			content.getChildren().add(checkBox);
		}

		r.setContent(content);

		return r;
	}

	private static TitledPane contentEnchantments() {
		TitledPane r = new TitledPane();
		r.setText("Enchantments");

		enchantmentView = new ListView<>();
		enchantmentView.setItems(FXCollections.observableArrayList(item.getEnchantments()));
		enchantmentView.setOnMouseClicked(click -> {

			if (click.getClickCount() == 2) {
				Enchref i = enchantmentView.getSelectionModel().getSelectedItem();
				Enchref n = Enchantments.enchrefDialog(i);
				if (n != null) item.updateEnchantment(i, n);
				enchantmentView.setItems(FXCollections.observableArrayList(item.getEnchantments()));
			}

		});
		enchantmentView.setCellFactory(param -> new ListCell<>() {

			protected void updateItem(Enchref item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null || item.getDisplayName() == null) setText(null);
				else setText(item.getDisplayName());
			}
		});

		Button bCreate = new Button("Create");
		bCreate.setOnAction(e -> {
			Enchref ench = Enchantments.enchrefDialog();
			if(ench != null) item.addEnchantment(ench);
			enchantmentView.setItems(FXCollections.observableArrayList(item.getEnchantments()));
		});

		Button bDelete = new Button("Delete");
		bDelete.disableProperty().bind(enchantmentView.selectionModelProperty().isNull());
		bDelete.setOnAction(e -> {
			item.removeEnchantment(enchantmentView.getSelectionModel().getSelectedItem());
			enchantmentView.setItems(FXCollections.observableArrayList(item.getEnchantments()));
		});

		HBox buttons = new HBox(bCreate, bDelete);
		buttons.setSpacing(10);

		BorderPane content = new BorderPane();
		content.setCenter(enchantmentView);
		content.setBottom(buttons);

		r.setContent(content);

		return r;
	}

	private static TitledPane contentCrafting() {
		TitledPane r = new TitledPane();
		r.setText("Crafting");

		crafting = new ListView<>();

		crafting.setOnMouseClicked(e -> {

			if (e.getClickCount() == 2) {
				item.updateCraftable(crafting.getSelectionModel().getSelectedItem(), fxEditCraftable.openEditor(crafting.getSelectionModel().getSelectedItem()));
				crafting.setItems(FXCollections.observableArrayList(item.getCrafting()));
			}

		});

		crafting.setCellFactory(param -> new ListCell<>() {

			protected void updateItem(Craftable item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null || item.getName() == null) setText(null);
				else setText(item.getName());
			}
		});

		crafting.setItems(FXCollections.observableArrayList(item.getCrafting()));

		Button create = new Button("Create");
		create.setOnAction(e -> {
			item.addCraftable(fxEditCraftable.openEditor());
			crafting.setItems(FXCollections.observableArrayList(item.getCrafting()));
		});

		Button delete = new Button("Delete");
		delete.setOnAction(e -> {
			item.removeCraftable(crafting.getSelectionModel().getSelectedItem());
			crafting.setItems(FXCollections.observableArrayList(item.getCrafting()));
		});

		HBox buttons = new HBox(create, delete);
		buttons.setSpacing(10);

		BorderPane con = new BorderPane();
		con.setCenter(crafting);
		con.setBottom(buttons);

		r.setContent(con);

		return r;
	}

	private static TitledPane contentWeapon() {
		TitledPane r = new TitledPane();
		r.setText("Weapon Stats");
		r.setCollapsible(true);

		Text tDice = new Text("Damage:");

		TextField dice = new TextField();
		dice.setTooltip(new Tooltip("The damage of the weapon in terms of the damage dice:\nValid entries can include:\n1d5\n1d5+5\n1d5 + 5\n5[1d5+5]\n5 [1d5 + 5] + 5"));
		dice.setText(item.getDamage().toEditString());
		dice.setOnKeyPressed(key -> {

//TODO edit the format to not include this?!
			if(key.getCode() == KeyCode.ENTER) {
				Dice d = new Dice(dice.getText());
				if(!d.isDefault()) item.setDamage(d);
				dice.setText(item.getDamage().toEditString());
			}

		});
		dice.focusedProperty().addListener((e, o, n) -> {

			if (!n) {
				Dice d = new Dice(dice.getText());
				if (!d.isDefault()) item.setDamage(d);
				dice.setText(item.getDamage().toEditString());
			}

		});

		Text tLowRoll = new Text("Low Crit Roll:");

		Spinner<Integer> lowRoll = new Spinner<>();
		lowRoll.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, item.getLowCritRoll()));
		lowRoll.valueProperty().addListener((e, o, n) -> item.setLowCritRoll(n));
		lowRoll.setPrefWidth(75);
		lowRoll.setEditable(true);

		Text tCritMultiplier = new Text("Crit Multiplier:");

		Spinner<Double> critMultiplier = new Spinner<>();
		critMultiplier.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 1000, item.getCritMultiplier()));
		critMultiplier.valueProperty().addListener((e, o, n) -> item.setCritMultiplier(n));
		critMultiplier.setPrefWidth(75);
		critMultiplier.setEditable(true);

		FlowPane attackPane = new FlowPane();
		attackPane.getChildren().add(new Text("Attack Modifiers:"));
		attackPane.setHgap(10);
		attackPane.setVgap(7);

		for(Ability a : Ability.values()) {
			CheckBox check = new CheckBox(a.displayName());
			check.setSelected(item.hasAttackModifier(a));
			check.selectedProperty().addListener((e, o, n) -> item.setAttackModifier(a, n));
			attackPane.getChildren().add(check);
		}

		FlowPane damagePane = new FlowPane();
		damagePane.getChildren().add(new Text("Damage Modifiers:"));
		damagePane.setHgap(10);
		damagePane.setVgap(7);

		for(Ability a : Ability.values()) {
			CheckBox check = new CheckBox(a.displayName());
			check.setSelected(item.hasDamageModifier(a));
			check.selectedProperty().addListener((e, o, n) -> item.setDamageModifier(a, n));
			damagePane.getChildren().add(check);
		}

		Text tDamageTypes = new Text("Damage Types");

		TextArea damageTypes = new TextArea();
		damageTypes.setText(item.getDamageTypeText());
		damageTypes.textProperty().addListener((e, o, n) -> item.setDamageTypesText(n));
		damageTypes.setPrefWidth(150);

		VBox vTypes = new VBox(tDamageTypes, damageTypes);
		vTypes.setSpacing(10);

		GridPane content = new GridPane();
		content.setHgap(10);
		content.setVgap(10);
		content.setPadding(new Insets(10));

		content.add(tDice, 0, 0);
		content.add(dice, 1, 0);
		content.add(tLowRoll, 0, 1);
		content.add(lowRoll, 1, 1);
		content.add(tCritMultiplier, 0, 2);
		content.add(critMultiplier, 1, 2);

		VBox leftContent = new VBox(content, attackPane, damagePane);
		leftContent.setSpacing(10);

		HBox contentMerge = new HBox(leftContent, vTypes);
		contentMerge.setSpacing(10);

		r.setContent(contentMerge);

		return r;
	}

	private static TitledPane contentArmor() {
		TitledPane r = new TitledPane();
		r.setText("Armor Stats");

		GridPane content = new GridPane();
		content.setHgap(10);
		content.setVgap(10);
		content.setPadding(new Insets(10));

		Text tArmorType = new Text("Armor Type");

		ChoiceBox<String> armorType = new ChoiceBox<>(FXCollections.observableArrayList("", "Armor", "Shield"));
		armorType.getSelectionModel().select(item.getArmorType());
		armorType.getSelectionModel().selectedItemProperty().addListener((e, o, n) -> item.setArmorType(n));

		Text tArmorBonus = new Text("Armor Bonus");

		Spinner<Integer> armorBonus = new Spinner<>();
		armorBonus.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, item.getArmorBonus()));
		armorBonus.valueProperty().addListener((e, o, n) -> item.setArmorBonus(n));
		armorBonus.setPrefWidth(75);
		armorBonus.setEditable(true);

		Text tMaxDex = new Text("Max Dex Bonus");

		Spinner<Integer> maxDex = new Spinner<>();
		maxDex.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-1, 1000, item.getMaxDex()));
		maxDex.valueProperty().addListener((e, o, n) -> item.setMaxDex(n));
		maxDex.setPrefWidth(75);
		maxDex.setEditable(true);

		Text tCheckPenalty = new Text("Armor Check Penalty");

		Spinner<Integer> checkPenalty = new Spinner<>();
		checkPenalty.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, item.getCheckPenalty()));
		checkPenalty.valueProperty().addListener((e, o, n) -> item.setCheckPenalty(n));
		checkPenalty.setPrefWidth(75);
		checkPenalty.setEditable(true);

		Text tAttackPenalty = new Text("Attack Penalty");

		Spinner<Integer> attackPenalty = new Spinner<>();
		attackPenalty.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, item.getAttackPenalty()));
		attackPenalty.valueProperty().addListener((e, o, n) -> item.setAttackPenalty(n));
		attackPenalty.setPrefWidth(75);
		attackPenalty.setEditable(true);

		Text tSpellFailure = new Text("Spell Failure");

		Spinner<Double> spellFailure = new Spinner<>();
		spellFailure.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, item.getSpellFailure()));
		spellFailure.valueProperty().addListener((e, o, n) -> item.setSpellFailure(n));
		spellFailure.setPrefWidth(75);
		spellFailure.setEditable(true);

		Text tDamageReduction = new Text("Damage Reduction");

		Spinner<Double> damageReduction = new Spinner<>();
		damageReduction.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, item.getDamageReduction()));
		damageReduction.valueProperty().addListener((e, o, n) -> item.setDamageReduction(n));
		damageReduction.setPrefWidth(75);
		damageReduction.setEditable(true);

		Button clearValues = new Button("Clear");
		clearValues.setOnAction(e -> {
			armorType.getSelectionModel().select(0);
			armorBonus.getValueFactory().setValue(0);
			maxDex.getValueFactory().setValue(0);
			checkPenalty.getValueFactory().setValue(0);
			spellFailure.getValueFactory().setValue(0.0);
		});

		content.add(tArmorType, 0, 0);
		content.add(armorType, 1, 0);

		content.add(tArmorBonus, 0, 1);
		content.add(armorBonus, 1, 1);

		content.add(tMaxDex, 0, 2);
		content.add(maxDex, 1, 2);

		content.add(tCheckPenalty, 0, 3);
		content.add(checkPenalty, 1, 3);

		content.add(tAttackPenalty, 0, 4);
		content.add(attackPenalty, 1, 4);

		content.add(tSpellFailure, 0, 5);
		content.add(spellFailure, 1, 5);

		content.add(tDamageReduction, 0, 6);
		content.add(damageReduction, 1, 6);

		content.add(clearValues, 2, 0);

		r.setContent(content);

		return r;
	}

	private static TitledPane contentDescription() {
		TitledPane r = new TitledPane();
		r.setText("Description");
		r.setExpanded(false);

		TextArea content = new TextArea();
		content.setText(item.getDescription());
		content.setWrapText(true);
		content.textProperty().addListener((e, o, n) -> item.setDescription(n));

		r.setContent(content);

		return r;
	}

	private static TitledPane contentImage() {
		TitledPane r = new TitledPane();
		r.setText("Image");

		iImage = new ImageView();
		iImage.setPreserveRatio(true);
		iImage.imageProperty().addListener(e -> {

			if(iImage.getImage() != null) {
				iImage.fitHeightProperty().bind(r.heightProperty().multiply(0.75));
				iImage.fitWidthProperty().bind(r.widthProperty().multiply(0.75));
			} else {
				iImage.fitWidthProperty().unbind();
				iImage.fitHeightProperty().unbind();
				iImage.setFitWidth(10);
			}

		});

		iImage.setImage(item.getImage());

		r.setContent(iImage);

		return r;
	}
}
