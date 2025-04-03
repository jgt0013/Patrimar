module ProjectFX {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires java.base;
	requires java.sql;

    opens Main to javafx.graphics, javafx.fxml;
    opens Vistas to javafx.fxml;
    opens Controller to javafx.fxml;
    opens Modelos to javafx.base;
    exports Controller;
}