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
    private String licencia;

    public Conductor(String licencia, String id, String nombre) {
        super(id, nombre);
        this.licencia = licencia;
    }
    
    
}
