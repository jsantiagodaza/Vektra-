
package vektra.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException; 

public class ConexionBD {
    
     private static final String URL = "jdbc:postgresql://localhost:5432/Vetkra";
    private static final String USUARIO = "postgres";
    private static final String CONTRASENA = "admin123";

    public static Connection getConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("Conexión exitosa");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return con;
    }

   
    
    
}
