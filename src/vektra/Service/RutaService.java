/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Service;

import java.util.List;
import vektra.Dao.RutaDao;
import vektra.Model.Estacion;
import vektra.Model.Ruta;

import java.util.ArrayList;

/**
 *
 * @author santi
 */
public class RutaService {


    private RutaDao rutaDao;

    public RutaService() {
        rutaDao = new RutaDao();
    }

    public void crearRuta(String id, String origen, String destino, List<String> intermedias, String colorLinea, double tiempoTotal, int transbordos) {

        Estacion estacionOrigen = new Estacion("E1", origen);
        Estacion estacionDestino = new Estacion("E2", destino);
        List<Estacion> estacionesIntermedias = new ArrayList<>();

        int contador = 3;
        for (String nombre : intermedias) {
            estacionesIntermedias.add(new Estacion("E" + contador, nombre));
            contador++;
        }
        Ruta ruta = new Ruta(id, estacionOrigen, estacionDestino, estacionesIntermedias, colorLinea, tiempoTotal, transbordos);
        rutaDao.GuardarRuta(ruta);
    }


}
