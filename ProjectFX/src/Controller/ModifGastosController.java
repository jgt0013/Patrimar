package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ModifGastosController extends Application {

    @FXML
    private TextField TFBuscar, TFNombre, TFTipo;

    @FXML
    private void buscarGasto(ActionEvent event) {
        String idTexto = TFBuscar.getText().trim();

        if (idTexto.isEmpty()) {
            showAlert(AlertType.WARNING, "Campo vacío", "Por favor, introduce un ID para buscar.");
            return;
        }

        String query = "SELECT nombre, tipo FROM programa.gastos WHERE id_gasto = ?;";

        try (Connection connection = ConexionBD.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, Integer.parseInt(idTexto));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TFNombre.setText(rs.getString("nombre"));
                    TFTipo.setText(rs.getString("tipo"));
                } else {
                    limpiarCampos();
                    showAlert(AlertType.INFORMATION, "No encontrado", "No existe un gasto con ese ID.");
                }
            }

        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Formato inválido", "El ID debe ser un número.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error de Base de Datos", "No se pudo buscar el gasto.");
        }
    }

    @FXML
    private void aplicarCambios(ActionEvent event) {
        String idTexto = TFBuscar.getText().trim();
        String nombre = TFNombre.getText().trim();
        String tipo = TFTipo.getText().trim();

        if (idTexto.isEmpty() || nombre.isEmpty()) {
            showAlert(AlertType.WARNING, "Campos vacíos", "El ID y el nombre son obligatorios.");
            return;
        }

        try {
            int id = Integer.parseInt(idTexto);

            String query = "UPDATE programa.gastos SET nombre = ?, tipo = ? WHERE id_gasto = ?;";

            try (Connection connection = ConexionBD.getConnection();
                 PreparedStatement ps = connection.prepareStatement(query)) {

                ps.setString(1, nombre);
                ps.setString(2, tipo.isEmpty() ? null : tipo);
                ps.setInt(3, id);

                int filasActualizadas = ps.executeUpdate();

                if (filasActualizadas > 0) {
                    showAlert(AlertType.INFORMATION, "Éxito", "Gasto actualizado correctamente.");
                    limpiarCampos();
                } else {
                    showAlert(AlertType.WARNING, "No encontrado", "No existe un gasto con ese ID.");
                }
            }

        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Formato inválido", "El ID debe ser un número válido.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error de Base de Datos", "No se pudo actualizar el gasto.");
        }
    }

    @FXML
    private void Limpiar(ActionEvent event) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        TFBuscar.clear();
        TFNombre.clear();
        TFTipo.clear();
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    }
}
