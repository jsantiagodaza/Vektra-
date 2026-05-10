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
public class Ticket {
    
     private String id;
    private LocalDateTime fecha;
    private double precio;
    private String codigoQR;

    public Ticket(String id, LocalDateTime fecha, double precio, String codigoQR) {
        this.id = id;
        this.fecha = fecha;
        this.precio = precio;
        this.codigoQR = codigoQR;
    }
    
    
}
