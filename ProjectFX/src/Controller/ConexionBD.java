package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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

    // Método agregar un nuevo cliente
    public boolean newCliente(String iNombre, String iApellidos, String iRazonSocial, String iDireccion,
                              String iProvincia, int iCodigoPostal, int iPersonaContacto1, int iTelefono1, 
                              int iPersonaContacto2, int iTelefono2, int iPersonaContacto3, int iTelefono3, 
                              String iEmail, String iBanco, String iObservaciones, String iDNI_Cliente) {

        String addClienteQuery = "INSERT INTO Cliente (nombre, apellidos, razon_social, cif_dni, direccion, provincia, codigo_postal, " +
                                 "persona_contacto1, telefono1, persona_contacto2, telefono2, persona_contacto3, telefono3, email, banco, observaciones) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection connection = getConnection(); 
             PreparedStatement ps = connection.prepareStatement(addClienteQuery)) {
             
            ps.setString(1, iNombre);
            ps.setString(2, iApellidos);
            ps.setString(3, iRazonSocial);
            ps.setString(4, iDNI_Cliente);
            ps.setString(5, iDireccion);
            ps.setString(6, iProvincia);
            ps.setInt(7, iCodigoPostal);
            ps.setInt(8, iPersonaContacto1);
            ps.setInt(9, iTelefono1);
            ps.setInt(10, iPersonaContacto2);
            ps.setInt(11, iTelefono2);
            ps.setInt(12, iPersonaContacto3);
            ps.setInt(13, iTelefono3);
            ps.setString(14, iEmail);
            ps.setString(15, iBanco);
            ps.setString(16, iObservaciones);

            ps.executeUpdate();
            
            // JOptionPane para javaFX
            showAlert("Éxito", "¡Operación realizada con éxito!", AlertType.INFORMATION);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

    // Método para mostrar una alerta en JavaFX
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
