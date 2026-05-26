package vektra.View;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.JPanel;
import vektra.Conexion.Conexion;

public class MapaView extends JPanel {
     private JFXPanel jfxPanel;
     
     public MapaView(){
          setLayout(new BorderLayout());
          jfxPanel = new JFXPanel();
          add(jfxPanel, BorderLayout.CENTER);
          Platform.runLater(this::initFX);
     }

    private void initFX(){
          WebView webView = new WebView();
          WebEngine engine = webView.getEngine();
        
          // Asegurar estructura de coordenadas en base de datos y datos demo
          try (Connection con = Conexion.conectar()) {
              if (con != null) {
                  asegurarColumnasYDatos(con);
              }
          } catch (Exception e) {
              System.err.println("Error al inicializar base de datos en MapaView: " + e.getMessage());
          }

          String url = getClass().getResource("/resources/mapa.html").toExternalForm();
          engine.load(url);
          Scene scene = new Scene(webView);
          
          // Vincular el tamaño del WebView al de la escena de JavaFX
          webView.prefWidthProperty().bind(scene.widthProperty());
          webView.prefHeightProperty().bind(scene.heightProperty());
          
          jfxPanel.setScene(scene);

          // Cargar datos dinámicamente cuando el HTML haya cargado con éxito
          engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
              if (newValue == javafx.concurrent.Worker.State.SUCCEEDED) {
                  String jsonEstaciones = obtenerEstacionesJson();
                  String jsonRutas = obtenerRutasJson();
                  
                  // Ejecutar función JavaScript en la página
                  Platform.runLater(() -> {
                      engine.executeScript("cargarDatos(" + jsonEstaciones + ", " + jsonRutas + ")");
                  });
              }
          });
     }

     private void asegurarColumnasYDatos(Connection con) {
         try (Statement stmt = con.createStatement()) {
             // 1. Agregar las columnas latitud y longitud si no existen
             stmt.execute("ALTER TABLE estaciones ADD COLUMN IF NOT EXISTS latitud DOUBLE PRECISION");
             stmt.execute("ALTER TABLE estaciones ADD COLUMN IF NOT EXISTS longitud DOUBLE PRECISION");
             
             // 2. Comprobar si hay estaciones, de lo contrario insertar valores demo
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM estaciones");
             if (rs.next() && rs.getInt(1) == 0) {
                 System.out.println("Insertando estaciones y rutas iniciales por defecto...");
                 stmt.execute("INSERT INTO estaciones (nombre, linea, orden, latitud, longitud) VALUES ('Portal Norte', 'Roja', 1, 10.4800, -73.2500)");
                 stmt.execute("INSERT INTO estaciones (nombre, linea, orden, latitud, longitud) VALUES ('Calle 72', 'Amarilla', 2, 10.4700, -73.2400)");
                 stmt.execute("INSERT INTO estaciones (nombre, linea, orden, latitud, longitud) VALUES ('Portal Sur', 'Azul', 3, 10.4500, -73.2300)");
                 
                 // Rutas por defecto que conecten las estaciones
                 stmt.execute("INSERT INTO rutas (estacion_origen_id, estacion_destino_id, tiempo_minutos) VALUES (1, 2, 10)");
                 stmt.execute("INSERT INTO rutas (estacion_origen_id, estacion_destino_id, tiempo_minutos) VALUES (2, 3, 15)");
             } else {
                 // 3. Si existen estaciones sin coordenadas asignadas, darles una por defecto
                 stmt.execute("UPDATE estaciones SET latitud = 10.4800, longitud = -73.2500 WHERE nombre = 'Portal Norte' AND latitud IS NULL");
                 stmt.execute("UPDATE estaciones SET latitud = 10.4700, longitud = -73.2400 WHERE nombre = 'Calle 72' AND latitud IS NULL");
                 stmt.execute("UPDATE estaciones SET latitud = 10.4500, longitud = -73.2300 WHERE nombre = 'Portal Sur' AND latitud IS NULL");
                 
                 // Asignar espaciado geométrico dinámico al resto de las estaciones nuevas agregadas sin coordenadas
                 try (ResultSet rs2 = stmt.executeQuery("SELECT id, orden FROM estaciones WHERE latitud IS NULL OR longitud IS NULL")) {
                     java.util.List<Integer> ids = new java.util.ArrayList<>();
                     java.util.List<Integer> ords = new java.util.ArrayList<>();
                     while (rs2.next()) {
                         ids.add(rs2.getInt("id"));
                         ords.add(rs2.getInt("orden"));
                     }
                     for (int i = 0; i < ids.size(); i++) {
                         int id = ids.get(i);
                         int orden = ords.get(i);
                         // Generar una retícula de coordenadas espaciadas alrededor de Valledupar (10.4631, -73.2532)
                         double latOffset = 0.008 * ((orden % 4) - 1.5);
                         double lngOffset = 0.008 * (((id + orden) % 4) - 1.5);
                         stmt.execute("UPDATE estaciones SET latitud = " + (10.4631 + latOffset) + 
                                      ", longitud = " + (-73.2532 + lngOffset) + " WHERE id = " + id);
                     }
                 }
             }
         } catch (Exception e) {
             System.err.println("Error ejecutando migraciones/inserts en base de datos: " + e.getMessage());
         }
     }

     private String obtenerEstacionesJson() {
         StringBuilder json = new StringBuilder("[");
         try (Connection con = Conexion.conectar();
              Statement stmt = con.createStatement();
              ResultSet rs = stmt.executeQuery("SELECT id, nombre, linea, orden, latitud, longitud FROM estaciones")) {
             boolean first = true;
             while (rs.next()) {
                 if (!first) {
                     json.append(",");
                 }
                 first = false;
                 json.append("{")
                     .append("\"id\":").append(rs.getInt("id")).append(",")
                     .append("\"nombre\":\"").append(rs.getString("nombre").replace("\"", "\\\"")).append("\",")
                     .append("\"linea\":\"").append(rs.getString("linea").replace("\"", "\\\"")).append("\",")
                     .append("\"orden\":").append(rs.getInt("orden")).append(",")
                     .append("\"lat\":").append(rs.getDouble("latitud")).append(",")
                     .append("\"lng\":").append(rs.getDouble("longitud"))
                     .append("}");
             }
         } catch (Exception e) {
             System.err.println("Error obteniendo estaciones JSON: " + e.getMessage());
         }
         json.append("]");
         return json.toString();
     }

     private String obtenerRutasJson() {
         StringBuilder json = new StringBuilder("[");
         try (Connection con = Conexion.conectar();
              Statement stmt = con.createStatement();
              ResultSet rs = stmt.executeQuery("SELECT id, estacion_origen_id, estacion_destino_id, tiempo_minutos, kilometro, color_linea, transbordo FROM rutas")) {
             boolean first = true;
             while (rs.next()) {
                 if (!first) {
                     json.append(",");
                 }
                 first = false;
                 json.append("{")
                     .append("\"id\":").append(rs.getInt("id")).append(",")
                     .append("\"origen_id\":").append(rs.getInt("estacion_origen_id")).append(",")
                     .append("\"destino_id\":").append(rs.getInt("estacion_destino_id")).append(",")
                     .append("\"tiempo\":").append(rs.getDouble("tiempo_minutos")).append(",")
                     .append("\"color\":\"") .append(rs.getString("color_linea").replace("\"", "\\\"")).append(",")
                     .append("\"transbordos\":") .append(rs.getInt("transbordos"))
                     .append("}");
             }
         } catch (Exception e) {
             System.err.println("Error obteniendo rutas JSON: " + e.getMessage());
         }
         json.append("]");
         return json.toString();
     }
}