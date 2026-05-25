/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Dao;

import vektra.Model.Conductor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import vektra.Conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConductorDao {
     Connection con = null;
      PreparedStatement ps = null;

    public boolean agregarConductor(Conductor c) {
        
        boolean registrado = false;
        
       
        try {
            con = Conexion.conectar();

            String sql = """
                         INSERT INTO conductores (id, nombre, licencia)
                         VALUES (?, ?, ?)
                         """;

            ps = con.prepareStatement(sql);

        
            ps.setString(1, c.getId());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getLicencia());

            int filas = ps.executeUpdate();

            registrado = (filas > 0);

        } catch (SQLException e) {
            System.out.println("Error al agregar conductor: " + e.getMessage());
        } finally {
            
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error cerrando conexión: " + e.getMessage());
            }
        }

        return registrado;
    }
    
    public List<Conductor> obtenerTodos() {

    List<Conductor> lista = new ArrayList<>();

    ResultSet rs = null;

    try {
        con = Conexion.conectar();

        String sql = "SELECT id, nombre, licencia FROM conductores";
        ps = con.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {

        
            Conductor c = new Conductor(
                rs.getString("licencia"),
                rs.getString("id"),
                rs.getString("nombre")
            );

            lista.add(c);
        }

    } catch (SQLException e) {
        System.out.println("Error al listar conductores: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error cerrando recursos: " + e.getMessage());
        }
    }

    return lista;
}
}
