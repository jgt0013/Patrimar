module ProjectFX {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires java.base;
	requires java.sql;
	
	requires org.apache.poi.ooxml;
	requires org.apache.poi.poi;
	requires org.apache.commons.compress;
	requires org.apache.logging.log4j;
	requires org.apache.xmlbeans;
	requires SparseBitSet;
	requires commons.math3;
	requires org.apache.commons.io;
	requires org.apache.commons.codec;

	requires kernel;
	requires layout;
	requires io;
	requires commons;
	requires pdfa;





    opens Main to javafx.graphics, javafx.fxml;
    opens Vistas to javafx.fxml;
    opens Controller to javafx.fxml;
    opens Modelos to javafx.base;
    exports Controller;
}