/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Dao;

import java.util.ArrayList;
import java.util.List;
import vektra.Model.Ruta;
import java.util.Map;
import java.util.HashMap;
import vektra.Model.Estacion;

/**
 *
 * @author santi
 */
public class RutaDao {

    private List<Ruta> rutas;
    private Map<String, List<String>> conexiones;

    public RutaDao() {
        rutas = new ArrayList<>();
        conexiones = new HashMap<>();
    }

    public void GuardarRuta(Ruta ruta) {
        rutas.add(ruta);
        construirConexion(ruta);
    }

    public List<Ruta> ObtenerRutas() {
        return rutas;
    }

    private void construirConexion(Ruta ruta) {
        List<String> estaciones = new ArrayList<>();
        estaciones.add(ruta.getOrigen().getNombre());

        for (Estacion e : ruta.getEstacionesIntermedias()) {
            estaciones.add(e.getNombre());
        }
        estaciones.add(ruta.getDestino().getNombre());
        for (int i = 0; i < estaciones.size() - 1; i++) {
            String actual = estaciones.get(i);
            String siguiente = estaciones.get(i + 1);
            agregarConexion(actual, siguiente);
        }
    }

    private void agregarConexion(String origen, String destino) {

        conexiones.putIfAbsent(origen, new ArrayList<>());
        conexiones.putIfAbsent(destino, new ArrayList<>());
        conexiones.get(origen).add(destino);
        conexiones.get(destino).add(origen);
    }
}
