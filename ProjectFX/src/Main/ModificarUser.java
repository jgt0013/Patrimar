package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class ModificarUser extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Vistas/ModifUsers.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/ModifUserCSS.css").toExternalForm());
            primaryStage.setTitle("Consultar Usuarios");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
