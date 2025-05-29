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
import Modelos.Presupuesto;
import Controller.ConexionBD;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;

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
    private VBox vboxOpcionesImprimir;
    
    

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
    private void toggleOpcionesImprimir() {
        vboxOpcionesImprimir.setVisible(!vboxOpcionesImprimir.isVisible());
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
    
    @FXML
    private void exportarPresupuestosAXLSX() {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet hoja = workbook.createSheet("Presupuestos");

            // Crear fila de encabezados
            Row encabezado = hoja.createRow(0);
            encabezado.createCell(0).setCellValue("Nº Presupuesto");
            encabezado.createCell(1).setCellValue("Fecha");
            encabezado.createCell(2).setCellValue("ID Cliente");
            encabezado.createCell(3).setCellValue("Razón Social");
            encabezado.createCell(4).setCellValue("IVA");
            encabezado.createCell(5).setCellValue("Completado");

            // Rellenar filas con datos
            int filaIndex = 1;
            for (Presupuesto p : TablePresupuestos.getItems()) {
                Row fila = hoja.createRow(filaIndex++);
                fila.createCell(0).setCellValue(p.getnPresupuesto());
                fila.createCell(1).setCellValue(p.getFecha());
                fila.createCell(2).setCellValue(p.getIdCliente());
                fila.createCell(3).setCellValue(p.getRazonSocial());
                fila.createCell(4).setCellValue(p.getIva());
                fila.createCell(5).setCellValue(p.isCompletado() ? "Sí" : "No");
            }

            // Ajustar tamaño columnas automáticamente
            for (int i = 0; i < 6; i++) {
                hoja.autoSizeColumn(i);
            }

            // Abrir diálogo para elegir ubicación y nombre del archivo
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar archivo Excel");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo Excel (*.xlsx)", "*.xlsx"));
            fileChooser.setInitialFileName("presupuestos_exportados.xlsx");

            Stage stage = (Stage) TablePresupuestos.getScene().getWindow();
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
    private void exportarPresupuestosAPDF() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf"));
            fileChooser.setInitialFileName("presupuestos_exportados.pdf");

            Stage stage = (Stage) TablePresupuestos.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                PdfWriter writer = new PdfWriter(file.getAbsolutePath());
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                document.add(new Paragraph("Lista de Presupuestos:"));

                for (Presupuesto p : presupuestosList) {
                    document.add(new Paragraph(
                        p.getnPresupuesto() + " - " + p.getFecha() + " - " +
                        p.getIdCliente() + " - " + p.getRazonSocial() + " - " +
                        p.getIva() + " - " + (p.isCompletado() ? "Sí" : "No")));
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
