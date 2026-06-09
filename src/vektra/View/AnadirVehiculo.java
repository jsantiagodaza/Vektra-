package vektra.View;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import vektra.Dao.ConductorDao;
import vektra.Model.Conductor;

/**
 * AnadirVehiculo — formulario estilizado Vektra. Swing puro, sin NetBeans Form
 * Editor.
 */
public class AnadirVehiculo extends JPanel {

    // ── Paleta ────────────────────────────────────────────────────────────────
    private static final Color fondo_Pagina = new Color(248, 250, 255);
    private static final Color Fondo_Card = Color.WHITE;
    private static final Color NAVY = new Color(15, 23, 42);
    private static final Color Azul_Acentuado = new Color(37, 99, 235);
    private static final Color HoverAzul = new Color(29, 78, 216);
    private static final Color AzulClaro = new Color(239, 246, 255);
    private static final Color Borde_Azul = new Color(191, 219, 254);
    private static final Color texto_Principal = new Color(15, 23, 42);
    private static final Color text_Multi = new Color(100, 116, 139);
    private static final Color textLabel = new Color(71, 85, 105);
    private static final Color colorBorde = new Color(226, 232, 240);
    private static final Color Campo_Background = new Color(249, 250, 251);
    private static final Color FIELD_PH = new Color(148, 163, 184);
    private static final Color COLOR_OK = new Color(34, 197, 94);
    private static final Color COLOR_ERROR = new Color(239, 68, 68);
    private static final Color background_Advertencia = new Color(255, 247, 237);
    private static final Color WARN_BRD = new Color(254, 215, 170);
    private static final Color WARN_FG = new Color(154, 52, 18);

    // ── Fuentes ───────────────────────────────────────────────────────────────
    private static final Font F_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font F_SUB = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font F_SECTION = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font F_LABEL = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font F_FIELD = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font F_BTN = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font F_WARN = new Font("Segoe UI", Font.PLAIN, 12);

    // ── Campos ────────────────────────────────────────────────────────────────
    private JTextField txtPlaca;
    private JTextField txtAnio;
    private JComboBox<String> cmbLineas;
    private JComboBox<String> cmbCapacidad;
    private JComboBox<Conductor> cmbConductores;
    private JButton btnAnadir;

    // ─────────────────────────────────────────────────────────────────────────
    public AnadirVehiculo() {
        inicializarUI();
        vektra.Util.FontUtil.applyCustomFont(this);
        cargarConductores();
        initPlaceholders();
        initValidaciones();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Layout principal
    // ─────────────────────────────────────────────────────────────────────────
    private void inicializarUI() {
        setLayout(new BorderLayout());
        setBackground(fondo_Pagina);

        // Scroll sobre todo el contenido
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(fondo_Pagina);
        content.setBorder(new EmptyBorder(28, 36, 32, 36));

        content.add(crearHeader());
        content.add(Box.createVerticalStrut(16));
        content.add(crearBannerObligatorio());
        content.add(Box.createVerticalStrut(24));
        content.add(crearSeccion("Identificación", crearFilaIdentificacion()));
        content.add(Box.createVerticalStrut(24));
        content.add(crearSeccion("Asignación", crearFilaAsignacion()));
        content.add(Box.createVerticalStrut(32));
        content.add(crearBotonAnadir());

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(scroll, BorderLayout.CENTER);
    }

    // ── Header ────────────────────────────────────────────────────────────────
    private JPanel crearHeader() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("Añadir Vehículo");
        title.setFont(F_TITLE);
        title.setForeground(texto_Principal);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Registra un nuevo vehículo en la flota del sistema");
        sub.setFont(F_SUB);
        sub.setForeground(text_Multi);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(title);
        p.add(Box.createVerticalStrut(4));
        p.add(sub);
        return p;
    }

    // ── Banner campos obligatorios ────────────────────────────────────────────
    private JPanel crearBannerObligatorio() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(background_Advertencia);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(WARN_BRD);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel icon = new JLabel("*");
        icon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        icon.setForeground(new Color(217, 119, 6));

        JLabel msg = new JLabel("Todos los campos son obligatorios");
        msg.setFont(F_WARN);
        msg.setForeground(WARN_FG);

        p.add(icon);
        p.add(msg);
        return p;
    }

    // ── Sección con título y contenido ────────────────────────────────────────
    private JPanel crearSeccion(String titulo, JPanel contenido) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Línea de título de sección
        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setOpaque(false);
        headerRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        headerRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(titulo);
        lbl.setFont(F_SECTION);
        lbl.setForeground(textLabel);

        // Línea divisora
        JSeparator sep = new JSeparator() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(colorBorde);
                g2.fillRect(0, getHeight() / 2, getWidth(), 1);
                g2.dispose();
            }
        };

        headerRow.add(lbl, BorderLayout.WEST);
        headerRow.add(sep, BorderLayout.CENTER);

        contenido.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(headerRow);
        p.add(Box.createVerticalStrut(14));
        p.add(contenido);
        return p;
    }

    // ── Fila Identificación: Placa + Año ──────────────────────────────────────
    private JPanel crearFilaIdentificacion() {
        JPanel row = new JPanel(new GridLayout(1, 2, 16, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtPlaca = new JTextField();
        txtAnio = new JTextField();

        row.add(crearCampoTexto("Placa / Matrícula", txtPlaca));
        row.add(crearCampoTexto("Año de Fabricación", txtAnio));
        return row;
    }

    // ── Fila Asignación: Línea + Capacidad + Conductor ───────────────────────
    private JPanel crearFilaAsignacion() {
        JPanel row = new JPanel(new GridLayout(1, 3, 16, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        cmbLineas = new JComboBox<>(new String[]{
            "Línea Roja", "Línea Azul", "Línea Amarilla", "Línea Verde"
        });
        cmbCapacidad = new JComboBox<>(new String[]{
            "100 Pasajeros", "200 Pasajeros", "300 Pasajeros", "400 Pasajeros"
        });
        cmbConductores = new JComboBox<>();

        row.add(crearCampoCombo("Línea Asignada", cmbLineas));
        row.add(crearCampoCombo("Capacidad", cmbCapacidad));
        row.add(crearCampoCombo("Conductor", cmbConductores));
        return row;
    }

    // ── Botón principal ───────────────────────────────────────────────────────
    private JPanel crearBotonAnadir() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnAnadir = new JButton("Añadir Vehículo") {
            private boolean hov = false;

            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        hov = true;
                        repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        hov = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? HoverAzul : Azul_Acentuado);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            public boolean isOpaque() {
                return false;
            }
        };
        btnAnadir.setFont(F_BTN);
        btnAnadir.setForeground(Color.WHITE);
        btnAnadir.setContentAreaFilled(false);
        btnAnadir.setBorderPainted(false);
        btnAnadir.setFocusPainted(false);
        btnAnadir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAnadir.setBorder(new EmptyBorder(11, 28, 11, 28));
        btnAnadir.setPreferredSize(new Dimension(220, 44));
        btnAnadir.setOpaque(false);

        // Ícono bus
        try {
            java.net.URL url = getClass().getResource("/vektra/View/Imagenes/ICONS CANVA/ADD.png");
            if (url != null) {
                Image img = new ImageIcon(url).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
                btnAnadir.setIcon(new ImageIcon(img));
                btnAnadir.setIconTextGap(8);
            }
        } catch (Exception ignored) {
        }

        btnAnadir.addActionListener(e -> btnAnadirVehiculoActionPerformed());

        p.add(btnAnadir);
        return p;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers: campo de texto y combo estilizados
    // ─────────────────────────────────────────────────────────────────────────
    private JPanel crearCampoTexto(String etiqueta, JTextField campo) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(F_LABEL);
        lbl.setForeground(textLabel);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        campo.setFont(F_FIELD);
        campo.setForeground(texto_Principal);
        campo.setBackground(Campo_Background);
        campo.setCaretColor(texto_Principal);
        campo.setBorder(BorderFactory.createCompoundBorder(new RoundedBorder(8, colorBorde, 1),
                new EmptyBorder(7, 12, 7, 12)
        ));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(lbl);
        p.add(Box.createVerticalStrut(5));
        p.add(campo);
        return p;
    }

    private JPanel crearCampoCombo(String etiqueta, JComboBox<?> combo) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(F_LABEL);
        lbl.setForeground(textLabel);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        combo.setFont(F_FIELD);
        combo.setForeground(texto_Principal);
        combo.setBackground(Campo_Background);
        combo.setBorder(new RoundedBorder(8, colorBorde, 1));
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);
        combo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Renderer limpio
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object v, int idx, boolean sel, boolean foc) {
                JLabel l = (JLabel) super.getListCellRendererComponent(list, v, idx, sel, foc);
                l.setBorder(new EmptyBorder(5, 10, 5, 10));
                l.setFont(F_FIELD);
                if (sel) {
                    l.setBackground(AzulClaro);
                    l.setForeground(Azul_Acentuado);
                } else {
                    l.setBackground(Color.WHITE);
                    l.setForeground(texto_Principal);
                }
                return l;
            }
        });

        p.add(lbl);
        p.add(Box.createVerticalStrut(5));
        p.add(combo);
        return p;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Lógica preservada del original
    // ─────────────────────────────────────────────────────────────────────────
    private void cargarConductores() {
        try {
            ConductorDao dao = new ConductorDao();
            List<Conductor> lista = dao.obtenerTodos();
            cmbConductores.removeAllItems();
            cmbConductores.addItem(new Conductor() {
                @Override
                public String toString() {
                    return "Seleccionar conductor...";
                }
            });
            for (Conductor c : lista) {
                cmbConductores.addItem(c);
            }
            cmbConductores.setSelectedIndex(0);
        } catch (Exception e) {
            System.out.println("Error al cargar conductores: " + e.getMessage());
        }
    }

    private void initPlaceholders() {
        configurarPlaceholder(txtPlaca, "Ej. VTRAIN-928");
        configurarPlaceholder(txtAnio, "Ej. 2023");
    }

    private void configurarPlaceholder(JTextField campo, String ph) {
        campo.setText(ph);
        campo.setForeground(FIELD_PH);
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(ph)) {
                    campo.setText("");
                    campo.setForeground(texto_Principal);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().trim().isEmpty()) {
                    campo.setText(ph);
                    campo.setForeground(FIELD_PH);
                    marcarCampo(campo, false);
                }
            }
        });
    }

    private void initValidaciones() {
        txtPlaca.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = txtPlaca.getText().trim();
            marcarCampo(txtPlaca, !v.isEmpty() && !v.equals("Ej. VTRAIN-928")
                    && v.matches("[A-Za-z0-9\\- ]{5,}"));
        }));

        txtAnio.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = txtAnio.getText().trim();
            boolean ok = false;
            if (!v.isEmpty() && !v.equals("Ej. 2023") && v.matches("\\d{4}")) {
                try {
                    int y = Integer.parseInt(v);
                    ok = y >= 1950 && y <= LocalDate.now().getYear();
                } catch (Exception ignored) {
                }
            }
            marcarCampo(txtAnio, ok);
        }));

        cmbLineas.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                marcarCampo(cmbLineas, cmbLineas.getSelectedIndex() >= 0);
            }
        });
        cmbCapacidad.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                marcarCampo(cmbCapacidad, cmbCapacidad.getSelectedIndex() >= 0);
            }
        });
        cmbConductores.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                marcarCampo(cmbConductores, cmbConductores.getSelectedIndex() > 0);
            }
        });
    }

    private void btnAnadirVehiculoActionPerformed() {
        if (!todosValidos()) {
            new ERRORview().setVisible(true);
            return;
        }
        new Confirmacion().setVisible(true);
        // Reset campos
        txtPlaca.setText("Ej. VTRAIN-928");
        txtPlaca.setForeground(FIELD_PH);
        txtAnio.setText("Ej. 2023");
        txtAnio.setForeground(FIELD_PH);
        txtPlaca.putClientProperty("valido", false);
        txtAnio.putClientProperty("valido", false);
        marcarCampo(txtPlaca, false);
        marcarCampo(txtAnio, false);
        cmbLineas.setSelectedIndex(0);
        cmbCapacidad.setSelectedIndex(0);
        cmbConductores.setSelectedIndex(0);
    }

    private void marcarCampo(JComponent campo, boolean valido) {
        Color c = valido ? COLOR_OK : COLOR_ERROR;
        if (campo instanceof JTextField) {
            ((JTextField) campo).setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(8, c, 2),
                    new EmptyBorder(7, 12, 7, 12)
            ));
        } else if (campo instanceof JComboBox) {
            campo.setBorder(new RoundedBorder(8, c, 2));
        }
        campo.putClientProperty("valido", valido);
    }

    private boolean todosValidos() {
        for (JComponent c : new JComponent[]{
            txtPlaca, txtAnio, cmbLineas, cmbCapacidad, cmbConductores}) {
            Object v = c.getClientProperty("valido");
            if (!(v instanceof Boolean) || !(Boolean) v) {
                return false;
            }
        }
        return true;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers internos
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Borde redondeado reutilizable
     */
    private static class RoundedBorder extends AbstractBorder {

        private final int radio;
        private final Color color;
        private final float trazado;

        RoundedBorder(int radius, Color color, float stroke) {
            this.radio = radius;
            this.color = color;
            this.trazado = stroke;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(trazado));
            g2.drawRoundRect(x, y, w - 1, h - 1, radio, radio);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets((int) trazado + 1, (int) trazado + 1, (int) trazado + 1, (int) trazado + 1);
        }
    }

    private static class SimpleDocListener implements DocumentListener {

        private final Runnable accion;

        SimpleDocListener(Runnable accion) {
            this.accion = accion;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            accion.run();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            accion.run();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            accion.run();
        }
    }
}
