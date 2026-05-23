
package vektra.View;

import java.awt.BorderLayout;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.JPanel;

public class MapaView extends JPanel  {
     private JFXPanel jfxPanel;
     
     public MapaView(){
         setLayout(new BorderLayout());
         jfxPanel = new JFXPanel();
         add (jfxPanel, BorderLayout.CENTER);
         Platform.runLater(()-> { initFX();});
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