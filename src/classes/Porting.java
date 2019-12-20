package classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import classes.Item.ItemExport;

import javafx.stage.FileChooser;
import util.system;

public class Porting {

	public static void importItem() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Item Export", "*.exportItem"));

		try {
			ItemExport i = system.objectJSON.fromJson(Files.newBufferedReader(fileChooser.showOpenDialog(null).toPath()), ItemExport.class);
			i.importItem();
		} catch(IOException e) {}

	}

	public static void exportItem(Item item) {
		item.saveItem();

		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Item Export", "*.exportItem"));

		File f = fileChooser.showSaveDialog(null);
		if(f.exists()) f.delete();

		try {
			FileWriter writer;
			writer = new FileWriter(f);
			writer.write(system.objectJSON.toJson(new ItemExport(item)));
			writer.close();
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
