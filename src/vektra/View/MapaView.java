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

    public MapaView() {

        System.out.println(javafx.scene.Parent.class);
        setLayout(new BorderLayout());
         jfxPanel = new JFXPanel();
         add(jfxPanel, BorderLayout.CENTER);

        Platform.runLater(this::initFX);
      //  javax.swing.JLabel lbl = new javax.swing.JLabel("Mapa cargado");
        //add(lbl, BorderLayout.CENTER);
    }

    private void initFX() {

        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();
        // Inicializar datos
        try (Connection con = Conexion.conectar()) {
            if (con != null) {
                asegurarColumnasYDatos(con);
            }

        } catch (Exception e) {
            System.err.println(
                    "Error al inicializar BD: "
                    + e.getMessage()
            );
        }

        String url = getClass()
                .getResource("/resources/mapa.html")
                .toExternalForm();

        engine.load(url);

        Scene scene = new Scene(webView);

        webView.prefWidthProperty().bind(scene.widthProperty());
        webView.prefHeightProperty().bind(scene.heightProperty());

        jfxPanel.setScene(scene);

        // Cuando cargue el HTML
        engine.getLoadWorker()
                .stateProperty()
                .addListener((obs, oldState, newState) -> {

                    if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                        String jsonEstaciones = obtenerEstacionesJson();
                        String jsonRutas = obtenerRutasJson();

                        Platform.runLater(() -> {

                            engine.executeScript(
                                    "cargarDatos("
                                    + jsonEstaciones
                                    + ","
                                    + jsonRutas
                                    + ")"
                            );

                        });
                    }
                });
    }

    private void asegurarColumnasYDatos(Connection con) {

        try (Statement stmt = con.createStatement()) {

            stmt.execute("""
                ALTER TABLE estacioness
                ADD COLUMN IF NOT EXISTS latitud DOUBLE PRECISION
            """);

            stmt.execute("""
                ALTER TABLE estacioness
                ADD COLUMN IF NOT EXISTS longitud DOUBLE PRECISION
            """);

        } catch (Exception e) {

            System.err.println(
                    "Error en migración BD: "
                    + e.getMessage()
            );
        }
    }

    private String obtenerEstacionesJson() {

        StringBuilder json = new StringBuilder("[");

        try (
                Connection con = Conexion.conectar(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("""
                    SELECT
                        id,
                        nombre,
                        linea,
                        orden_estacion,
                        latitud,
                        longitud
                    FROM estacioness
                """)) {

            boolean first = true;

            while (rs.next()) {

                if (!first) {
                    json.append(",");
                }

                first = false;

                json.append("{")
                        .append("\"id\":")
                        .append(rs.getInt("id"))
                        .append(",")
                        .append("\"nombre\":\"")
                        .append(rs.getString("nombre")
                                .replace("\"", "\\\""))
                        .append("\",")
                        .append("\"linea\":\"")
                        .append(rs.getString("linea")
                                .replace("\"", "\\\""))
                        .append("\",")
                        .append("\"orden\":")
                        .append(rs.getInt("orden_estacion"))
                        .append(",")
                        .append("\"lat\":")
                        .append(rs.getDouble("latitud"))
                        .append(",")
                        .append("\"lng\":")
                        .append(rs.getDouble("longitud"))
                        .append("}");
            }

        } catch (Exception e) {

            System.err.println(
                    "Error obteniendo estaciones JSON: "
                    + e.getMessage()
            );
        }

        json.append("]");

        return json.toString();
    }

    private String obtenerRutasJson() {

        StringBuilder json = new StringBuilder("[");

        try (
                Connection con = Conexion.conectar(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("""
                    SELECT
                        id,
                        estacion_origen_id,
                        estacion_destino_id,
                        tiempo_minutos,
                        kilometros,
                        color_linea,
                        transbordos
                    FROM rutass
                """)) {

            boolean first = true;

            while (rs.next()) {

                if (!first) {
                    json.append(",");
                }

                first = false;

                json.append("{")
                        .append("\"id\":")
                        .append(rs.getInt("id"))
                        .append(",")
                        .append("\"origen_id\":")
                        .append(rs.getInt("estacion_origen_id"))
                        .append(",")
                        .append("\"destino_id\":")
                        .append(rs.getInt("estacion_destino_id"))
                        .append(",")
                        .append("\"tiempo\":")
                        .append(rs.getDouble("tiempo_minutos"))
                        .append(",")
                        .append("\"kilometros\":")
                        .append(rs.getDouble("kilometros"))
                        .append(",")
                        .append("\"color_linea\":\"")
                        .append(rs.getString("color_linea")
                                .replace("\"", "\\\""))
                        .append("\",")
                        .append("\"transbordos\":")
                        .append(rs.getInt("transbordos"))
                        .append("}");
            }

        } catch (Exception e) {

            System.err.println(
                    "Error obteniendo rutas JSON: "
                    + e.getMessage()
            );
        }

        json.append("]");

        return json.toString();
    }
}
