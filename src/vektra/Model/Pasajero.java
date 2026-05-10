/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Model;

/**
 *
 * @author santi
 */
public class Pasajero extends Persona {
    private String email;

    public Pasajero(String email, String id, String nombre) {
        super(id, nombre);
        this.email = email;
    }

   
    
}
