
package vektra.View;

import javafx.application.Application;

import javafx.scene.Scene;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javafx.stage.Stage;

public class MapaView extends Application {

    @Override
    public void start(Stage stage) {

        WebView webView =
                new WebView();

        WebEngine engine =
                webView.getEngine();

        // Cargar HTML
        engine.load(

            getClass()

            .getResource(
                    "/resources/mapa.html"
            )

            .toExternalForm()
        );

        Scene scene =
                new Scene(
                        webView,
                        1200,
                        700
                );

        stage.setTitle("Mapa Metro");

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}