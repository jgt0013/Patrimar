package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

public class Login extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar el FXML de la ventana de login
            Parent root = FXMLLoader.load(getClass().getResource("/Vistas/LoginFXML.fxml"));
            
            // Obtener dimensiones de la pantalla
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            
            // Crear escena con tamaño proporcional a la pantalla
            Scene scene = new Scene(root, screenBounds.getWidth() * 0.8, screenBounds.getHeight() * 0.8);
            
            // Cargar CSS
            scene.getStylesheets().add(getClass().getResource("/Styles/login.css").toExternalForm());
            
            // Configurar stage
            primaryStage.setScene(scene);
            primaryStage.setTitle("LOGIN");
            primaryStage.setMaximized(true); // Maximiza la ventana de login
            primaryStage.centerOnScreen();
            
            // Mostrar la ventana de login
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la interfaz: " + e.getMessage());
        }
    }

    // Método que se llama para abrir la nueva ventana tras el login
    public void abrirMenu(Stage primaryStage) {
        try {
            // Cargar el FXML de la nueva ventana
            Parent rootMenu = FXMLLoader.load(getClass().getResource("/Vistas/MenuFXML.fxml"));
            
            // Crear la nueva escena
            Scene sceneMenu = new Scene(rootMenu);
            
            // Crear el nuevo Stage
            Stage menuStage = new Stage();
            
            // Cargar CSS
            sceneMenu.getStylesheets().add(getClass().getResource("/Styles/menu.css").toExternalForm());
            
            // Configurar el stage de la nueva ventana
            menuStage.setScene(sceneMenu);
            menuStage.setTitle("MENU");
            
            // Configuración para ventana maximizada
            menuStage.setMaximized(true);
            
            // Obtener dimensiones de pantalla
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            menuStage.setX(screenBounds.getMinX());
            menuStage.setY(screenBounds.getMinY());
            menuStage.setWidth(screenBounds.getWidth());
            menuStage.setHeight(screenBounds.getHeight());
            
            // Mostrar la nueva ventana
            menuStage.show();
            
            // Cerrar la ventana de login
            primaryStage.close();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al abrir el menú: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
