package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Users extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/AddUsersFXML.fxml"));
            Parent root = loader.load(); // Cambio aquí
            Scene scene = new Scene(root, 1920, 1080);
            primaryStage.setTitle("Gestión de Usuarios");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            scene.getStylesheets().add(getClass().getResource("/Styles/NuevoUser.css").toExternalForm());
            primaryStage.setResizable(true);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
