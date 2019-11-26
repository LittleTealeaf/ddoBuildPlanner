package fxItem;

import classes.Attribute;
import classes.Item;
import classes.Item.Enchantment;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
		
		private static void editEnchantment(Enchantment ench) {
			e = ench;
			
			if(sEdit != null && sEdit.isShowing()) sEdit.close();
			sEdit = new Stage();
			if(ench == null) sEdit.setTitle("Creating Enchantment");
			else sEdit.setTitle("Editing " + ench.name);
			
			table = attributeTable();
			
			HBox center = new HBox(table,editGrid());
			
			
			BorderPane content = new BorderPane();
			content.setCenter(center);
			
			sEdit.setScene(new Scene(content));
			sEdit.show();
			
		}
		
		@SuppressWarnings("unchecked")
		private static TableView<Attribute> attributeTable() {
			TableView<Attribute> r = new TableView<Attribute>();
			
			TableColumn<Attribute,String> cName = new TableColumn<Attribute,String>();
			cName.setCellValueFactory(new PropertyValueFactory<Attribute,String>("title"));
			
			TableColumn<Attribute,String> cDescription = new TableColumn<Attribute,String>();
			cDescription.setCellValueFactory(new PropertyValueFactory<Attribute,String>("description"));
			
			r.getColumns().addAll(cName, cDescription);
			
			return r;
		}
		
		private static void updateTable() {
			
		}
		
		private static GridPane editGrid() {
			
			
			GridPane r = new GridPane();
			r.setHgap(10);
			r.setVgap(10);

			return r;
		}
	}
	
}
