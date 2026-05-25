/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Service;

import java.util.List;
import vektra.Dao.RutaDao;
import vektra.Model.Ruta;
/**
 *
 * @author santi
 */
public class RutaService {

    private RutaDao rutaDao;

    public RutaService() {
        rutaDao = new RutaDao();
    }
    public List<Ruta>loadRoutesFromDB(){
        return rutaDao.TodasLasRutas();
    }

}
