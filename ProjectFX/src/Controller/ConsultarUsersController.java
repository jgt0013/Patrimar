package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConsultarUsersController {

    @FXML private TextField TFID;
    @FXML private TextField TFNombre;
    @FXML private TextField TFPassword;
    @FXML private TextField TFUsers;
    @FXML private TextField TFPermisos;
    /*
    @FXML private TableView<Usuario> TableUsuarios;
    @FXML private TableColumn<Usuario, Integer> colID;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colPassword;
    @FXML private TableColumn<Usuario, String> colUsers;
    @FXML private TableColumn<Usuario, String> colPermisos;

    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colUsers.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colPermisos.setCellValueFactory(new PropertyValueFactory<>("permisos"));

        TableClientes.setItems(usuarios);

        // Agregar datos de prueba si quieres
        usuarios.add(new Usuario(1, "Juan", "1234", "juan123", "admin"));
    }

    @FXML
    private void eliminarCliente() {
        Usuario seleccionado = TableUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            usuarios.remove(seleccionado);
            limpiarCampos();
        } else {
            mostrarAlerta("Por favor selecciona un usuario para eliminar.");
        }
    }
*/
    @FXML
    private void limpiarCampos() {
        TFID.clear();
        TFNombre.clear();
        TFPassword.clear();
        TFUsers.clear();
        TFPermisos.clear();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
