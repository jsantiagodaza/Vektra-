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
      String sql =
        "INSERT INTO conductores " +
        "(nombre, apellido, cedula, telefono, licencia, ruta_asignada) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    try (
        Connection con = Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setString(1, c.getNombre());
        ps.setString(2, c.getApellido());
        ps.setString(3, c.getId());
        ps.setString(4, c.getTelefono());
        ps.setString(5, c.getLicencia());
        ps.setString(6, c.getRutaAsignada());

        return ps.executeUpdate() > 0;

    } catch (Exception e) {

        System.out.println(e.getMessage());
        return false;
    }
}
    
    public List<Conductor> obtenerTodos() {

    List<Conductor> lista = new ArrayList<>();
     String sql = "SELECT * FROM conductores";

    try {
        Connection con = Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Conductor c = new Conductor();
                    
            c.setId(rs.getString("id"));
            c.setNombre(rs.getString("nombre"));
            c.setApellido(rs.getString("apellido"));
            c.setCedula(rs.getString("cedula"));
            c.setTelefono(rs.getString("telefono"));
            c.setLicencia(rs.getString("licencia"));
            c.setRutaAsignada(rs.getString("ruta_asignada"));
            
            lista.add(c);
        }

    } catch (SQLException e) {
        System.out.println("Error al listar: " + e.getMessage());
    } finally {
        try {
            
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error cerrando recursos: " + e.getMessage());
        }
    }

    return lista;
}
    
    public boolean editarConductor(Conductor c) {
       String sql =
        "UPDATE conductores " +
        "SET nombre=?, apellido=?, telefono=?, licencia=?, ruta_asignada=? " +
        "WHERE id=?";
    try (
       Connection con = Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql)
            ){

        ps.setString(1, c.getNombre());
        ps.setString(2, c.getCedula());
        ps.setString(3, c.getApellido());
        ps.setString(4, c.getTelefono());
        ps.setString(5, c.getLicencia());
        ps.setString(6, c.getRutaAsignada());
        ps.setString(7, c.getId()); 

        return ps.executeUpdate()> 0;

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

   return false; 
}
    
    public boolean eliminarConductor(String id) {

    String sql = "DELETE FROM conductor_ruta WHERE conductor_id = ?";

     try (
        Connection con = Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setString(1, id);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        System.out.println("Error al eliminar conductor: " + e.getMessage());
        return false;
    }

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
    

