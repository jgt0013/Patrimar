package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewUserController {

	@FXML private CheckBox chkPresupuesto, chkPedidos, chkAlbaranes, chkFacturas, chkClientes, chkProveedores, chkDiario, chkResumen, chkOtros;
	@FXML private VBox vboxPresupuesto, vboxPedidos, vboxAlbaranes, vboxFacturas, vboxClientes, vboxProveedores, vboxDiario, vboxResumen, vboxOtros;
	
	@FXML private javafx.scene.control.TextField TFNombre;
	@FXML private javafx.scene.control.TextField TFUser;
	@FXML private javafx.scene.control.PasswordField TFPassword;


	@FXML
	private void toggleCheckboxes() {
	    vboxPresupuesto.setVisible(chkPresupuesto.isSelected());
	    vboxPresupuesto.setManaged(chkPresupuesto.isSelected());

	    vboxPedidos.setVisible(chkPedidos.isSelected());
	    vboxPedidos.setManaged(chkPedidos.isSelected());

	    vboxAlbaranes.setVisible(chkAlbaranes.isSelected());
	    vboxAlbaranes.setManaged(chkAlbaranes.isSelected());

	    vboxFacturas.setVisible(chkFacturas.isSelected());
	    vboxFacturas.setManaged(chkFacturas.isSelected());

	    vboxClientes.setVisible(chkClientes.isSelected());
	    vboxClientes.setManaged(chkClientes.isSelected());

	    vboxProveedores.setVisible(chkProveedores.isSelected());
	    vboxProveedores.setManaged(chkProveedores.isSelected());

	    vboxDiario.setVisible(chkDiario.isSelected());
	    vboxDiario.setManaged(chkDiario.isSelected());

	    vboxResumen.setVisible(chkResumen.isSelected());
	    vboxResumen.setManaged(chkResumen.isSelected());
	    
	    vboxOtros.setVisible(chkOtros.isSelected());
	    vboxOtros.setManaged(chkOtros.isSelected());
	}
	
	
	
	@FXML
	private void AddUsuario(ActionEvent event) {
	    String addUsuarioQuery = "INSERT INTO usuarios (nombre, usuario, password, " +
	        "presupuesto_nuevo, presupuesto_modificar, presupuesto_listar, " +
	        "pedidos_nuevo, pedidos_modificar, pedidos_listar, " +
	        "albaranes_nuevo, albaranes_modificar, albaranes_listar, " +
	        "facturas_nuevo, facturas_modificar, facturas_listar, " +
	        "clientes_nuevo, clientes_modificar, clientes_listar, " +
	        "proveedores_nuevo, proveedores_modificar, proveedores_listar, " +
	        "diario_emitidas, diario_recibidas, diario_gastos, " +
	        "resumen_emitidas, resumen_recibidas, resumen_gastos, " +
	        "otros_listado_gastos, otros_servicios, otros_productos, otros_usuarios) " +
	        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try {
	        String nombre = TFNombre.getText().trim();
	        String usuario = TFUser.getText().trim();
	        String contrasena = TFPassword.getText().trim();

	        if (nombre.isEmpty() || usuario.isEmpty() || contrasena.isEmpty()) {
	            showAlert(Alert.AlertType.WARNING, "Campos incompletos", "Nombre, usuario y contraseña son obligatorios.");
	            return;
	        }

	        try (Connection connection = ConexionBD.getConnection()) {
	            if (connection == null || connection.isClosed()) {
	                showAlert(Alert.AlertType.ERROR, "Error de conexión", "No se pudo conectar a la base de datos.");
	                return;
	            }

	            try (PreparedStatement ps = connection.prepareStatement(addUsuarioQuery)) {
	                ps.setString(1, nombre);
	                ps.setString(2, usuario);
	                ps.setString(3, contrasena);

	                // Presupuesto (nuevo, modificar, listar)
	                ps.setBoolean(4, getCheckBoxBoolean(vboxPresupuesto, 0));
	                ps.setBoolean(5, getCheckBoxBoolean(vboxPresupuesto, 1));
	                ps.setBoolean(6, getCheckBoxBoolean(vboxPresupuesto, 2));

	                // Pedidos (nuevo, modificar, listar)
	                ps.setBoolean(7, getCheckBoxBoolean(vboxPedidos, 0));
	                ps.setBoolean(8, getCheckBoxBoolean(vboxPedidos, 1));
	                ps.setBoolean(9, getCheckBoxBoolean(vboxPedidos, 2));

	                // Albaranes (nuevo, modificar, listar)
	                ps.setBoolean(10, getCheckBoxBoolean(vboxAlbaranes, 0));
	                ps.setBoolean(11, getCheckBoxBoolean(vboxAlbaranes, 1));
	                ps.setBoolean(12, getCheckBoxBoolean(vboxAlbaranes, 2));

	                // Facturas (nuevo, modificar, listar)
	                ps.setBoolean(13, getCheckBoxBoolean(vboxFacturas, 0));
	                ps.setBoolean(14, getCheckBoxBoolean(vboxFacturas, 1));
	                ps.setBoolean(15, getCheckBoxBoolean(vboxFacturas, 2));

	                // Clientes (nuevo, modificar, listar)
	                ps.setBoolean(16, getCheckBoxBoolean(vboxClientes, 0));
	                ps.setBoolean(17, getCheckBoxBoolean(vboxClientes, 1));
	                ps.setBoolean(18, getCheckBoxBoolean(vboxClientes, 2));

	                // Proveedores (nuevo, modificar, listar)
	                ps.setBoolean(19, getCheckBoxBoolean(vboxProveedores, 0));
	                ps.setBoolean(20, getCheckBoxBoolean(vboxProveedores, 1));
	                ps.setBoolean(21, getCheckBoxBoolean(vboxProveedores, 2));

	                // Diario (emitidas, recibidas, gastos)
	                ps.setBoolean(22, getCheckBoxBoolean(vboxDiario, 0));
	                ps.setBoolean(23, getCheckBoxBoolean(vboxDiario, 1));
	                ps.setBoolean(24, getCheckBoxBoolean(vboxDiario, 2));

	                // Resumen (emitidas, recibidas, gastos)
	                ps.setBoolean(25, getCheckBoxBoolean(vboxResumen, 0));
	                ps.setBoolean(26, getCheckBoxBoolean(vboxResumen, 1));
	                ps.setBoolean(27, getCheckBoxBoolean(vboxResumen, 2));

	                // Otros (listado_gastos, servicios, productos, usuarios)
	                ps.setBoolean(28, getCheckBoxBoolean(vboxOtros, 0));
	                ps.setBoolean(29, getCheckBoxBoolean(vboxOtros, 1));
	                ps.setBoolean(30, getCheckBoxBoolean(vboxOtros, 2));
	                ps.setBoolean(31, getCheckBoxBoolean(vboxOtros, 3));

	                int rowsInserted = ps.executeUpdate();

	                if (rowsInserted > 0) {
	                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Usuario añadido correctamente.");
	                    TFNombre.clear();
	                    TFUser.clear();
	                    TFPassword.clear();
	                    clearCheckboxes();
	                } else {
	                    showAlert(Alert.AlertType.ERROR, "Error", "No se pudo añadir el usuario.");
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            showAlert(Alert.AlertType.ERROR, "Error SQL", "Error al insertar el usuario: " + e.getMessage());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        showAlert(Alert.AlertType.ERROR, "Error", "Ocurrió un error inesperado: " + e.getMessage());
	    }
	}



	private boolean getCheckBoxBoolean(VBox vbox, int index) {
	    if (vbox != null && vbox.getChildren().size() > index) {
	        if (vbox.getChildren().get(index) instanceof CheckBox) {
	            return ((CheckBox) vbox.getChildren().get(index)).isSelected();
	        }
	    }
	    return false;
	}

	private void clearCheckboxes() {
	    chkPresupuesto.setSelected(false);
	    chkPedidos.setSelected(false);
	    chkAlbaranes.setSelected(false);
	    chkFacturas.setSelected(false);
	    chkClientes.setSelected(false);
	    chkProveedores.setSelected(false);
	    chkDiario.setSelected(false);
	    chkResumen.setSelected(false);
	    chkOtros.setSelected(false);

	    clearSubCheckboxes(vboxPresupuesto);
	    clearSubCheckboxes(vboxPedidos);
	    clearSubCheckboxes(vboxAlbaranes);
	    clearSubCheckboxes(vboxFacturas);
	    clearSubCheckboxes(vboxClientes);
	    clearSubCheckboxes(vboxProveedores);
	    clearSubCheckboxes(vboxDiario);
	    clearSubCheckboxes(vboxResumen);
	    clearSubCheckboxes(vboxOtros);
	}

	private void clearSubCheckboxes(VBox vbox) {
	    if (vbox != null) {
	        for (var node : vbox.getChildren()) {
	            if (node instanceof CheckBox) {
	                ((CheckBox) node).setSelected(false);
	            }
	        }
	    }
	}


	
	@FXML
    private void abrirModifUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ModifUsers.fxml"));
            Scene scene = new Scene(loader.load(), 1920, 1080);

            Stage stage = new Stage();
            stage.setTitle("Modificar Usuarios");
            stage.setScene(scene);
            stage.show();
            stage.setMaximized(true);

            scene.getStylesheets().add(getClass().getResource("/Styles/ModifUserCSS.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@FXML
    private void abrirConsultarUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ConsultarUsers.fxml"));
            Scene scene = new Scene(loader.load(), 1920, 1080);

            Stage stage = new Stage();
            stage.setTitle("Consultar Usuarios");
            stage.setScene(scene);
            stage.show();
            stage.setMaximized(true);

            scene.getStylesheets().add(getClass().getResource("/Styles/ConsultarUsersCSS.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	private void showAlert(AlertType type, String title, String message) {
	    Alert alert = new Alert(type);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}

}
