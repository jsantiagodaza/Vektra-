package vektra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class DbQuery {
    public static void main(String[] args) {

        String URL = "jdbc:postgresql://localhost:5432/Vektra";

        String USER = "postgres";
        String PASSWORD = "adminadmin";
        
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                 Statement stmt = con.createStatement()) {
                
                System.out.println("--- COLUMNAS DE ESTACIONESS ---");
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM estacioness LIMIT 1")) {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        System.out.println("Columna: " + rsmd.getColumnName(i) + " (" + rsmd.getColumnTypeName(i) + ")");
                    }
                }
                
                System.out.println("--- DATOS DE ESTACIONESS ---");
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM estacioness")) {
                    while (rs.next()) {
                        System.out.printf("ID: %d, Nombre: %s, Linea: %s, Latitud: %f, Longitud: %f%n",
                                rs.getInt("id"), rs.getString("nombre"), rs.getString("linea"),
                                rs.getDouble("latitud"), rs.getDouble("longitud"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
