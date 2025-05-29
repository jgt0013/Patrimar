package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Modelos.Producto;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

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
    private VBox vboxOpcionesImprimir;

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
    
    
    @FXML
    private void toggleOpcionesImprimir() {
        vboxOpcionesImprimir.setVisible(!vboxOpcionesImprimir.isVisible());
    }
    
    
    @FXML
    private void exportarAXLSM() {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet hoja = workbook.createSheet("Productos");

            // Encabezado
            Row encabezado = hoja.createRow(0);
            encabezado.createCell(0).setCellValue("ID");
            encabezado.createCell(1).setCellValue("Código");
            encabezado.createCell(2).setCellValue("Producto");
            encabezado.createCell(3).setCellValue("Precio");

            // Datos de la tabla
            int filaIndex = 1;
            for (Producto p : TableProductos.getItems()) {
                Row fila = hoja.createRow(filaIndex++);
                fila.createCell(0).setCellValue(p.getIdProducto());
                fila.createCell(1).setCellValue(p.getCodigoProducto());
                fila.createCell(2).setCellValue(p.getProducto());
                fila.createCell(3).setCellValue(p.getImporte());
            }

            // Crear el FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar archivo Excel");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivo Excel (*.xlsx)", "*.xlsx")
            );
            fileChooser.setInitialFileName("productos_exportados.xlsx");

            // Obtener ventana actual
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
    private void exportarProductosAPDF() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf"));
            fileChooser.setInitialFileName("productos_exportados.pdf");

            Stage stage = (Stage) TableProductos.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                PdfWriter writer = new PdfWriter(file.getAbsolutePath());
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                document.add(new Paragraph("Lista de Productos:"));

                for (Producto p : TableProductos.getItems()) {
                    document.add(new Paragraph(
                        "ID: " + p.getIdProducto() +
                        " | Código: " + p.getCodigoProducto() +
                        " | Producto: " + p.getProducto() +
                        " | Importe: " + p.getImporte()
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
