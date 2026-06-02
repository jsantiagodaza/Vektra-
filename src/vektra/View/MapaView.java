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

        setLayout(new BorderLayout());

        jfxPanel = new JFXPanel();
        jfxPanel.setPreferredSize(new java.awt.Dimension(860, 500));

        add(jfxPanel, BorderLayout.CENTER);

        Platform.runLater(this::initFX);
    }

    private void initFX() {

        WebView webView = new WebView();

        WebEngine engine = webView.getEngine();
        engine.setUserAgent("VektraApp/1.0 (Contact: tu_email@ejemplo.com)");

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

        java.net.URL resourceUrl = getClass().getResource("/resources/mapa.html");
        if (resourceUrl == null) {
            System.err.println("No se encontro /resources/mapa.html");
            return;
        }
        
        String url = resourceUrl.toExternalForm();
        System.out.println("Cargando URL: " + url);

        engine.load(url);

        Scene scene = new Scene(webView);

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

        // Forzar resize después de que cargue
        Platform.runLater(() -> {
            javafx.animation.PauseTransition pause = 
                new javafx.animation.PauseTransition(
                    javafx.util.Duration.millis(1000)
                );
            pause.setOnFinished(e -> {
                jfxPanel.setSize(jfxPanel.getWidth() + 1, jfxPanel.getHeight());
                jfxPanel.setSize(jfxPanel.getWidth() - 1, jfxPanel.getHeight());
            });
            pause.play();
        });
    }

    private void asegurarColumnasYDatos(Connection con) {

        try (Statement stmt = con.createStatement()) {

            stmt.execute("""
                ALTER TABLE estaciones
                ADD COLUMN IF NOT EXISTS latitud DOUBLE PRECISION
            """);

            stmt.execute("""
                ALTER TABLE estaciones
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
                Connection con = Conexion.conectar();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("""
                    SELECT
                        id,
                        nombre,
                        linea,
                        orden_estacion,
                        latitud,
                        longitud
                    FROM estaciones
                """)
                ) {

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
                Connection con = Conexion.conectar();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("""
                    SELECT
                        id,
                        estacion_origen_id,
                        estacion_destino_id,
                        tiempo_minutos,
                        kilometros,
                        color_linea,
                        transbordos
                    FROM rutas
                """)
                ) {

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