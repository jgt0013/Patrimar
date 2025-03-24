package Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class Controller {

    @FXML
    private VBox vboxButtons;

    // Método para mostrar y ocultar el VBox con los botones
    public void toggleButtons() {
        vboxButtons.setVisible(!vboxButtons.isVisible()); // Cambia la visibilidad del VBox
    }
}
