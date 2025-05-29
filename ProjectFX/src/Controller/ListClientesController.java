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

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import Modelos.Cliente;
import Modelos.Presupuesto;
import Controller.ConexionBD;

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
	private TableColumn<Cliente, String> colMetodoPago;
	@FXML
	private TableColumn<Cliente, String> colPuedePedir;


    @FXML
    private TextField TFID, TFNombre, TFApellidos, TFDNI, TFEmail, TFBanco, TFDireccion, TFProvincia, TFCPostal, TFContacto1, TFContacto2,
    TFContacto3, TFTel1, TFTel2, TFTel3, TFObservaciones, TFMetodoPago, TFPuedePedir, TFRS;

    private ObservableList<Cliente> clientesList;
    private ObservableList<Cliente> clientesFiltrados;
    
    @FXML
    private javafx.scene.control.Button btnEliminar;
    
    @FXML
    private VBox vboxOpcionesImprimir;

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
        colBanco.setCellValueFactory(new PropertyValueFactory<>("CuentaBancaria"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        colMetodoPago.setCellValueFactory(new PropertyValueFactory<>("metodoPago"));
        colPuedePedir.setCellValueFactory(new PropertyValueFactory<>("puedePedir"));

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
        TFRS.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
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
        TFMetodoPago.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFPuedePedir.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
    }


    private void filtrarDatos() {
        Predicate<Cliente> filtro = cliente -> {
            boolean coincide = true;

            if (!TFID.getText().isBlank()) coincide &= String.valueOf(cliente.getId()).contains(TFID.getText().trim());
            if (!TFNombre.getText().isBlank()) coincide &= cliente.getNombre().toLowerCase().contains(TFNombre.getText().trim().toLowerCase());
            if (!TFApellidos.getText().isBlank()) coincide &= cliente.getApellidos().toLowerCase().contains(TFApellidos.getText().trim().toLowerCase());
            if (!TFRS.getText().isBlank()) coincide &= cliente.getRazonSocial().toLowerCase().contains(TFRS.getText().trim().toLowerCase());
            if (!TFDNI.getText().isBlank()) coincide &= cliente.getCifDni().toLowerCase().contains(TFDNI.getText().trim().toLowerCase());
            if (!TFEmail.getText().isBlank()) coincide &= cliente.getEmail().toLowerCase().contains(TFEmail.getText().trim().toLowerCase());
            if (!TFBanco.getText().isBlank()) coincide &= cliente.getCuentaBancaria().toLowerCase().contains(TFBanco.getText().trim().toLowerCase());
            if (!TFDireccion.getText().isBlank()) coincide &= cliente.getDireccion().toLowerCase().contains(TFDireccion.getText().trim().toLowerCase());
            if (!TFProvincia.getText().isBlank()) coincide &= cliente.getProvincia().toLowerCase().contains(TFProvincia.getText().trim().toLowerCase());
            if (!TFCPostal.getText().isBlank()) coincide &= cliente.getCodigoPostal().toLowerCase().contains(TFCPostal.getText().trim().toLowerCase());
            if (!TFContacto1.getText().isBlank()) coincide &= cliente.getPersonaContacto1().toLowerCase().contains(TFContacto1.getText().trim().toLowerCase());
            if (!TFContacto2.getText().isBlank()) coincide &= cliente.getPersonaContacto2().toLowerCase().contains(TFContacto2.getText().trim().toLowerCase());
            if (!TFContacto3.getText().isBlank()) coincide &= cliente.getPersonaContacto3().toLowerCase().contains(TFContacto3.getText().trim().toLowerCase());
            if (!TFTel1.getText().isBlank()) coincide &= cliente.getTelefono1().contains(TFTel1.getText().trim());
            if (!TFTel2.getText().isBlank()) coincide &= cliente.getTelefono2().contains(TFTel2.getText().trim());
            if (!TFTel3.getText().isBlank()) coincide &= cliente.getTelefono3().contains(TFTel3.getText().trim());
            if (!TFObservaciones.getText().isBlank()) coincide &= cliente.getObservaciones().toLowerCase().contains(TFObservaciones.getText().trim().toLowerCase());
            if (!TFMetodoPago.getText().isBlank()) coincide &= cliente.getMetodoPago().toLowerCase().contains(TFMetodoPago.getText().trim().toLowerCase());

            if (!TFPuedePedir.getText().isBlank()) {
                boolean filtroPuedePedir = TFPuedePedir.getText().trim().equalsIgnoreCase("true");
                coincide &= (cliente.getPuedePedir() == filtroPuedePedir);
            }

            return coincide;
        };

        clientesFiltrados.setAll(clientesList.stream().filter(filtro).toList());
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
                        clientesFiltrados.setAll(clientesList); // 🔄 Asegurar que la tabla refleje el cambio
                        TableClientes.refresh(); // Forzar refresco de la tabla
                    } else {
                        mostrarAlerta("Error", "No se pudo eliminar el cliente de la base de datos.", Alert.AlertType.ERROR);
                    }
                }
            });
        } else {
            mostrarAlerta("Error", "Selecciona un cliente para eliminar.", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void limpiarCampos() {
        TFID.clear();
        TFNombre.clear();
        TFApellidos.clear();
        TFDNI.clear();
        TFEmail.clear();
        TFBanco.clear();
        TFDireccion.clear();
        TFProvincia.clear();
        TFCPostal.clear();
        TFContacto1.clear();
        TFContacto2.clear();
        TFContacto3.clear();
        TFTel1.clear();
        TFTel2.clear();
        TFTel3.clear();
        TFObservaciones.clear();
        TFMetodoPago.clear();
        TFPuedePedir.clear();
        TFRS.clear();
    }
    
    @FXML
    private void toggleOpcionesImprimir() {
        vboxOpcionesImprimir.setVisible(!vboxOpcionesImprimir.isVisible());
    }
    
    
    @FXML
    private void exportarAXLSM() {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet hoja = workbook.createSheet("Clientes");

            // Encabezado
            Row encabezado = hoja.createRow(0);
            encabezado.createCell(0).setCellValue("ID");
            encabezado.createCell(1).setCellValue("Nombre");
            encabezado.createCell(2).setCellValue("Apellidos");
            encabezado.createCell(3).setCellValue("Razón Social");
            encabezado.createCell(4).setCellValue("DNI");
            encabezado.createCell(5).setCellValue("Dirección");
            encabezado.createCell(6).setCellValue("C. Postal");
            encabezado.createCell(7).setCellValue("Provincia");
            encabezado.createCell(8).setCellValue("Contacto 1");
            encabezado.createCell(9).setCellValue("Tel. 1");
            encabezado.createCell(10).setCellValue("Contacto 2");
            encabezado.createCell(11).setCellValue("Tel. 2");
            encabezado.createCell(12).setCellValue("Contacto 3");
            encabezado.createCell(13).setCellValue("Tel. 3");
            encabezado.createCell(14).setCellValue("Email");
            encabezado.createCell(15).setCellValue("Banco");
            encabezado.createCell(16).setCellValue("Observaciones");
            encabezado.createCell(17).setCellValue("Método de Pago");
            encabezado.createCell(18).setCellValue("¿Puede Pedir?");

            // Datos
            int filaIndex = 1;
            for (Cliente c : TableClientes.getItems()) {
                Row fila = hoja.createRow(filaIndex++);
                fila.createCell(0).setCellValue(c.getId());
                fila.createCell(1).setCellValue(c.getNombre());
                fila.createCell(2).setCellValue(c.getApellidos());
                fila.createCell(3).setCellValue(c.getRazonSocial());
                fila.createCell(4).setCellValue(c.getCifDni());
                fila.createCell(5).setCellValue(c.getDireccion());
                fila.createCell(6).setCellValue(c.getCodigoPostal());
                fila.createCell(7).setCellValue(c.getProvincia());
                fila.createCell(8).setCellValue(c.getPersonaContacto1());
                fila.createCell(9).setCellValue(c.getTelefono1());
                fila.createCell(10).setCellValue(c.getPersonaContacto2());
                fila.createCell(11).setCellValue(c.getTelefono2());
                fila.createCell(12).setCellValue(c.getPersonaContacto3());
                fila.createCell(13).setCellValue(c.getTelefono3());
                fila.createCell(14).setCellValue(c.getEmail());
                fila.createCell(15).setCellValue(c.getCuentaBancaria());
                fila.createCell(16).setCellValue(c.getObservaciones());
                fila.createCell(17).setCellValue(c.getMetodoPago());
                fila.createCell(18).setCellValue(c.getPuedePedir() ? "Sí" : "No");
            }

            // FileChooser para guardar archivo
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar archivo Excel");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivo Excel (*.xlsx)", "*.xlsx")
            );
            fileChooser.setInitialFileName("clientes_exportados.xlsx");

            Stage stage = (Stage) TableClientes.getScene().getWindow();
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
    private void exportarClientesAPDF() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf"));
            fileChooser.setInitialFileName("clientes_exportados.pdf");

            Stage stage = (Stage) TableClientes.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                PdfWriter writer = new PdfWriter(file.getAbsolutePath());
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                document.add(new Paragraph("Lista de Clientes:"));
                document.add(new Paragraph(" "));

                for (Cliente c : clientesList) {
                    String clienteInfo =
                        "ID: " + c.getId() +
                        " | Nombre: " + c.getNombre() + " " + c.getApellidos() +
                        " | Razón Social: " + c.getRazonSocial() +
                        " | CIF/DNI: " + c.getCifDni() +
                        " | Dirección: " + c.getDireccion() + ", " + c.getCodigoPostal() + " " + c.getPoblacion() +
                        " | Provincia: " + c.getProvincia() +
                        " | Email: " + c.getEmail() +
                        " | Teléfonos: " + c.getTelefono1();

                    if (!c.getTelefono2().isEmpty()) clienteInfo += ", " + c.getTelefono2();
                    if (!c.getTelefono3().isEmpty()) clienteInfo += ", " + c.getTelefono3();

                    clienteInfo += " | Puede pedir: " + (c.getPuedePedir() ? "Sí" : "No");

                    document.add(new Paragraph(clienteInfo));
                    document.add(new Paragraph(" "));
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
