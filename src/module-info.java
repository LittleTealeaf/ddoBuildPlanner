module buildPlanner {
	exports application;
	exports classes;
	exports fxTabs;
	exports interfaces;

	opens classes to com.google.gson;

	requires javafx.base;
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	requires transitive com.google.gson;
	requires net.harawata.appdirs;
	requires java.datatransfer;
	requires java.desktop;
}
