package interfaces;

import classes.Enchantment;
import classes.Enchantment.AttributeBonus;
import classes.Enchantments;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class fxEditEnchantment {

	private static Enchantment ench;

	public static Stage stage;

	private static GridPane attributes;

	public static void open() {
		open(null);
	}
	
	public static void openAndWait(Enchantment enchantment) {
		openScript(enchantment);
		stage.showAndWait();
	}

	public static void open(Enchantment enchantment) {
		openScript(enchantment);
		stage.show();
	}
	
	private static void openScript(Enchantment enchantment) {
		ench = (enchantment != null) ? enchantment : new Enchantment();

		if(stage != null && stage.isShowing()) stage.close();
		stage = new Stage();
		stage.setTitle((enchantment == null) ? "Create Enchantment" : "Edit Enchantment " + ench.getName());

		Text variables = new Text();
		variables.wrappingWidthProperty().bind(stage.widthProperty().multiply(0.75));
		
		String contText = "The following variables can be used in any field except for the Name";
		contText+="\n[type] - Type (sub-enchantment)";
		contText+="\n[bonus] - Bonus Type (Insightful, etc)";
		contText+="\n[value] - value";
				
		variables.setText(contText);

		VBox content = new VBox(getTopSection(), getAttribute(), variables);
		content.setPadding(new Insets(10));
		content.setSpacing(10);

		Button bSave = new Button("Save");
		bSave.setOnAction(e -> {
			save();
			stage.close();
		});

		BorderPane pane = new BorderPane();
		pane.setCenter(content);
		pane.setBottom(bSave);
		pane.setPadding(new Insets(10));

		stage.setScene(new Scene(pane, 520, 500));
	}

	private static void save() {
		Enchantments.updateEnchantment(ench);
	}

	private static GridPane getTopSection() {
		Text lName = new Text("Name:");
		TextField name = new TextField();
		name.setText(ench.getName());
		name.textProperty().addListener((e, o, n) -> ench.setName(n));

		Text lDisplay = new Text("Display:");
		TextField display = new TextField();
		display.setText(ench.getDisplayName());
		display.textProperty().addListener((e, o, n) -> ench.setDisplayName(n));

		Button addBonus = new Button("Add Bonus");
		addBonus.setOnAction(e -> addBonus());

		GridPane top = new GridPane();
		top.setHgap(10);
		top.setVgap(10);

		top.add(lName, 0, 0);
		top.add(name, 1, 0);
		top.add(lDisplay, 0, 1);
		top.add(display, 1, 1);
		top.add(addBonus, 2, 1);

		return top;
	}

	private static ScrollPane getAttribute() {
		ScrollPane r = new ScrollPane();
		r.setHbarPolicy(ScrollBarPolicy.NEVER);

		attributes = new GridPane();
		attributes.setPadding(new Insets(10));
		attributes.setHgap(5);
		attributes.setVgap(5);

		r.setContent(attributes);

		updateAttributes();

		return r;
	}

	private static void updateAttributes() {
		attributes.getChildren().clear();

		attributes.add(new Text("Attribute"), 0, 0);
		attributes.add(new Text("Bonus"), 1, 0);
		attributes.add(new Text("Multiplier"), 2, 0);

		int row = 1;

		for(AttributeBonus a : ench.getAttributeBonuses()) {
			// STUFFZ

			TextField attrName = new TextField();
			attrName.setText(a.getAttribute());
			attrName.textProperty().addListener((e, o, n) -> a.setAttribute(n));

			TextField bonus = new TextField();
			bonus.setText(a.getBonus());
			bonus.textProperty().addListener((e, o, n) -> a.setBonus(n));

			Spinner<Double> multiplier = new Spinner<Double>();
			multiplier.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-10000, 10000, a.getmultiplier()));
			multiplier.setEditable(true);
			multiplier.setPrefWidth(75);

			Button delete = new Button("Delete");
			delete.setOnAction(e -> deleteBonus(a));

			attributes.add(attrName, 0, row);
			attributes.add(bonus, 1, row);
			attributes.add(multiplier, 2, row);
			attributes.add(delete, 3, row);

			row++;
		}
	}

	private static void deleteBonus(AttributeBonus a) {
		ench.removeAttributeBonus(a);
		updateAttributes();
	}

	private static void addBonus() {
		ench.addAttributeBonus(new AttributeBonus());
		updateAttributes();
	}
}
