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
import Modelos.Presupuesto;
import Controller.ConexionBD;

import java.util.List;

public class ListPresupuestosController {

    @FXML
    private TableView<Presupuesto> TablePresupuestos;
    @FXML
    private TableColumn<Presupuesto, String> colNPresupuesto;
    @FXML
    private TableColumn<Presupuesto, String> colFecha;
    @FXML
    private TableColumn<Presupuesto, Integer> colIDCliente;
    @FXML
    private TableColumn<Presupuesto, String> colRazonSocial;
    @FXML
    private TableColumn<Presupuesto, Double> colIVA;
    @FXML
    private TableColumn<Presupuesto, Boolean> colCompletado;

    @FXML
    private TextField TFNPresupuesto, TFFecha, TFIDCliente, TFRazonSocial, TFIVA, TFCompletado;

    private ObservableList<Presupuesto> presupuestosList;

    @FXML
    public void initialize() {
        presupuestosList = FXCollections.observableArrayList();
        cargarDatos();
        configurarTabla();
    }

    private void configurarTabla() {
        colNPresupuesto.setCellValueFactory(new PropertyValueFactory<>("nPresupuesto"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colIDCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory<>("razonSocial"));
        colIVA.setCellValueFactory(new PropertyValueFactory<>("iva"));
        colCompletado.setCellValueFactory(new PropertyValueFactory<>("completado"));

        TablePresupuestos.setItems(presupuestosList);
    }

    private void cargarDatos() {
        ConexionBD conexion = new ConexionBD();
        List<Presupuesto> presupuestos = conexion.getAllPresupuestos();
        System.out.println("Presupuestos cargados: " + presupuestos);
        presupuestosList.setAll(presupuestos);
    }


    @FXML
    private void eliminarPresupuesto() {
        Presupuesto presupuestoSeleccionado = TablePresupuestos.getSelectionModel().getSelectedItem();

        if (presupuestoSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmación");
            confirmacion.setHeaderText("Eliminar Presupuesto");
            confirmacion.setContentText("¿Estás seguro de que quieres eliminar el presupuesto " + presupuestoSeleccionado.getnPresupuesto() + "?");

            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.OK) {
                    ConexionBD conexion = new ConexionBD();
                    boolean eliminado = conexion.deletePresupuesto(presupuestoSeleccionado.getnPresupuesto());

                    if (eliminado) {
                        presupuestosList.remove(presupuestoSeleccionado);
                        TablePresupuestos.refresh();
                    } else {
                        mostrarAlerta("Error", "No se pudo eliminar el presupuesto de la base de datos.", Alert.AlertType.ERROR);
                    }
                }
            });
        } else {
            mostrarAlerta("Error", "Selecciona un presupuesto para eliminar.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void limpiarCampos() {
        TFNPresupuesto.clear();
        TFFecha.clear();
        TFIDCliente.clear();
        TFRazonSocial.clear();
        TFIVA.clear();
        TFCompletado.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
