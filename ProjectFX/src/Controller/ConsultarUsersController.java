package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Modelos.Usuario;
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

public class ConsultarUsersController {

    @FXML private TextField TFID;
    @FXML private TextField TFNombre;
    @FXML private TextField TFUsers;

    @FXML private TableView<Usuario> TableUsuarios;

    @FXML private TableColumn<Usuario, Integer> colID;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colPassword;
    @FXML private TableColumn<Usuario, String> colUsers;

    @FXML private TableColumn<Usuario, Boolean> colPresupuestoNuevo;
    @FXML private TableColumn<Usuario, Boolean> colPresupuestoModificar;
    @FXML private TableColumn<Usuario, Boolean> colPresupuestoListar;

    @FXML private TableColumn<Usuario, Boolean> colPedidosNuevo;
    @FXML private TableColumn<Usuario, Boolean> colPedidosModificar;
    @FXML private TableColumn<Usuario, Boolean> colPedidosListar;

    @FXML private TableColumn<Usuario, Boolean> colAlbaranesNuevo;
    @FXML private TableColumn<Usuario, Boolean> colAlbaranesModificar;
    @FXML private TableColumn<Usuario, Boolean> colAlbaranesListar;

    @FXML private TableColumn<Usuario, Boolean> colFacturasNuevo;
    @FXML private TableColumn<Usuario, Boolean> colFacturasModificar;
    @FXML private TableColumn<Usuario, Boolean> colFacturasListar;

    @FXML private TableColumn<Usuario, Boolean> colClientesNuevo;
    @FXML private TableColumn<Usuario, Boolean> colClientesModificar;
    @FXML private TableColumn<Usuario, Boolean> colClientesListar;

    @FXML private TableColumn<Usuario, Boolean> colProveedoresNuevo;
    @FXML private TableColumn<Usuario, Boolean> colProveedoresModificar;
    @FXML private TableColumn<Usuario, Boolean> colProveedoresListar;

    @FXML private TableColumn<Usuario, Boolean> colDiarioEmitidas;
    @FXML private TableColumn<Usuario, Boolean> colDiarioRecibidas;
    @FXML private TableColumn<Usuario, Boolean> colDiarioGastos;

    @FXML private TableColumn<Usuario, Boolean> colResumenEmitidas;
    @FXML private TableColumn<Usuario, Boolean> colResumenRecibidas;
    @FXML private TableColumn<Usuario, Boolean> colResumenGastos;

    @FXML private TableColumn<Usuario, Boolean> colOtrosListadoGastos;
    @FXML private TableColumn<Usuario, Boolean> colOtrosServicios;
    @FXML private TableColumn<Usuario, Boolean> colOtrosProductos;
    @FXML private TableColumn<Usuario, Boolean> colOtrosUsuarios;

    @FXML
    private VBox vboxOpcionesImprimir;

    private ObservableList<Usuario> usuariosList = FXCollections.observableArrayList();
    private ObservableList<Usuario> usuariosFiltrados = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabla();
        cargarDatos();
        agregarFiltros();
    }

    private void configurarTabla() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colUsers.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        colPresupuestoNuevo.setCellValueFactory(new PropertyValueFactory<>("presupuestoNuevo"));
        colPresupuestoModificar.setCellValueFactory(new PropertyValueFactory<>("presupuestoModificar"));
        colPresupuestoListar.setCellValueFactory(new PropertyValueFactory<>("presupuestoListar"));

        colPedidosNuevo.setCellValueFactory(new PropertyValueFactory<>("pedidosNuevo"));
        colPedidosModificar.setCellValueFactory(new PropertyValueFactory<>("pedidosModificar"));
        colPedidosListar.setCellValueFactory(new PropertyValueFactory<>("pedidosListar"));

        colAlbaranesNuevo.setCellValueFactory(new PropertyValueFactory<>("albaranesNuevo"));
        colAlbaranesModificar.setCellValueFactory(new PropertyValueFactory<>("albaranesModificar"));
        colAlbaranesListar.setCellValueFactory(new PropertyValueFactory<>("albaranesListar"));

        colFacturasNuevo.setCellValueFactory(new PropertyValueFactory<>("facturasNuevo"));
        colFacturasModificar.setCellValueFactory(new PropertyValueFactory<>("facturasModificar"));
        colFacturasListar.setCellValueFactory(new PropertyValueFactory<>("facturasListar"));

        colClientesNuevo.setCellValueFactory(new PropertyValueFactory<>("clientesNuevo"));
        colClientesModificar.setCellValueFactory(new PropertyValueFactory<>("clientesModificar"));
        colClientesListar.setCellValueFactory(new PropertyValueFactory<>("clientesListar"));

        colProveedoresNuevo.setCellValueFactory(new PropertyValueFactory<>("proveedoresNuevo"));
        colProveedoresModificar.setCellValueFactory(new PropertyValueFactory<>("proveedoresModificar"));
        colProveedoresListar.setCellValueFactory(new PropertyValueFactory<>("proveedoresListar"));

        colDiarioEmitidas.setCellValueFactory(new PropertyValueFactory<>("diarioEmitidas"));
        colDiarioRecibidas.setCellValueFactory(new PropertyValueFactory<>("diarioRecibidas"));
        colDiarioGastos.setCellValueFactory(new PropertyValueFactory<>("diarioGastos"));

        colResumenEmitidas.setCellValueFactory(new PropertyValueFactory<>("resumenEmitidas"));
        colResumenRecibidas.setCellValueFactory(new PropertyValueFactory<>("resumenRecibidas"));
        colResumenGastos.setCellValueFactory(new PropertyValueFactory<>("resumenGastos"));

        colOtrosListadoGastos.setCellValueFactory(new PropertyValueFactory<>("otrosListadoGastos"));
        colOtrosServicios.setCellValueFactory(new PropertyValueFactory<>("otrosServicios"));
        colOtrosProductos.setCellValueFactory(new PropertyValueFactory<>("otrosProductos"));
        colOtrosUsuarios.setCellValueFactory(new PropertyValueFactory<>("otrosUsuarios"));

        TableUsuarios.setItems(usuariosFiltrados);
    }

    private void cargarDatos() {
        ConexionBD conexion = new ConexionBD();
        List<Usuario> usuarios = conexion.getAllUsuarios();
        usuariosList.setAll(usuarios);
        usuariosFiltrados.setAll(usuarios);
    }

    private void agregarFiltros() {
        TFID.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFNombre.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
        TFUsers.textProperty().addListener((obs, oldVal, newVal) -> filtrarDatos());
    }

    private void filtrarDatos() {
        Predicate<Usuario> filtro = usuario -> {
            boolean coincide = true;

            if (!TFID.getText().isBlank())
                coincide &= String.valueOf(usuario.getId()).contains(TFID.getText().trim());
            if (!TFNombre.getText().isBlank())
                coincide &= usuario.getNombre().toLowerCase().contains(TFNombre.getText().trim().toLowerCase());
            if (!TFUsers.getText().isBlank())
                coincide &= usuario.getUsuario().toLowerCase().contains(TFUsers.getText().trim().toLowerCase());

            return coincide;
        };

        usuariosFiltrados.setAll(usuariosList.stream().filter(filtro).toList());
    }

    @FXML
    private void limpiarCampos() {
        TFID.clear();
        TFNombre.clear();
        TFUsers.clear();
        TableUsuarios.getSelectionModel().clearSelection();
    }

    @FXML
    private void exportarAXLSX() {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet hoja = workbook.createSheet("Usuarios");

            Row encabezado = hoja.createRow(0);
            encabezado.createCell(0).setCellValue("ID");
            encabezado.createCell(1).setCellValue("Nombre");
            encabezado.createCell(2).setCellValue("Password");
            encabezado.createCell(3).setCellValue("Usuario");

            encabezado.createCell(4).setCellValue("Presupuesto Nuevo");
            encabezado.createCell(5).setCellValue("Presupuesto Modificar");
            encabezado.createCell(6).setCellValue("Presupuesto Listar");

            encabezado.createCell(7).setCellValue("Pedidos Nuevo");
            encabezado.createCell(8).setCellValue("Pedidos Modificar");
            encabezado.createCell(9).setCellValue("Pedidos Listar");

            encabezado.createCell(10).setCellValue("Albaranes Nuevo");
            encabezado.createCell(11).setCellValue("Albaranes Modificar");
            encabezado.createCell(12).setCellValue("Albaranes Listar");

            encabezado.createCell(13).setCellValue("Facturas Nuevo");
            encabezado.createCell(14).setCellValue("Facturas Modificar");
            encabezado.createCell(15).setCellValue("Facturas Listar");

            encabezado.createCell(16).setCellValue("Clientes Nuevo");
            encabezado.createCell(17).setCellValue("Clientes Modificar");
            encabezado.createCell(18).setCellValue("Clientes Listar");

            encabezado.createCell(19).setCellValue("Proveedores Nuevo");
            encabezado.createCell(20).setCellValue("Proveedores Modificar");
            encabezado.createCell(21).setCellValue("Proveedores Listar");

            encabezado.createCell(22).setCellValue("Diario Emitidas");
            encabezado.createCell(23).setCellValue("Diario Recibidas");
            encabezado.createCell(24).setCellValue("Diario Gastos");

            encabezado.createCell(25).setCellValue("Resumen Emitidas");
            encabezado.createCell(26).setCellValue("Resumen Recibidas");
            encabezado.createCell(27).setCellValue("Resumen Gastos");

            encabezado.createCell(28).setCellValue("Otros Listado Gastos");
            encabezado.createCell(29).setCellValue("Otros Servicios");
            encabezado.createCell(30).setCellValue("Otros Productos");
            encabezado.createCell(31).setCellValue("Otros Usuarios");

            int filaIndex = 1;
            for (Usuario u : TableUsuarios.getItems()) {
                Row fila = hoja.createRow(filaIndex++);
                fila.createCell(0).setCellValue(u.getId());
                fila.createCell(1).setCellValue(u.getNombre());
                fila.createCell(2).setCellValue(u.getPassword());
                fila.createCell(3).setCellValue(u.getUsuario());

                fila.createCell(4).setCellValue(u.isPresupuestoNuevo());
                fila.createCell(5).setCellValue(u.isPresupuestoModificar());
                fila.createCell(6).setCellValue(u.isPresupuestoListar());

                fila.createCell(7).setCellValue(u.isPedidosNuevo());
                fila.createCell(8).setCellValue(u.isPedidosModificar());
                fila.createCell(9).setCellValue(u.isPedidosListar());

                fila.createCell(10).setCellValue(u.isAlbaranesNuevo());
                fila.createCell(11).setCellValue(u.isAlbaranesModificar());
                fila.createCell(12).setCellValue(u.isAlbaranesListar());

                fila.createCell(13).setCellValue(u.isFacturasNuevo());
                fila.createCell(14).setCellValue(u.isFacturasModificar());
                fila.createCell(15).setCellValue(u.isFacturasListar());

                fila.createCell(16).setCellValue(u.isClientesNuevo());
                fila.createCell(17).setCellValue(u.isClientesModificar());
                fila.createCell(18).setCellValue(u.isClientesListar());

                fila.createCell(19).setCellValue(u.isProveedoresNuevo());
                fila.createCell(20).setCellValue(u.isProveedoresModificar());
                fila.createCell(21).setCellValue(u.isProveedoresListar());

                fila.createCell(22).setCellValue(u.isDiarioEmitidas());
                fila.createCell(23).setCellValue(u.isDiarioRecibidas());
                fila.createCell(24).setCellValue(u.isDiarioGastos());

                fila.createCell(25).setCellValue(u.isResumenEmitidas());
                fila.createCell(26).setCellValue(u.isResumenRecibidas());
                fila.createCell(27).setCellValue(u.isResumenGastos());

                fila.createCell(28).setCellValue(u.isOtrosListadoGastos());
                fila.createCell(29).setCellValue(u.isOtrosServicios());
                fila.createCell(30).setCellValue(u.isOtrosProductos());
                fila.createCell(31).setCellValue(u.isOtrosUsuarios());
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar archivo Excel");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            File archivo = fileChooser.showSaveDialog(new Stage());

            if (archivo != null) {
                try (FileOutputStream fos = new FileOutputStream(archivo)) {
                    workbook.write(fos);
                    mostrarAlerta("Exportación completada", "Archivo Excel creado correctamente.", Alert.AlertType.INFORMATION);
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al exportar a Excel: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void exportarAPDF() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar archivo PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File archivo = fileChooser.showSaveDialog(new Stage());

            if (archivo != null) {
                PdfWriter writer = new PdfWriter(archivo);
                PdfDocument pdfDoc = new PdfDocument(writer);
                Document document = new Document(pdfDoc);

                for (Usuario u : TableUsuarios.getItems()) {
                    String texto = "ID: " + u.getId()
                            + "\nNombre: " + u.getNombre()
                            + "\nPassword: " + u.getPassword()
                            + "\nUsuario: " + u.getUsuario()
                            + "\nPresupuesto -> Nuevo: " + u.isPresupuestoNuevo()
                            + ", Modificar: " + u.isPresupuestoModificar()
                            + ", Listar: " + u.isPresupuestoListar()
                            + "\nPedidos -> Nuevo: " + u.isPedidosNuevo()
                            + ", Modificar: " + u.isPedidosModificar()
                            + ", Listar: " + u.isPedidosListar()
                            + "\nAlbaranes -> Nuevo: " + u.isAlbaranesNuevo()
                            + ", Modificar: " + u.isAlbaranesModificar()
                            + ", Listar: " + u.isAlbaranesListar()
                            + "\nFacturas -> Nuevo: " + u.isFacturasNuevo()
                            + ", Modificar: " + u.isFacturasModificar()
                            + ", Listar: " + u.isFacturasListar()
                            + "\nClientes -> Nuevo: " + u.isClientesNuevo()
                            + ", Modificar: " + u.isClientesModificar()
                            + ", Listar: " + u.isClientesListar()
                            + "\nProveedores -> Nuevo: " + u.isProveedoresNuevo()
                            + ", Modificar: " + u.isProveedoresModificar()
                            + ", Listar: " + u.isProveedoresListar()
                            + "\nDiario -> Emitidas: " + u.isDiarioEmitidas()
                            + ", Recibidas: " + u.isDiarioRecibidas()
                            + ", Gastos: " + u.isDiarioGastos()
                            + "\nResumen -> Emitidas: " + u.isResumenEmitidas()
                            + ", Recibidas: " + u.isResumenRecibidas()
                            + ", Gastos: " + u.isResumenGastos()
                            + "\nOtros -> Listado Gastos: " + u.isOtrosListadoGastos()
                            + ", Servicios: " + u.isOtrosServicios()
                            + ", Productos: " + u.isOtrosProductos()
                            + ", Usuarios: " + u.isOtrosUsuarios()
                            + "\n---------------------------------------------";

                    document.add(new Paragraph(texto));
                }

                document.close();
                mostrarAlerta("Exportación completada", "Archivo PDF creado correctamente.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al exportar a PDF: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
