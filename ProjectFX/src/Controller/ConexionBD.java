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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Modelos.Cliente;
import Modelos.Pedido;
import Modelos.Presupuesto;
import Modelos.Producto;
import Modelos.Servicio;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/programa";
    private static final String USER = "root";
    private static final String PASSWORD = "asd123";

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
                ps.setString(6, iCodigoPostal);
                ps.setString(7, iProvincia);
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
                    iDireccion + ", " + iCodigoPostal + ", " + iProvincia + ", " +
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
    private TextField txtNombre, txtApellidos, txtRazonSocial, txtCIFNIF, txtDireccion, txtCodigoPostal, 
    txtProvincia, txtPersonaContacto1, txtTelefono1, txtPersonaContacto2, 
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
            String codigoPostal =txtCodigoPostal.getText();
            String provincia = txtProvincia.getText();
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

    
    public List<Cliente> getAllClientes() {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM clientes")) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellidos(rs.getString("apellidos"));
                cliente.setRazonSocial(rs.getString("razon_social"));
                cliente.setCifDni(rs.getString("cif_nif"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setCodigoPostal(rs.getString("cp"));
                cliente.setPoblacion(rs.getString("poblacion"));
                cliente.setProvincia(rs.getString("provincia"));
                cliente.setPersonaContacto1(rs.getString("persona_contacto_1"));
                cliente.setTelefono1(rs.getString("telefono1"));
                cliente.setPersonaContacto2(rs.getString("persona_contacto_2"));
                cliente.setTelefono2(rs.getString("telefono2"));
                cliente.setPersonaContacto3(rs.getString("persona_contacto_3"));
                cliente.setTelefono3(rs.getString("telefono3"));
                cliente.setEmail(rs.getString("email"));
                cliente.setObservaciones(rs.getString("observaciones"));
                cliente.setCuentaBancaria(rs.getString("cuenta_bancaria"));
                cliente.setMetodoPago(rs.getString("metodo_pago"));
                cliente.setPuedePedir(rs.getBoolean("puede_pedir"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }


    
    public boolean deleteCliente(int idCliente) {
        String query = "DELETE FROM clientes WHERE id_cliente = ?";
        
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
    
    public List<Presupuesto> getAllPresupuestos() {
        List<Presupuesto> presupuestos = new ArrayList<>();

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM presupuestos")) {

            while (rs.next()) {
                Presupuesto presupuesto = new Presupuesto();
                presupuesto.setnPresupuesto(rs.getString("n_presupuesto"));
                presupuesto.setFecha(rs.getString("fecha"));
                presupuesto.setIdCliente(rs.getInt("id_cliente"));
                presupuesto.setRazonSocial(rs.getString("razon_social"));
                presupuesto.setIva(rs.getDouble("iva"));
                presupuesto.setCompletado(rs.getBoolean("completado"));
                presupuestos.add(presupuesto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return presupuestos;
    }

    public boolean deletePresupuesto(String nPresupuesto) {
        String query = "DELETE FROM presupuestos WHERE n_presupuesto = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nPresupuesto);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addPresupuesto(Presupuesto presupuesto) {
        String query = "INSERT INTO presupuestos (n_presupuesto, fecha, id_cliente, razon_social, iva, completado) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, presupuesto.getnPresupuesto());
            pstmt.setString(2, presupuesto.getFecha());
            pstmt.setInt(3, presupuesto.getIdCliente());
            pstmt.setString(4, presupuesto.getRazonSocial());
            pstmt.setDouble(5, presupuesto.getIva());
            pstmt.setBoolean(6, presupuesto.isCompletado());
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Pedido> getAllPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM pedidos")) {

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setNPedido(rs.getString("n_pedido"));       // PK
                pedido.setFecha(rs.getString("fecha"));             // Puede ser String o convertir a java.sql.Date según convenga
                pedido.setIdCliente(rs.getInt("id_cliente"));
                pedido.setRazonSocial(rs.getString("razon_social"));
                pedido.setIva(rs.getDouble("iva"));
                pedido.setnPresupuesto(rs.getString("n_presupuesto"));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    public boolean deletePedido(String nPedido) {
        String query = "DELETE FROM pedidos WHERE n_pedido = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
             
            ps.setString(1, nPedido);
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                showAlert("Éxito", "Pedido eliminado correctamente.", Alert.AlertType.INFORMATION);
                return true;
            } else {
                showAlert("Error", "No se pudo eliminar el pedido.", Alert.AlertType.ERROR);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error en la base de datos: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }
    
    
    public List<Producto> getAllProductos() {
        List<Producto> productos = new ArrayList<>();

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM productos")) {

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setCodigoProducto(rs.getString("codigo_producto"));
                producto.setProducto(rs.getString("producto"));
                producto.setImporte(rs.getDouble("importe"));
                productos.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }
    
    
    public boolean deleteProducto(int idProducto) {
        String query = "DELETE FROM productos WHERE id_producto = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
             
            ps.setInt(1, idProducto);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                showAlert("Éxito", "Producto eliminado correctamente.", Alert.AlertType.INFORMATION);
                return true;
            } else {
                showAlert("Error", "No se pudo eliminar el producto.", Alert.AlertType.ERROR);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error en la base de datos: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }
    
    
    
    public List<Servicio> getAllServicios() {
        List<Servicio> servicios = new ArrayList<>();

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM servicios")) {

            while (rs.next()) {
                Servicio servicio = new Servicio();
                servicio.setIdServicio(rs.getInt("id_servicio"));
                servicio.setCodigoServicio(rs.getString("codigo_servicio"));
                servicio.setServicio(rs.getString("servicio"));
                servicio.setImporte(rs.getDouble("importe"));
                servicios.add(servicio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicios;
    }

    
    
    public boolean deleteServicio(int idServicio) {
        String query = "DELETE FROM servicios WHERE id_servicio = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
             
            ps.setInt(1, idServicio);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                showAlert("Éxito", "Servicio eliminado correctamente.", Alert.AlertType.INFORMATION);
                return true;
            } else {
                showAlert("Error", "No se pudo eliminar el servicio.", Alert.AlertType.ERROR);
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
