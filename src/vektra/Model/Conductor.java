/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Model;

/**
 *
 * @author santi
 */
public class Conductor extends Persona {
    private String id;
    private String nombre;
    private String apellido;
    private String cedula;
    private String telefono;
    private String licencia;
    private String rutaAsignada;
    
    public Conductor(){
    }

    public Conductor(String cedula, String licencia, String id, String nombre, String apellido, String telefono, String rutaAsignada) {
        super(id, nombre);
        this.apellido = apellido;
        this.cedula = cedula;
        this.telefono = telefono;
        this.licencia = licencia;
        this.rutaAsignada = rutaAsignada;
        
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setRutaAsignada(String rutaAsignada) {
        this.rutaAsignada = rutaAsignada;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getRutaAsignada() {
        return rutaAsignada;
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
}
