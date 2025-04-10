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
import Modelos.Pedido;
import Controller.ConexionBD;
import java.util.List;

public class ListPedidosController {

    @FXML
    private TableView<Pedido> TablePedidos;
    
    @FXML
    private TableColumn<Pedido, String> colNPedido;
    @FXML
    private TableColumn<Pedido, String> colFecha;
    @FXML
    private TableColumn<Pedido, Integer> colIDCliente;
    @FXML
    private TableColumn<Pedido, String> colRazonSocial;
    @FXML
    private TableColumn<Pedido, Double> colIVA;
    @FXML
    private TableColumn<Pedido, String> colNPresupuesto;

    @FXML
    private TextField TFNPedido, TFFecha, TFIDCliente, TFRazonSocial, TFIVA, TFNPresupuesto;

    private ObservableList<Pedido> pedidosList;

    @FXML
    public void initialize() {
        pedidosList = FXCollections.observableArrayList();
        cargarDatos();
        configurarTabla();
    }

    private void configurarTabla() {
        // Asegúrate de que los nombres de las propiedades coincidan con los getters de la clase Pedido.
        colNPedido.setCellValueFactory(new PropertyValueFactory<>("nPedido"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colIDCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory<>("razonSocial"));
        colIVA.setCellValueFactory(new PropertyValueFactory<>("iva"));
        colNPresupuesto.setCellValueFactory(new PropertyValueFactory<>("nPresupuesto"));

        TablePedidos.setItems(pedidosList);
    }

    private void cargarDatos() {
        ConexionBD conexion = new ConexionBD();
        List<Pedido> pedidos = conexion.getAllPedidos();  // Asegúrate de que este método devuelva una lista con datos.
        pedidosList.setAll(pedidos);
    }

    @FXML
    private void eliminarPedido() {
        Pedido pedidoSeleccionado = TablePedidos.getSelectionModel().getSelectedItem();

        if (pedidoSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmación");
            confirmacion.setHeaderText("Eliminar Pedido");
            confirmacion.setContentText("¿Estás seguro de que deseas eliminar el pedido " + pedidoSeleccionado.getNPedido() + "?");

            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.OK) {
                    ConexionBD conexion = new ConexionBD();
                    boolean eliminado = conexion.deletePedido(pedidoSeleccionado.getNPedido());
                    if (eliminado) {
                        pedidosList.remove(pedidoSeleccionado);
                        TablePedidos.refresh();
                    } else {
                        mostrarAlerta("Error", "No se pudo eliminar el pedido de la base de datos.", Alert.AlertType.ERROR);
                    }
                }
            });
        } else {
            mostrarAlerta("Error", "Selecciona un pedido para eliminar.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void limpiarCampos() {
        TFNPedido.clear();
        TFFecha.clear();
        TFIDCliente.clear();
        TFRazonSocial.clear();
        TFIVA.clear();
        TFNPresupuesto.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
