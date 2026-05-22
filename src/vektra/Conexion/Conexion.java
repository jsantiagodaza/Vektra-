/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Usuario
 */
public class Conexion {
    
    private static final String URL = "jdbc:mysql://localhost:3306/vektra";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public static Connection getConnection(){
        Connection con = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (Exception e){
            System.out.println("Error conexion:"+ e.getMessage());
        }
        return con;
    }
}
