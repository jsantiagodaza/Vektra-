 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import vektra.Conexion.Conexion;
import vektra.Model.Estacion;
import vektra.Model.Ruta;


/**
 *
 * @author santi
 */
public class RutaDao {

    public List<Ruta> TodasLasRutas(){
    List<Ruta> rutas= new ArrayList<>();
    
    String sql = """
         SELECT
             r.id,
             e1.nombre AS origen,
             e2.nombre AS destino,
             r.color_linea,
             r.tiempo_total,
             r.transbordos
         FROM rutas r
         JOIN estaciones e1 ON r.origen_id = e1.id
         JOIN estaciones e2 ON r.destino_id = e2.id
                 """;
    try {
        Connection con = Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()){
            
            Ruta ruta = new Ruta();
            ruta.setId(rs.getString("id"));
            
            Estacion origen = new Estacion();
            origen.setNombre(rs.getString("origen"));
            
            Estacion destino = new Estacion();
            destino.setNombre(rs.getString("destino"));
            
            ruta.setOrigen(origen);
            ruta.setDestino(destino);
            ruta.setColorLinea(rs.getString("color_linea"));
            ruta.setTiempoTotal(rs.getDouble("tiempo_total"));
            ruta.setTransbordos(rs.getInt("transbordos"));
            rutas.add(ruta);
        }        
    }catch(Exception e){
        System.out.println("Error obtenido rutas :"+ e.getMessage());
    }
    
    return rutas;
    }
}
