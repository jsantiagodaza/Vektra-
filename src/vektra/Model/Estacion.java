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

    public Estacion(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.lineas = new ArrayList<>();
    }
    
}
