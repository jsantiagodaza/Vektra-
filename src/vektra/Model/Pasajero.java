/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Model;


import java.time.LocalDateTime;

    public class Pasajero extends Persona {

        private String email;
        private String contraseña;
        private LocalDateTime fechaRegistro;

        public Pasajero() {}

        public Pasajero(String id, String nombre, String email, String contraseña, LocalDateTime fechaRegistro) {
        super(id, nombre);
        this.email = email;
        this.contraseña = contraseña;
        this.fechaRegistro = fechaRegistro;
    }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        // Alias para compatibilidad con EmailService
        public String getCorreo() { return email; }
        public void setCorreo(String correo) { this.email = correo; }

        public String getContraseña() { return contraseña; }
        public void setContraseña(String contraseña) { this.contraseña = contraseña; }

        public LocalDateTime getFechaRegistro() { return fechaRegistro; }
        public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

        @Override
        public String toString() {
            return getNombre() + " <" + email + ">";
        }

/**
 *
 * @author santi
 */


   
    

}
