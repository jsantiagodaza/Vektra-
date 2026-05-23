/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Service;

import vektra.Dao.UsuarioDao;
public class UsuarioService {
    public String validarLogin(String id, String password){
        if (id.isEmpty()|| password.isEmpty()){
            return "Todos los campos son abligatorios";
        }
        
        if (!id.matches("\\d+")){
            return "El ID debe contener solo números";
        }
        
        if (id.length()< 3){
            return "El ID debe tener minimo 7 digitos";
        }
        
        if (password.length()<4){
            return "La contraseña es muy corta";
        }
        UsuarioDao dao = new UsuarioDao();
        boolean existe = dao.validarCredenciales(id, password);
        if(!existe){
            return "Usuario o contraseña incorrectos";
        }
        return "OK";
    }
}
