
package vektra.View;

import java.awt.BorderLayout;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MapaView extends JPanel  {
     private JFXPanel jfxPanel;
     private JList<String> listaRutas; 
     
     public MapaView(){
         setLayout(new BorderLayout());
         jfxPanel = new JFXPanel();
         add (jfxPanel, BorderLayout.CENTER);
         listaRutas = new JList<>();
         JScrollPane scroll = new JScrollPane(listaRutas);
         add(scroll, BorderLayout.EAST);
         Platform.runLater(this::initFX);
     }

    private void initFX(){
         WebView webView = new WebView();
         WebEngine engine = webView.getEngine();
        
         String url = getClass().getResource("/resources/mapa.html").toExternalForm();
         engine.load(url);
         Scene scene=new Scene(webView);
         jfxPanel.setScene(scene);
    }
    
}