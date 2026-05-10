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
    private Pasajero pasajero;
    private Estacion estacionOrigen;
    private Estacion estacionDestino;
    
    public Ticket(){
    }
    
    public Ticket(String id, LocalDateTime fecha, double precio, String codigoQR, Pasajero pasajero, Estacion estacionOrigen, Estacion estacionDestino) {
        this.id = id;
        this.fecha = fecha;
        this.precio = precio;
        this.codigoQR = codigoQR;
        this.pasajero = pasajero;
        this.estacionOrigen = estacionOrigen;
        this.estacionDestino = estacionDestino;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public Estacion getEstacionOrigen() {
        return estacionOrigen;
    }

    public Estacion getEstacionDestino() {
        return estacionDestino;
    }
    
    
}
