/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Util;

import vektra.Model.Ticket;


/**
 *
 * @author santi
 */
public class GeneradorTickets {

   public static void imprimirTicket(Ticket ticket) {

        System.out.println("===== TICKET VEKTRA =====");

        System.out.println("ID: "
                + ticket.getId());

        System.out.println("Pasajero: "
                + ticket.getPasajero().getNombre());

        System.out.println("Origen: "
                + ticket.getEstacionOrigen().getNombre());

        System.out.println("Destino: "
                + ticket.getEstacionDestino().getNombre());

        System.out.println("Precio: "
                + ticket.getPrecio());

        System.out.println("=========================");
    }

}
