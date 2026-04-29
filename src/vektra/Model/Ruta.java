/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Model;

import java.util.List;

/**
 *
 * @author santi
 */
public class Ruta {

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

}
