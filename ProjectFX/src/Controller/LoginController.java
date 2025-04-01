package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    
    @FXML
    private TextField txtUsuario;
    
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private Label lblMensaje;

    @FXML
    private void iniciarSesion() {
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();
        String query = "SELECT * FROM usuarios WHERE username = ? AND pass = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, usuario);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                lblMensaje.setText("¡Inicio de sesión exitoso!");
                lblMensaje.setStyle("-fx-text-fill: green;");  // Texto en color verde para éxito
                abrirMenu();
            } else {
                lblMensaje.setText("Usuario o contraseña incorrectos.");
                lblMensaje.setStyle("-fx-text-fill: red;");  // Texto en color rojo para error
            }

        } catch (SQLException e) {
            e.printStackTrace();
            lblMensaje.setText("Error en la conexión.");
        }
    }

    private void abrirMenu() {
        try {
            // Cargar el archivo FXML de la ventana principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/MainFXML.fxml"));
            Parent root = loader.load();
            
            // Crear una nueva escena
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/menu.css").toExternalForm());

            // Crear el Stage (ventana) para el menú principal
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Menú Principal");
            
            // Configuración para ventana maximizada
            stage.setMaximized(true); // Esto maximiza la ventana
            
            // Obtener dimensiones de la pantalla
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            
            // Asegurar que la ventana ocupe toda la pantalla
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());
            
            stage.show();

            // Cerrar la ventana de login
            Stage loginStage = (Stage) txtUsuario.getScene().getWindow();
            loginStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            lblMensaje.setText("Error al cargar el menú principal.");
            lblMensaje.setStyle("-fx-text-fill: red;");
        }
    }
}


 