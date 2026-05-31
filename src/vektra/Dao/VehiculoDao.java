/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Dao;

import vektra.Model.Vehiculo;

import vektra.Conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDao {
    
    public void agregarVehiculo(Vehiculo v) {

    String sql = "INSERT INTO vehiculos (id, capacidad) VALUES (?, ?)";

    Connection con = null;

    try {
        con = Conexion.conectar(); 

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, v.getId());
        ps.setInt(2, v.getCapacidad());

        ps.executeUpdate();

        System.out.println("Vehículo agregado correctamente");

    } catch (Exception e) {
        System.out.println("Error al agregar vehículo: " + e.getMessage());

    } finally {
        Conexion.cerrarConexion(); 
    }
}
    
    public List<Vehiculo> obtenerTodos() {

    List<Vehiculo> lista = new ArrayList<>();
    String sql = "SELECT * FROM vehiculos";

    Connection con = null;

    try {
        con = Conexion.conectar(); 

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Vehiculo v = new Vehiculo();

            v.setId(rs.getString("id"));
            v.setCapacidad(rs.getInt("capacidad"));

            lista.add(v);
        }

    } catch (Exception e) {
        System.out.println("Error al obtener vehículos: " + e.getMessage());

    } finally {
        Conexion.cerrarConexion(); 
    }

    return lista;
}
    
    
}
