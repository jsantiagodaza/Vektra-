/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author santi
 */
public class Estacion {

    private String id;
    private String nombre;
    private List<String> lineas; //Colores de las Líneas que pasan

    public Estacion() {
    }

    public Estacion(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.lineas = new ArrayList<>();
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getLineas() {
        return lineas;
    }

    public void setLineas(List<String> lineas) {
        this.lineas = lineas;
    }

    @Override
    public String toString() {
        return "Estacion{"
                + "id='" + id + '\''
                + ", nombre='" + nombre + '\''
                + ", color linea='" + lineas + '\''
                + '}';
    }

}
