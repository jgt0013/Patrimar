package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Modelos.Cliente;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/programa";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    // Método conexión
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static void testConnection() {
        try (Connection connection = getConnection()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("✅ Conexión exitosa a la base de datos.");
            } else {
                System.out.println("❌ No se pudo establecer la conexión.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

  @FXML  
 // Método agregar a cliente
    public void addTablaCliente() {
	  agregarClienteProveedor("clientes");
    }
  
  @FXML  
  // Método agregar a cliente
     public void addTablaProveedor() {
     	agregarClienteProveedor("proveedores");
     }
   
    // Método agregar un nuevo cliente
    public void addRegistroClienteProveedor(String tabla, String iNombre, String iApellidos, String iRazonSocial, String iDNI_Cliente, String iDireccion,
    		 String iCodigoPostal, String iProvincia, String iPersonaContacto1, String iTelefono1, 
                              String iPersonaContacto2, String iTelefono2, String iPersonaContacto3, String iTelefono3, 
                              String iEmail, String iBanco, String iObservaciones) {

        String addClienteQuery = "INSERT INTO "+ tabla +" (nombre, apellidos, razon_social, cif_nif, direccion, cp, provincia, " +
                                 "persona_contacto_1, telefono1, persona_contacto_2, telefono2, persona_contacto_3, telefono3, email, cuenta_bancaria, observaciones) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection connection = getConnection()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Conexión a la base de datos establecida con éxito.");
            } else {
                System.out.println("Error: No se pudo establecer la conexión.");
                return;  // Si no hay conexión, no intentamos realizar la consulta.
            }

            try (PreparedStatement ps = connection.prepareStatement(addClienteQuery)) {
                // Establecer los parámetros de la consulta
                ps.setString(1, iNombre);
                ps.setString(2, iApellidos);
                ps.setString(3, iRazonSocial);
                ps.setString(4, iDNI_Cliente);
                ps.setString(5, iDireccion);
                ps.setString(6, iProvincia);
                ps.setString(7, iCodigoPostal);
                ps.setString(8, iPersonaContacto1);
                ps.setString(9, iTelefono1);
                ps.setString(10, iPersonaContacto2);
                ps.setString(11, iTelefono2);
                ps.setString(12, iPersonaContacto3);
                ps.setString(13, iTelefono3);
                ps.setString(14, iEmail);
                ps.setString(15, iObservaciones);
                ps.setString(16, iBanco);

            ps.executeUpdate();
            
            System.out.println("INSERT INTO Cliente VALUES: " +
                    iNombre + ", " + iApellidos + ", " + iRazonSocial + ", " + iDNI_Cliente + ", " +
                    iDireccion + ", " + iProvincia + ", " + iCodigoPostal + ", " +
                    iPersonaContacto1 + ", " + iTelefono1 + ", " + 
                    iPersonaContacto2 + ", " + iTelefono2 + ", " + 
                    iPersonaContacto3 + ", " + iTelefono3 + ", " + 
                    iEmail + ", " + iBanco + ", " + iObservaciones);
            
            // JOptionPane para javaFX
            showAlert("Éxito", "¡Operación realizada con éxito!", AlertType.INFORMATION);
            

            } catch (SQLException e) {
                System.out.println("Error al ejecutar la consulta: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    @FXML
    private TextField txtNombre, txtApellidos, txtRazonSocial, txtCIFNIF, txtDireccion, txtProvincia, 
                      txtCodigoPostal, txtPersonaContacto1, txtTelefono1, txtPersonaContacto2, 
                      txtTelefono2, txtPersonaContacto3, txtTelefono3, txtEmail, txtBanco;
    @FXML
    private TextArea tarObservaciones;
    
    @FXML
    private void agregarClienteProveedor(String tabla) {
        try {
            // Obtener valores desde los campos de texto
            String nombre = txtNombre.getText();
            String apellidos = txtApellidos.getText();
            String razonSocial = txtRazonSocial.getText();
            String dni = txtCIFNIF.getText();
            String direccion = txtDireccion.getText();
            String provincia = txtProvincia.getText();
            String codigoPostal =txtCodigoPostal.getText();
            String personaContacto1 = txtPersonaContacto1.getText();
            String telefono1 = txtTelefono1.getText();
            String personaContacto2 = txtPersonaContacto2.getText();
            String telefono2 = txtTelefono2.getText();
            String personaContacto3 = txtPersonaContacto3.getText();
            String telefono3 = txtTelefono3.getText();
            String email = txtEmail.getText();
            String banco = txtBanco.getText();
            String observaciones = tarObservaciones.getText();

            // Llamar al método addCliente
            addRegistroClienteProveedor(tabla, nombre, apellidos, razonSocial, dni, direccion, codigoPostal, provincia,
                       personaContacto1, telefono1, personaContacto2, telefono2,
                       personaContacto3, telefono3, email, banco, observaciones);

            showAlert("Éxito", "Cliente agregado correctamente.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Error", "Por favor ingrese datos válidos.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert1(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
    public ObservableList<Cliente> getAllClientes() {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList();
        String query = "SELECT * FROM Cliente;";
        
        try (Connection connection = getConnection(); 
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
            	int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String razon_social = rs.getString("razon_social");
                String cif_dni = rs.getString("cif_dni");
                String direccion = rs.getString("direccion");
                String provincia = rs.getString("provincia");
                int codigo_postal = rs.getInt("codigo_postal");
                String persona_contacto1 = rs.getString("persona_contacto1");
                int telefono1 = rs.getInt("telefono1");
                String persona_contacto2 = rs.getString("persona_contacto2");
                int telefono2 = rs.getInt("telefono2");
                String persona_contacto3 = rs.getString("persona_contacto3");
                int telefono3 = rs.getInt("telefono3");
                String email = rs.getString("email");
                String banco = rs.getString("banco");
                String observaciones = rs.getString("observaciones");

                Cliente cliente = new Cliente(id, nombre, apellidos, razon_social, cif_dni, direccion, provincia, 
                        codigo_postal, persona_contacto1, telefono1, persona_contacto2, 
                        telefono2, persona_contacto3, telefono3, email, banco, observaciones);

                                              
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }
    
    public boolean deleteCliente(int idCliente) {
        String query = "DELETE FROM Cliente WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
             
            ps.setInt(1, idCliente);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                showAlert("Éxito", "Cliente eliminado correctamente.", Alert.AlertType.INFORMATION);
                return true;
            } else {
                showAlert("Error", "No se pudo eliminar el cliente.", Alert.AlertType.ERROR);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error en la base de datos: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }


    // Método para mostrar una alerta en JavaFX
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
