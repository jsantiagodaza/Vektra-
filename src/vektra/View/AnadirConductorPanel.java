package vektra.View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import vektra.Dao.ConductorDao;
import vektra.Model.Conductor;

/**
 * AnadirConductorPanel — rediseñado con estética Vektra. Swing puro, sin
 * NetBeans Form Editor.
 */
public class AnadirConductorPanel extends JPanel {

    // ── Paleta ────────────────────────────────────────────────────────────────
    private static final Color BG_PAGE = new Color(248, 250, 255);
    private static final Color TEXT_PRI = new Color(15, 23, 42);
    private static final Color TEXT_MUT = new Color(100, 116, 139);
    private static final Color TEXT_LABEL = new Color(71, 85, 105);
    private static final Color BORDER_COL = new Color(226, 232, 240);
    private static final Color FIELD_BG = new Color(249, 250, 251);
    private static final Color FIELD_PH = new Color(148, 163, 184);
    private static final Color COLOR_OK = new Color(34, 197, 94);
    private static final Color COLOR_ERR = new Color(239, 68, 68);
    private static final Color BLUE_ACC = new Color(37, 99, 235);
    private static final Color BLUE_HOV = new Color(29, 78, 216);
    private static final Color BLUE_LIGHT = new Color(239, 246, 255);
    private static final Color WARN_BG = new Color(255, 247, 237);
    private static final Color WARN_BRD = new Color(254, 215, 170);
    private static final Color WARN_FG = new Color(154, 52, 18);

    // ── Fuentes ───────────────────────────────────────────────────────────────
    private static final Font F_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font F_SUB = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font F_SECTION = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font F_LABEL = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font F_FIELD = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font F_BTN = new Font("Segoe UI", Font.BOLD, 14);

    // ── Campos ────────────────────────────────────────────────────────────────
    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtCedula;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JTextField txtLicencia;
    private JComboBox<String> cmbRutas;
    private JButton btnAnadir;

    // ─────────────────────────────────────────────────────────────────────────
    public AnadirConductorPanel() {
        initUI();
        vektra.Util.FontUtil.applyCustomFont(this);
        cargarRutas();
        initPlaceholders();
        initValidaciones();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Layout
    // ─────────────────────────────────────────────────────────────────────────
    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(BG_PAGE);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BG_PAGE);
        content.setBorder(new EmptyBorder(28, 36, 32, 36));

        content.add(crearHeader());
        content.add(Box.createVerticalStrut(14));
        content.add(crearBannerObligatorio());
        content.add(Box.createVerticalStrut(26));
        content.add(crearSeccion("Información personal", crearFilaPersonal()));
        content.add(Box.createVerticalStrut(22));
        content.add(crearSeccion("Contacto", crearFilaContacto()));
        content.add(Box.createVerticalStrut(22));
        content.add(crearSeccion("Asignación y licencia", crearFilaAsignacion()));
        content.add(Box.createVerticalStrut(32));
        content.add(crearBoton());

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

        JLabel title = new JLabel("Añadir Conductor");
        title.setFont(F_TITLE);
        title.setForeground(TEXT_PRI);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Registra un nuevo conductor en el sistema");
        sub.setFont(F_SUB);
        sub.setForeground(TEXT_MUT);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(title);
        p.add(Box.createVerticalStrut(4));
        p.add(sub);
        return p;
    }

    // ── Banner obligatorio ────────────────────────────────────────────────────
    private JPanel crearBannerObligatorio() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(WARN_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(WARN_BRD);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel icon = new JLabel("*");
        icon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        icon.setForeground(new Color(217, 119, 6));

        JLabel msg = new JLabel("Todos los campos son obligatorios");
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        msg.setForeground(WARN_FG);

        p.add(icon);
        p.add(msg);
        return p;
    }

    // ── Sección con separador ─────────────────────────────────────────────────
    private JPanel crearSeccion(String titulo, JPanel contenido) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel headerRow = new JPanel(new BorderLayout(10, 0));
        headerRow.setOpaque(false);
        headerRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        headerRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(titulo);
        lbl.setFont(F_SECTION);
        lbl.setForeground(TEXT_LABEL);

        JSeparator sep = new JSeparator() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(BORDER_COL);
                g.fillRect(0, getHeight() / 2, getWidth(), 1);
            }
        };
        headerRow.add(lbl, BorderLayout.WEST);
        headerRow.add(sep, BorderLayout.CENTER);

        contenido.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(headerRow);
        p.add(Box.createVerticalStrut(12));
        p.add(contenido);
        return p;
    }

    // ── Fila 1: Nombres + Apellidos ───────────────────────────────────────────
    private JPanel crearFilaPersonal() {
        txtNombre = nuevoTextField();
        txtApellidos = nuevoTextField();

        JPanel row = new JPanel(new GridLayout(1, 2, 10, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        row.add(grupo("Nombres", txtNombre));
        row.add(grupo("Apellidos", txtApellidos));
        return row;
    }

    // ── Fila 2: Teléfono + Correo ─────────────────────────────────────────────
    private JPanel crearFilaContacto() {
        txtTelefono = nuevoTextField();
        txtCorreo = nuevoTextField();

        JPanel row = new JPanel(new GridLayout(1, 2, 16, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 68));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        row.add(grupo("Teléfono", txtTelefono));
        row.add(grupo("Correo electrónico", txtCorreo));
        return row;
    }

    // ── Fila 3: Cédula + Licencia + Ruta ─────────────────────────────────────
    private JPanel crearFilaAsignacion() {
        txtCedula = nuevoTextField();
        txtLicencia = nuevoTextField();

        cmbRutas = new JComboBox<>();
        cmbRutas.setFont(F_FIELD);
        cmbRutas.setBackground(FIELD_BG);
        cmbRutas.setForeground(TEXT_PRI);
        cmbRutas.setBorder(new RoundedBorder(8, BORDER_COL, 1));
        cmbRutas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cmbRutas.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object v, int i, boolean sel, boolean foc) {
                JLabel l = (JLabel) super.getListCellRendererComponent(list, v, i, sel, foc);
                l.setBorder(new EmptyBorder(5, 10, 5, 10));
                l.setFont(F_FIELD);
                if (sel) {
                    l.setBackground(BLUE_LIGHT);
                    l.setForeground(BLUE_ACC);
                } else {
                    l.setBackground(Color.WHITE);
                    l.setForeground(TEXT_PRI);
                }
                return l;
            }
        });

        JPanel row = new JPanel(new GridLayout(1, 3, 16, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 68));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        row.add(grupo("Cédula", txtCedula));
        row.add(grupo("Número de licencia", txtLicencia));
        row.add(grupoCombo("Ruta asignada", cmbRutas));
        return row;
    }

    // ── Botón ─────────────────────────────────────────────────────────────────
    private JPanel crearBoton() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnAnadir = new JButton("Añadir Conductor") {
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
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? BLUE_HOV : BLUE_ACC);
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

        try {
            java.net.URL url = getClass().getResource(
                    "/vektra/View/Imagenes/PLUS (1) (1).png");
            if (url != null) {
                Image img = new ImageIcon(url).getImage()
                        .getScaledInstance(18, 18, Image.SCALE_SMOOTH);
                btnAnadir.setIcon(new ImageIcon(img));
                btnAnadir.setIconTextGap(8);
            }
        } catch (Exception ignored) {
        }

        btnAnadir.addActionListener(e -> btnAnadirActionPerformed());
        p.add(btnAnadir);
        return p;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers constructores de campo
    // ─────────────────────────────────────────────────────────────────────────
    private JTextField nuevoTextField() {
        JTextField f = new JTextField();
        f.setFont(F_FIELD);
        f.setForeground(TEXT_PRI);
        f.setBackground(FIELD_BG);
        f.setCaretColor(TEXT_PRI);
        f.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(8, BORDER_COL, 1),
                new EmptyBorder(7, 12, 7, 12)
        ));
        return f;
    }

    private JPanel grupo(String etiqueta, JTextField campo) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(F_LABEL);
        lbl.setForeground(TEXT_LABEL);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(lbl);
        p.add(Box.createVerticalStrut(5));
        p.add(campo);
        return p;
    }

    private JPanel grupoCombo(String etiqueta, JComboBox<?> combo) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(F_LABEL);
        lbl.setForeground(TEXT_LABEL);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(lbl);
        p.add(Box.createVerticalStrut(5));
        p.add(combo);
        return p;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Lógica preservada del original
    // ─────────────────────────────────────────────────────────────────────────
    private void cargarRutas() {
        try {
            vektra.Dao.RutaDao dao = new vektra.Dao.RutaDao();
            java.util.List<vektra.Model.Ruta> rutas = dao.obtenerTodasLasRutas();
            cmbRutas.removeAllItems();
            cmbRutas.addItem("Seleccionar ruta...");
            for (vektra.Model.Ruta r : rutas) {
                cmbRutas.addItem(r.formatoUI());
            }
        } catch (Exception e) {
            System.out.println("Error al cargar rutas: " + e.getMessage());
        }
    }

    private void initPlaceholders() {
        configurarPlaceholder(txtNombre, "Ej. Marco Javier");
        configurarPlaceholder(txtApellidos, "Ej. Torres Piña");
        configurarPlaceholder(txtCedula, "Ej. 12345678");
        configurarPlaceholder(txtTelefono, "Ej. +57 300 000 0000");
        configurarPlaceholder(txtCorreo, "Ej. correo@email.com");
        configurarPlaceholder(txtLicencia, "Ej. LIC-2025-9293839");
    }

    private void configurarPlaceholder(JTextField campo, String ph) {
        campo.setText(ph);
        campo.setForeground(FIELD_PH);
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(ph)) {
                    campo.setText("");
                    campo.setForeground(TEXT_PRI);
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
        txtNombre.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = txtNombre.getText().trim();
            marcarCampo(txtNombre, !v.isEmpty() && !v.equals("Ej. Marco Javier")
                    && v.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"));
        }));
        txtApellidos.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = txtApellidos.getText().trim();
            marcarCampo(txtApellidos, !v.isEmpty() && !v.equals("Ej. Torres Piña")
                    && v.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"));
        }));
        txtCedula.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = txtCedula.getText().trim();
            marcarCampo(txtCedula, !v.isEmpty() && !v.equals("Ej. 12345678")
                    && v.matches("\\d{6,12}"));
        }));
        txtTelefono.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = txtTelefono.getText().trim().replaceAll("\\s", "");
            marcarCampo(txtTelefono, !v.isEmpty() && !v.equals("Ej.+57300000000")
                    && v.matches("\\+57\\d{10}"));
        }));
        txtCorreo.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = txtCorreo.getText().trim();
            marcarCampo(txtCorreo, !v.isEmpty() && !v.equals("Ej. correo@email.com")
                    && v.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"));
        }));
        txtLicencia.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = txtLicencia.getText().trim();
            marcarCampo(txtLicencia, !v.isEmpty() && !v.equals("Ej. LIC-2025-9293839")
                    && v.matches("[A-Za-z0-9\\-]+") && v.length() >= 5);
        }));
    }

    private void btnAnadirActionPerformed() {
        if (!todosValidos()) {
            new ERRORview().setVisible(true);
            return;
        }
        if (cmbRutas.getSelectedItem() == null
                || cmbRutas.getSelectedItem().toString().equals("Seleccionar ruta...")) {
            new ERRORview().setVisible(true);
            return;
        }
        try {
            Conductor c = new Conductor();
            c.setNombre(txtNombre.getText());
            c.setApellido(txtApellidos.getText());
            c.setCedula(txtCedula.getText());
            c.setTelefono(txtTelefono.getText());
            c.setLicencia(txtLicencia.getText());
            c.setCorreo(txtCorreo.getText());
            c.setRutaAsignada(cmbRutas.getSelectedItem().toString());

            ConductorDao dao = new ConductorDao();
            if (dao.agregarConductor(c)) {
                new Confirmacion().setVisible(true);
                // Reset
                for (JTextField f : new JTextField[]{
                    txtNombre, txtApellidos, txtCedula, txtTelefono, txtCorreo, txtLicencia}) {
                    f.setText("");
                }
                cmbRutas.setSelectedIndex(0);
                initPlaceholders();
            } else {
                new ERRORview().setVisible(true);
            }
        } catch (Exception ex) {
            new ERRORview().setVisible(true);
        }
    }

    private void marcarCampo(JTextField campo, boolean valido) {
        Color c = valido ? COLOR_OK : COLOR_ERR;
        campo.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(8, c, 2),
                new EmptyBorder(7, 12, 7, 12)
        ));
        campo.putClientProperty("valido", valido);
    }

    private boolean todosValidos() {
        for (JTextField f : new JTextField[]{
            txtNombre, txtApellidos, txtCedula, txtTelefono, txtCorreo, txtLicencia}) {
            Object v = f.getClientProperty("valido");
            if (!(v instanceof Boolean b) || !b) {
                return false;
            }
        }
        return true;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Utilidades
    // ─────────────────────────────────────────────────────────────────────────
    private static class RoundedBorder extends AbstractBorder {

        private final int radius;
        private final Color color;
        private final float stroke;

        RoundedBorder(int r, Color c, float s) {
            radius = r;
            color = c;
            stroke = s;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(stroke));
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            int i = (int) stroke + 1;
            return new Insets(i, i, i, i);
        }
    }

    private static class SimpleDocListener implements DocumentListener {

        private final Runnable accion;

        SimpleDocListener(Runnable a) {
            accion = a;
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
