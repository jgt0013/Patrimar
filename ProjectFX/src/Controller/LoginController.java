package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton; // Nombre debe coincidir con fx:id
    
    @FXML
    private void initialize() {
        // Verifica que los componentes no son null
        if (loginButton != null) {
            loginButton.setOnAction(event -> handleLogin());
        } else {
            System.err.println("Error: loginButton no fue inyectado correctamente");
        }
    }
    
    @FXML
    private void handleLogin() {
        if (usernameField == null || passwordField == null) {
            System.err.println("Error: Campos no inyectados correctamente");
            return;
        }
        
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        // Lógica de autenticación
        System.out.println("Intento de login: " + username + "/" + password);
    }
}