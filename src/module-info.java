module buildPlanner {
	exports application;
	exports classes;
	exports fxTabs;
	exports interfaces;

	opens classes to com.google.gson;

	requires javafx.base;
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	requires com.google.gson;
}
