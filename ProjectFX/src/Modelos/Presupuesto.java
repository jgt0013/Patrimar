package Modelos;

public class Presupuesto {
    private String nPresupuesto;
    private String fecha;
    private int idCliente;
    private String razonSocial;
    private double iva;
    private boolean completado;

    // Constructor vacío (opcional para inicializaciones flexibles)
    public Presupuesto() {}

    // Constructor completo
    public Presupuesto(String nPresupuesto, String fecha, int idCliente, String razonSocial, double iva, boolean completado) {
        this.nPresupuesto = nPresupuesto;
        this.fecha = fecha;
        this.idCliente = idCliente;
        this.razonSocial = razonSocial;
        this.iva = iva;
        this.completado = completado;
    }

    // Getters y Setters
    public String getnPresupuesto() {
        return nPresupuesto;
    }

    public void setnPresupuesto(String nPresupuesto) {
        this.nPresupuesto = nPresupuesto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }
}
