package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Modelos.Presupuesto;
import Modelos.Servicio;
import Controller.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.function.Predicate;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class ConsultarServicioController {
	
    @FXML
    private VBox vboxOpcionesImprimir;

    @FXML
    private TextField TFID, TFCodigo, TFServicio, TFPrecio;

    @FXML
    private TableView<Servicio> TableProductos;
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
                    boolean eliminado = conexion.deleteServicio(servicioSeleccionado.getIdServicio());

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

    @FXML
    private void exportarAXLSM() {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet hoja = workbook.createSheet("Servicios");

            Row encabezado = hoja.createRow(0);
            encabezado.createCell(0).setCellValue("ID");
            encabezado.createCell(1).setCellValue("Código");
            encabezado.createCell(2).setCellValue("Servicio");
            encabezado.createCell(3).setCellValue("Precio");

            // Datos de la tabla
            int filaIndex = 1;
            for (Servicio s : TableProductos.getItems()) {
                Row fila = hoja.createRow(filaIndex++);
                fila.createCell(0).setCellValue(s.getIdServicio());
                fila.createCell(1).setCellValue(s.getCodigoServicio());
                fila.createCell(2).setCellValue(s.getServicio());
                fila.createCell(3).setCellValue(s.getImporte());
            }

            // Crear el FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar archivo Excel");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivo Excel (*.xlsx)", "*.xlsx")
            );
            fileChooser.setInitialFileName("servicios_exportados.xlsx");

            // FileChooser
            Stage stage = (Stage) TableProductos.getScene().getWindow();
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
    private void exportarServiciosAPDF() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf"));
            fileChooser.setInitialFileName("servicios_exportados.pdf");

            Stage stage = (Stage) TableProductos.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                PdfWriter writer = new PdfWriter(file.getAbsolutePath());
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                document.add(new Paragraph("Lista de Servicios:"));

                for (Servicio s : TableProductos.getItems()) {
                    document.add(new Paragraph(
                        "ID: " + s.getIdServicio() +
                        " | Código: " + s.getCodigoServicio() +
                        " | Servicio: " + s.getServicio() +
                        " | Precio: $" + s.getImporte()
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
