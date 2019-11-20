module buildPlanner {
	exports application;
	exports classes;
	exports fxTabs;
	exports resource;

	requires javafx.base;
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	requires com.google.gson;
}
