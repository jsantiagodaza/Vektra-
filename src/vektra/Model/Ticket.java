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
    private String Ruta;
    private String Codigo;
    private Pasajero pasajero;
    private Estacion estacionOrigen;
    private Estacion estacionDestino;

    public Ticket() {
    }

    public Ticket(String id) {
        this.id = id;
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

    public String getRuta() {
        return Ruta;
    }

    public String getCodigo() {
        return Codigo;
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public Estacion getEstacionOrigen() {
        return estacionOrigen;
    }

    public void setEstacionOrigen(Estacion estacionOrigen) {
        this.estacionOrigen = estacionOrigen;
    }

    public Estacion getEstacionDestino() {
        return estacionDestino;
    }

    public void setEstacionDestino(Estacion estacionDestino) {
        this.estacionDestino = estacionDestino;
    }

    @Override
    public String toString() {
        return "Ticket{"
                + "id='" + id + '\''
                + ", fecha=" + fecha
                + ", precio=" + precio
                + ", codigoQR='" + codigoQR + '\''
                + ", pasajero=" + pasajero
                + ", estacionOrigen=" + estacionOrigen
                + ", estacionDestino=" + estacionDestino
                + '}';
    }

}