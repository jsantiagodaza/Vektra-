/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Service;

/**
 *
 * @author Usuario
 */
public class UsuarioService {
    public String validarLogin(String id, String password){
        if (id.isEmpty()|| password.isEmpty()){
            return "Todos los campos son abligatorios";
        }
        
        
    }
}
