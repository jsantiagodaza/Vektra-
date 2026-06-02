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
import java.sql.Timestamp;
import vektra.Conexion.Conexion;
import vektra.Dao.ConexionBD;
import vektra.Util.Sesion;

public class TicketDao {

    private List<Ticket> tickets;

    public TicketDao() {
        tickets = new ArrayList<>();
    }

    public void guardarTicket(Ticket ticket) {
        String sql = "INSERT INTO boletos (usuario_id, estacion_origen_id, estacion_destino_id, fecha_compra, precio, codigo_boleto) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(ticket.getPasajero().getId()));
            ps.setInt(2, Integer.parseInt(ticket.getEstacionOrigen().getId()));
            ps.setInt(3, Integer.parseInt(ticket.getEstacionDestino().getId()));
            ps.setTimestamp(4, Timestamp.valueOf(ticket.getFechaCompleta()));
            ps.setDouble(5, ticket.getPrecio());
            ps.setString(6, ticket.getCodigo());

            ps.executeUpdate();

            System.out.println("Ticket guardado en la base de datos");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Ticket> obtenerTickets() {
        List<Ticket> lista = new ArrayList<>();

        String sql = "SELECT * FROM boletos";

        try (Connection conn = Conexion.conectar(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Ticket ticket = new Ticket();

                ticket.setId(String.valueOf(rs.getInt("id")));
                ticket.setCodigo(rs.getString("codigo_boleto"));
                ticket.setFecha(rs.getTimestamp("fecha_compra").toLocalDateTime());
                ticket.setPrecio(rs.getDouble("precio"));

                Pasajero pasajero = new Pasajero();
                pasajero.setId(String.valueOf(rs.getInt("usuario_id")));
                ticket.setPasajero(pasajero);

                Estacion origen = new Estacion();
                origen.setId(String.valueOf(rs.getInt("estacion_origen_id")));
                ticket.setEstacionOrigen(origen);

                Estacion destino = new Estacion();
                destino.setId(String.valueOf(rs.getInt("estacion_destino_id")));
                ticket.setEstacionDestino(destino);

                lista.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void mostrarTickets() {
        List<Ticket> tickets = obtenerTickets();

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
                     "JOIN estacioness eo ON b.estacion_origen_id = eo.id " +
                     "JOIN estacioness ed ON b.estacion_destino_id = ed.id " +
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