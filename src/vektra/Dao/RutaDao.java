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

    public List<Ruta> obtenerTodasLasRutas() {
        List<Ruta> rutas = new ArrayList<>();
        
        // Consulta SQL ajustada a las columnas reales del modelo de base de datos
        String sql = """
             SELECT
                 r.id,
                 r.tiempo_minutos,
                 r.estacion_origen_id,
                 r.estacion_destino_id,
                 e1.nombre AS origen_nombre,
                 e1.linea AS origen_linea,
                 e2.nombre AS destino_nombre,
                 e2.linea AS destino_linea
             FROM rutas r
             JOIN estaciones e1 ON r.estacion_origen_id = e1.id
             JOIN estaciones e2 ON r.estacion_destino_id = e2.id
             """;
             
        try {
            Connection con = Conexion.conectar();
            if (con == null) {
                System.out.println("Error: conexión nula");
                return rutas;
            }
             
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Ruta ruta = new Ruta();
                ruta.setId(String.valueOf(rs.getInt("id")));
                
                Estacion origen = new Estacion();
                origen.setId(String.valueOf(rs.getInt("estacion_origen_id")));
                origen.setNombre(rs.getString("origen_nombre"));
                
                List<String> lineasOrigen = new ArrayList<>();
                lineasOrigen.add(rs.getString("origen_linea"));
                origen.setLineas(lineasOrigen);
                
                Estacion destino = new Estacion();
                destino.setId(String.valueOf(rs.getInt("estacion_destino_id")));
                destino.setNombre(rs.getString("destino_nombre"));
                
                List<String> lineasDestino = new ArrayList<>();
                lineasDestino.add(rs.getString("destino_linea"));
                destino.setLineas(lineasDestino);
                
                ruta.setOrigen(origen);
                ruta.setDestino(destino);
                
                // Color de la línea de la ruta
                ruta.setColorLinea(rs.getString("origen_linea"));
                
                double tiempo = rs.getDouble("tiempo_minutos");
                ruta.setTiempoTotal(tiempo);
                
                // Kilómetros calculados de forma dinámica si no existen en la BD
                ruta.setKilometros(Math.round((tiempo * 1.2) * 10.0) / 10.0);
                ruta.setTransbordos(0); // Por defecto
                
                rutas.add(ruta);
            }        
        } catch (Exception e) {
            System.out.println("Error obteniendo rutas: " + e.getMessage());
        }
        
        return rutas;
    }

    public List<Ruta> TodasLasRutas() {
        return obtenerTodasLasRutas();
    }
}
