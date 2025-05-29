package Modelos;

import javafx.beans.property.*;

import java.sql.Timestamp;

public class Usuario {

    private int id;
    private String nombre;
    private String usuario;
    private String Password;

    // Permisos
    private boolean presupuestoNuevo, presupuestoModificar, presupuestoListar;

    private boolean pedidosNuevo, pedidosModificar, pedidosListar;

    private boolean albaranesNuevo, albaranesModificar, albaranesListar;

    private boolean facturasNuevo, facturasModificar, facturasListar;

    private boolean clientesNuevo, clientesModificar, clientesListar;

    private boolean proveedoresNuevo, proveedoresModificar, proveedoresListar;

    private boolean diarioEmitidas, diarioRecibidas, diarioGastos;

    private boolean resumenEmitidas, resumenRecibidas, resumenGastos;

    private boolean otrosListadoGastos, otrosServicios, otrosProductos, otrosUsuarios;

    private Timestamp fechaCreacion;
    private boolean activo;

    public Usuario() {}

    // Getters y Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPassword() { return Password; }
    public void setPassword(String Password) { this.Password = Password; }

    public boolean isPresupuestoNuevo() { return presupuestoNuevo; }
    public void setPresupuestoNuevo(boolean presupuestoNuevo) { this.presupuestoNuevo = presupuestoNuevo; }

    public boolean isPresupuestoModificar() { return presupuestoModificar; }
    public void setPresupuestoModificar(boolean presupuestoModificar) { this.presupuestoModificar = presupuestoModificar; }

    public boolean isPresupuestoListar() { return presupuestoListar; }
    public void setPresupuestoListar(boolean presupuestoListar) { this.presupuestoListar = presupuestoListar; }

    public boolean isPedidosNuevo() { return pedidosNuevo; }
    public void setPedidosNuevo(boolean pedidosNuevo) { this.pedidosNuevo = pedidosNuevo; }

    public boolean isPedidosModificar() { return pedidosModificar; }
    public void setPedidosModificar(boolean pedidosModificar) { this.pedidosModificar = pedidosModificar; }

    public boolean isPedidosListar() { return pedidosListar; }
    public void setPedidosListar(boolean pedidosListar) { this.pedidosListar = pedidosListar; }

    public boolean isAlbaranesNuevo() { return albaranesNuevo; }
    public void setAlbaranesNuevo(boolean albaranesNuevo) { this.albaranesNuevo = albaranesNuevo; }

    public boolean isAlbaranesModificar() { return albaranesModificar; }
    public void setAlbaranesModificar(boolean albaranesModificar) { this.albaranesModificar = albaranesModificar; }

    public boolean isAlbaranesListar() { return albaranesListar; }
    public void setAlbaranesListar(boolean albaranesListar) { this.albaranesListar = albaranesListar; }

    public boolean isFacturasNuevo() { return facturasNuevo; }
    public void setFacturasNuevo(boolean facturasNuevo) { this.facturasNuevo = facturasNuevo; }

    public boolean isFacturasModificar() { return facturasModificar; }
    public void setFacturasModificar(boolean facturasModificar) { this.facturasModificar = facturasModificar; }

    public boolean isFacturasListar() { return facturasListar; }
    public void setFacturasListar(boolean facturasListar) { this.facturasListar = facturasListar; }

    public boolean isClientesNuevo() { return clientesNuevo; }
    public void setClientesNuevo(boolean clientesNuevo) { this.clientesNuevo = clientesNuevo; }

    public boolean isClientesModificar() { return clientesModificar; }
    public void setClientesModificar(boolean clientesModificar) { this.clientesModificar = clientesModificar; }

    public boolean isClientesListar() { return clientesListar; }
    public void setClientesListar(boolean clientesListar) { this.clientesListar = clientesListar; }

    public boolean isProveedoresNuevo() { return proveedoresNuevo; }
    public void setProveedoresNuevo(boolean proveedoresNuevo) { this.proveedoresNuevo = proveedoresNuevo; }

    public boolean isProveedoresModificar() { return proveedoresModificar; }
    public void setProveedoresModificar(boolean proveedoresModificar) { this.proveedoresModificar = proveedoresModificar; }

    public boolean isProveedoresListar() { return proveedoresListar; }
    public void setProveedoresListar(boolean proveedoresListar) { this.proveedoresListar = proveedoresListar; }

    public boolean isDiarioEmitidas() { return diarioEmitidas; }
    public void setDiarioEmitidas(boolean diarioEmitidas) { this.diarioEmitidas = diarioEmitidas; }

    public boolean isDiarioRecibidas() { return diarioRecibidas; }
    public void setDiarioRecibidas(boolean diarioRecibidas) { this.diarioRecibidas = diarioRecibidas; }

    public boolean isDiarioGastos() { return diarioGastos; }
    public void setDiarioGastos(boolean diarioGastos) { this.diarioGastos = diarioGastos; }

    public boolean isResumenEmitidas() { return resumenEmitidas; }
    public void setResumenEmitidas(boolean resumenEmitidas) { this.resumenEmitidas = resumenEmitidas; }

    public boolean isResumenRecibidas() { return resumenRecibidas; }
    public void setResumenRecibidas(boolean resumenRecibidas) { this.resumenRecibidas = resumenRecibidas; }

    public boolean isResumenGastos() { return resumenGastos; }
    public void setResumenGastos(boolean resumenGastos) { this.resumenGastos = resumenGastos; }

    public boolean isOtrosListadoGastos() { return otrosListadoGastos; }
    public void setOtrosListadoGastos(boolean otrosListadoGastos) { this.otrosListadoGastos = otrosListadoGastos; }

    public boolean isOtrosServicios() { return otrosServicios; }
    public void setOtrosServicios(boolean otrosServicios) { this.otrosServicios = otrosServicios; }

    public boolean isOtrosProductos() { return otrosProductos; }
    public void setOtrosProductos(boolean otrosProductos) { this.otrosProductos = otrosProductos; }

    public boolean isOtrosUsuarios() { return otrosUsuarios; }
    public void setOtrosUsuarios(boolean otrosUsuarios) { this.otrosUsuarios = otrosUsuarios; }

    public Timestamp getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Timestamp fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

}
