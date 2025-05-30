package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModifUserController extends Application {

    @FXML private TextField TFBuscar;
    @FXML private TextField TFNombre;
    @FXML private TextField TFUser;
    @FXML private TextField TFPassword;

    // CheckBoxes principales (modulos)
    @FXML private CheckBox chkPresupuesto, chkPedidos, chkAlbaranes, chkFacturas, chkClientes,
            chkProveedores, chkDiario, chkResumen, chkOtros;

    // VBox con sub-permisos por módulo (los CheckBoxes dentro)
    @FXML private VBox vboxPresupuesto, vboxPedidos, vboxAlbaranes, vboxFacturas, vboxClientes,
            vboxProveedores, vboxDiario, vboxResumen, vboxOtros;

    @FXML
    private void buscarUsuario(ActionEvent event) {
        String idTexto = TFBuscar.getText().trim();

        if (idTexto.isEmpty()) {
            showAlert(AlertType.WARNING, "Campo vacío", "Por favor, introduce un ID para buscar.");
            return;
        }

        String query = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection connection = ConexionBD.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, Integer.parseInt(idTexto));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Datos básicos
                    TFNombre.setText(rs.getString("nombre"));
                    TFUser.setText(rs.getString("usuario"));
                    TFPassword.setText(rs.getString("password"));

                    // Permisos principales (nuevo)
                    chkPresupuesto.setSelected(rs.getBoolean("presupuesto_nuevo"));
                    chkPedidos.setSelected(rs.getBoolean("pedidos_nuevo"));
                    chkAlbaranes.setSelected(rs.getBoolean("albaranes_nuevo"));
                    chkFacturas.setSelected(rs.getBoolean("facturas_nuevo"));
                    chkClientes.setSelected(rs.getBoolean("clientes_nuevo"));
                    chkProveedores.setSelected(rs.getBoolean("proveedores_nuevo"));
                    chkDiario.setSelected(rs.getBoolean("diario_emitidas"));
                    chkResumen.setSelected(rs.getBoolean("resumen_emitidas"));
                    chkOtros.setSelected(rs.getBoolean("otros_listado_gastos"));

                    // Sub permisos (sin borrar)
                    setCheckBoxFromVBox(vboxPresupuesto, new boolean[]{
                        rs.getBoolean("presupuesto_modificar"),
                        rs.getBoolean("presupuesto_listar")
                    });

                    setCheckBoxFromVBox(vboxPedidos, new boolean[]{
                        rs.getBoolean("pedidos_modificar"),
                        rs.getBoolean("pedidos_listar")
                    });

                    setCheckBoxFromVBox(vboxAlbaranes, new boolean[]{
                        rs.getBoolean("albaranes_modificar"),
                        rs.getBoolean("albaranes_listar")
                    });

                    setCheckBoxFromVBox(vboxFacturas, new boolean[]{
                        rs.getBoolean("facturas_modificar"),
                        rs.getBoolean("facturas_listar")
                    });

                    setCheckBoxFromVBox(vboxClientes, new boolean[]{
                        rs.getBoolean("clientes_modificar"),
                        rs.getBoolean("clientes_listar")
                    });

                    setCheckBoxFromVBox(vboxProveedores, new boolean[]{
                        rs.getBoolean("proveedores_modificar"),
                        rs.getBoolean("proveedores_listar")
                    });

                    setCheckBoxFromVBox(vboxDiario, new boolean[]{
                        rs.getBoolean("diario_recibidas"),
                        rs.getBoolean("diario_gastos")
                    });

                    setCheckBoxFromVBox(vboxResumen, new boolean[]{
                        rs.getBoolean("resumen_recibidas"),
                        rs.getBoolean("resumen_gastos")
                    });

                    setCheckBoxFromVBox(vboxOtros, new boolean[]{
                        rs.getBoolean("otros_servicios"),
                        rs.getBoolean("otros_productos"),
                        rs.getBoolean("otros_usuarios")
                    });

                } else {
                    limpiarCampos();
                    showAlert(AlertType.INFORMATION, "No encontrado", "No existe un usuario con ese ID.");
                }
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Formato inválido", "El ID debe ser un número.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error de Base de Datos", "No se pudo buscar el usuario.");
        }
    }


    @FXML
    private void aplicarCambios(ActionEvent event) {
        String idTexto = TFBuscar.getText().trim();
        String nombre = TFNombre.getText().trim();
        String usuario = TFUser.getText().trim();
        String password = TFPassword.getText().trim();

        if (idTexto.isEmpty() || nombre.isEmpty() || usuario.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campos vacíos", "Todos los campos deben estar llenos.");
            return;
        }

        try {
            int id = Integer.parseInt(idTexto);

            String updateQuery = "UPDATE usuarios SET " +
                    "nombre = ?, usuario = ?, password = ?, " +
                    "presupuesto_nuevo = ?, presupuesto_modificar = ?, presupuesto_listar = ?, " +
                    "pedidos_nuevo = ?, pedidos_modificar = ?, pedidos_listar = ?, " +
                    "albaranes_nuevo = ?, albaranes_modificar = ?, albaranes_listar = ?, " +
                    "facturas_nuevo = ?, facturas_modificar = ?, facturas_listar = ?, " +
                    "clientes_nuevo = ?, clientes_modificar = ?, clientes_listar = ?, " +
                    "proveedores_nuevo = ?, proveedores_modificar = ?, proveedores_listar = ?, " +
                    "diario_emitidas = ?, diario_recibidas = ?, diario_gastos = ?, " +
                    "resumen_emitidas = ?, resumen_recibidas = ?, resumen_gastos = ?, " +
                    "otros_listado_gastos = ?, otros_servicios = ?, otros_productos = ?, otros_usuarios = ? " +
                    "WHERE id = ?";

            try (Connection connection = ConexionBD.getConnection();
                 PreparedStatement ps = connection.prepareStatement(updateQuery)) {

                ps.setString(1, nombre);
                ps.setString(2, usuario);
                ps.setString(3, password);

                // Presupuesto
                ps.setBoolean(4, chkPresupuesto.isSelected());
                ps.setBoolean(5, getCheckBoxBoolean(vboxPresupuesto, 0));
                ps.setBoolean(6, getCheckBoxBoolean(vboxPresupuesto, 1));

                // Pedidos
                ps.setBoolean(7, chkPedidos.isSelected());
                ps.setBoolean(8, getCheckBoxBoolean(vboxPedidos, 0));
                ps.setBoolean(9, getCheckBoxBoolean(vboxPedidos, 1));

                // Albaranes
                ps.setBoolean(10, chkAlbaranes.isSelected());
                ps.setBoolean(11, getCheckBoxBoolean(vboxAlbaranes, 0));
                ps.setBoolean(12, getCheckBoxBoolean(vboxAlbaranes, 1));

                // Facturas
                ps.setBoolean(13, chkFacturas.isSelected());
                ps.setBoolean(14, getCheckBoxBoolean(vboxFacturas, 0));
                ps.setBoolean(15, getCheckBoxBoolean(vboxFacturas, 1));

                // Clientes
                ps.setBoolean(16, chkClientes.isSelected());
                ps.setBoolean(17, getCheckBoxBoolean(vboxClientes, 0));
                ps.setBoolean(18, getCheckBoxBoolean(vboxClientes, 1));

                // Proveedores
                ps.setBoolean(19, chkProveedores.isSelected());
                ps.setBoolean(20, getCheckBoxBoolean(vboxProveedores, 0));
                ps.setBoolean(21, getCheckBoxBoolean(vboxProveedores, 1));

                // Diario
                ps.setBoolean(22, chkDiario.isSelected());
                ps.setBoolean(23, getCheckBoxBoolean(vboxDiario, 0));
                ps.setBoolean(24, getCheckBoxBoolean(vboxDiario, 1));

                // Resumen
                ps.setBoolean(25, chkResumen.isSelected());
                ps.setBoolean(26, getCheckBoxBoolean(vboxResumen, 0));
                ps.setBoolean(27, getCheckBoxBoolean(vboxResumen, 1));

                // Otros
                ps.setBoolean(28, chkOtros.isSelected());
                ps.setBoolean(29, getCheckBoxBoolean(vboxOtros, 0));
                ps.setBoolean(30, getCheckBoxBoolean(vboxOtros, 1));
                ps.setBoolean(31, getCheckBoxBoolean(vboxOtros, 2));

                ps.setInt(32, id);

                int filasActualizadas = ps.executeUpdate();

                if (filasActualizadas > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Usuario actualizado correctamente.");
                    limpiarCampos();
                } else {
                    showAlert(Alert.AlertType.WARNING, "No encontrado", "No existe un usuario con ese ID.");
                }
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Formato inválido", "El ID debe ser un número válido.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error de Base de Datos", "No se pudo actualizar el usuario.");
        }
    }

    @FXML
    private void limpiarCampos() {
        TFBuscar.clear();
        TFNombre.clear();
        TFUser.clear();
        TFPassword.clear();

        chkPresupuesto.setSelected(false);
        chkPedidos.setSelected(false);
        chkAlbaranes.setSelected(false);
        chkFacturas.setSelected(false);
        chkClientes.setSelected(false);
        chkProveedores.setSelected(false);
        chkDiario.setSelected(false);
        chkResumen.setSelected(false);
        chkOtros.setSelected(false);

        clearVBoxCheckboxes(vboxPresupuesto);
        clearVBoxCheckboxes(vboxPedidos);
        clearVBoxCheckboxes(vboxAlbaranes);
        clearVBoxCheckboxes(vboxFacturas);
        clearVBoxCheckboxes(vboxClientes);
        clearVBoxCheckboxes(vboxProveedores);
        clearVBoxCheckboxes(vboxDiario);
        clearVBoxCheckboxes(vboxResumen);
        clearVBoxCheckboxes(vboxOtros);
    }
    
    
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

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper para marcar checkbox de VBox con un array booleano (en orden)
    private void setCheckBoxFromVBox(VBox vbox, boolean[] permisos) {
        for (int i = 0; i < permisos.length && i < vbox.getChildren().size(); i++) {
            if (vbox.getChildren().get(i) instanceof CheckBox) {
                ((CheckBox) vbox.getChildren().get(i)).setSelected(permisos[i]);
            }
        }
    }

    // Helper para leer valor booleano de un checkbox dentro de VBox en cierta posición
    private boolean getCheckBoxBoolean(VBox vbox, int index) {
        if (index >= 0 && index < vbox.getChildren().size()) {
            if (vbox.getChildren().get(index) instanceof CheckBox) {
                return ((CheckBox) vbox.getChildren().get(index)).isSelected();
            }
        }
        return false;
    }

    // Helper para limpiar todos los CheckBoxes dentro de un VBox
    private void clearVBoxCheckboxes(VBox vbox) {
        vbox.getChildren().forEach(node -> {
            if (node instanceof CheckBox) {
                ((CheckBox) node).setSelected(false);
            }
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Si es ventana independiente, carga aquí el FXML
    }

    public static void main(String[] args) {
        launch(args);
    }
}
