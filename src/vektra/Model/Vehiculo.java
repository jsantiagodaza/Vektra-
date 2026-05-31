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

    private String id;
    private int capacidad;
    private int anioFabricacion;

    
    public Vehiculo() {
    }

    
    public Vehiculo(String id, int capacidad, int anioFabricacion) {
        this.id = id;
        this.capacidad = capacidad;
        this.anioFabricacion = anioFabricacion;
    }

    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getAnioFabricacion() {
        return anioFabricacion;
    }

    public void setAnioFabricacion(int anioFabricacion) {
        this.anioFabricacion = anioFabricacion;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "id='" + id + '\'' +
                ", capacidad=" + capacidad +
                ", anioFabricacion=" + anioFabricacion +
                '}';
    }
}
