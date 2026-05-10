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

    public RutaDao() {
        rutas = new ArrayList<>();
    }

    public void GuardarRuta(Ruta ruta) {
        rutas.add(ruta);
    }

    public List<Ruta> ObtenerRutas() {
        return rutas;
    }

    public Ruta buscarRuta(String id){
        for (Ruta r : rutas){
            if (r.getId().equals(id)){
                return r;
            }
        }
        return null;
    }   
}
