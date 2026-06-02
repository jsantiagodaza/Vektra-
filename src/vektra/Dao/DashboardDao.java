/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author Usuario
 */
public class DashboardDao {
     public int contarEstaciones() {
        String sql = "SELECT COUNT(*) FROM estacioness";

        try (
            Connection con = ConexionBD.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return 0;
    }
     
     public int totalConductores() {

        String sql = "SELECT COUNT(*) FROM conductores";
        try (
            Connection con = ConexionBD.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }
     
      public int contarLineas() {
        String sql = "SELECT COUNT(DISTINCT linea) FROM estacioness";
        try (
            Connection con = ConexionBD.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }
      
        public int ticketsVendidosHoy() {

        String sql = """
            SELECT COUNT(*)
            FROM boletos
            WHERE DATE(fecha_compra) = CURRENT_DATE """;
        
        try (
            Connection con = ConexionBD.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }    
}




  