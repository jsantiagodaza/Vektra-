package vektra.Model;

import vektra.Model.Persona;
import vektra.Model.Vehiculo;

public class Conductor extends Persona {

    private String apellido;
    private String telefono;
    private String cedula;
    private String licencia;
    private String estado;
    private String rutaAsignada;
    private Vehiculo vehiculo;

    public Conductor() {
        super();
    }

    public Conductor(String id, String nombre, String cedula, String licencia, String estado) {
        super(id, nombre); 
        this.cedula = cedula;
        this.licencia = licencia;
        this.estado = estado;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRutaAsignada() {
        return rutaAsignada;
    }

    public void setRutaAsignada(String rutaAsignada) {
        this.rutaAsignada = rutaAsignada;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

}