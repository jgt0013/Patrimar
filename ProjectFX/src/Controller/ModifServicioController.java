package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class ModifServicioController extends Application {
	
    @FXML
    private TextField TFBuscar, TFCodigo, TFServicio, TFPrecio;

	@FXML
    private void buscarServicio(ActionEvent event) {
        String idTexto = TFBuscar.getText().trim();

        if (idTexto.isEmpty()) {
            showAlert(AlertType.WARNING, "Campo vacío", "Por favor, introduce un ID para buscar.");
            return;
        }

        String query = "SELECT codigo_servicio, servicio, importe FROM servicios WHERE id_servicio = ?;";

        try (Connection connection = ConexionBD.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, Integer.parseInt(idTexto));

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        TFCodigo.setText(rs.getString("codigo_servicio"));
                        TFServicio.setText(rs.getString("servicio"));
                        TFPrecio.setText(String.valueOf(rs.getDouble("importe")));
                    } else {
                        limpiarCampos();
                        showAlert(AlertType.INFORMATION, "No encontrado", "No existe un producto con ese ID.");
                    }
                }
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Formato inválido", "El ID debe ser un número.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error de Base de Datos", "No se pudo buscar el producto.");
        }
    }

    @FXML
    private void aplicarCambios(ActionEvent event) {
        String idTexto = TFBuscar.getText().trim();
        String codigo = TFCodigo.getText().trim();
        String servicio = TFServicio.getText().trim();
        String precioTexto = TFPrecio.getText().trim();

        if (idTexto.isEmpty() || codigo.isEmpty() || servicio.isEmpty() || precioTexto.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campos vacíos", "Todos los campos deben estar llenos.");
            return;
        }

        try {
            double precio = Double.parseDouble(precioTexto);
            int id = Integer.parseInt(idTexto);

            String query = "UPDATE servicios SET codigo_servicio = ?, servicio = ?, importe = ? WHERE id_servicio = ?";

            try (Connection connection = ConexionBD.getConnection();
                 PreparedStatement ps = connection.prepareStatement(query)) {

                ps.setString(1, codigo);
                ps.setString(2, servicio);
                ps.setDouble(3, precio);
                ps.setInt(4, id);

                int filasActualizadas = ps.executeUpdate();

                if (filasActualizadas > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Producto actualizado correctamente.");
                    limpiarCampos();
                } else {
                    showAlert(Alert.AlertType.WARNING, "No encontrado", "No existe un producto con ese ID.");
                }
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Formato inválido", "Precio e ID deben ser números válidos.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error de Base de Datos", "No se pudo actualizar el producto.");
        }
    }

    
    
    private void limpiarCampos() {
        TFBuscar.clear();
        TFCodigo.clear();
        TFServicio.clear();
        TFPrecio.clear();
    }

    
    @FXML
    private void Limpiar(ActionEvent event) {
        limpiarCampos();
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
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
