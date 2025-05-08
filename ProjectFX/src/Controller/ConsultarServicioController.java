package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Modelos.Servicio;
import Controller.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.function.Predicate;

public class ConsultarServicioController {

    @FXML
    private TextField TFID, TFCodigo, TFServicio, TFPrecio;

    @FXML
    private TableView<Servicio> TableProductos;  // Puedes renombrar a TableServicios si lo deseas
    @FXML
    private TableColumn<Servicio, Integer> colID;
    @FXML
    private TableColumn<Servicio, String> colCodigo;
    @FXML
    private TableColumn<Servicio, String> colServicio;
    @FXML
    private TableColumn<Servicio, Double> colPrecio;

    private ObservableList<Servicio> serviciosList = FXCollections.observableArrayList();
    private ObservableList<Servicio> serviciosFiltrados = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        configurarTabla();
        cargarDatos();
        agregarFiltros();
    }

    private void configurarTabla() {
        colID.setCellValueFactory(new PropertyValueFactory<>("idServicio"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoServicio"));
        colServicio.setCellValueFactory(new PropertyValueFactory<>("servicio"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("Importe"));

        TableProductos.setItems(serviciosFiltrados);
    }

    private void cargarDatos() {
        ConexionBD conexion = new ConexionBD();
        List<Servicio> servicios = conexion.getAllServicios();
        serviciosList.setAll(servicios);
        serviciosFiltrados.setAll(servicios);
    }

    private void agregarFiltros() {
        TFID.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFCodigo.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFServicio.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFPrecio.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
    }

    private void filtrarDatos() {
        Predicate<Servicio> filtro = servicio -> {
            boolean coincide = true;

            if (!TFID.getText().isBlank())
                coincide &= String.valueOf(servicio.getIdServicio()).contains(TFID.getText().trim());
            if (!TFCodigo.getText().isBlank())
                coincide &= servicio.getCodigoServicio().toLowerCase().contains(TFCodigo.getText().trim().toLowerCase());
            if (!TFServicio.getText().isBlank())
                coincide &= servicio.getServicio().toLowerCase().contains(TFServicio.getText().trim().toLowerCase());
            if (!TFPrecio.getText().isBlank()) {
                try {
                    double precioFiltro = Double.parseDouble(TFPrecio.getText().trim());
                    coincide &= servicio.getImporte() == precioFiltro;
                } catch (NumberFormatException e) {
                    // Ignorar
                }
            }

            return coincide;
        };

        serviciosFiltrados.setAll(serviciosList.stream().filter(filtro).toList());
    }

    @FXML
    private void eliminarServicio() {
        Servicio servicioSeleccionado = TableProductos.getSelectionModel().getSelectedItem();

        if (servicioSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmación");
            confirmacion.setHeaderText("Eliminar Servicio");
            confirmacion.setContentText("¿Estás seguro de que quieres eliminar el servicio con código: " + servicioSeleccionado.getCodigoServicio() + "?");

            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.OK) {
                    ConexionBD conexion = new ConexionBD();
                    boolean eliminado = conexion.deleteServicio(servicioSeleccionado.getIdServicio()); // nuevo método

                    if (eliminado) {
                        serviciosList.remove(servicioSeleccionado);
                        serviciosFiltrados.setAll(serviciosList);
                        TableProductos.refresh();
                    } else {
                        mostrarAlerta("Error", "No se pudo eliminar el servicio de la base de datos.", Alert.AlertType.ERROR);
                    }
                }
            });
        } else {
            mostrarAlerta("Error", "Selecciona un servicio para eliminar.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void limpiarCampos() {
        TFID.clear();
        TFCodigo.clear();
        TFServicio.clear();
        TFPrecio.clear();
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
