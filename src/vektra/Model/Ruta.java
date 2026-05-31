/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Model;

import java.util.ArrayList;
import java.util.List;

public class Ruta {

    //static String getOrigen;
    private String id;
    private Estacion origen;
    private Estacion destino;
    private List<Estacion> estacionesIntermedias;
    private String colorLinea;
    private double tiempoTotal;
    private double kilometros;

    private int transbordos;

    public Ruta() {
        estacionesIntermedias = new ArrayList<>();
    }

    public Ruta(String id, Estacion origen, Estacion destino, List<Estacion> estacionesIntermedias,
            String colorLinea, double tiempoTotal, double kilometros, int transbordos) {

        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.colorLinea = colorLinea;
        this.tiempoTotal = tiempoTotal;
        this.kilometros = kilometros;
        this.transbordos = transbordos;

        this.estacionesIntermedias = new ArrayList<>();
    }

    public void agregarEstacionIntermedia(Estacion estacion) {
        estacionesIntermedias.add(estacion);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Estacion getOrigen() {
        return origen;
    }

    public void setOrigen(Estacion origen) {
        this.origen = origen;
    }

    public Estacion getDestino() {
        return destino;
    }

    public void setDestino(Estacion destino) {
        this.destino = destino;
    }

    public List<Estacion> getEstacionesIntermedias() {
        return estacionesIntermedias;
    }

    public void setEstacionesIntermedias(List<Estacion> estacionesIntermedias) {
        this.estacionesIntermedias = estacionesIntermedias;
    }

    public String getColorLinea() {
        return colorLinea;
    }

    public void setColorLinea(String colorLinea) {
        this.colorLinea = colorLinea;
    }

    public double getTiempoTotal() {
        return tiempoTotal;
    }

    public void setTiempoTotal(double tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }

    public double getKilometros() {
        return kilometros;
    }

    public void setKilometros(double kilometros) {
        this.kilometros = kilometros;
    }

    public int getTransbordos() {
        return transbordos;
    }

    public void setTransbordos(int transbordos) {
        this.transbordos = transbordos;
    }

    public String formatoUI() {

        return origen.getNombre()
                + " → "
                + destino.getNombre()
                + " | "
                + kilometros + " km"
                + " | "
                + tiempoTotal + " min"
                + " | Línea "
                + colorLinea
                + " | Transbordos: "
                + transbordos;
    }

}
