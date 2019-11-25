package fxItem;

import classes.Item;
import classes.Item.Enchantment;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
		
		Tab r = new Tab("Enchantments");
		r.setContent(table);
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
		
		private static void editEnchantment(Enchantment ench) {
			
			if(sEdit != null && sEdit.isShowing()) sEdit.close();
			sEdit = new Stage();
			if(ench == null) sEdit.setTitle("Creating Enchantment");
			else sEdit.setTitle("Editing " + ench.name);
			
			
			
		}
	}
	
}
