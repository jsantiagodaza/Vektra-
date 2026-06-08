package vektra.View;

import java.awt.BorderLayout;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import vektra.Dao.PasajeroDao;
import vektra.Model.Pasajero;

/**
 * LoginFrame migrado a JavaFX con estilo moderno de dos columnas.
 * @author santi
 */
public class LoginFrame extends JFrame {
    
    private static final Logger logger = Logger.getLogger(LoginFrame.class.getName());
    private final JFXPanel jfxPanel;

    public LoginFrame() {
        setTitle("Vektra - Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(2780, 1920);
        setLocationRelativeTo(null);
        
        jfxPanel = new JFXPanel();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jfxPanel, BorderLayout.CENTER);
        
        Platform.runLater(this::initFX);
    }

    private void initFX() {
        // Contenedor principal: 2 columnas
        HBox root = new HBox();
        root.setStyle("-fx-background-color: white;");

        // --- Columna Izquierda (Oscura con Logo) ---
        VBox leftCol = new VBox(20);
        leftCol.setAlignment(Pos.CENTER);
        leftCol.prefWidthProperty().bind(root.widthProperty().multiply(0.35));
        // Un degradado oscuro sutil para la columna izquierda
        leftCol.setStyle("-fx-background-color: linear-gradient(to bottom, #1d1a1a, #0f0e0e);");
        
        ImageView logoView = new ImageView();
        try {
            URL logoUrl = getClass().getResource("/vektra/View/Imagenes/ICONS CANVA/VektraLogo (1).png");
            if (logoUrl != null) {
                Image logoImage = new Image(logoUrl.toExternalForm());
                logoView.setImage(logoImage);
                logoView.setFitWidth(350);
                logoView.setPreserveRatio(true);
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "No se pudo cargar el logo de Vektra", e);
        }
        
        Label lblBrand = new Label("Vektra");
        lblBrand.setFont(Font.font("Segoe UI", FontWeight.BOLD, 42));
        lblBrand.setTextFill(Color.WHITE);
        
        leftCol.getChildren().addAll(logoView, lblBrand);

        // --- Columna Derecha (Formulario Blanco) ---
        StackPane rightColContainer = new StackPane();
        HBox.setHgrow(rightColContainer, Priority.ALWAYS);
        
        // Contenedor del formulario (centrado)
        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER_LEFT);
        formBox.setMaxWidth(500);
        formBox.setPadding(new Insets(40, 40, 40, 40));
        
        // Títulos
        Label lblTitle = new Label("Iniciar sesión");
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 30));
        lblTitle.setTextFill(Color.web("#333333"));
        
        Label lblSubtitle = new Label("Accede a Vektra");
        lblSubtitle.setFont(Font.font("Segoe UI", 16));
        lblSubtitle.setTextFill(Color.web("#888888"));
        
        VBox titleBox = new VBox(5, lblTitle, lblSubtitle);
        titleBox.setPadding(new Insets(0, 0, 20, 0));

        // Campos
        Label lblTipo = createInputLabel("Tipo de usuario:");
        ComboBox<String> cmbTipoUsuario = new ComboBox<>();
        cmbTipoUsuario.getItems().addAll("Pasajero Regular", "Administrador");
        cmbTipoUsuario.getSelectionModel().selectFirst();
        cmbTipoUsuario.setMaxWidth(Double.MAX_VALUE);
        cmbTipoUsuario.setStyle("-fx-background-color: #F3F4F6; -fx-background-radius: 8; -fx-padding: 8; -fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");

        Label lblEmail = createInputLabel("Correo Electrónico:");
        TextField txtEmail = createModernTextField("ejemplo@correo.com");
        
        Label lblPassword = createInputLabel("Contraseña:");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Tu contraseña secreta");
        styleModernTextField(txtPassword);

        // Validaciones reactivas
        setupValidations(txtEmail, txtPassword);

        // Botones
        Button btnEntrar = new Button("Entrar");
        btnEntrar.setMaxWidth(Double.MAX_VALUE);
        btnEntrar.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        btnEntrar.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 12;");
        btnEntrar.setCursor(Cursor.HAND);
        
        btnEntrar.setOnMouseEntered(e -> btnEntrar.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 12;"));
        btnEntrar.setOnMouseExited(e -> btnEntrar.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 12;"));

        // Enlace para crear cuenta
        VBox registerBox = new VBox(10);
        registerBox.setAlignment(Pos.CENTER);
        registerBox.setPadding(new Insets(20, 0, 0, 0));
        
        Label lblNoAccount = new Label("¿No tienes una cuenta?, crea una:");
        lblNoAccount.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        lblNoAccount.setTextFill(Color.web("#0066CC"));
        
        Button btnCrearCuenta = new Button("Crear Cuenta");
        btnCrearCuenta.setMaxWidth(Double.MAX_VALUE);
        btnCrearCuenta.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        btnCrearCuenta.setStyle("-fx-background-color: #0263C8; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 10;");
        btnCrearCuenta.setCursor(Cursor.HAND);
        
        btnCrearCuenta.setOnMouseEntered(e -> btnCrearCuenta.setStyle("-fx-background-color: #0251a3; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 10;"));
        btnCrearCuenta.setOnMouseExited(e -> btnCrearCuenta.setStyle("-fx-background-color: #0263C8; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 10;"));
        
        registerBox.getChildren().addAll(lblNoAccount, btnCrearCuenta);

        // Ensamblaje formulario
        formBox.getChildren().addAll(
            titleBox,
            lblTipo, cmbTipoUsuario,
            lblEmail, txtEmail,
            lblPassword, txtPassword,
            new Region(), // Spacer
            btnEntrar,
            registerBox
        );
        
        // Imagen decorativa del osito en la esquina inferior derecha
        ImageView bearView = new ImageView();
        try {
            URL bearUrl = getClass().getResource("/vektra/View/Imagenes/ICONS CANVA/VIKTOR BN (2) (2).png");
            if (bearUrl != null) {
                Image bearImage = new Image(bearUrl.toExternalForm());
                bearView.setImage(bearImage);
                bearView.fitWidthProperty().bind(root.heightProperty().multiply(0.35));
                bearView.setPreserveRatio(true);
            }
        } catch (Exception e) {
            // Ignorar si no existe
        }
        
        StackPane.setAlignment(bearView, Pos.BOTTOM_RIGHT);
        
        rightColContainer.getChildren().addAll(formBox, bearView);
        root.getChildren().addAll(leftCol, rightColContainer);

        // Lógica de los botones
        btnEntrar.setOnAction(e -> handleLogin(txtEmail, txtPassword, cmbTipoUsuario));
        
        btnCrearCuenta.setOnAction(e -> {
            SwingUtilities.invokeLater(() -> {
                CrearCuentaFrame crearCuenta = new CrearCuentaFrame();
                crearCuenta.setLocationRelativeTo(null);
                crearCuenta.setVisible(true);
                this.dispose();
            });
        });

        Scene scene = new Scene(root);
        jfxPanel.setScene(scene);
    }
    
    private Label createInputLabel(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        lbl.setTextFill(Color.web("#666666"));
        return lbl;
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
            if (isValid == null) return; 

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

    private void setupValidations(TextField txtEmail, PasswordField txtPassword) {
        txtEmail.textProperty().addListener((obs, o, n) -> {
            marcarCampo(txtEmail, !n.trim().isEmpty() && n.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"));
        });

        txtPassword.textProperty().addListener((obs, o, n) -> {
            marcarCampo(txtPassword, !n.trim().isEmpty() && n.length() >= 4);
        });
    }

    private void marcarCampo(TextField tf, boolean valido) {
        tf.getProperties().put("valido", valido);
        if (valido) {
            tf.setStyle("-fx-background-color: #F0FDF4; -fx-border-color: #10B981; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 11; -fx-text-fill: #065F46;");
        } else {
            tf.setStyle("-fx-background-color: #FEF2F2; -fx-border-color: #EF4444; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 11; -fx-text-fill: #991B1B;");
        }
    }

    private boolean validarTodos(TextField txtEmail, PasswordField txtPassword) {
        Boolean eVal = (Boolean) txtEmail.getProperties().get("valido");
        Boolean pVal = (Boolean) txtPassword.getProperties().get("valido");
        return (eVal != null && eVal) && (pVal != null && pVal);
    }

    private void handleLogin(TextField txtEmail, PasswordField txtPassword, ComboBox<String> cmbTipoUsuario) {
        // Forzar validación manual por si no han escrito nada
        if (txtEmail.getText() == null || txtEmail.getText().isEmpty()) marcarCampo(txtEmail, false);
        if (txtPassword.getText() == null || txtPassword.getText().isEmpty()) marcarCampo(txtPassword, false);

        if (!validarTodos(txtEmail, txtPassword)) {
            SwingUtilities.invokeLater(() -> new ERRORview().setVisible(true));
            return;
        }

        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        boolean isAdmin = "Administrador".equals(cmbTipoUsuario.getValue());

        if (isAdmin) {
            if ("admin@admin.com".equals(email) && "adminadmin".equals(password)) {
                abrirMainFrame(true);
            } else {
                SwingUtilities.invokeLater(() -> new ERRORview().setVisible(true));
            }
        } else {
            PasajeroDao dao = new PasajeroDao();
            Pasajero pasajero = dao.autenticar(email, password);
            if (pasajero != null) {
                abrirMainFrame(false);
            } else {
                SwingUtilities.invokeLater(() -> new ERRORview().setVisible(true));
            }
        }
    }

    private void abrirMainFrame(boolean isAdmin) {
        SwingUtilities.invokeLater(() -> {
            MainFrameView main = new MainFrameView(isAdmin);
            main.setLocationRelativeTo(null);
            main.setVisible(true);
            this.dispose();
        });
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
