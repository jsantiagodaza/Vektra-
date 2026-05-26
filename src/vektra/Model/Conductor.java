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
    private String cedula;
    private String licencia;
    private String estado; 

    public Conductor(String cedula, String licencia, String estado, String id, String nombre) {
        super(id, nombre);
        this.cedula = cedula;
        this.licencia = licencia;
        this.estado = estado;
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

  

    
}
