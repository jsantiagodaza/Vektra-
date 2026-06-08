package vektra.View;

import java.awt.BorderLayout;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.swing.JPanel;

/**
 * Panel central del Dashboard conectado a MainFrameView.
 * Utiliza JavaFX Bento Grid con tema claro.
 */
public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        setLayout(new BorderLayout());
        
        JFXPanel jfxPanel = new JFXPanel();
        add(jfxPanel, BorderLayout.CENTER);
        
        Platform.runLater(() -> initBentoGrid(jfxPanel));
    }

    private void initBentoGrid(JFXPanel jfxPanel) {
        GridPane grid = new GridPane();
        // Fondo base blanco como solicitaste
        grid.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 40;");
        grid.setHgap(20);
        grid.setVgap(20);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33.33);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(33.33);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33.33);
        grid.getColumnConstraints().addAll(col1, col2, col3);

        // Tarjeta 1: Bienvenida
        VBox cardWelcome = createBentoCard();
        Label lblWelcome = new Label("Bienvenido a Vektra");
        lblWelcome.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));
        lblWelcome.setTextFill(Color.web("#333333"));
        Label lblDesc = new Label("Resumen general del sistema y estadísticas clave de hoy.");
        lblDesc.setFont(Font.font("Segoe UI", 16));
        lblDesc.setTextFill(Color.web("#888888"));
        cardWelcome.getChildren().addAll(lblWelcome, lblDesc);
        grid.add(cardWelcome, 0, 0, 2, 1);

        // Tarjeta 2: Pasajeros (Gris oscuro)
        VBox cardPasajeros = createBentoCard();
        Label lblPasajerosVal = new Label("0");
        lblPasajerosVal.setFont(Font.font("Segoe UI", FontWeight.BOLD, 54));
        lblPasajerosVal.setTextFill(Color.web("#4A4A4A")); // Gris oscuro
        Label lblPasajerosTit = new Label("Pasajeros en Circulación");
        lblPasajerosTit.setFont(Font.font("Segoe UI", 18));
        lblPasajerosTit.setTextFill(Color.web("#666666"));
        cardPasajeros.getChildren().addAll(lblPasajerosVal, lblPasajerosTit);
        grid.add(cardPasajeros, 2, 0, 1, 1);

        // Tarjeta 3: Estaciones (Verde suave oscuro)
        VBox cardEstaciones = createBentoCard();
        Label lblEstacionesVal = new Label("0");
        lblEstacionesVal.setFont(Font.font("Segoe UI", FontWeight.BOLD, 54));
        lblEstacionesVal.setTextFill(Color.web("#2E7D32")); // Verde suave oscuro
        Label lblEstacionesTit = new Label("Estaciones Activas");
        lblEstacionesTit.setFont(Font.font("Segoe UI", 18));
        lblEstacionesTit.setTextFill(Color.web("#666666"));
        cardEstaciones.getChildren().addAll(lblEstacionesVal, lblEstacionesTit);
        grid.add(cardEstaciones, 0, 1, 1, 1);

        // Tarjeta 4: Tickets (Azul)
        VBox cardTickets = createBentoCard();
        Label lblTicketsVal = new Label("0");
        lblTicketsVal.setFont(Font.font("Segoe UI", FontWeight.BOLD, 54));
        lblTicketsVal.setTextFill(Color.web("#0263C8")); // Azul Vektra
        Label lblTicketsTit = new Label("Tickets Vendidos Hoy");
        lblTicketsTit.setFont(Font.font("Segoe UI", 18));
        lblTicketsTit.setTextFill(Color.web("#666666"));
        cardTickets.getChildren().addAll(lblTicketsVal, lblTicketsTit);
        grid.add(cardTickets, 1, 1, 1, 1);

        // Tarjeta 5: Líneas Activas
        VBox cardLineas = createBentoCard();
        Label lblLineasTit = new Label("Estado de las Líneas");
        lblLineasTit.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblLineasTit.setTextFill(Color.web("#333333"));
        cardLineas.getChildren().add(lblLineasTit);
        
        String[][] lineasData = {
            {"Línea Roja Activa", "#E50822"},
            {"Línea Amarilla Activa", "#F39C12"},
            {"Línea Verde Activa", "#27AE60"},
            {"Línea Azul Activa", "#2980B9"}
        };
        for (String[] ld : lineasData) {
            HBox hbLinea = new HBox(10);
            hbLinea.setAlignment(Pos.CENTER_LEFT);
            Circle dot = new Circle(6, Color.web(ld[1]));
            Label lName = new Label(ld[0]);
            lName.setTextFill(Color.web("#555555"));
            lName.setFont(Font.font("Segoe UI", 16));
            hbLinea.getChildren().addAll(dot, lName);
            cardLineas.getChildren().add(hbLinea);
        }
        grid.add(cardLineas, 2, 1, 1, 1);
        
        GridPane.setVgrow(cardWelcome, Priority.ALWAYS);
        GridPane.setVgrow(cardPasajeros, Priority.ALWAYS);
        GridPane.setVgrow(cardEstaciones, Priority.ALWAYS);
        GridPane.setVgrow(cardTickets, Priority.ALWAYS);
        GridPane.setVgrow(cardLineas, Priority.ALWAYS);

        Scene scene = new Scene(grid);
        jfxPanel.setScene(scene);
    }

    private VBox createBentoCard() {
        VBox card = new VBox(15);
        // Gris claro de fondo para las cards
        card.setStyle("-fx-background-color: #F3F4F6; -fx-background-radius: 15; -fx-padding: 30;");
        card.setAlignment(Pos.CENTER_LEFT);
        
        // Suave contorno iluminado en hover
        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-padding: 30; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 4);"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: #F3F4F6; -fx-background-radius: 15; -fx-padding: 30;"));
        
        return card;
    }
}
