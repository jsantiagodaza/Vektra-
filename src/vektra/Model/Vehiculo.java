/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Model;

/**
 *
 * @author santi
 */
public class Vehiculo {

    private int id;
    private String codigo;
    private String tipo; 
    private int capacidad;
    private boolean activo;

    
    public Vehiculo() {
    }

    
    public Vehiculo(String codigo, String tipo, int capacidad, boolean activo) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.activo = activo;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    @Override
    public String toString() {
        return "Vehiculo{"
                + "id=" + id
                + ", codigo='" + codigo + '\''
                + ", tipo='" + tipo + '\''
                + ", capacidad=" + capacidad
                + ", activo=" + activo
                +        '}';
}
    
}

   



