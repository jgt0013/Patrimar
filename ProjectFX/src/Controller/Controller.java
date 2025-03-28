package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

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
    
    @FXML
    private void abrirListarClientes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ListClienteFXML.fxml"));
            Scene scene = new Scene(loader.load(), 1920, 1080);
            
            Stage stage = new Stage();
            stage.setTitle("Gestión de Clientes");
            stage.setScene(scene);
            stage.show();
            stage.setMaximized(true);
            
            scene.getStylesheets().add(getClass().getResource("/Styles/ListClientes.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
