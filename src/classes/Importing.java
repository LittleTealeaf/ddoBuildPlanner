package classes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import application.Main;
import javafx.stage.FileChooser;
import util.system;

public class Importing {
	
	public static void importItem() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Item Export", "*.exportItem"));
		
		try {
			ItemExport i = system.objectJSON.fromJson(Files.newBufferedReader(fileChooser.showOpenDialog(null).toPath()), ItemExport.class);
			i.importItem();
		} catch(IOException e) {}
	}
}
