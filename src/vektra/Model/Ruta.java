/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Model;

import java.util.List;
import java.util.ArrayList;
/**
/**
 *
 * @author santi
 */
public class Ruta {

    //static String getOrigen;

    private String id;
    private Estacion origen;
    private Estacion destino;
    private List<Estacion> estacionesIntermedias;
    private String colorLinea;
    private double tiempoTotal;
    private int transbordos;

    public Ruta(String id, Estacion origen, Estacion destino, List<Estacion> estacionesIntermedias,
            String colorLinea, double tiempoTotal, int transbordos) {
        
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.estacionesIntermedias = estacionesIntermedias;
        this.colorLinea = colorLinea;
        this.tiempoTotal = tiempoTotal;
        this.transbordos = transbordos;
    }
    public void agregarEstacionIntermedia(Estacion estacion){
        if(estacionesIntermedias==null){
            estacionesIntermedias= new ArrayList<>(); 
        }
        estacionesIntermedias.add(estacion);
    }

    public String getId() {
        return id;
    }

    public Estacion getOrigen() {
        return origen;
    }

    public Estacion getDestino() {
        return destino;
    }

    public List<Estacion> getEstacionesIntermedias() {
        return estacionesIntermedias;
    }

    public String getColorLinea() {
        return colorLinea;
    }

    public double getTiempoTotal() {
        return tiempoTotal;
    }
    public void setTiempoTotal(double tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }

    public int getTransbordos() {
        return transbordos;
    }
    public void setTransbordos(int transbordos) {
        this.transbordos = transbordos;
    }
    
    @Override 
    public String toString(){
        return "Ruta{"+
                "id=" + id +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", intermedias=" + estacionesIntermedias +
                ", color='" + colorLinea + '\'' +
                ", tiempoViaje=" + tiempoTotal +
                ", trasbordos=" + transbordos +
                '}';
    }

}
