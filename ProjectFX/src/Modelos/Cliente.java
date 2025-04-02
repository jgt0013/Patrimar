package Modelos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cliente {

    private SimpleStringProperty nombre, apellidos, razonSocial, cifDni, direccion, provincia, 
                                  personaContacto1, personaContacto2, personaContacto3, email, banco, observaciones;
    private SimpleIntegerProperty codigoPostal, telefono1, telefono2, telefono3;
    private int id;

    public Cliente(int id, String nombre, String apellidos, String razonSocial, String cifDni, String direccion, 
                   String provincia, int codigoPostal, String personaContacto1, int telefono1, 
                   String personaContacto2, int telefono2, String personaContacto3, int telefono3, 
                   String email, String banco, String observaciones) {

        this.id = id;
        this.nombre = new SimpleStringProperty(nombre);
        this.apellidos = new SimpleStringProperty(apellidos);
        this.razonSocial = new SimpleStringProperty(razonSocial);
        this.cifDni = new SimpleStringProperty(cifDni);
        this.direccion = new SimpleStringProperty(direccion);
        this.provincia = new SimpleStringProperty(provincia);
        this.codigoPostal = new SimpleIntegerProperty(codigoPostal);
        this.personaContacto1 = new SimpleStringProperty(personaContacto1);
        this.telefono1 = new SimpleIntegerProperty(telefono1);
        this.personaContacto2 = new SimpleStringProperty(personaContacto2);
        this.telefono2 = new SimpleIntegerProperty(telefono2);
        this.personaContacto3 = new SimpleStringProperty(personaContacto3);
        this.telefono3 = new SimpleIntegerProperty(telefono3);
        this.email = new SimpleStringProperty(email);
        this.banco = new SimpleStringProperty(banco);
        this.observaciones = new SimpleStringProperty(observaciones);
    }

    public int getId() { return id; }

    // Getters y Setters con JavaFX Properties
    public String getNombre() { return nombre.get(); }
    public void setNombre(String value) { nombre.set(value); }
    public SimpleStringProperty nombreProperty() { return nombre; }

    public String getApellidos() { return apellidos.get(); }
    public void setApellidos(String value) { apellidos.set(value); }
    public SimpleStringProperty apellidosProperty() { return apellidos; }

    public String getRazonSocial() { return razonSocial.get(); }
    public void setRazonSocial(String value) { razonSocial.set(value); }
    public SimpleStringProperty razonSocialProperty() { return razonSocial; }

    public String getCifDni() { return cifDni.get(); }
    public void setCifDni(String value) { cifDni.set(value); }
    public SimpleStringProperty cifDniProperty() { return cifDni; }

    public String getDireccion() { return direccion.get(); }
    public void setDireccion(String value) { direccion.set(value); }
    public SimpleStringProperty direccionProperty() { return direccion; }

    public String getProvincia() { return provincia.get(); }
    public void setProvincia(String value) { provincia.set(value); }
    public SimpleStringProperty provinciaProperty() { return provincia; }

    public int getCodigoPostal() { return codigoPostal.get(); }
    public void setCodigoPostal(int value) { codigoPostal.set(value); }
    public SimpleIntegerProperty codigoPostalProperty() { return codigoPostal; }

    public String getPersonaContacto1() { return personaContacto1.get(); }
    public void setPersonaContacto1(String value) { personaContacto1.set(value); }
    public SimpleStringProperty personaContacto1Property() { return personaContacto1; }

    public int getTelefono1() { return telefono1.get(); }
    public void setTelefono1(int value) { telefono1.set(value); }
    public SimpleIntegerProperty telefono1Property() { return telefono1; }

    public String getPersonaContacto2() { return personaContacto2.get(); }
    public void setPersonaContacto2(String value) { personaContacto2.set(value); }
    public SimpleStringProperty personaContacto2Property() { return personaContacto2; }

    public int getTelefono2() { return telefono2.get(); }
    public void setTelefono2(int value) { telefono2.set(value); }
    public SimpleIntegerProperty telefono2Property() { return telefono2; }

    public String getPersonaContacto3() { return personaContacto3.get(); }
    public void setPersonaContacto3(String value) { personaContacto3.set(value); }
    public SimpleStringProperty personaContacto3Property() { return personaContacto3; }

    public int getTelefono3() { return telefono3.get(); }
    public void setTelefono3(int value) { telefono3.set(value); }
    public SimpleIntegerProperty telefono3Property() { return telefono3; }

    public String getEmail() { return email.get(); }
    public void setEmail(String value) { email.set(value); }
    public SimpleStringProperty emailProperty() { return email; }

    public String getBanco() { return banco.get(); }
    public void setBanco(String value) { banco.set(value); }
    public SimpleStringProperty bancoProperty() { return banco; }

    public String getObservaciones() { return observaciones.get(); }
    public void setObservaciones(String value) { observaciones.set(value); }
    public SimpleStringProperty observacionesProperty() { return observaciones; }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + getNombre() + '\'' +
                ", apellidos='" + getApellidos() + '\'' +
                ", razonSocial='" + getRazonSocial() + '\'' +
                ", cifDni='" + getCifDni() + '\'' +
                ", direccion='" + getDireccion() + '\'' +
                ", provincia='" + getProvincia() + '\'' +
                ", codigoPostal=" + getCodigoPostal() +
                ", personaContacto1='" + getPersonaContacto1() + '\'' +
                ", telefono1=" + getTelefono1() +
                ", personaContacto2='" + getPersonaContacto2() + '\'' +
                ", telefono2=" + getTelefono2() +
                ", personaContacto3='" + getPersonaContacto3() + '\'' +
                ", telefono3=" + getTelefono3() +
                ", email='" + getEmail() + '\'' +
                ", banco='" + getBanco() + '\'' +
                ", observaciones='" + getObservaciones() + '\'' +
                '}';
    }
}
