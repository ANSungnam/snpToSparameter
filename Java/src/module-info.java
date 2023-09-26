module Y21W475D1126 {
	requires java.desktop;
	requires org.jfree.jfreechart;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.base;
	requires javafx.fxml;
	
	opens funcSuites.snpToSpara to javafx.graphics, javafx.fxml;
}