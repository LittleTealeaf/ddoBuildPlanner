package debug;

import classes.Settings;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import util.system;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.PrintStream;
import java.net.URI;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Debug {

    public static void setCrashReporting() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
            String filename = "crash_log" + new SimpleDateFormat("yyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + ".txt";

            File file = system.getAppFile("crashlogs", filename);
            if (file.getParentFile().mkdirs()) System.out.println("Created Directory " + file.toString());

            try {
                PrintStream stream = new PrintStream(file);
                stream.print(e.getMessage() + "\n");
                e.printStackTrace(stream);

                if (Settings.advanced.debug.showCrashReports) showPrompt(Files.readAllLines(file.toPath()));
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });
	}

	private static void showPrompt(List<String> lines) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Crash Dialog");
        alert.setHeaderText("We have encountered an error!");
        alert.setContentText("You can either ignore this error, or report it!");

        StringBuilder exceptionText = new StringBuilder();
        for (String s : lines) exceptionText.append(s).append("\n");
        final String exception = exceptionText.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        Button reportLink = new Button("Report Issue");
        reportLink.setOnAction(e -> {
            StringSelection stringSelection = new StringSelection(exception);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            try {
                java.awt.Desktop.getDesktop().browse(new URI("https://github.com/LittleTealeaf/ddoBuildPlanner/issues/new?assignees=LittleTealeaf&labels=crash&template=crash_report.md&title=Crash+Report"));
            } catch (Exception ignored) {
            }

        });

        Label warning = new Label("Warning: This will use your clipboard");

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);
		expContent.add(reportLink, 0, 2);
		expContent.add(warning, 0, 3);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);
		alert.getDialogPane().setExpanded(true);

		alert.showAndWait();
	}
}
