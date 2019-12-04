package interfaces;

import classes.Iref;
import classes.Item;
import classes.Items;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class fxEditItem {

	public static Stage stage;
	
	private static Item item;
	
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
		if(i != null) stage.setTitle("Editing " + i.getName());
		else {
			stage.setTitle("Create Item");
			item = new Item();
		}
		
		TabPane tabs = new TabPane();
		tabs.getTabs().addAll(tabGeneral(),tabWeapon(),tabArmor());
		
		for(Tab t : tabs.getTabs()) {
			t.setClosable(false);
			
		}
		
		BorderPane content = new BorderPane();
		content.setTop(contentHeader());
		content.setCenter(tabs);
		content.setBottom(contentFooter());
		
		stage.setScene(new Scene(content));
		stage.show();
	}
	
	private static void saveItem() {
		item.saveItem();
	}
	
	private static HBox contentHeader() {
		HBox r = new HBox();
		r.setSpacing(10);
		r.setPadding(new Insets(10));
		
		Label labelName = new Label("Item Name:");
		
		TextField itemName = new TextField(item.getName());
		itemName.textProperty().addListener((e,o,n) -> item.setName(n));
		
		r.getChildren().addAll(labelName,itemName);
		
		return r;
	}
	
	private static HBox contentFooter() {
		HBox r = new HBox();
		r.setSpacing(10);
		r.setPadding(new Insets(10));
		
		Button save = new Button("Save");
		save.setOnAction(e -> saveItem());
		
		
		r.getChildren().add(save);
		
		return r;
	}
	
	private static Tab tabGeneral() {
		Tab r = new Tab("General");
		
		
		
		return r;
	}
	
	private static Tab tabWeapon() {
		Tab r = new Tab("Weapon");
		
		
		
		return r;
	}
	
	private static Tab tabArmor() {
		Tab r = new Tab("Armor");
		
		Text tArmorType = new Text("Armor Type");
		
		ChoiceBox<String> armorType = new ChoiceBox<String>(FXCollections.observableArrayList("", "Armor", "Shield"));
		armorType.getSelectionModel().select(item.getArmorType());
		armorType.getSelectionModel().selectedItemProperty().addListener((e,o,n) -> item.setArmorType(n));
		
		Text tArmorBonus = new Text("Armor Bonus");
		
		Spinner<Integer> armorBonus = new Spinner<Integer>();
		armorBonus.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,item.getArmorBonus()));
		armorBonus.valueProperty().addListener((e,o,n) -> item.setArmorBonus(n));
		armorBonus.setPrefWidth(75);
		armorBonus.setEditable(true);
		
		Text tMaxDex = new Text("Max Dex Bonus");
		
		Spinner<Integer> maxDex = new Spinner<Integer>();
		maxDex.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, item.getMaxDex()));
		maxDex.valueProperty().addListener((e,o,n) -> item.setMaxDex(n));
		maxDex.setPrefWidth(75);
		maxDex.setEditable(true);
		
		Text tCheckPenalty = new Text("Armor Check Penalty");
		
		Spinner<Integer> checkPenalty = new Spinner<Integer>();
		checkPenalty.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, item.getCheckPenalty()));
		checkPenalty.valueProperty().addListener((e,o,n) -> item.setCheckPenalty(n));
		checkPenalty.setPrefWidth(75);
		checkPenalty.setEditable(true);
		
		Text tSpellFailure = new Text("Spell Failure");
		
		Spinner<Double> spellFailure = new Spinner<Double>();
		spellFailure.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, item.getSpellFailure()));
		spellFailure.valueProperty().addListener((e,o,n) -> item.setSpellFailure(n));
		spellFailure.setPrefWidth(75);
		spellFailure.setEditable(true);
		
		Button clearValues = new Button("Clear");
		clearValues.setOnAction(e -> {
			armorType.getSelectionModel().select(0);
			armorBonus.getValueFactory().setValue(0);
			maxDex.getValueFactory().setValue(0);
			checkPenalty.getValueFactory().setValue(0);
			spellFailure.getValueFactory().setValue(0);
		});
		
		GridPane content = new GridPane();
		content.setHgap(10);
		content.setVgap(10);
		content.setPadding(new Insets(10));
		
		content.add(tArmorType, 0, 0);
		content.add(armorType, 1, 0);
		content.add(tArmorBonus, 0, 1);
		content.add(armorBonus, 1, 1);
		content.add(tMaxDex, 0, 2);
		content.add(maxDex, 1, 2);
		content.add(tCheckPenalty, 0, 3);
		content.add(checkPenalty, 1, 3);
		content.add(tSpellFailure, 0, 4);
		content.add(spellFailure, 1, 4);
		
		r.setContent(content);
		
		return r;
	}
}
