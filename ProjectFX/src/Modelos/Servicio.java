package Modelos;

import javafx.beans.property.*;

public class Servicio {
    private final IntegerProperty idServicio = new SimpleIntegerProperty();
    private final StringProperty codigoServicio = new SimpleStringProperty();
    private final StringProperty servicio = new SimpleStringProperty();
    private final DoubleProperty importe = new SimpleDoubleProperty();

    // Constructor vacío
    public Servicio() {}

    // Constructor con parámetros
    public Servicio(int idServicio, String codigoServicio, String servicio, double importe) {
        this.idServicio.set(idServicio);
        this.codigoServicio.set(codigoServicio);
        this.servicio.set(servicio);
        this.importe.set(importe);
    }

    // Getters y setters
    public int getIdServicio() { return idServicio.get(); }
    public void setIdServicio(int idProducto) { this.idServicio.set(idProducto); }
    public IntegerProperty idServicioProperty() { return idServicio; }

    public String getCodigoServicio() { return codigoServicio.get(); }
    public void setCodigoServicio(String codigoProducto) { this.codigoServicio.set(codigoProducto); }
    public StringProperty codigoServicioProperty() { return codigoServicio; }

    public String getServicio() { return servicio.get(); }
    public void setServicio(String producto) { this.servicio.set(producto); }
    public StringProperty servicioProperty() { return servicio; }

    public double getImporte() { return importe.get(); }
    public void setImporte(double importe) { this.importe.set(importe); }
    public DoubleProperty importeProperty() { return importe; }

    @Override
    public String toString() {
        return "Producto{" +
               "idProducto=" + idServicio.get() +
               ", codigoProducto='" + codigoServicio.get() + '\'' +
               ", producto='" + servicio.get() + '\'' +
               ", importe=" + importe.get() +
               '}';
    }
}
