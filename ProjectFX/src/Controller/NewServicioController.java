package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class NewServicioController extends Application {
	
	@FXML
    private TextField TFCodigo, TFServicio, TFPrecio;

    @FXML
    private void AbrirModif(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ModifServicio.fxml"));
            Scene scene = new Scene(loader.load(), 1920, 1080);

            Stage stage = new Stage();
            stage.setTitle("Gestión de Servicios");
            stage.setScene(scene);
            stage.show();
            stage.setMaximized(true);

            scene.getStylesheets().add(getClass().getResource("/Styles/ModifServicioCSS.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void AbrirConsultar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ConsultarServicio.fxml"));
            Scene scene = new Scene(loader.load(), 1920, 1080);

            Stage stage = new Stage();
            stage.setTitle("Gestión de Servicios");
            stage.setScene(scene);
            stage.show();
            stage.setMaximized(true);

            scene.getStylesheets().add(getClass().getResource("/Styles/ConsultarServicioCSS.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void AddServicio(ActionEvent event) {
        String addProductoQuery = "INSERT INTO servicios (codigo_servicio, servicio, importe) VALUES (?, ?, ?);";

        try {
            String codigo = TFCodigo.getText().trim();
            String servicio = TFServicio.getText().trim();
            String precioTexto = TFPrecio.getText().trim();

            if (codigo.isEmpty() || servicio.isEmpty() || precioTexto.isEmpty()) {
                showAlert(AlertType.WARNING, "Campos incompletos", "Todos los campos son obligatorios.");
                return;
            }

            double precio = Double.parseDouble(precioTexto);

            try (Connection connection = ConexionBD.getConnection()) { // Usa tu clase de conexión a BD
                if (connection != null && !connection.isClosed()) {
                    System.out.println("Conexión a la base de datos establecida con éxito.");
                } else {
                    System.out.println("Error: No se pudo establecer la conexión.");
                    return;
                }

                try (PreparedStatement ps = connection.prepareStatement(addProductoQuery)) {
                    ps.setString(1, codigo);
                    ps.setString(2, servicio);
                    ps.setDouble(3, precio);

                    ps.executeUpdate();

                    System.out.println("Producto añadido: " + codigo + ", " + servicio + ", " + precio);
                    showAlert(AlertType.INFORMATION, "Éxito", "Producto añadido correctamente.");

                    // Limpiar los campos
                    TFCodigo.clear();
                    TFServicio.clear();
                    TFPrecio.clear();
                } catch (SQLException e) {
                    System.out.println("Error al ejecutar la consulta: " + e.getMessage());
                    e.printStackTrace();
                    showAlert(AlertType.ERROR, "Error SQL", "Error al insertar el producto.");
                }
            } catch (SQLException e) {
                System.out.println("Error de conexión: " + e.getMessage());
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Error de Conexión", "No se pudo conectar a la base de datos.");
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error de formato", "El precio debe ser un número válido.");
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error inesperado", "Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
