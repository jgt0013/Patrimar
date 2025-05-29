package Modelos;

import javafx.beans.property.*;

public class Gastos {

    private final IntegerProperty idGasto = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty tipo = new SimpleStringProperty();

    // Constructor vacío
    public Gastos() {}

    // Constructor con parámetros
    public Gastos(int idGasto, String nombre, String tipo) {
        this.idGasto.set(idGasto);
        this.nombre.set(nombre);
        this.tipo.set(tipo);
    }

    // Getters y setters
    public int getIdGasto() {
        return idGasto.get();
    }

    public void setIdGasto(int idGasto) {
        this.idGasto.set(idGasto);
    }

    public IntegerProperty idGastoProperty() {
        return idGasto;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getTipo() {
        return tipo.get();
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

    public StringProperty tipoProperty() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Gastos{" +
                "idGasto=" + idGasto.get() +
                ", nombre='" + nombre.get() + '\'' +
                ", tipo='" + tipo.get() + '\'' +
                '}';
    }
}
