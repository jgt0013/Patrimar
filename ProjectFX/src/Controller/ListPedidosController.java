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
import javafx.scene.layout.VBox;
import Modelos.Pedido;
import Modelos.Presupuesto;
import Controller.ConexionBD;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import org.apache.poi.ss.usermodel.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;


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
    private VBox vboxOpcionesImprimir;

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
    
    @FXML
    private void toggleOpcionesImprimir() {
        vboxOpcionesImprimir.setVisible(!vboxOpcionesImprimir.isVisible());
    }
    
    
    @FXML
    private void exportarAXLSM() {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet hoja = workbook.createSheet("Pedidos");

            // Crear fila de encabezados
            Row encabezado = hoja.createRow(0);
            encabezado.createCell(0).setCellValue("Nº Pedido");
            encabezado.createCell(1).setCellValue("Fecha");
            encabezado.createCell(2).setCellValue("ID Cliente");
            encabezado.createCell(3).setCellValue("Razón Social");
            encabezado.createCell(4).setCellValue("IVA");
            encabezado.createCell(5).setCellValue("Nº Presupuesto");

            // Rellenar filas con datos
            int filaIndex = 1;
            for (Pedido p : TablePedidos.getItems()) {
                Row fila = hoja.createRow(filaIndex++);
                fila.createCell(0).setCellValue(p.getNPedido());
                fila.createCell(1).setCellValue(p.getFecha());
                fila.createCell(2).setCellValue(p.getIdCliente());
                fila.createCell(3).setCellValue(p.getRazonSocial());
                fila.createCell(4).setCellValue(p.getIva());
                fila.createCell(5).setCellValue(p.getnPresupuesto());
            }

            // Abrir diálogo para elegir ubicación donde guardar
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar archivo Excel");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo Excel (*.xlsx)", "*.xlsx"));
            fileChooser.setInitialFileName("pedidos_exportados.xlsx");

            Stage stage = (Stage) TablePedidos.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                }
                mostrarAlerta("Exportación exitosa", "Archivo exportado correctamente:\n" + file.getAbsolutePath(), Alert.AlertType.INFORMATION);
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo exportar el archivo XLSX.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void exportarPedidosAPDF() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf"));
            fileChooser.setInitialFileName("pedidos_exportados.pdf");

            Stage stage = (Stage) TablePedidos.getScene().getWindow(); // Asegúrate de tener esta tabla definida en tu controlador
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                PdfWriter writer = new PdfWriter(file.getAbsolutePath());
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                document.add(new Paragraph("Lista de Pedidos:"));

                for (Pedido p : TablePedidos.getItems()) {
                    document.add(new Paragraph(
                        "Pedido N°: " + p.getNPedido() +
                        " | Fecha: " + p.getFecha() +
                        " | Cliente ID: " + p.getIdCliente() +
                        " | Razón Social: " + p.getRazonSocial() +
                        " | IVA: " + p.getIva() +
                        " | N° Presupuesto: " + p.getnPresupuesto()
                    ));
                }

                document.close();
                mostrarAlerta("Exportación exitosa", "Archivo PDF exportado correctamente:\n" + file.getAbsolutePath(), Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo exportar el archivo PDF.", Alert.AlertType.ERROR);
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
