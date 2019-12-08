package classes;

import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;
import util.system;

public class Inventory {
	
	private static List<invItem> items;
	
	public Inventory() {}
	
	public static void load() {
		
		if(!system.inventory.exists()) {
			items = new ArrayList<invItem>();
			save();
		} else {
			try {
				system.staticJSON.fromJson(Files.newBufferedReader(system.inventory.toPath()), Inventory.class);
			} catch(Exception e) {}
		}
	}
	
	public static void save() {
		try {
			FileWriter writer = new FileWriter(system.inventory);
			writer.write(system.staticJSON.toJson(new Inventory()));
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<invItem> getItems() {
		return items;
	}
	
	public static void setItems(List<invItem> items) {
		Inventory.items = items;
	}
	
	public static void addItem(String itemName) {
		addItem(itemName,1);
	}
	
	public static void addItem(String itemName, int count) {
		items.add(new invItem(itemName,count));
		save();
	}
	
	public static void removeItem(String itemName) {
		List<invItem> replace = new ArrayList<invItem>();
		int count = 0;
		for(invItem i : items) if(!i.getName().contentEquals(itemName) || count >= 1) {
			replace.add(i);
		} else count++;
		items = replace;
		save();
	}
	
	public static class invItem extends Iref {
		
		private String location;
		private int count;
		
		public invItem(String name) {
			this(name,"");
		}
		
		public invItem(String name, int count) {
			this(name,"",count);
		}
		
		public invItem(String name, String location) {
			this(name,location,1);
		}
		
		public invItem(String name, String location, int count) {
			super(name);
			this.setLocation(location);
			this.setCount(count);
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public int getCount() {
			return count;
		}
		
		public String getCountView() {
			return getCount() + "";
		}

		public void setCount(int count) {
			this.count = count;
		}
		
		public ImageView getIconViewSmall() {
			return this.getItem().getIconViewSmall();
		}
		
		public String getName() {
			return this.getItem().getName();
		}
		
		public String getDescription() {
			return this.getItem().getDescriptionTrimmed();
		}
	}
}
