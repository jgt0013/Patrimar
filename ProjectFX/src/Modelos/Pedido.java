package Modelos;

import javafx.beans.property.*;

public class Pedido {

    private final StringProperty NPedido = new SimpleStringProperty();
    private final StringProperty fecha = new SimpleStringProperty();
    private final IntegerProperty idCliente = new SimpleIntegerProperty();
    private final StringProperty razonSocial = new SimpleStringProperty();
    private final DoubleProperty iva = new SimpleDoubleProperty();
    private final StringProperty nPresupuesto = new SimpleStringProperty();

    // Constructor vacío
    public Pedido() {}

    // Constructor con parámetros
    public Pedido(String numeroPedido, String fecha, int idCliente, String razonSocial, double iva, String numeroPresupuesto) {
        this.NPedido.set(numeroPedido);
        this.fecha.set(fecha);
        this.idCliente.set(idCliente);
        this.razonSocial.set(razonSocial);
        this.iva.set(iva);
        this.nPresupuesto.set(numeroPresupuesto);
    }

    // Getters y setters
    public String getNPedido() {
        return NPedido.get();
    }

    public void setNPedido(String numeroPedido) {
        this.NPedido.set(numeroPedido);
    }

    public StringProperty numeroPedidoProperty() {
        return NPedido;
    }

    public String getFecha() {
        return fecha.get();
    }

    public void setFecha(String fecha) {
        this.fecha.set(fecha);
    }

    public StringProperty fechaProperty() {
        return fecha;
    }

    public int getIdCliente() {
        return idCliente.get();
    }

    public void setIdCliente(int idCliente) {
        this.idCliente.set(idCliente);
    }

    public IntegerProperty idClienteProperty() {
        return idCliente;
    }

    public String getRazonSocial() {
        return razonSocial.get();
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial.set(razonSocial);
    }

    public StringProperty razonSocialProperty() {
        return razonSocial;
    }

    public double getIva() {
        return iva.get();
    }

    public void setIva(double d) {
        this.iva.set(d);
    }

    public DoubleProperty ivaProperty() {
        return iva;
    }

    public String getnPresupuesto() {
        return nPresupuesto.get();
    }

    public void setnPresupuesto(String nPresupuesto) {
        this.nPresupuesto.set(nPresupuesto);
    }

    public StringProperty nPresupuestoProperty() {
        return nPresupuesto;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "numeroPedido=" + NPedido.get() +
                ", fecha='" + fecha.get() + '\'' +
                ", idCliente=" + idCliente.get() +
                ", razonSocial='" + razonSocial.get() + '\'' +
                ", iva='" + iva.get() + '\'' +
                ", numeroPresupuesto=" + nPresupuesto.get() +
                '}';
    }

	
}
