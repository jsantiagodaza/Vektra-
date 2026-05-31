/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Dao;

import vektra.Model.Vehiculo;

import vektra.Conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class VehiculoDao {

    Connection con = null;

    public void agregarVehiculo(Vehiculo v) {

        String sql = "INSERT INTO vehiculos (id, capacidad, anio_fabricacion) VALUES (?, ?, ?)";

        try {
            con = Conexion.conectar();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, v.getId());
            ps.setInt(2, v.getCapacidad());
            ps.setInt(3, v.getAnioFabricacion());

            ps.executeUpdate();

            System.out.println("Vehículo agregado correctamente");

        } catch (Exception e) {
            System.out.println("Error al agregar vehículo: " + e.getMessage());

        } finally {
            Conexion.cerrarConexion();
        }
    }

    public List<Vehiculo> obtenerTodos() {

        List<Vehiculo> lista = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement ps = null;
        con = Conexion.conectar();

        try {

            String sql = "SELECT id, capacidad, anio_fabricacion FROM vehiculos";
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {

                Vehiculo v = new Vehiculo(
                        rs.getString("id"),
                        rs.getInt("capacidad"),
                        rs.getInt("anio_fabricacion")
                );

                lista.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error cerrando recursos: " + e.getMessage());
            }
        }

        return lista;
    }

    public Vehiculo buscarPorId(String id) {
        String sql = "SELECT * FROM vehiculos WHERE id = ?";
        Vehiculo v = null;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                v = new Vehiculo();
                v.setId(rs.getString("id"));
                v.setCapacidad(rs.getInt("capacidad"));
                v.setAnioFabricacion(rs.getInt("anio_fabricacion"));
            }

        } catch (Exception e) {
            System.out.println("Error al buscar vehículo: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error cerrando conexión: " + e.getMessage());
            }
        }
        return v;
    }
    
    public void actualizarVehiculo(Vehiculo v) {
        String sql = "UPDATE vehiculos SET capacidad = ?, anio_fabricacion = ? WHERE id = ?";

        try {

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, v.getCapacidad());
            ps.setInt(2, v.getAnioFabricacion());
            ps.setString(3, v.getId());
            ps.executeUpdate();
            System.out.println("Vehículo actualizado correctamente");

        } catch (Exception e) {
            System.out.println("Error al actualizar vehículo: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }
    
    public void eliminarVehiculo(int id) {
        String sql = "DELETE FROM vehiculos WHERE id = ?";

        try {
            con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Vehículo eliminado correctamente");

        } catch (Exception e) {
            System.out.println("Error al eliminar vehículo: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error cerrando conexión: " + e.getMessage());
            }
        }
}
    
}
