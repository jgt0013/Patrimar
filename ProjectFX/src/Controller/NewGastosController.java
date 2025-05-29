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

public class NewGastosController extends Application {

    @FXML
    private TextField TFNombre, TFTipo;

    @FXML
    private void AbrirModif(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ModifGastos.fxml"));
            Scene scene = new Scene(loader.load(), 1920, 1080);

            Stage stage = new Stage();
            stage.setTitle("Modificar Gasto");
            stage.setScene(scene);
            stage.show();
            stage.setMaximized(true);

            scene.getStylesheets().add(getClass().getResource("/Styles/ModifGastosCSS.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void AbrirConsultar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ConsultarGastos.fxml"));
            Scene scene = new Scene(loader.load(), 1920, 1080);

            Stage stage = new Stage();
            stage.setTitle("Consultar Gastos");
            stage.setScene(scene);
            stage.show();
            stage.setMaximized(true);

            scene.getStylesheets().add(getClass().getResource("/Styles/ConsultarGastoCSS.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void AddGasto(ActionEvent event) {
        String insertQuery = "INSERT INTO programa.gastos (nombre, tipo) VALUES (?, ?);";

        try {
            String nombre = TFNombre.getText().trim();
            String tipo = TFTipo.getText().trim();

            if (nombre.isEmpty()) {
                showAlert(AlertType.WARNING, "Campos incompletos", "El campo 'nombre' es obligatorio.");
                return;
            }

            try (Connection connection = ConexionBD.getConnection()) {
                if (connection != null && !connection.isClosed()) {
                    System.out.println("Conexión establecida correctamente.");
                } else {
                    System.out.println("Error: No se pudo establecer la conexión.");
                    return;
                }

                try (PreparedStatement ps = connection.prepareStatement(insertQuery)) {
                    ps.setString(1, nombre);
                    ps.setString(2, tipo.isEmpty() ? null : tipo);

                    ps.executeUpdate();

                    System.out.println("Gasto añadido: " + nombre + ", " + tipo);
                    showAlert(AlertType.INFORMATION, "Éxito", "Gasto añadido correctamente.");

                    // Limpiar campos
                    TFNombre.clear();
                    TFTipo.clear();
                } catch (SQLException e) {
                    System.out.println("Error SQL: " + e.getMessage());
                    e.printStackTrace();
                    showAlert(AlertType.ERROR, "Error SQL", "No se pudo insertar el gasto.");
                }
            } catch (SQLException e) {
                System.out.println("Error de conexión: " + e.getMessage());
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Error de Conexión", "No se pudo conectar a la base de datos.");
            }
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
    public void start(Stage primaryStage) {
    }
}
