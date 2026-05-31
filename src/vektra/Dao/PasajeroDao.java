
package vektra.Dao;
import java.sql.*;
import java.time.LocalDateTime;
import vektra.Conexion.Conexion;
import vektra.Model.Pasajero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PasajeroDao {

  

    public Pasajero validarPasajero(String correo, String contrasena) {
        Pasajero pasajero = null;
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ?";

        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

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

    public void registrarPasajero(Pasajero p) {
        String sql = "INSERT INTO usuarios (id, nombre, correo, contraseña, fecha_registro) "
                + "VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(p.getId()));
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getContraseña());
            ps.setTimestamp(5, p.getFechaRegistro() != null
                    ? Timestamp.valueOf(p.getFechaRegistro())
                    : Timestamp.valueOf(LocalDateTime.now()));

            ps.executeUpdate();
            ps.close();
            System.out.println("[PasajeroDao] Pasajero registrado: " + p.getNombre());

        } catch (SQLException e) {
            System.err.println("[PasajeroDao] Error en registrarPasajero: " + e.getMessage());
        }
    }

    public List<Pasajero> obtenerTodos() {
        List<Pasajero> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, correo, contraseña, fecha_registro FROM usuarios ORDER BY id";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("[PasajeroDao] Error en obtenerTodos: " + e.getMessage());
        }
        return lista;

    }

    public Pasajero buscarPorId(int id) {
        String sql = "SELECT id, nombre, correo, contraseña, fecha_registro "
                + "FROM usuarios WHERE id = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Pasajero p = mapear(rs);
                rs.close();
                ps.close();
                return p;
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.err.println("[PasajeroDao] Error en buscarPorId: " + e.getMessage());
        }
        return null;
    }

    public Pasajero buscarPorCorreo(String correo) {
        String sql = "SELECT id, nombre, correo, contraseña, fecha_registro "
                + "FROM usuarios WHERE correo = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Pasajero p = mapear(rs);
                rs.close();
                ps.close();
                return p;
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.err.println("[PasajeroDao] Error en buscarPorCorreo: " + e.getMessage());
        }
        return null;
    }

    public void actualizarPasajero(Pasajero p) {
        String sql = "UPDATE usuarios SET nombre = ?, correo = ?, contraseña = ?, fecha_registro = ? "
                + "WHERE id = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getContraseña());
            ps.setTimestamp(4, p.getFechaRegistro() != null
                    ? Timestamp.valueOf(p.getFechaRegistro())
                    : Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(5, Integer.parseInt(p.getId()));

            int filas = ps.executeUpdate();
            ps.close();
            System.out.println(filas > 0
                    ? "[PasajeroDao] Pasajero actualizado: id=" + p.getId()
                    : "[PasajeroDao] No se encontró pasajero con id=" + p.getId());

        } catch (SQLException e) {
            System.err.println("[PasajeroDao] Error en actualizarPasajero: " + e.getMessage());
        }
    }

    public void eliminarPasajero(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            ps.close();
            System.out.println(filas > 0
                    ? "[PasajeroDao] Pasajero eliminado: id=" + id
                    : "[PasajeroDao] No se encontró pasajero con id=" + id);

        } catch (SQLException e) {
            System.err.println("[PasajeroDao] Error en eliminarPasajero: " + e.getMessage());
        }
    }

    public Pasajero autenticar(String correo, String contrasena) {
        String sql = "SELECT id, nombre, correo, contraseña, fecha_registro "
                + "FROM usuarios WHERE correo = ? AND contraseña = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Pasajero p = mapear(rs);
                rs.close();
                ps.close();
                System.out.println("[PasajeroDao] Login exitoso: " + p.getNombre());
                return p;
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.err.println("[PasajeroDao] Error en autenticar: " + e.getMessage());
        }

        System.out.println("[PasajeroDao] Credenciales incorrectas para: " + correo);
        return null;
    }
    
      private Pasajero mapear(ResultSet rs) throws SQLException {
        String id = String.valueOf(rs.getInt("id"));
        String nombre = rs.getString("nombre");
        String correo = rs.getString("correo");
        String contrasena = rs.getString("contraseña");
        Timestamp ts = rs.getTimestamp("fecha_registro");
        LocalDateTime fecha = (ts != null) ? ts.toLocalDateTime() : null;

        return new Pasajero(id, nombre, correo, contrasena, fecha);
    }

      
      
}
