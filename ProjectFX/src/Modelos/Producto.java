package Modelos;

import javafx.beans.property.*;

public class Producto {
    private final IntegerProperty idProducto = new SimpleIntegerProperty();
    private final StringProperty codigoProducto = new SimpleStringProperty();
    private final StringProperty producto = new SimpleStringProperty();
    private final DoubleProperty importe = new SimpleDoubleProperty();

    // Constructor vacío
    public Producto() {}

    // Constructor con parámetros
    public Producto(int idProducto, String codigoProducto, String producto, double importe) {
        this.idProducto.set(idProducto);
        this.codigoProducto.set(codigoProducto);
        this.producto.set(producto);
        this.importe.set(importe);
    }

    // Getters y setters
    public int getIdProducto() { return idProducto.get(); }
    public void setIdProducto(int idProducto) { this.idProducto.set(idProducto); }
    public IntegerProperty idProductoProperty() { return idProducto; }

    public String getCodigoProducto() { return codigoProducto.get(); }
    public void setCodigoProducto(String codigoProducto) { this.codigoProducto.set(codigoProducto); }
    public StringProperty codigoProductoProperty() { return codigoProducto; }

    public String getProducto() { return producto.get(); }
    public void setProducto(String producto) { this.producto.set(producto); }
    public StringProperty productoProperty() { return producto; }

    public double getImporte() { return importe.get(); }
    public void setImporte(double importe) { this.importe.set(importe); }
    public DoubleProperty importeProperty() { return importe; }

    @Override
    public String toString() {
        return "Producto{" +
               "idProducto=" + idProducto.get() +
               ", codigoProducto='" + codigoProducto.get() + '\'' +
               ", producto='" + producto.get() + '\'' +
               ", importe=" + importe.get() +
               '}';
    }
}
