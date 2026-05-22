/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Dao;

import Conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDao {

    public boolean validarCredenciales(String id, String password) {
        boolean valido = false;
        try {
            Connection con = Conexion.getConnection();
            String sql = """
                         SELECT *
                         FROM usuarios
                         WHERE id_usuario = ?
                         AND password = ?
                         """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            valido = rs.next();
        } catch (Exception e) {
            System.out.println("Error login: " + e.getMessage());
        }
        return valido;
    }
}
