package vektra.Service;

import java.time.LocalDateTime;
import java.util.List;
import vektra.Dao.TicketDao;
import vektra.Model.Estacion;
import vektra.Model.Pasajero;
import vektra.Model.Ticket;

/**
 *
 * @author santi
 */
public class TicketService {

    private TicketDao ticketDao;

    public TicketService() {
        ticketDao = new TicketDao();
    }

   public void crearTicket(String id, Pasajero pasajero, Estacion origen, Estacion destino, double precio) {

    Ticket ticket = new Ticket(id);

    ticket.setFecha(LocalDateTime.now());
    ticket.setPrecio(precio);
    ticket.setCodigoQR("QR-" + id);
    ticket.setPasajero(pasajero);
    ticket.setEstacionOrigen(origen);
    ticket.setEstacionDestino(destino);

    ticketDao.guardarTicket(ticket);
}

    public void mostrarTickets() {
        ticketDao.obtenerTickets();
    }

    public List<Ticket> obtenerTickets() {
        return ticketDao.obtenerTickets();
    }

}