/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Service;

import java.time.LocalDateTime;
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
 
 public TicketService(){
     ticketDao = new TicketDao();
 }
 
 public void crearTicket(String id,Pasajero pasajero, Estacion origen, Estacion destino, double precio ){
     
     Ticket ticket = new Ticket(  id, LocalDateTime.now(), precio, "QR-" + id, pasajero, origen, destino);
     ticketDao.guardarTicket(ticket);
 }
 public void mostrarTickets(){
     ticketDao.obtenerTickets();
 }
 
}
