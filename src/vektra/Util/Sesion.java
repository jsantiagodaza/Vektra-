/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Util;

import vektra.Model.Pasajero;

public class Sesion {
    private static Pasajero pasajeroActivo;

    public static Pasajero getPasajeroActivo() { return pasajeroActivo; }
    public static void setPasajeroActivo(Pasajero p) { pasajeroActivo = p; }
    public static void cerrarSesion() { pasajeroActivo = null; }
}
