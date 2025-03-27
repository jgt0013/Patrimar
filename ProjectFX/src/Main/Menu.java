package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Menu extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Vistas/MainFXML.fxml"));
            Scene scene = new Scene(root, 1280, 720);
            scene.getStylesheets().add(getClass().getResource("/Styles/menu.css").toExternalForm());
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("APP CONT");
            primaryStage.setMinWidth(1280);
            primaryStage.setMinHeight(720);
            primaryStage.centerOnScreen();
            primaryStage.setMaximized(true);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al cargar la interfaz: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}