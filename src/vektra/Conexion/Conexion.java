/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Usuario
 */
public class Conexion {

    private static Connection con;
    private static Connection conectar(){
      
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String URL = "jdbc:mysql://localhost:3306/vektra";
            String USER = "root";
            String PASSWORD = "";
            
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion Exitosa");
        } catch (ClassNotFoundException e){
             System.out.println("Error Driver:" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error conexion:" + e.getMessage());
        }
        return con;
    }
    
    public static void cerrarConexion (){
        try {
            if (con != null){
                con.close();
                System.out.println("Conexion Cerrada");
            }
        }catch (SQLException e){
              System.out.println( "Error cerrando conexion: " + e.getMessage());
        }
        
    }
}
