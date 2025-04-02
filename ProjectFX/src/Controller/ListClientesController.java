package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import Modelos.Cliente;
import Controller.ConexionBD;

public class ListClientesController {

    @FXML
    private TableView<Cliente> TableClientes;

    @FXML
    private TableColumn<Cliente, Integer> colID;
    @FXML
    private TableColumn<Cliente, String> colNombre;
    @FXML
    private TableColumn<Cliente, String> colApellidos;
    @FXML
    private TableColumn<Cliente, String> colDNI;
    @FXML
    private TableColumn<Cliente, String> colEmail;
    @FXML
    private TableColumn<Cliente, String> colBanco;

    @FXML
    private TextField TFID, TFNombre, TFApellidos, TFDNI, TFEmail, TFBanco;

    private ObservableList<Cliente> clientesList;
    private ObservableList<Cliente> clientesFiltrados;
    
    @FXML
    private javafx.scene.control.Button btnEliminar;

    @FXML
    public void initialize() {
        clientesList = FXCollections.observableArrayList();
        clientesFiltrados = FXCollections.observableArrayList();
        cargarDatos();
        configurarTabla();
        agregarFiltros();
        btnEliminar.setOnAction(event -> eliminarCliente());

    }

    private void configurarTabla() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colDNI.setCellValueFactory(new PropertyValueFactory<>("cifDni"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colBanco.setCellValueFactory(new PropertyValueFactory<>("banco"));

        TableClientes.setItems(clientesFiltrados);
        clientesFiltrados.setAll(clientesList);
    }

    private void cargarDatos() {
    	ConexionBD conexion = new ConexionBD();
    	List<Cliente> clientes = conexion.getAllClientes();
        clientesList.setAll(clientes);
        clientesFiltrados.setAll(clientes);
    }

    private void agregarFiltros() {
        TFID.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFNombre.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFApellidos.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFDNI.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFEmail.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFBanco.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
    }

    private void filtrarDatos() {
        Predicate<Cliente> filtro = cliente -> {
            boolean coincide = true;
            if (!TFID.getText().isEmpty()) coincide &= String.valueOf(cliente.getId()).contains(TFID.getText());
            if (!TFNombre.getText().isEmpty()) coincide &= cliente.getNombre().toLowerCase().contains(TFNombre.getText().toLowerCase());
            if (!TFApellidos.getText().isEmpty()) coincide &= cliente.getApellidos().toLowerCase().contains(TFApellidos.getText().toLowerCase());
            if (!TFDNI.getText().isEmpty()) coincide &= cliente.getCifDni().toLowerCase().contains(TFDNI.getText().toLowerCase());
            if (!TFEmail.getText().isEmpty()) coincide &= cliente.getEmail().toLowerCase().contains(TFEmail.getText().toLowerCase());
            if (!TFBanco.getText().isEmpty()) coincide &= cliente.getBanco().toLowerCase().contains(TFBanco.getText().toLowerCase());
            return coincide;
        };

        clientesFiltrados.setAll(clientesList.stream().filter(filtro).collect(Collectors.toList()));
    }
    
    @FXML
    private void eliminarCliente() {
        Cliente clienteSeleccionado = TableClientes.getSelectionModel().getSelectedItem();
        
        if (clienteSeleccionado != null) {
            ConexionBD conexion = new ConexionBD();
            boolean eliminado = conexion.deleteCliente(clienteSeleccionado.getId());

            if (eliminado) {
                clientesList.remove(clienteSeleccionado);
                clientesFiltrados.remove(clienteSeleccionado);
            }
        } else {
            mostrarAlerta("Error", "Selecciona un cliente para eliminar.", Alert.AlertType.WARNING);
        }
    }    
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}
