package Modelos;

import javafx.beans.property.*;

public class Cliente {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty apellidos = new SimpleStringProperty();
    private final StringProperty razonSocial = new SimpleStringProperty();
    private final StringProperty cifDni = new SimpleStringProperty();
    private final StringProperty direccion = new SimpleStringProperty();
    private final StringProperty provincia = new SimpleStringProperty();
    private final StringProperty codigoPostal = new SimpleStringProperty();
    private final StringProperty poblacion = new SimpleStringProperty();
    private final StringProperty personaContacto1 = new SimpleStringProperty();
    private final StringProperty telefono1 = new SimpleStringProperty();
    private final StringProperty personaContacto2 = new SimpleStringProperty();
    private final StringProperty telefono2 = new SimpleStringProperty();
    private final StringProperty personaContacto3 = new SimpleStringProperty();
    private final StringProperty telefono3 = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty banco = new SimpleStringProperty();
    private final StringProperty observaciones = new SimpleStringProperty();
    private final StringProperty cuentaBancaria = new SimpleStringProperty();
    private final StringProperty metodoPago = new SimpleStringProperty();
    private final BooleanProperty puedePedir = new SimpleBooleanProperty();

    // Constructor vacío
    public Cliente() {}

    // Constructor con parámetros
    public Cliente(int id, String nombre, String apellidos, String razonSocial, String cifDni, String direccion,
                   String provincia, String codigoPostal, String poblacion, String personaContacto1, String telefono1,
                   String personaContacto2, String telefono2, String personaContacto3, String telefono3,
                   String email, String banco, String observaciones, String cuentaBancaria,
                   String metodoPago, boolean puedePedir) {
        this.id.set(id);
        this.nombre.set(nombre);
        this.apellidos.set(apellidos);
        this.razonSocial.set(razonSocial);
        this.cifDni.set(cifDni);
        this.direccion.set(direccion);
        this.provincia.set(provincia);
        this.codigoPostal.set(codigoPostal);
        this.poblacion.set(poblacion);
        this.personaContacto1.set(personaContacto1);
        this.telefono1.set(telefono1);
        this.personaContacto2.set(personaContacto2);
        this.telefono2.set(telefono2);
        this.personaContacto3.set(personaContacto3);
        this.telefono3.set(telefono3);
        this.email.set(email);
        this.banco.set(banco);
        this.observaciones.set(observaciones);
        this.cuentaBancaria.set(cuentaBancaria);
        this.metodoPago.set(metodoPago);
        this.puedePedir.set(puedePedir);
    }

    // Getters y Setters
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public StringProperty nombreProperty() { return nombre; }

    public String getApellidos() { return apellidos.get(); }
    public void setApellidos(String apellidos) { this.apellidos.set(apellidos); }
    public StringProperty apellidosProperty() { return apellidos; }

    public String getRazonSocial() { return razonSocial.get(); }
    public void setRazonSocial(String razonSocial) { this.razonSocial.set(razonSocial); }
    public StringProperty razonSocialProperty() { return razonSocial; }

    public String getCifDni() { return cifDni.get(); }
    public void setCifDni(String cifDni) { this.cifDni.set(cifDni); }
    public StringProperty cifDniProperty() { return cifDni; }

    public String getDireccion() { return direccion.get(); }
    public void setDireccion(String direccion) { this.direccion.set(direccion); }
    public StringProperty direccionProperty() { return direccion; }

    public String getProvincia() { return provincia.get(); }
    public void setProvincia(String provincia) { this.provincia.set(provincia); }
    public StringProperty provinciaProperty() { return provincia; }

    public String getCodigoPostal() { return codigoPostal.get(); }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal.set(codigoPostal); }
    public StringProperty codigoPostalProperty() { return codigoPostal; }

    public String getPoblacion() { return poblacion.get(); }
    public void setPoblacion(String poblacion) { this.poblacion.set(poblacion); }
    public StringProperty poblacionProperty() { return poblacion; }

    public String getPersonaContacto1() { return personaContacto1.get(); }
    public void setPersonaContacto1(String personaContacto1) { this.personaContacto1.set(personaContacto1); }
    public StringProperty personaContacto1Property() { return personaContacto1; }

    public String getTelefono1() { return telefono1.get(); }
    public void setTelefono1(String telefono1) { this.telefono1.set(telefono1); }
    public StringProperty telefono1Property() { return telefono1; }

    public String getPersonaContacto2() { return personaContacto2.get(); }
    public void setPersonaContacto2(String personaContacto2) { this.personaContacto2.set(personaContacto2); }
    public StringProperty personaContacto2Property() { return personaContacto2; }

    public String getTelefono2() { return telefono2.get(); }
    public void setTelefono2(String telefono2) { this.telefono2.set(telefono2); }
    public StringProperty telefono2Property() { return telefono2; }

    public String getPersonaContacto3() { return personaContacto3.get(); }
    public void setPersonaContacto3(String personaContacto3) { this.personaContacto3.set(personaContacto3); }
    public StringProperty personaContacto3Property() { return personaContacto3; }

    public String getTelefono3() { return telefono3.get(); }
    public void setTelefono3(String telefono3) { this.telefono3.set(telefono3); }
    public StringProperty telefono3Property() { return telefono3; }

    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }
    public StringProperty emailProperty() { return email; }

    public String getBanco() { return banco.get(); }
    public void setBanco(String banco) { this.banco.set(banco); }
    public StringProperty bancoProperty() { return banco; }

    public String getObservaciones() { return observaciones.get(); }
    public void setObservaciones(String observaciones) { this.observaciones.set(observaciones); }
    public StringProperty observacionesProperty() { return observaciones; }

    public String getCuentaBancaria() { return cuentaBancaria.get(); }
    public void setCuentaBancaria(String cuentaBancaria) { this.cuentaBancaria.set(cuentaBancaria); }
    public StringProperty cuentaBancariaProperty() { return cuentaBancaria; }

    public String getMetodoPago() { return metodoPago.get(); }
    public void setMetodoPago(String metodoPago) { this.metodoPago.set(metodoPago); }
    public StringProperty metodoPagoProperty() { return metodoPago; }

    public boolean isPuedePedir() { return puedePedir.get(); }
    public void setPuedePedir(boolean puedePedir) { this.puedePedir.set(puedePedir); }
    public BooleanProperty puedePedirProperty() { return puedePedir; }

    @Override
    public String toString() {
        return "Cliente [id=" + id.get() + ", nombre=" + nombre.get() + ", apellidos=" + apellidos.get() + 
               ", razonSocial=" + razonSocial.get() + ", cifDni=" + cifDni.get() + ", email=" + email.get() + "]";
    }
}
