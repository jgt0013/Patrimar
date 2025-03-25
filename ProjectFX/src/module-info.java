module ProjectFX {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires java.base;

    opens Main to javafx.graphics, javafx.fxml;
    opens Vistas to javafx.fxml;
    opens Controller to javafx.fxml;  // Abrir el paquete Controller para javafx.fxml
}
