package vektra.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {

    private String id;
    private String codigo;
    private LocalDateTime fecha;
    private double precio;
    private String codigoQR;
    private Pasajero pasajero;
    private Estacion estacionOrigen;
    private Estacion estacionDestino;

    public Ticket() {
    }

    public Ticket(String id) {
        this.id = id;
        this.codigo = "TKT-" + id;
    }

    public Ticket(String id, LocalDateTime fecha, double precio, String codigoQR,
            Pasajero pasajero, Estacion estacionOrigen, Estacion estacionDestino) {
        this.id = id;
        this.codigo = "TKT-" + id;
        this.fecha = fecha;
        this.precio = precio;
        this.codigoQR = codigoQR;
        this.pasajero = pasajero;
        this.estacionOrigen = estacionOrigen;
        this.estacionDestino = estacionDestino;
    }

    // Getters que necesita EmailService
    public String getCodigo() {
        return codigo;
    }

    public String getRuta() {
        if (estacionOrigen != null && estacionDestino != null) {
            return estacionOrigen.getNombre() + " → " + estacionDestino.getNombre();
        }
        return "N/A";
    }

    public String getFecha() {
        return fecha != null
                ? fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                : "N/A";
    }

    // Getters y setters completos
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getFechaCompleta() {
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

    public void setEstacionOrigen(Estacion e) {
        this.estacionOrigen = e;
    }

    public Estacion getEstacionDestino() {
        return estacionDestino;
    }

    public void setEstacionDestino(Estacion e) {
        this.estacionDestino = e;
    }

    @Override
    public String toString() {
        return "Ticket{id='" + id + "', ruta='" + getRuta() + "', precio=" + precio + "}";
    }
}
