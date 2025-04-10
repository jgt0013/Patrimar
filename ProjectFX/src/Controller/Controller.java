package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.io.IOException;

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

    @FXML
    private void abrirNuevoCliente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/NuevoClienteFXML.fxml"));
            Scene scene = new Scene(loader.load(), 1920, 1080);

            Stage stage = new Stage();
            stage.setTitle("Nuevo Cliente");
            stage.setScene(scene);
            stage.show();
            stage.setMaximized(true);

            scene.getStylesheets().add(getClass().getResource("/Styles/Nuevo.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirListarPedidos(ActionEvent event) {
        try {
            // Carga el archivo FXML correspondiente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ListPedidosFXML.fxml"));
            
            if (loader.getLocation() == null) {
                throw new IOException("No se pudo encontrar el archivo FXML.");
            }

            // Crea una nueva escena con el archivo cargado
            Scene scene = new Scene(loader.load(), 1920, 1080);
            
            // Crea una nueva ventana (Stage)
            Stage stage = new Stage();
            stage.setTitle("Gestión de Pedidos");
            stage.setScene(scene);
            stage.show();
            stage.setMaximized(true); // Maximiza la ventana

            // Asegúrate de que la ruta del CSS sea correcta
            scene.getStylesheets().add(getClass().getResource("/Styles/ListPedidos.css").toExternalForm());

        } catch (IOException e) {
            // En caso de error, imprime el error en consola
            e.printStackTrace();  
            System.out.println("Error al cargar la vista: " + e.getMessage());
        }
    }
    
    @FXML
    private void abrirListarPresupuestos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ListPresupuestosFXML.fxml"));
            Scene scene = new Scene(loader.load(), 1920, 1080);

            Stage stage = new Stage();
            stage.setTitle("Gestión de Presupuestos");
            stage.setScene(scene);
            stage.show();
            stage.setMaximized(true);

            scene.getStylesheets().add(getClass().getResource("/Styles/ListPresupuestos.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
