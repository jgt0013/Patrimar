package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
	private TableColumn<Cliente, String> colRS;
	@FXML
	private TableColumn<Cliente, String> colDNI;
	@FXML
	private TableColumn<Cliente, String> colDireccion;
	@FXML
	private TableColumn<Cliente, String> colCPostal;
	@FXML
	private TableColumn<Cliente, String> colProvincia;
	@FXML
	private TableColumn<Cliente, String> colContacto1;
	@FXML
	private TableColumn<Cliente, String> colTel1;
	@FXML
	private TableColumn<Cliente, String> colContacto2;
	@FXML
	private TableColumn<Cliente, String> colTel2;
	@FXML
	private TableColumn<Cliente, String> colContacto3;
	@FXML
	private TableColumn<Cliente, String> colTel3;
	@FXML
	private TableColumn<Cliente, String> colEmail;
	@FXML
	private TableColumn<Cliente, String> colBanco;
	@FXML
	private TableColumn<Cliente, String> colObservaciones;


    @FXML
    private TextField TFID, TFNombre, TFApellidos, TFDNI, TFEmail, TFBanco, TFDireccion, TFProvincia, TFCPostal, TFContacto1, TFContacto2, TFContacto3, TFTel1, TFTel2, TFTel3, TFObservaciones;

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
        colRS.setCellValueFactory(new PropertyValueFactory<>("razonSocial"));
        colDNI.setCellValueFactory(new PropertyValueFactory<>("cifDni"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colCPostal.setCellValueFactory(new PropertyValueFactory<>("codigoPostal"));
        colProvincia.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        colContacto1.setCellValueFactory(new PropertyValueFactory<>("personaContacto1"));
        colTel1.setCellValueFactory(new PropertyValueFactory<>("telefono1"));
        colContacto2.setCellValueFactory(new PropertyValueFactory<>("personaContacto2"));
        colTel2.setCellValueFactory(new PropertyValueFactory<>("telefono2"));
        colContacto3.setCellValueFactory(new PropertyValueFactory<>("personaContacto3"));
        colTel3.setCellValueFactory(new PropertyValueFactory<>("telefono3"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colBanco.setCellValueFactory(new PropertyValueFactory<>("banco"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));

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
        TFDireccion.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFProvincia.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFCPostal.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFContacto1.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFContacto2.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFContacto3.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFTel1.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFTel2.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFTel3.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFObservaciones.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
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
            if (!TFDireccion.getText().isEmpty()) coincide &= cliente.getDireccion().toLowerCase().contains(TFDireccion.getText().toLowerCase());
            if (!TFProvincia.getText().isEmpty()) coincide &= cliente.getProvincia().toLowerCase().contains(TFProvincia.getText().toLowerCase());
            if (!TFCPostal.getText().isEmpty()) coincide &= cliente.getCodigoPostal().toLowerCase().contains(TFCPostal.getText().toLowerCase());
            if (!TFContacto1.getText().isEmpty()) coincide &= cliente.getPersonaContacto1().toLowerCase().contains(TFContacto1.getText().toLowerCase());
            if (!TFContacto2.getText().isEmpty()) coincide &= cliente.getPersonaContacto2().toLowerCase().contains(TFContacto2.getText().toLowerCase());
            if (!TFContacto3.getText().isEmpty()) coincide &= cliente.getPersonaContacto3().toLowerCase().contains(TFContacto3.getText().toLowerCase());
            if (!TFTel1.getText().isEmpty()) coincide &= cliente.getTelefono1().toLowerCase().contains(TFTel1.getText().toLowerCase());
            if (!TFTel2.getText().isEmpty()) coincide &= cliente.getTelefono2().toLowerCase().contains(TFTel2.getText().toLowerCase());
            if (!TFTel3.getText().isEmpty()) coincide &= cliente.getTelefono3().toLowerCase().contains(TFTel3.getText().toLowerCase());
            if (!TFObservaciones.getText().isEmpty()) coincide &= cliente.getObservaciones().toLowerCase().contains(TFObservaciones.getText().toLowerCase());

            return coincide;
        };

        clientesFiltrados.setAll(clientesList.stream().filter(filtro).collect(Collectors.toList()));
    }
    
    @FXML
    private void eliminarCliente() {
        Cliente clienteSeleccionado = TableClientes.getSelectionModel().getSelectedItem();
        
        if (clienteSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmación");
            confirmacion.setHeaderText("Eliminar Cliente");
            confirmacion.setContentText("¿Estás seguro de que quieres eliminar a " + clienteSeleccionado.getNombre() + "?");

            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.OK) {
                    ConexionBD conexion = new ConexionBD();
                    boolean eliminado = conexion.deleteCliente(clienteSeleccionado.getId());

                    if (eliminado) {
                        clientesList.remove(clienteSeleccionado);
                    } else {
                        mostrarAlerta("Error", "No se pudo eliminar el cliente de la base de datos.", Alert.AlertType.ERROR);
                    }
                }
            });
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
