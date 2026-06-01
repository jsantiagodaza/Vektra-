/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Dao;

import java.util.ArrayList;
import java.util.List;
import vektra.Model.Estacion;
import vektra.Model.Pasajero;
import vektra.Model.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import vektra.Util.Sesion;

/**
 *
 * @author santi
 */
public class TicketDao {

    private List<Ticket> tickets;

    public TicketDao() {
        tickets = new ArrayList<>();
    }

    public void guardarTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public List<Ticket> obtenerTickets() {
        return tickets;
    }

    public void mostrarTickets() {
        for (Ticket t : tickets) {
            System.out.println(t);
        }
    }
    
    public List<Ticket> obtenerTicketsPorUsuario(String usuarioId) {
        List<Ticket> tickets = new ArrayList<>();

        String sql = "SELECT b.id, b.codigo_boleto, b.fecha_compra, b.precio, " +
                     "u.id AS u_id, u.nombre AS u_nombre, u.correo, " +
                     "eo.id AS origen_id, eo.nombre AS origen_nombre, " +
                     "ed.id AS destino_id, ed.nombre AS destino_nombre " +
                     "FROM boletos b " +
                     "JOIN usuarios u ON b.usuario_id = u.id " +
                     "JOIN estaciones eo ON b.estacion_origen_id = eo.id " +
                     "JOIN estaciones ed ON b.estacion_destino_id = ed.id " +
                     "WHERE b.usuario_id = ?";

        try (Connection con = ConexionBD.getConexion()) {
            if (con == null) {
                System.out.println("Error al obtener tickets por usuario: no se pudo conectar a la base de datos");
                return tickets;
            }
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, usuarioId);
                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {

                        Estacion origen = new Estacion(
                            rs.getString("origen_id"),
                            rs.getString("origen_nombre")
                        );

                        Estacion destino = new Estacion(
                            rs.getString("destino_id"),
                            rs.getString("destino_nombre")
                        );

                        Pasajero pasajero = new Pasajero(
                            rs.getString("u_id"),
                            rs.getString("u_nombre"),
                            rs.getString("correo"),
                            null,   // contraseña no necesaria aquí
                            null    // fechaRegistro no necesaria aquí
                        );

                        Ticket t = new Ticket(
                            rs.getString("id"),
                            rs.getTimestamp("fecha_compra").toLocalDateTime(),
                            rs.getDouble("precio"),
                            rs.getString("codigo_boleto"),
                            pasajero,
                            origen,
                            destino
                        );

                        tickets.add(t);
                    }
                }
            }
            

        } catch (SQLException e) {
            System.out.println("Error al obtener tickets: " + e.getMessage());
        }

        return tickets;
    }
    
    public List<Ticket> obtenerTicketsActivos() {
        List<Ticket> tickets = new ArrayList<>();

        String sql = "SELECT b.id, b.codigo_boleto, b.fecha_compra, b.precio, " +
                     "u.id AS u_id, u.nombre AS u_nombre, u.correo, " +
                     "eo.id AS origen_id, eo.nombre AS origen_nombre, " +
                     "ed.id AS destino_id, ed.nombre AS destino_nombre " +
                     "FROM boletos b " +
                     "JOIN usuarios u ON b.usuario_id = u.id " +
                     "JOIN estaciones eo ON b.estacion_origen_id = eo.id " +
                     "JOIN estaciones ed ON b.estacion_destino_id = ed.id";

        try (Connection con = ConexionBD.getConexion()) {
            if (con == null) {
                System.out.println("Error al obtener tickets activos: no se pudo conectar a la base de datos");
                return tickets;
            }
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Estacion origen = new Estacion(
                        rs.getString("origen_id"),
                        rs.getString("origen_nombre")
                    );

                    Estacion destino = new Estacion(
                        rs.getString("destino_id"),
                        rs.getString("destino_nombre")
                    );

                    Pasajero pasajero = new Pasajero(
                        rs.getString("u_id"),
                        rs.getString("u_nombre"),
                        rs.getString("correo"),
                        null,
                        null
                    );

                    Ticket t = new Ticket(
                        rs.getString("id"),
                        rs.getTimestamp("fecha_compra").toLocalDateTime(),
                        rs.getDouble("precio"),
                        rs.getString("codigo_boleto"),
                        pasajero,
                        origen,
                        destino
                    );

                    tickets.add(t);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener tickets activos: " + e.getMessage());
        }

        return tickets;
    }
     
     public List<Ticket> obtenerTicketsDelPasajeroActivo() {
        String usuarioId = Sesion.getPasajeroActivo().getId();
        return obtenerTicketsPorUsuario(usuarioId);
    }
}



