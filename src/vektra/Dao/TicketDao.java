/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Dao;

import java.util.ArrayList;
import java.util.List;
import vektra.Model.Ticket;

/**
 *
 * @author santi
 */
public class TicketDao {
    private List<Ticket> tickets;
    
    public TicketDao(){
        tickets = new ArrayList<>();
    }
    
    public void guardarTicket(Ticket ticket){
        tickets.add(ticket);
    }
    
    public List<Ticket> obtenerTickets(){
        return tickets;
    }
    
    
}
