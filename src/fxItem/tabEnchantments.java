package fxItem;

import classes.Attribute;
import classes.Item;
import classes.Item.Enchantment;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class tabEnchantments {
	
	private static List<Enchantment> enchantments;
	
	private static TableView<Enchantment> table;
	
	public static Tab getTab() {
		
		enchantments = fxItem.item.enchantments;
		
		//TODO make "create" button
		
		table = getTableView();
		updateEnchantmentTable();
		
		BorderPane content = new BorderPane();
		content.setCenter(table);
		
		Tab r = new Tab("Enchantments");
		r.setContent(content);
		r.setClosable(false);
		return r;
	}
	
	public static List<Enchantment> getEnchantments() {
		return enchantments;
	}
	
	private static void updateEnchantmentTable() {
		table.getItems().clear();
		table.getItems().addAll(enchantments);
	}
	
	@SuppressWarnings("unchecked")
	private static TableView<Enchantment> getTableView() {
		TableView<Enchantment> r = new TableView<Enchantment>();
		
		TableColumn<Enchantment,String> cName = new TableColumn<Enchantment,String>("Name");
		cName.setCellValueFactory(new PropertyValueFactory<Enchantment,String>("name"));
		
		TableColumn<Enchantment,String> cDescription = new TableColumn<Enchantment,String>("Description");
		cDescription.setCellValueFactory(new PropertyValueFactory<Enchantment,String>("description"));
		
		r.getColumns().addAll(cName,cDescription);
		
		r.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		r.setOnMouseClicked(e -> {
			if(e.getClickCount() == 2) {
				Edit.editEnchantment(r.getSelectionModel().getSelectedItem());
			}
		});
		
		return r;
	}
	
	public static class Edit {
		
		public static Stage sEdit;
		
		private static Enchantment e;
		private static Attribute editAttribute;
		
		private static TableView<Attribute> table;
		
		//Attribute Editor
		private static TextField tAttributeName;
		private static TextField tAttributeType;
		private static TextField tAttributeValue;
		
		private static boolean doUpdate;
		
		private static void editEnchantment(Enchantment ench) {
			e = ench;
			
			doUpdate = true;
			
			if(sEdit != null && sEdit.isShowing()) sEdit.close();
			sEdit = new Stage();
			if(ench == null) sEdit.setTitle("Creating Enchantment");
			else sEdit.setTitle("Editing " + ench.name);
			
			Button bCreate = new Button("Add Attribute");
			
			table = attributeTable();
			updateTable();
			
			HBox center = new HBox(table,editGrid());
			
			BorderPane content = new BorderPane();
			content.setCenter(center);
			
			sEdit.setScene(new Scene(content));
			sEdit.show();
			
		}
		
		@SuppressWarnings("unchecked")
		private static TableView<Attribute> attributeTable() {
			TableView<Attribute> r = new TableView<Attribute>();
			
			TableColumn<Attribute,String> cAttribute = new TableColumn<Attribute,String>("Attribute");
			cAttribute.setCellValueFactory(new PropertyValueFactory<Attribute,String>("attribute"));
			
			TableColumn<Attribute,String> cType = new TableColumn<Attribute,String>("Type");
			cType.setCellValueFactory(new PropertyValueFactory<Attribute,String>("type"));
			
			TableColumn<Attribute,String> cValue = new TableColumn<Attribute,String>("Value");
			cValue.setCellValueFactory(new PropertyValueFactory<Attribute,String>("value"));
			
			r.getColumns().addAll(cAttribute, cType, cValue);
			r.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
			r.setOnMouseClicked(c -> {
				loadAttribute(r.getSelectionModel().getSelectedItem());
			});
			
			return r;
		}
		
		private static void updateTable() {
			table.getItems().clear();
			table.getItems().addAll(e.attributes);
		}
		
		private static GridPane editGrid() {
			
			Text lName = new Text("Attribute: ");
			Text lType = new Text("Bonus Type");
			Text lValue = new Text("Value: ");
			
			tAttributeName = new TextField();
			tAttributeType = new TextField();
			tAttributeValue = new TextField();
			
			for(TextField a : new TextField[] {tAttributeName, tAttributeType, tAttributeValue}) {
				a.textProperty().addListener((obs,o,n) -> updateAttribute());
			}
			
			Button bCreate = new Button("New");
			bCreate.setOnAction(e -> createAttribute());
			
			Button bDelete = new Button("Delete");
			bDelete.setOnAction(e -> deleteAttribute());
			
			GridPane r = new GridPane();
			
			r.add(lName, 0, 0);
			r.add(lType, 0, 1);
			r.add(lValue, 0, 2);
			r.add(tAttributeName, 1, 0);
			r.add(tAttributeType, 1, 1);
			r.add(tAttributeValue, 1, 2);
			r.add(bCreate, 0, 3);
			r.add(bDelete, 1, 3);
			
			r.setHgap(10);
			r.setVgap(10);
			r.setPadding(new Insets(10));
			
			updateAttribute();
			return r;
		}
		
		private static void loadAttribute(Attribute a) {
			doUpdate = false;
			
			editAttribute = a;
			
			tAttributeName.setText("");
			tAttributeType.setText("");
			tAttributeValue.setText("");
			
			if(a != null) {
				tAttributeName.setText(a.attribute);
				tAttributeType.setText(a.type);
				if(a.value != 0) tAttributeValue.setText(a.value + "");
				else tAttributeValue.setText(a.stringValue);
			}
			
			doUpdate = true;
		}
		
		private static void updateAttribute() {
			if(editAttribute == null || !doUpdate) return;
			editAttribute.attribute = tAttributeName.getText();
			editAttribute.type = tAttributeType.getText();
			try {
				editAttribute.value = Double.parseDouble(tAttributeValue.getText());
			} catch (Exception e) {
				editAttribute.stringValue = tAttributeValue.getText();
			}
			updateTable();
		}
		
		private static void deleteAttribute() {
			e.attributes.remove(editAttribute);
			updateTable();
			loadAttribute(null);
		}
		
		private static void createAttribute() {
			Attribute a = new Attribute();
			e.attributes.add(a);
			loadAttribute(a);
		}
	}
	
}
