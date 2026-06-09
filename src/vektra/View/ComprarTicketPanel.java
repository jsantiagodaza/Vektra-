package vektra.View;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.StringConverter;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import vektra.Dao.PasajeroDao;
import vektra.Dao.RutaDao;
import vektra.Model.Estacion;
import vektra.Model.Pasajero;
import vektra.Model.Ruta;
import vektra.Service.TicketService;

/**
 * Panel de compra de ticket migrado a JavaFX para un diseño más moderno.
 * @author santi
 */
public class ComprarTicketPanel extends JPanel {

    private final JFXPanel jfxPanel;
    private List<Estacion> estaciones = new ArrayList<>();

    public ComprarTicketPanel() {
        setLayout(new BorderLayout());
        jfxPanel = new JFXPanel();
        add(jfxPanel, BorderLayout.CENTER);

        // Inicializar JavaFX en su propio hilo
        Platform.runLater(this::initFX);
    }

    private void initFX() {
        // --- Contenedor Principal (Fondo Degradado Moderno) ---
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e0f2fe, #ffffff);");

        // --- Tarjeta Central Blanca ---
        VBox card = new VBox(15);
        card.setAlignment(Pos.TOP_CENTER);
        card.setMaxWidth(500);
        card.setMaxHeight(Region.USE_PREF_SIZE);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-padding: 40;");

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(25);
        dropShadow.setOffsetY(10);
        dropShadow.setColor(Color.color(0, 0, 0, 0.4));
        card.setEffect(dropShadow);

        // --- Título ---
        Label lblTitle = new Label("Comprar un Ticket");
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        lblTitle.setTextFill(Color.web("#333333"));

        Label lblSubtitle = new Label("Completa tus datos para generar tu ticket");
        lblSubtitle.setFont(Font.font("Segoe UI", 14));
        lblSubtitle.setTextFill(Color.web("#888888"));

        VBox headerBox = new VBox(5, lblTitle, lblSubtitle);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(0, 0, 15, 0));

        // --- Campos del Formulario ---
        TextField txtNombre = createModernTextField("Nombre Completo (Ej. Juan Pérez)");
        TextField txtId = createModernTextField("Identificación (C.C / C.E / T.I)");
        TextField txtEmail = createModernTextField("Correo Electrónico");
        TextField txtFechaNac = createModernTextField("Fecha Nacimiento (DD/MM/AAAA)");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña de Usuario");
        styleModernTextField(txtPassword);

        // --- ComboBoxes ---
        ComboBox<Estacion> cmbOrigen = new ComboBox<>();
        ComboBox<Estacion> cmbDestino = new ComboBox<>();
        setupComboBox(cmbOrigen, "Seleccionar estación origen");
        setupComboBox(cmbDestino, "Seleccionar estación destino");

        HBox comboHBox = new HBox(15, cmbOrigen, cmbDestino);
        comboHBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(cmbOrigen, Priority.ALWAYS);
        HBox.setHgrow(cmbDestino, Priority.ALWAYS);
        cmbOrigen.setMaxWidth(Double.MAX_VALUE);
        cmbDestino.setMaxWidth(Double.MAX_VALUE);

        // --- Botón de Acción ---
        Button btnGenerar = new Button("Generar Ticket");
        btnGenerar.setMaxWidth(Double.MAX_VALUE);
        btnGenerar.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        btnGenerar.setStyle("-fx-background-color: #37B9D6; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 12;");
        btnGenerar.setCursor(javafx.scene.Cursor.HAND);

        btnGenerar.setOnMouseEntered(e -> btnGenerar.setStyle("-fx-background-color: #2da1bb; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 12;"));
        btnGenerar.setOnMouseExited(e -> btnGenerar.setStyle("-fx-background-color: #37B9D6; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 12;"));

        // --- Carga de Datos ---
        cargarEstaciones(cmbOrigen, cmbDestino);

        // --- Validaciones Estéticas Reactivas ---
        validacionesdeConfiguracion(txtNombre, txtId, txtEmail, txtFechaNac, txtPassword, cmbOrigen, cmbDestino);

        // --- Acción del botón ---
        btnGenerar.setOnAction(e -> {
            if (validarTodos(txtNombre, txtId, txtEmail, txtFechaNac, txtPassword, cmbOrigen, cmbDestino)) {
                procesarCompra(txtNombre.getText(), txtId.getText(), txtEmail.getText(), txtPassword.getText(),
                        cmbOrigen.getValue(), cmbDestino.getValue());
                
                // Limpiar después del éxito
                txtNombre.clear(); txtId.clear(); txtEmail.clear(); txtFechaNac.clear(); txtPassword.clear();
                cmbOrigen.getSelectionModel().selectFirst();
                cmbDestino.getSelectionModel().selectFirst();
                resetStyle(txtNombre, txtId, txtEmail, txtFechaNac, txtPassword);
            } else {
                SwingUtilities.invokeLater(() -> new ERRORview().setVisible(true));
            }
        });

        // --- Ensamblaje ---
        card.getChildren().addAll(
                headerBox,
                txtNombre,
                txtId,
                txtEmail,
                txtFechaNac,
                comboHBox,
                txtPassword,
                btnGenerar
        );

        root.getChildren().add(card);
        Scene scene = new Scene(root, 800, 600);
        jfxPanel.setScene(scene);
    }

    private TextField createModernTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        styleModernTextField(tf);
        return tf;
    }

    private void styleModernTextField(TextField tf) {
        tf.setFont(Font.font("Segoe UI", 14));
        tf.setStyle("-fx-background-color: #F3F4F6; -fx-background-radius: 8; -fx-padding: 12; -fx-text-fill: #333333; -fx-prompt-text-fill: #9CA3AF;");

        tf.focusedProperty().addListener((obs, oldV, newV) -> {
            Boolean isValid = (Boolean) tf.getProperties().get("valido");
            if (isValid == null) return; // Aún no ha sido validado activamente

            if (newV) {
                if (!isValid) {
                    tf.setStyle("-fx-background-color: #FEF2F2; -fx-border-color: #EF4444; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 11; -fx-text-fill: #991B1B;");
                } else {
                    tf.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #37B9D6; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 11; -fx-text-fill: #333333;");
                }
            } else {
                marcarCampo(tf, isValid);
            }
        });
    }

    private void resetStyle(TextField... fields) {
        for (TextField tf : fields) {
            tf.getProperties().put("valido", null);
            tf.setStyle("-fx-background-color: #F3F4F6; -fx-background-radius: 8; -fx-padding: 12; -fx-text-fill: #333333; -fx-prompt-text-fill: #9CA3AF;");
        }
    }

    private void marcarCampo(TextField tf, boolean valido) {
        tf.getProperties().put("valido", valido);
        if (valido) {
            tf.setStyle("-fx-background-color: #F0FDF4; -fx-border-color: #10B981; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 11; -fx-text-fill: #065F46;");
        } else {
            tf.setStyle("-fx-background-color: #FEF2F2; -fx-border-color: #EF4444; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 11; -fx-text-fill: #991B1B;");
        }
    }

    private void setupComboBox(ComboBox<Estacion> cmb, String prompt) {
        cmb.setPromptText(prompt);
        cmb.setStyle("-fx-background-color: #F3F4F6; -fx-background-radius: 8; -fx-padding: 5; -fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
        cmb.setConverter(new StringConverter<Estacion>() {
            @Override
            public String toString(Estacion object) {
                return object != null && object.getNombre() != null ? object.getNombre() : prompt;
            }

            @Override
            public Estacion fromString(String string) {
                return null;
            }
        });
    }

    private void validacionesdeConfiguracion(TextField txtNombre, TextField txtId, TextField txtEmail, TextField txtFechaNac, PasswordField txtPassword, ComboBox<Estacion> cmbOrigen, ComboBox<Estacion> cmbDestino) {
        txtNombre.textProperty().addListener((obs, o, n) -> {
            marcarCampo(txtNombre, !n.trim().isEmpty() && n.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"));
        });

        txtId.textProperty().addListener((obs, o, n) -> {
            marcarCampo(txtId, !n.trim().isEmpty() && n.matches("\\d{6,12}"));
        });

        txtEmail.textProperty().addListener((obs, o, n) -> {
            marcarCampo(txtEmail, !n.trim().isEmpty() && n.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"));
        });

        // Formato automático de fecha DD/MM/AAAA
        txtFechaNac.textProperty().addListener((obs, o, n) -> {
            if (n.length() > o.length()) { // Si está escribiendo
                if (n.length() == 2 || n.length() == 5) {
                    txtFechaNac.setText(n + "/");
                }
            }
            if (n.length() > 10) {
                txtFechaNac.setText(o); // Limitar a 10 caracteres
                return;
            }

            boolean valido = false;
            if (n.matches("\\d{2}/\\d{2}/\\d{4}")) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate dob = LocalDate.parse(n, formatter);
                    int edad = Period.between(dob, LocalDate.now()).getYears();
                    valido = !dob.isAfter(LocalDate.now()) && edad >= 0 && edad < 150;
                } catch (Exception ex) {
                    valido = false;
                }
            }
            marcarCampo(txtFechaNac, valido);
        });

        txtPassword.textProperty().addListener((obs, o, n) -> {
            marcarCampo(txtPassword, !n.trim().isEmpty() && n.length() >= 6);
        });

        cmbOrigen.valueProperty().addListener((obs, o, n) -> {
            actualizarEstacionesDestino(cmbOrigen, cmbDestino);
        });
    }

    private boolean validarTodos(TextField txtNombre, TextField txtId, TextField txtEmail, TextField txtFechaNac, PasswordField txtPassword, ComboBox<Estacion> cmbOrigen, ComboBox<Estacion> cmbDestino) {
        Boolean nVal = (Boolean) txtNombre.getProperties().get("valido");
        Boolean iVal = (Boolean) txtId.getProperties().get("valido");
        Boolean eVal = (Boolean) txtEmail.getProperties().get("valido");
        Boolean fVal = (Boolean) txtFechaNac.getProperties().get("valido");
        Boolean pVal = (Boolean) txtPassword.getProperties().get("valido");

        boolean fieldsValid = (nVal != null && nVal) && (iVal != null && iVal) && (eVal != null && eVal) && (fVal != null && fVal) && (pVal != null && pVal);
        boolean combosValid = cmbOrigen.getValue() != null && cmbOrigen.getValue().getId() != null && !cmbOrigen.getValue().getId().isEmpty()
                && cmbDestino.getValue() != null && cmbDestino.getValue().getId() != null && !cmbDestino.getValue().getId().isEmpty();

        return fieldsValid && combosValid;
    }

    private void cargarEstaciones(ComboBox<Estacion> cmbOrigen, ComboBox<Estacion> cmbDestino) {
        try {
            RutaDao dao = new RutaDao();
            List<Ruta> rutas = dao.obtenerTodasLasRutas();
            Map<String, Estacion> mapa = new LinkedHashMap<>();
            for (Ruta ruta : rutas) {
                if (ruta.getOrigen() != null && ruta.getOrigen().getId() != null) {
                    mapa.put(ruta.getOrigen().getId(), ruta.getOrigen());
                }
                if (ruta.getDestino() != null && ruta.getDestino().getId() != null) {
                    mapa.put(ruta.getDestino().getId(), ruta.getDestino());
                }
            }
            estaciones = new ArrayList<>(mapa.values());
            
            Estacion placeholder = new Estacion("", "Seleccionar estación");
            cmbOrigen.getItems().add(placeholder);
            cmbOrigen.getItems().addAll(estaciones);
            cmbOrigen.getSelectionModel().selectFirst();
            
            actualizarEstacionesDestino(cmbOrigen, cmbDestino);
        } catch (Exception e) {
            System.out.println("Error al cargar estaciones: " + e.getMessage());
        }
    }

    private void actualizarEstacionesDestino(ComboBox<Estacion> cmbOrigen, ComboBox<Estacion> cmbDestino) {
        Estacion seleccionado = cmbOrigen.getValue();
        cmbDestino.getItems().clear();
        Estacion placeholder = new Estacion("", "Seleccionar estación");
        cmbDestino.getItems().add(placeholder);

        for (Estacion estacion : estaciones) {
            if (seleccionado == null || !estacion.getId().equals(seleccionado.getId())) {
                cmbDestino.getItems().add(estacion);
            }
        }
        cmbDestino.getSelectionModel().selectFirst();
    }

    private void procesarCompra(String nombre, String id, String email, String contrasena, Estacion origen, Estacion destino) {
        try {
            PasajeroDao pasajeroDao = new PasajeroDao();
            Pasajero pasajero = new Pasajero();
            pasajero.setId(id);
            pasajero.setNombre(nombre);
            pasajero.setEmail(email);
            pasajero.setContraseña(contrasena);
            pasajero.setFechaRegistro(java.time.LocalDateTime.now());

            // Guardar pasajero si es nuevo
            try {
                pasajeroDao.registrarPasajero(pasajero);
            } catch (Exception e) {
                System.out.println("Pasajero puede ya existir: " + e.getMessage());
            }

            // Crear ticket
            double precio = 2500; // Precio base por defecto
            TicketService ticketService = new TicketService();
            ticketService.crearTicket(id, pasajero, origen, destino, precio);
            

            // Mostrar confirmación en el hilo de Swing
            SwingUtilities.invokeLater(() -> new Confirmacion().setVisible(true));

        } catch (Exception e) {
            System.err.println("Error al generar ticket: " + e.getMessage());
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> new ERRORview().setVisible(true));
        }
    }   
}
