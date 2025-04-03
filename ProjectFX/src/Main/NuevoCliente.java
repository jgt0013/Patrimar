package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

public class NuevoCliente extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
        	 // Cargar el FXML de la ventana de login
            Parent root = FXMLLoader.load(getClass().getResource("/Vistas/NuevoClienteFXML.fxml"));
            
            // Obtener dimensiones de la pantalla
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            
            // Crear escena con tamaño proporcional a la pantalla
            Scene scene = new Scene(root, screenBounds.getWidth() * 0.8, screenBounds.getHeight() * 0.8);
            
            // Cargar CSS
            scene.getStylesheets().add(getClass().getResource("/Styles/Nuevo.css").toExternalForm());
            
            // Configurar stage
            primaryStage.setScene(scene);
            primaryStage.setTitle("NUEVO_CLIENTE");
            primaryStage.setMaximized(true); // Maximiza la ventana de login
            primaryStage.centerOnScreen();
            
            // Mostrar la ventana de login
            primaryStage.show();
            

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la interfaz: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
