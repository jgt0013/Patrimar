package Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class Controller {

    @FXML private VBox vboxPresupuesto, vboxPedidos, vboxAlbaranes, vboxFacturas, vboxClientes, vboxProveedores, vboxDiario, vboxResumen, vboxOtros;

    @FXML
    public void toggleButtons(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        switch (buttonId) {
            case "btnPresupuesto":
                vboxPresupuesto.setVisible(!vboxPresupuesto.isVisible());
                break;
            case "btnPedidos":
                vboxPedidos.setVisible(!vboxPedidos.isVisible());
                break;
            case "btnAlbaranes":
                vboxAlbaranes.setVisible(!vboxAlbaranes.isVisible());
                break;
            case "btnFacturas":
                vboxFacturas.setVisible(!vboxFacturas.isVisible());
                break;
            case "btnClientes":
                vboxClientes.setVisible(!vboxClientes.isVisible());
                break;
            case "btnProveedores":
                vboxProveedores.setVisible(!vboxProveedores.isVisible());
                break;
            case "btnDiario":
                vboxDiario.setVisible(!vboxDiario.isVisible());
                break;
            case "btnResumen":
                vboxResumen.setVisible(!vboxResumen.isVisible());
                break;
            case "btnOtros":
                vboxOtros.setVisible(!vboxOtros.isVisible());
                break;
            default:
                System.out.println("Botón no reconocido: " + buttonId);
                break;
        }
    }
}
