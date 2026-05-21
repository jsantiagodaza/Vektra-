
package vektra.Dao;
import java.sql.*;
import vektra.Model.Pasajero;

public class PasajeroDao {
    
    public Pasajero validarPasajero(String correo, String contrasena) {
        Pasajero pasajero = null;
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pasajero = new Pasajero(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("contrasena"),
                    rs.getTimestamp("fecha_registro").toLocalDateTime()
                );
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return pasajero;
    }
    
    
}
