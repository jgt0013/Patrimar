package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class ListPedidos extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ListClienteFXML.fxml"));
            VBox root = loader.load();
            Scene scene = new Scene(root, 1920, 1080);
            primaryStage.setTitle("Gestión de Clientes");
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setMaximized(true);
            scene.getStylesheets().add(getClass().getResource("/Styles/ListClientes.css").toExternalForm());
            primaryStage.setResizable(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
