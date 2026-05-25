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

public class ConductorDao {

    public boolean agregarConductor(Conductor c) {
        boolean registrado = false;

        Connection con = null;
        PreparedStatement ps = null;

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
}
