package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import Modelos.Producto;
import Controller.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.function.Predicate;

public class ConsultarProductoController {

    @FXML
    private TextField TFID, TFCodigo, TFServicio, TFPrecio;

    @FXML
    private TableView<Producto> TableProductos;
    @FXML
    private TableColumn<Producto, Integer> colID;
    @FXML
    private TableColumn<Producto, String> colCodigo;
    @FXML
    private TableColumn<Producto, String> colServicio;
    @FXML
    private TableColumn<Producto, Double> colPrecio;

    private ObservableList<Producto> productosList = FXCollections.observableArrayList();
    private ObservableList<Producto> productosFiltrados = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        configurarTabla();
        cargarDatos();
        agregarFiltros();
    }

    private void configurarTabla() {
        colID.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
        colServicio.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("importe"));

        TableProductos.setItems(productosFiltrados);
    }

    private void cargarDatos() {
        ConexionBD conexion = new ConexionBD();
        List<Producto> productos = conexion.getAllProductos();
        productosList.setAll(productos);
        productosFiltrados.setAll(productos);
    }

    private void agregarFiltros() {
        TFID.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFCodigo.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFServicio.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFPrecio.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
    }

    private void filtrarDatos() {
        Predicate<Producto> filtro = producto -> {
            boolean coincide = true;

            if (!TFID.getText().isBlank())
                coincide &= String.valueOf(producto.getIdProducto()).contains(TFID.getText().trim());
            if (!TFCodigo.getText().isBlank())
                coincide &= producto.getCodigoProducto().toLowerCase().contains(TFCodigo.getText().trim().toLowerCase());
            if (!TFServicio.getText().isBlank())
                coincide &= producto.getProducto().toLowerCase().contains(TFServicio.getText().trim().toLowerCase());
            if (!TFPrecio.getText().isBlank()) {
                try {
                    double precioFiltro = Double.parseDouble(TFPrecio.getText().trim());
                    coincide &= producto.getImporte() == precioFiltro;
                } catch (NumberFormatException e) {
                    // Ignorar si no es un número válido
                }
            }

            return coincide;
        };

        productosFiltrados.setAll(productosList.stream().filter(filtro).toList());
    }

    @FXML
    private void eliminarProducto() {
        Producto productoSeleccionado = TableProductos.getSelectionModel().getSelectedItem();

        if (productoSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmación");
            confirmacion.setHeaderText("Eliminar Producto");
            confirmacion.setContentText("¿Estás seguro de que quieres eliminar el producto con código: " + productoSeleccionado.getCodigoProducto() + "?");

            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.OK) {
                    ConexionBD conexion = new ConexionBD();
                    boolean eliminado = conexion.deleteProducto(productoSeleccionado.getIdProducto());

                    if (eliminado) {
                        productosList.remove(productoSeleccionado);
                        productosFiltrados.setAll(productosList);
                        TableProductos.refresh();
                    } else {
                        mostrarAlerta("Error", "No se pudo eliminar el producto de la base de datos.", Alert.AlertType.ERROR);
                    }
                }
            });
        } else {
            mostrarAlerta("Error", "Selecciona un producto para eliminar.", Alert.AlertType.WARNING);
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
