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
        Connection con = Conexion.conectar();

        String sql = """
                     INSERT INTO conductores (id, nombre, cedula, licencia, estado)
                     VALUES (?, ?, ?, ?, ?)
                     """;

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, c.getId());
        ps.setString(2, c.getNombre());
        ps.setString(3, c.getCedula());
        ps.setString(4, c.getLicencia());
        ps.setString(5, c.getEstado());

        int filas = ps.executeUpdate();
        registrado = (filas > 0);

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }

    return registrado;
}
    
    public List<Conductor> obtenerTodos() {

    List<Conductor> lista = new ArrayList<>();


    ResultSet rs = null;

    try {
        con = Conexion.conectar();

        String sql = "SELECT id, nombre, cedula, licencia, estado FROM conductores";
        ps = con.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {

            Conductor c = new Conductor(
                rs.getString("id"),
                rs.getString("nombre"),
                rs.getString("cedula"),
                rs.getString("licencia"),
                rs.getString("estado")     
            );

            lista.add(c);
        }

    } catch (SQLException e) {
        System.out.println("Error al listar: " + e.getMessage());
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
    
    public boolean editarConductor(Conductor c) {

    boolean actualizado = false;


    try {
        con = Conexion.conectar();

        String sql = """
                     UPDATE conductores
                     SET nombre = ?, cedula = ?, licencia = ?, estado = ?
                     WHERE id = ?
                     """;

        ps = con.prepareStatement(sql);

        ps.setString(1, c.getNombre());
        ps.setString(2, c.getCedula());
        ps.setString(3, c.getLicencia());
        ps.setString(4, c.getEstado());
        ps.setString(5, c.getId()); 

        int filas = ps.executeUpdate();

        actualizado = (filas > 0);

    } catch (SQLException e) {
        System.out.println("Error al editar conductor: " + e.getMessage());
    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error cerrando recursos: " + e.getMessage());
        }
    }

    return actualizado;
}
    
    public boolean eliminarConductor(String id) {

    boolean eliminado = false;

    PreparedStatement ps1 = null;
    PreparedStatement ps2 = null;

    try {
        con = Conexion.conectar();

       
        String sql1 = "DELETE FROM conductor_ruta WHERE conductor_id = ?";
        ps1 = con.prepareStatement(sql1);
        ps1.setString(1, id);
        ps1.executeUpdate();

        
        String sql2 = "DELETE FROM conductores WHERE id = ?";
        ps2 = con.prepareStatement(sql2);
        ps2.setString(1, id);

        int filas = ps2.executeUpdate();

        eliminado = (filas > 0);

    } catch (SQLException e) {
        System.out.println("Error al eliminar conductor: " + e.getMessage());
    } finally {
        try {
            if (ps1 != null) ps1.close();
            if (ps2 != null) ps2.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error cerrando recursos: " + e.getMessage());
        }
    }

    return eliminado;
}
    public boolean asignarARuta(String conductorId, int rutaId) {

    boolean asignado = false;
  

    try {
        con = Conexion.conectar();

        String sql = """
                     INSERT INTO conductor_ruta (conductor_id, ruta_id)
                     VALUES (?, ?)
                     """;

        ps = con.prepareStatement(sql);

        ps.setString(1, conductorId);
        ps.setInt(2, rutaId);

        int filas = ps.executeUpdate();

        asignado = (filas > 0);

    } catch (SQLException e) {

      
        if (e.getMessage().contains("duplicate")) {
            System.out.println("El conductor ya tiene esa ruta asignada");
        } else {
            System.out.println("Error al asignar ruta: " + e.getMessage());
        }

    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error cerrando recursos: " + e.getMessage());
        }
    }

    return asignado;
}
    
}
    

