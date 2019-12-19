package fxTabs;

import java.util.Arrays;
import java.util.List;

import classes.Enchref;
import classes.Gearset;
import classes.Iref;
import classes.Item;
import classes.Items;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import vars.GearSlot;

public class Gearsets {

	private static Tab tab;

	private static GridPane grid;

	private static Gearset currentGearset;

	public static Tab getTab() {
		tab = new Tab("Gearsets");

		currentGearset = new Gearset();

		BorderPane content = new BorderPane();
		content.setCenter(gridContent());

		// TODO build the gearset into the build class

		ScrollPane scrollContent = new ScrollPane();
		scrollContent.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollContent.setContent(content);

		tab.setContent(scrollContent);

		return tab;
	}

	public static void setGearset(Gearset gearset) {
		currentGearset = gearset;
	}

	public static GridPane gridContent() {
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
			Iref ref = currentGearset.getItemBySlot(slot);
			
			ImageView icon = (ref != null) ? ref.getItem().getIconViewSmall() : new ImageView();

			Text name = new Text();
			name.setText((ref != null) ? ref.getItem().getName() : "");

			Text enchantments = new Text();
			if(ref != null) for(Enchref e : ref.getItem().getEnchantments()) enchantments.setText(enchantments.getText() + e.getDisplayName() + "\n");

			Button bSelect = new Button("Select...");
			bSelect.setOnAction(e -> {
				System.out.println("slot " + slot.getItemSlot());
				Item i = Items.selectItemPrompt(slot.getItemSlot());

				if(i != null) {
					currentGearset.setItemBySlot(i, slot);
					updateContent();
				}

			});

			grid.addRow((row++), new Text(slot.displayName()), icon, name, enchantments, bSelect);
		}

	}
}
