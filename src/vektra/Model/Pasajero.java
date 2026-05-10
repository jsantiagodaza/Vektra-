/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Model;

import java.time.LocalDateTime;

/**
 *
 * @author santi
 */
public class Pasajero extends Persona {
    private String email;
    private String contraseña;
    private LocalDateTime fechaRegistro;

    public Pasajero() {
    }

    public Pasajero(String email, String contraseña, LocalDateTime fechaRegistro, String id, String nombre) {
        super(id, nombre);
        this.email = email;
        this.contraseña = contraseña;
        this.fechaRegistro = fechaRegistro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    
  

   
    
}
