package fxItem;

import classes.Attribute;
import classes.Item;
import classes.Item.Enchantment;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
		
		private static TableView<Attribute> table;
		
		//Attribute Editor
		private static TextField tAttributeName;
		private static TextField tAttributeType;
		private static TextField tAttributeStringValue;
		private static Spinner<Integer> sAttributeValue;
		
		private static void editEnchantment(Enchantment ench) {
			e = ench;
			
			if(sEdit != null && sEdit.isShowing()) sEdit.close();
			sEdit = new Stage();
			if(ench == null) sEdit.setTitle("Creating Enchantment");
			else sEdit.setTitle("Editing " + ench.name);
			
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
			
			TableColumn<Attribute,String> cAttribute = new TableColumn<Attribute,String>();
			cAttribute.setCellValueFactory(new PropertyValueFactory<Attribute,String>("attribute"));
			
			TableColumn<Attribute,String> cType = new TableColumn<Attribute,String>();
			cType.setCellValueFactory(new PropertyValueFactory<Attribute,String>("type"));
			
			TableColumn<Attribute,String> cValue = new TableColumn<Attribute,String>();
			cValue.setCellValueFactory(new PropertyValueFactory<Attribute,String>("value"));
			
			r.getColumns().addAll(cAttribute, cType, cValue);
			
			return r;
		}
		
		private static void updateTable() {
			table.getItems().clear();
			table.getItems().addAll(e.attributes);
		}
		
		private static GridPane editGrid() {
			
			tAttributeName = new TextField();
			tAttributeType = new TextField();
			tAttributeStringValue = new TextField();
			
			sAttributeValue = new Spinner<Integer>();
			sAttributeValue.setEditable(true);
			
			
			GridPane r = new GridPane();
			r.setHgap(10);
			r.setVgap(10);

			return r;
		}
	}
	
}
