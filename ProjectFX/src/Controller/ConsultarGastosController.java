package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Modelos.Gastos;
import Controller.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.function.Predicate;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class ConsultarGastosController {

    @FXML
    private VBox vboxOpcionesImprimir;

    @FXML
    private TextField TFID, TFNombre, TFTipo;

    @FXML
    private TableView<Gastos> TableGastos;
    @FXML
    private TableColumn<Gastos, Integer> colID;
    @FXML
    private TableColumn<Gastos, String> colNombre;
    @FXML
    private TableColumn<Gastos, String> colTipo;

    private ObservableList<Gastos> gastosList = FXCollections.observableArrayList();
    private ObservableList<Gastos> gastosFiltrados = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        configurarTabla();
        cargarDatos();
        agregarFiltros();
    }

    private void configurarTabla() {
        colID.setCellValueFactory(new PropertyValueFactory<>("idGasto"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableGastos.setItems(gastosFiltrados);
    }

    private void cargarDatos() {
        ConexionBD conexion = new ConexionBD();
        List<Gastos> gastos = conexion.getAllGastos();
        gastosList.setAll(gastos);
        gastosFiltrados.setAll(gastos);
    }

    private void agregarFiltros() {
        TFID.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFNombre.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFTipo.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
    }

    private void filtrarDatos() {
        Predicate<Gastos> filtro = gasto -> {
            boolean coincide = true;

            if (!TFID.getText().isBlank())
                coincide &= String.valueOf(gasto.getIdGasto()).contains(TFID.getText().trim());
            if (!TFNombre.getText().isBlank())
                coincide &= gasto.getNombre().toLowerCase().contains(TFNombre.getText().trim().toLowerCase());
            if (!TFTipo.getText().isBlank())
                coincide &= gasto.getTipo().toLowerCase().contains(TFTipo.getText().trim().toLowerCase());

            return coincide;
        };

        gastosFiltrados.setAll(gastosList.stream().filter(filtro).toList());
    }

    @FXML
    private void eliminarGasto() {
        Gastos gastoSeleccionado = TableGastos.getSelectionModel().getSelectedItem();

        if (gastoSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmación");
            confirmacion.setHeaderText("Eliminar Gasto");
            confirmacion.setContentText("¿Estás seguro de que quieres eliminar el gasto: " + gastoSeleccionado.getNombre() + "?");

            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.OK) {
                    ConexionBD conexion = new ConexionBD();
                    boolean eliminado = conexion.deleteGasto(gastoSeleccionado.getIdGasto());

                    if (eliminado) {
                        gastosList.remove(gastoSeleccionado);
                        gastosFiltrados.setAll(gastosList);
                        TableGastos.refresh();
                    } else {
                        mostrarAlerta("Error", "No se pudo eliminar el gasto de la base de datos.", Alert.AlertType.ERROR);
                    }
                }
            });
        } else {
            mostrarAlerta("Error", "Selecciona un gasto para eliminar.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void limpiarCampos() {
        TFID.clear();
        TFNombre.clear();
        TFTipo.clear();
    }

    @FXML
    private void exportarAXLSX() {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet hoja = workbook.createSheet("Gastos");

            Row encabezado = hoja.createRow(0);
            encabezado.createCell(0).setCellValue("ID");
            encabezado.createCell(1).setCellValue("Nombre");
            encabezado.createCell(2).setCellValue("Tipo");

            int filaIndex = 1;
            for (Gastos g : TableGastos.getItems()) {
                Row fila = hoja.createRow(filaIndex++);
                fila.createCell(0).setCellValue(g.getIdGasto());
                fila.createCell(1).setCellValue(g.getNombre());
                fila.createCell(2).setCellValue(g.getTipo());
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar archivo Excel");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo Excel (*.xlsx)", "*.xlsx"));
            fileChooser.setInitialFileName("gastos_exportados.xlsx");

            Stage stage = (Stage) TableGastos.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                }
                mostrarAlerta("Éxito", "Archivo exportado correctamente:\n" + file.getAbsolutePath(), Alert.AlertType.INFORMATION);
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo exportar el archivo XLSX.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void toggleOpcionesImprimir() {
        vboxOpcionesImprimir.setVisible(!vboxOpcionesImprimir.isVisible());
    }

    @FXML
    private void exportarGastosAPDF() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf"));
            fileChooser.setInitialFileName("gastos_exportados.pdf");

            Stage stage = (Stage) TableGastos.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                PdfWriter writer = new PdfWriter(file.getAbsolutePath());
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                document.add(new Paragraph("Lista de Gastos:"));

                for (Gastos g : TableGastos.getItems()) {
                    document.add(new Paragraph(
                        "ID: " + g.getIdGasto() +
                        " | Nombre: " + g.getNombre() +
                        " | Tipo: " + g.getTipo()
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

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
