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
/**
 *
 * @author santi
 */
public class RutaDao {
    private List<Ruta> rutas;
    private Map<String, List<String>> conexiones;
    
    public RutaDao(){
        rutas = new ArrayList<>();
        conexiones = new HashMap<>();
    }
    public void GuardarRuta(Ruta ruta){
        rutas.add(ruta);
        construirConexion(ruta);
    }
    
    public List<Ruta>ObtenerRutas(){
        return rutas;
    }
            
    
    private void construirConexion(Ruta ruta){
        
    }
    
}
